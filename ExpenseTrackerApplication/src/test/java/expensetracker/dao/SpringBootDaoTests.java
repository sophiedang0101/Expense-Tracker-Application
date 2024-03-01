package expensetracker.dao;

import expensetracker.model.Expense;
import expensetracker.service.ExpenseTrackerDuplicateIdException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class SpringBootDaoTests {
    @Test
    public void contextLoads(){}

    @Autowired
    private ExpenseTrackerDao expenseDao;
    //private JdbcTemplate jdbcTemplate;


    @Test
    void addNewExpense() throws ExpenseTrackerDuplicateIdException {
        Expense expense = new Expense();
        expense.setExpenseDescription("Business loan payment");
        expense.setCost(new BigDecimal("1500.00"));
        expense.setExpenseDate(LocalDate.parse("2023-02-03"));
        expense.setVendor("Truist Bank");
        expense.setCategoryId(4);
        expense.setNotes("N/A");
        Expense addedNow = expenseDao.addNewExpense(expense);
        Expense fromDb = expenseDao.getExpenseById(addedNow.getExpenseId());
        assertEquals(addedNow,fromDb);
    }


    @Test
    void getAllExpenses() throws ExpenseTrackerDuplicateIdException {
        Expense expense = new Expense();
        expense.setExpenseDescription("Gadgets");
        expense.setCost(new BigDecimal("1500.00"));
        expense.setExpenseDate(LocalDate.parse("2023-12-01"));
        expense.setVendor("Best Buy");
        expense.setCategoryId(1);
        expense.setNotes("N/A");
        Expense testExpense1 = expenseDao.addNewExpense(expense);

        Expense expense2 = new Expense();
        expense2.setExpenseDescription("Furniture Purchase");
        expense2.setCost(new BigDecimal("2500.00"));
        expense2.setExpenseDate(LocalDate.parse("2023-02-03"));
        expense2.setVendor("RK Furniture");
        expense2.setCategoryId(1);
        expense2.setNotes("Old one damaged");
        Expense testExpense3 = expenseDao.addNewExpense(expense2);

        List<Expense> expenses = expenseDao.getAllExpenses();

        // assertEquals(2,expenses.size());
        assertTrue(expenses.contains(testExpense1));
        assertTrue(expenses.contains(testExpense3));

    }

    @Test
    void getExpenseById() throws ExpenseTrackerDuplicateIdException {
        Expense expense = new Expense();
        expense.setExpenseDescription("Test item");
        expense.setCost(new BigDecimal("1500.00"));
        expense.setExpenseDate(LocalDate.parse("2023-12-01"));
        expense.setVendor("Best Buy");
        expense.setCategoryId(6);
        expense.setNotes("N/A");
        Expense testExpense = expenseDao.addNewExpense(expense);

        Expense fromDb = expenseDao.getExpenseById(testExpense.getExpenseId());

        assertEquals(fromDb,testExpense);

    }


    @Test
    void editExpense() throws ExpenseTrackerDuplicateIdException {
        Expense expense = new Expense();
        expense.setExpenseDescription("Inventory");
        expense.setCost(new BigDecimal("5500.00"));
        expense.setExpenseDate(LocalDate.parse("2023-02-03"));
        expense.setVendor("KK inc.");
        expense.setCategoryId(1);
        expense.setNotes("Restock");
        Expense testExpense = expenseDao.addNewExpense(expense);

        Expense fromDb = expenseDao.getExpenseById(testExpense.getExpenseId());

        assertEquals(testExpense,fromDb);

        expense.setExpenseDescription("New Inventory");
        expenseDao.editExpense(expense);
        fromDb = expenseDao.getExpenseById(expense.getExpenseId());
        assertEquals(expense,fromDb);
    }

    @Test
    void removeExpenseById() throws ExpenseTrackerDuplicateIdException {
        Expense expense = new Expense();
        expense.setExpenseDescription("Remove test");
        expense.setCost(new BigDecimal("100.00"));
        expense.setExpenseDate(LocalDate.parse("2023-04-03"));
        expense.setVendor("Remove vendor");
        expense.setCategoryId(1);
        expense.setNotes("Removing");
        expenseDao.addNewExpense(expense);

        expenseDao.removeExpenseById(expense.getExpenseId());

        Expense fromExpense = expenseDao.getExpenseById(expense.getExpenseId());

        assertNull(fromExpense);
    }
}
