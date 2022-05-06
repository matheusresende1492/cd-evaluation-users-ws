package com.cd.evaluation.users.commons.validation.user.roles;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Role input validation annotation
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidation.class)
public @interface ValidRole {
    String message() default "user.role.not.valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
