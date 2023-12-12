package io.datajek.moneytransferrest.handler;

import io.datajek.moneytransferrest.exception.UserErrorResponse;
import io.datajek.moneytransferrest.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> playerNotFoundHandler(UserNotFoundException ex, HttpServletRequest req){

        UserErrorResponse error = new UserErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                req.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> genericHandler (Exception ex, HttpServletRequest req){
        UserErrorResponse error = new UserErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);

    }

}
