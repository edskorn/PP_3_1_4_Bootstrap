package ru.kata.spring.boot_security.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqLoginValidator.class)
public @interface UniqLogin {
    String message() default "Login must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
