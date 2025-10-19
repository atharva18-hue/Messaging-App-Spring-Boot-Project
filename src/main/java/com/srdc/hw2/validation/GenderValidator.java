package com.srdc.hw2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    //String gender is the object to be validated
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        gender = gender.toLowerCase();
        if (gender.equals("male") || gender.equals("female")) {
            return true; //means object is validated
        }
        return false;  //means object is not validated
    }
}
