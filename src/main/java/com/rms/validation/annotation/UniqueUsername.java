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
    String message() default "Потребителското име е вече заето. Моля изберете друго потребителско име";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
