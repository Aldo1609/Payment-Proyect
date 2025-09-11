package com.aldob.payment.validation.anotations;

import com.aldob.payment.validation.validator.ValidCardDetailsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCardDetailsValidator.class)
@Target({ElementType.TYPE}) // A nivel de clase, no de campo
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardDetails {
    String message() default "Datos de tarjeta no válidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}