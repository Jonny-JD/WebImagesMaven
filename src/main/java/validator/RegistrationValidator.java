package validator;


import dto.UserDTO;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RegistrationValidator implements Validator<Optional<UserDTO>> {

    private static final RegistrationValidator INSTANCE = new RegistrationValidator();
    @Override
    public ValidationResult isValid(Optional<UserDTO> object) {
        var validationResult = new ValidationResult();
        if (object.isPresent()) {
            validationResult.addError(ValidationError.of("page.reg.error.user-exists", "user already exists"));
        }
        return validationResult;
    }

    public static RegistrationValidator getInstance() {
        return INSTANCE;
    }
}
