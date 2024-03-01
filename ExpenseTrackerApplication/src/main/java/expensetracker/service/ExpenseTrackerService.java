package expensetracker.service;

import expensetracker.dao.ExpenseTrackerDao;
import expensetracker.model.Expense;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class ExpenseTrackerService {
    private final ExpenseTrackerDao expenseTrackerDao;

    @Autowired
    public ExpenseTrackerService(ExpenseTrackerDao expenseTrackerDao) {
        this.expenseTrackerDao = expenseTrackerDao;
    }

    //Retrieve all expenses from the expense table and return each object in a list.
    public List<Expense> getAllExpenses() {
        return expenseTrackerDao.getAllExpenses();
    }

    public Expense addNewExpense(Expense expense) throws ExpenseTrackerDuplicateIdException,
            InvalidDataInputException {

        //First check if there's already an expense associated with the given id.
        //If yes, we're all done here. Else, throw an ExpenseTrackerDuplicateIdException
        if (expenseTrackerDao.getExpenseById(expense.getExpenseId()) != null) {
            throw new ExpenseTrackerDuplicateIdException("ERROR: Could not create Expense. Expense Id " +
                    expense.getExpenseId() + " already exists.");
        }

        //Now validate all the fields in the given Expense object.
        //This method will throw InvalidDataInputException if any validation rules are violated.
        validateExpenseData(expense);

        //Passed all business rules checks so a new expense object is created and added to the expense table.
        return expenseTrackerDao.addNewExpense(expense);
    }

    // Retrieve a particular expense from the expense table using an Id number.
    public Expense getExpenseById(int expenseId) throws InvalidExpenseException {
        Expense expense = expenseTrackerDao.getExpenseById(expenseId);
        if (expense != null) {
            return expense;
        } else {
            throw new InvalidExpenseException("ERROR: Could not find expense with given Id " + expenseId);
        }
    }


    //Edit a certain expense by using its id number to find it.
    //If the id isn't in the expense table, an invalid expense exception will be thrown.
    public Expense editExpense(int id, Expense expense) throws InvalidExpenseException {
        if (id == expense.getExpenseId()) {
            expenseTrackerDao.editExpense(expense);
        } else {
            throw new InvalidExpenseException("IDs do not match, expense was not updated");
        }
        return expense;
    }

    //Remove an expense by using its id number.
    //Method will throw an invalid expense exception if the expense isn't in the expense table.
    public boolean removeExpenseById(int expenseId) throws InvalidExpenseException {
        if (expenseTrackerDao.getExpenseById(expenseId) != null) {
            expenseTrackerDao.removeExpenseById(expenseId);
            return true;
        } else {
            throw new InvalidExpenseException("ERROR: Deletion Unsuccessful. Could not find expense with Id: " + expenseId);
        }
    }

    //Method to validate data for expense fields.
    //Ensures that all fields are filled and not null.
    public void validateExpenseData(Expense expense) throws InvalidDataInputException {
        if (expense.getExpenseDescription() == null ||
                expense.getExpenseDate() == null || expense.getCost() == null ||
                expense.getVendor() == null) {
            throw new InvalidDataInputException("ERROR: All Fields (Expense Description, Expense Date," +
                    "Expense Cost, and Vendor) are required.");

        }
    }

    //Method to export all current data in the expense table to an Excel sheet.
    public void exportToExcel(List<Expense> expenses, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=expenses.xlsx");

        try (XSSFWorkbook excelWorkBook = new XSSFWorkbook()) {
            XSSFSheet sheet = excelWorkBook.createSheet("Expenses");

            // Create header row.
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Expense_ID", "Expense_Description", "Expense_Cost", "Vendor", "Notes", "Expense_Date"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows.
            int rowNum = 1;
            for (Expense expense : expenses) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(expense.getExpenseId());
                row.createCell(1).setCellValue(expense.getExpenseDescription());
                row.createCell(2).setCellValue(expense.getCost().doubleValue());
                row.createCell(3).setCellValue(expense.getVendor());
                row.createCell(4).setCellValue(expense.getNotes());
                row.createCell(5).setCellValue(expense.getExpenseDate());
            }

            // Write to response output stream.
            try (OutputStream outputStream = response.getOutputStream()) {
                excelWorkBook.write(outputStream);

            } catch (Exception e) {
                e.printStackTrace(); // Log or handle the exception appropriately.
            }
        }
    }
}
