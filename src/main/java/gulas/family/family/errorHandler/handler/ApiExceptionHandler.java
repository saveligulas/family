package gulas.family.family.errorHandler.handler;

import gulas.family.family.errorHandler.exception.ApiException;
import gulas.family.family.errorHandler.exception.UsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value={ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception,badRequest);
    }

    @ExceptionHandler(value={UsernameException.class})
    public ResponseEntity<Object> handleInvalidTokenException(UsernameException e) {

        return null;
    }
}
