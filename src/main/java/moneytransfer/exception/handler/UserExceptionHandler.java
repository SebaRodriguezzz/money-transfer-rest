package moneytransfer.exception.handler;

import moneytransfer.exception.authentication.InvalidLoginException;
import moneytransfer.exception.transaction.SameAccountTransactionException;
import moneytransfer.exception.transaction.TransactionFailedException;
import moneytransfer.exception.user.InsufficientFundsException;
import moneytransfer.exception.user.UserErrorResponse;
import moneytransfer.exception.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> insufficientFundsHandler(InsufficientFundsException ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> invalidLoginHandler(InvalidLoginException ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> sameAccountTransferHandler(SameAccountTransactionException ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> TransactionFailed(TransactionFailedException ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> userNotFoundHandler(UserNotFoundException ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> genericHandler (Exception ex, HttpServletRequest req){
        return buildErrorResponse(ex, req, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<UserErrorResponse> buildErrorResponse(Exception ex, HttpServletRequest req, HttpStatus status) {
        UserErrorResponse error = new UserErrorResponse(
                ZonedDateTime.now(),
                status.value(),
                req.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(error, status);
    }
}
