package exception;

import lombok.Getter;
import validator.ValidationError;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    public final List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }
}
