package io.datajek.moneytransferrest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class BankUserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BankUserErrorResponse> playerNotFoundHandler(BankUserNotFoundException ex, HttpServletRequest req){

        BankUserErrorResponse error = new BankUserErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                req.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BankUserErrorResponse> genericHandler (Exception ex, HttpServletRequest req){
        BankUserErrorResponse error = new BankUserErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);

    }

}
