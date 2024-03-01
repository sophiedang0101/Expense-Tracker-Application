package expensetracker.service;

public class InvalidDataInputException extends Exception{
    public InvalidDataInputException(String message) {
        super(message);
    }
}
