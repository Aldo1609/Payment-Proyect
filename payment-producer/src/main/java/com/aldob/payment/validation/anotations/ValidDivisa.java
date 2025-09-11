package com.aldob.payment.validation.anotations;

import com.aldob.payment.validation.validator.DivisasValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DivisasValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDivisa {
    String message() default "Divisa no válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
