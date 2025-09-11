package com.aldob.payment.validation.validator;

import com.aldob.payment.enums.Divisas;
import com.aldob.payment.validation.anotations.ValidDivisa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DivisasValidator implements ConstraintValidator<ValidDivisa, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Divisas.isValid(value);
    }

}
