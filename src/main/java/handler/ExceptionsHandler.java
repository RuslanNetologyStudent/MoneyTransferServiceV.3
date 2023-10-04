package handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import api.error.InvalidCardDataException;
import api.error.InvalidConfirmationDataException;
import api.error.Error;

import java.util.UUID;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({InvalidConfirmationDataException.class, InvalidCardDataException.class})
    public ResponseEntity<Error> handleInvalidDataException(Exception ex) {
        return sendError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleUnregisteredException(Exception ex) {
        return sendError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Error> sendError(Exception ex, HttpStatus httpStatus) {

        String id = UUID.randomUUID().toString();
        String errorMsg = ex.getMessage();

        return new ResponseEntity<>(new Error(id, errorMsg), httpStatus);
    }
}