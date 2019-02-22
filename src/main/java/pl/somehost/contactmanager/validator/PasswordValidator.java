package pl.somehost.contactmanager.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements
        ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String passwordField,
                           ConstraintValidatorContext cxt) {
        return passwordField != null && passwordField.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,}");
    }
}

/*Password pattern Explanations:
    (?=.*[0-9]) a digit must occur at least once
    (?=.*[a-z]) a lower case letter must occur at least once
    (?=.*[A-Z]) an upper case letter must occur at least once
    (?=\\S+$) no whitespace allowed in the entire string
    .{7,} at least 7 characters
*/