package com.cd.evaluation.users.commons.validation.user.address.country;

import com.cd.evaluation.users.model.enums.address.CountriesEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CountryValidation implements ConstraintValidator<ValidCountry, String> {
    @Override
    public void initialize(ValidCountry constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(CountriesEnum.values()).anyMatch(country -> country.toString().equals(s));
    }
}
