package com.cd.evaluation.users.commons.validation.user.address.state;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * brazil state input validation annotation
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BrazilStateValidation.class)
public @interface ValidBrazilState {
    String message() default "brazil.state.invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
