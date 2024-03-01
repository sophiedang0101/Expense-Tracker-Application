package expensetracker.service;

public class InvalidExpenseException extends Exception{
    public InvalidExpenseException(String message) {
        super(message);
    }
}
