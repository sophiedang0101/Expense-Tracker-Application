package expensetracker.service;

public class ExpenseTrackerDuplicateIdException extends Exception {
    public ExpenseTrackerDuplicateIdException(String message) {
        super(message);
    }

    public ExpenseTrackerDuplicateIdException(String message, Throwable cause) {

    }
}
