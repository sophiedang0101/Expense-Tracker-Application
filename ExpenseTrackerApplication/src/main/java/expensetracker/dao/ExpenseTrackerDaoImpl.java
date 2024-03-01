package expensetracker.dao;

import expensetracker.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("database")
public class ExpenseTrackerDaoImpl implements ExpenseTrackerDao{
    private final JdbcTemplate jdbc;
    @Autowired
    public ExpenseTrackerDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //Retrieve data of all expenses from the expense table.
    @Override
    public List<Expense> getAllExpenses() {
        final String GET_ALL_EXPENSES = "SELECT * FROM expense";
        return jdbc.query(GET_ALL_EXPENSES, new ExpenseMapper());
    }

    //Retrieve data for a particular expense from the expense table.
    @Override
    public Expense getExpenseById(int expenseId) {
        try {
            final String GET_EXPENSE_BY_ID = "SELECT * FROM expense WHERE expenseId = ?";
            return jdbc.queryForObject(GET_EXPENSE_BY_ID, new ExpenseMapper(), expenseId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    //Add a new expense into the expense table.
    @Transactional
    @Override
    public Expense addNewExpense(Expense expense) {
        final String ADD_NEW_EXPENSE = "INSERT INTO expense (expenseDesc, expenseDate, cost, vendor, categoryId, notes) " +
                "VALUES( ?, ?, ?, ?, ?, ?)";
        jdbc.update(ADD_NEW_EXPENSE,
                expense.getExpenseDescription(),
                expense.getExpenseDate(),
                expense.getCost(),
                expense.getVendor(),
                expense.getCategoryId(),
                expense.getNotes()
        );

        int newIdNumber = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expense.setExpenseId(newIdNumber);
        return expense;
    }

    //Edit a particular expense in the expense table.
    @Override
    public void editExpense(Expense expense) {
        final String EDIT_EXPENSE = "UPDATE expense SET expenseDesc = ?, expenseDate = ?, cost = ?," +
                " vendor = ?, categoryId = ?, notes = ? WHERE expenseId = ?";
        jdbc.update(EDIT_EXPENSE,
                expense.getExpenseDescription(),
                expense.getExpenseDate(),
                expense.getCost(),
                expense.getVendor(),
                expense.getCategoryId(),
                expense.getNotes(),
                expense.getExpenseId()
        );
    }

    //Delete a particular expense from the expense table using its id number.
    @Transactional
    @Override
    public void removeExpenseById(int expenseId) {
        String DELETE_EXPENSE_BY_ID = "DELETE FROM expense WHERE expenseId = ?";
        jdbc.update(DELETE_EXPENSE_BY_ID, expenseId);
    }

    //Mapper class containing data retrieved from the db stored as an object.
    public static final class ExpenseMapper implements RowMapper<Expense>{
        @Override
        public Expense mapRow(ResultSet rs, int index) throws SQLException{
            Expense expense = new Expense();
            expense.setExpenseId(rs.getInt("expenseId"));
            expense.setExpenseDescription(rs.getString("expenseDesc"));
            expense.setCost(rs.getBigDecimal("cost"));
            expense.setExpenseDate(rs.getDate("expenseDate").toLocalDate());
            expense.setVendor(rs.getString("vendor"));
            expense.setCategoryId(rs.getInt("categoryId"));
            expense.setNotes(rs.getString("notes"));

            return expense;
        }
    }
}
