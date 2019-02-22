package pl.somehost.contactmanager.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    String PASSWORD_MESSAGE = "Invalid password: password should not be empty and it should contains min 7 characters: one digit, " +
            "a lower case letter, an upper case letter" +
            ", no whitespace allowed.";

    String message() default PASSWORD_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
