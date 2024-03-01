package expensetracker.service;

import expensetracker.model.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseTrackerServiceTests {

    @Test
    public void contextLoads() {}

    @Autowired
    private ExpenseTrackerService service;

    @Test
    public void testGetExpenseById() throws ExpenseTrackerDuplicateIdException {
        // ARRANGE
        Expense onlyExpense = new Expense();
        onlyExpense.setExpenseId(1);
        onlyExpense.setExpenseDescription("Printing Papers");
        onlyExpense.setExpenseDate(LocalDate.parse("2024-01-01"));
        onlyExpense.setCost(new BigDecimal("50.00"));
        onlyExpense.setVendor("OfficeMart");
        onlyExpense.setNotes("White printing papers");
        onlyExpense.setCategoryId(1);

        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();
        expenseTrackerDao.addNewExpense(onlyExpense);

        // ACT
        Expense retrievedExpense = expenseTrackerDao.getExpenseById(1);

        // ASSERT
        assertNotNull(retrievedExpense, "Should not be null.");
        assertEquals(onlyExpense, retrievedExpense, "The retrieved expense should match " +
                "the expected expense.");
    }

    @Test
    public void testGetExpenseByIdNotFound() {
        // ARRANGE
        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();
        // ACT
        Expense retrievedExpense = expenseTrackerDao.getExpenseById(2);
        // ASSERT
        assertNull(retrievedExpense, "Should be null for a non-existent ID.");
    }


    @Test
    public void testAddNewExpense() {
        // ARRANGE
        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();
        Expense newExpense = new Expense();
        newExpense.setExpenseId(2);
        newExpense.setExpenseDescription("New Expense");
        newExpense.setExpenseDate(LocalDate.parse("2024-02-01"));
        newExpense.setCost(new BigDecimal("30.00"));
        newExpense.setVendor("NewVendor");
        newExpense.setNotes("Sample notes");
        newExpense.setCategoryId(2);

        // ACT
        try {
            Expense addedExpense = expenseTrackerDao.addNewExpense(newExpense);

            // ASSERT
            assertNotNull(addedExpense, "Added expense should not be null.");
            assertEquals(newExpense, addedExpense, "Added expense should match the expected " +
                    "expense.");

            // Check if the expense list contains the added expense
            assertTrue(expenseTrackerDao.getAllExpenses().contains(newExpense),
                    "Expense list should contain the added expense.");

        } catch (ExpenseTrackerDuplicateIdException e) {
            fail("Unexpected ExpenseTrackerDuplicateIdException: " + e.getMessage());
        }
    }

    @Test
    public void testAddNewExpenseDuplicateId() throws ExpenseTrackerDuplicateIdException {
        // ARRANGE
        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();
        Expense existingExpense = new Expense();
        existingExpense.setExpenseId(1);  // Existing expense has ID 1

        // ACT & ASSERT
        expenseTrackerDao.addNewExpense(existingExpense);
        assertTrue(expenseTrackerDao.getAllExpenses().contains(existingExpense), "Expense " +
                "with Id " + existingExpense.getExpenseId() + " already exists!");
    }

    @Test
    public void testRemoveExpenseById() {
        // ARRANGE
        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();
        Expense existingExpense = new Expense();
        existingExpense.setExpenseId(1);  // Existing expense has ID 1

        // ACT
        expenseTrackerDao.removeExpenseById(existingExpense.getExpenseId());

        // ASSERT
        assertFalse(expenseTrackerDao.getAllExpenses().contains(existingExpense),
                "The list should not contain an expense with Id " + existingExpense.getExpenseId());
    }


    @Test
    public void testRemoveExpenseByIdNotFound() {
        // ARRANGE
        ExpenseTrackerDaoStubImpl expenseTrackerDao = new ExpenseTrackerDaoStubImpl();

        // ACT
        expenseTrackerDao.removeExpenseById(2); // Assuming ID 2 is not present

        // ASSERT
        List<Expense> allExpenses = expenseTrackerDao.getAllExpenses();
        assertEquals(0, allExpenses.size(), "Expense list should be empty.");
    }


    @Test
    public void testCreateExpenseInvalidData() {
        // ARRANGE
        Expense invalidExpense = new Expense();
        invalidExpense.setExpenseDescription("");
        invalidExpense.setExpenseDate(LocalDate.parse("2024-01-01"));
        invalidExpense.setCost(new BigDecimal("50.00"));

        // ACT & ASSERT
        assertThrows(InvalidDataInputException.class, () -> service.validateExpenseData(invalidExpense),
                "Expected InvalidDataInputException was not thrown");
    }
}
