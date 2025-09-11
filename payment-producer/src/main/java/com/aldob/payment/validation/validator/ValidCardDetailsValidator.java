package com.aldob.payment.validation.validator;

import com.aldob.payment.dto.PaymentDetailDTO;
import com.aldob.payment.validation.anotations.ValidCardDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCardDetailsValidator implements ConstraintValidator<ValidCardDetails, PaymentDetailDTO> {

    private static final String CARD_REGEX =
            "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])" +
                    "[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$";

    @Override
    public boolean isValid(PaymentDetailDTO paymentDetail, ConstraintValidatorContext context) {
        if (paymentDetail == null) {
            return false;
        }

        String cardNumber = paymentDetail.getNumeroTarjeta();
        String cvv = paymentDetail.getCvv();

        if (cardNumber == null || cvv == null) {
            return false;
        }

        String cleanNumber = cardNumber.replaceAll("[\\s-]+", "");

        if (!cleanNumber.matches(CARD_REGEX)) {
            addConstraintViolation(context, "Número de tarjeta no válido", "numeroTarjeta");
            return false;
        }

        if (!isValidLuhn(cleanNumber)) {
            addConstraintViolation(context, "Número de tarjeta no válido según algoritmo de Luhn", "numeroTarjeta");
            return false;
        }

        if (!isValidCvvForCardType(cleanNumber, cvv)) {
            String expectedDigits = isAmericanExpress(cleanNumber) ? "4" : "3";
            addConstraintViolation(context, "CVV debe tener " + expectedDigits + " dígitos para este tipo de tarjeta", "cvv");
            return false;
        }

        return true;
    }

    private boolean isValidCvvForCardType(String cardNumber, String cvv) {
        if (isAmericanExpress(cardNumber)) {
            return cvv.matches("^[0-9]{4}$");
        } else {
            return cvv.matches("^[0-9]{3}$");
        }
    }

    private boolean isAmericanExpress(String cardNumber) {
        return cardNumber.startsWith("34") || cardNumber.startsWith("37");
    }

    private boolean isValidLuhn(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message, String propertyNode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyNode)
                .addConstraintViolation();
    }
}