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

    //Validating if the brazil state is present in the permitted list
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(BrazilStateEnum.values()).anyMatch(brazilStateEnum -> brazilStateEnum.toString().equals(s));
    }
}
