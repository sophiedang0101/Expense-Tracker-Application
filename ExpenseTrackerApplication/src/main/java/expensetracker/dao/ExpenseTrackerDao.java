package expensetracker.dao;
import expensetracker.model.Expense;
import expensetracker.service.ExpenseTrackerDuplicateIdException;

import java.util.List;

public interface ExpenseTrackerDao {
    List<Expense> getAllExpenses();
    Expense getExpenseById(int expenseId);
    Expense addNewExpense(Expense expense) throws ExpenseTrackerDuplicateIdException;
    void editExpense(Expense expense);
    void removeExpenseById(int expenseId);

}

