package com.rms.validation.annotation;

import com.rms.validation.UniqueUsernameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username is already taken! Please use another Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
