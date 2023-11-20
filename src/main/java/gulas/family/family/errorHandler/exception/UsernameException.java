package gulas.family.family.errorHandler.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class UsernameException extends RuntimeException {
    public UsernameException(String message) {
        super(message);
    }
    public UsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
