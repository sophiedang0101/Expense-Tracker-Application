package expensetracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@RestController
public class ExpenseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_MESSAGE = "Unable to save the expense, please make sure all input fields contain valid data." +
            "and try again.";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSQLException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request){
        Error error = new Error();
        error.setMessage(ERROR_MESSAGE);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
