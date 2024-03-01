package expensetracker.controller;

import expensetracker.model.Expense;
import expensetracker.service.ExpenseTrackerDuplicateIdException;
import expensetracker.service.ExpenseTrackerService;
import expensetracker.service.InvalidDataInputException;
import expensetracker.service.InvalidExpenseException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExpenseTrackerController {
    @Autowired
    private final ExpenseTrackerService expenseService;

    public ExpenseTrackerController(ExpenseTrackerService expenseService) {
        this.expenseService = expenseService;
    }


    //Get an expense using an id.
    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable int expenseId) {
        try {
            Expense expense = expenseService.getExpenseById(expenseId);
            if (expense == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(expense);

        } catch (Exception e) {
            // Log the exception details for debugging purposes.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Retrieve all expenses.
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    //Create a new expense.
    @PostMapping("/create")
    public ResponseEntity<String> createNewExpense(@RequestBody Expense expense) {
        try {

            Expense createdExpense = expenseService.addNewExpense(expense);
            String resultMsg = "New expense was successfully created with ID: " + createdExpense.getExpenseId();
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMsg);

        } catch (ExpenseTrackerDuplicateIdException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Duplicate ID found, expense was not created.");

        } catch (InvalidDataInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid data input.");
        }
    }

    //Update an expense.
    @PutMapping("/edit/{expenseId}")
    public ResponseEntity<String> editExpense(@PathVariable int expenseId, @RequestBody Expense updatedExpense) {
        try {
            Expense existingExpense = expenseService.getExpenseById(expenseId);
            if (existingExpense == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found with ID: " + expenseId);
            }

            // Update the existing expense with the data from the updated expense.
            existingExpense.setExpenseDescription(updatedExpense.getExpenseDescription());
            existingExpense.setCost(updatedExpense.getCost());
            existingExpense.setVendor(updatedExpense.getVendor());
            existingExpense.setNotes(updatedExpense.getNotes());
            existingExpense.setExpenseDate(updatedExpense.getExpenseDate());

            // Save the updated expense.
            Expense editedExpense = expenseService.editExpense(expenseId, existingExpense);
            String resultMessage = "Expense with ID " + expenseId + " was successfully edited!";
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);

        } catch (InvalidExpenseException e) {
            throw new RuntimeException(e);
        }
    }

    //Remove an expense using an id.
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable int expenseId) {
        try {
            boolean isDeleted = expenseService.removeExpenseById(expenseId);

            if (isDeleted) {
                String resultMessage = "Expense with ID " + expenseId + " was successfully deleted";
                return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found with ID: " + expenseId);
            }

        } catch (Exception e) {
            // Log the exception details for debugging purposes.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting expense");
        }
    }

    //Export data from expense table to an Excel sheet.
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        List<Expense> listOfExpenses = expenseService.getAllExpenses();
        expenseService.exportToExcel(listOfExpenses, response);
    }
}
