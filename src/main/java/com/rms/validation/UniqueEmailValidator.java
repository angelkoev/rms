package com.rms.validation;

import com.rms.service.Impl.UserServiceImpl;
import com.rms.validation.annotation.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserServiceImpl userServiceImpl;

    public UniqueEmailValidator(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userServiceImpl.findUserByEmail(value) == null;
    }
}