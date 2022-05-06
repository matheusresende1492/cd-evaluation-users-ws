package com.cd.evaluation.users.commons.validation.user.roles;

import com.cd.evaluation.users.model.enums.roles.RoleEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RoleValidation implements ConstraintValidator<ValidRole, String> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    //Validating if the role is present in the permitted list
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(RoleEnum.values()).anyMatch(roleEnum -> roleEnum.toString().equals(s));
    }
}
