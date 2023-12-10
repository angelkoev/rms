package com.rms.validation;

import com.rms.service.Impl.UserServiceImpl;
import com.rms.validation.annotation.UniqueUsername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserServiceImpl userServiceImpl;

    public UniqueUsernameValidator(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userServiceImpl.findUserByUsername(value) == null;
    }
}