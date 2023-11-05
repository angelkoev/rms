package com.rms.validation.annotation;

import com.rms.validation.UniqueEmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email адреса е вече зает. Моля изберете друг Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
