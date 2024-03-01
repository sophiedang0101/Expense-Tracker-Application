package expensetracker.service;

import expensetracker.dao.ExpenseTrackerDao;
import expensetracker.model.Expense;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExpenseTrackerDaoStubImpl implements ExpenseTrackerDao {
    private final List<Expense> expenseList = new ArrayList<>();
    private final Expense onlyExpense;

    public ExpenseTrackerDaoStubImpl() {
        // Initialize properties of onlyExpense
        onlyExpense = new Expense();
        onlyExpense.setExpenseId(1);
        onlyExpense.setExpenseDescription("Printing Papers");
        onlyExpense.setExpenseDate(LocalDate.parse("2024-01-01"));
        onlyExpense.setCost(new BigDecimal("50.00"));
        onlyExpense.setVendor("OfficeMart");
        onlyExpense.setNotes("White printing papers");
        onlyExpense.setCategoryId(1);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseList;
    }

    @Override
    public Expense getExpenseById(int expenseId) {
        if (expenseId == onlyExpense.getExpenseId()) {
            return onlyExpense;
        }
        return null;
    }

    @Override
    public Expense addNewExpense(Expense expense) throws ExpenseTrackerDuplicateIdException {
        int expenseId = expense.getExpenseId();
        for (Expense e : expenseList) {
            if (e.getExpenseId() == expenseId) {
                throw new ExpenseTrackerDuplicateIdException("Expense Id " + expenseId + " already exists");
            }
        }
        expenseList.add(expense);
        return expense;
    }


    @Override
    public void editExpense(Expense expense) {
        if (expense.getExpenseId() == onlyExpense.getExpenseId()) {
            System.out.println("Expenses edited.");
        } else {
            System.out.println("Id NOT FOUND.");
        }
    }

    @Override
    public void removeExpenseById(int expenseId) {
        Iterator<Expense> expenseIterator = expenseList.iterator();
        while (expenseIterator.hasNext()) {
            Expense expense = expenseIterator.next();
            if (expense.getExpenseId() == expenseId) {
                expenseIterator.remove();
                System.out.println("Expense Deleted.");
                return;
            }
        }
        System.out.println("Id NOT FOUND.");
    }
}
