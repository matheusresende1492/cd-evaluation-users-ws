package com.cd.evaluation.users.commons.validation.user.address.country;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * country input validation annotation
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidation.class)
public @interface ValidCountry {
    String message() default "country.invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
