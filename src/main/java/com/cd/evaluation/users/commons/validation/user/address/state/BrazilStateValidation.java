package com.cd.evaluation.users.commons.validation.user.address.state;

import com.cd.evaluation.users.model.enums.address.BrazilStateEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BrazilStateValidation implements ConstraintValidator<ValidBrazilState, String> {
    @Override
    public void initialize(ValidBrazilState constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(BrazilStateEnum.values()).anyMatch(brazilStateEnum -> brazilStateEnum.toString().equals(s));
    }
}
