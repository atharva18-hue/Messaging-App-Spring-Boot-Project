package com.srdc.hw2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {

    public String value() default "";

    //define default error message
    public String message() default "Invalid gender type: gender is either male or female";

    //define default groups, ---> groups can group related constraints
    public Class<?>[] groups() default {};

    //define default payloads, ---> payloads provide custom details about validation failure
    public Class<? extends Payload>[] payload() default {};

}
