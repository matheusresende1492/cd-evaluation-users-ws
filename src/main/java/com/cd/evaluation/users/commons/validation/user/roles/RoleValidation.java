package com.cd.evaluation.users.commons.validation.user.roles;

import com.cd.evaluation.users.model.enums.roles.RoleEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RoleValidation implements ConstraintValidator<ValidateRole, String> {

    @Override
    public void initialize(ValidateRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(RoleEnum.values()).anyMatch(roleEnum -> roleEnum.toString().equals(s));
    }
}
