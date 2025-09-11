package com.aldob.payment.enums;

public enum Divisas {
    MXN,
    USD,
    EUR;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        String upperValue = value.toUpperCase();
        for (Divisas divisa : values()) {
            if (divisa.name().equals(upperValue)) {
                return true;
            }
        }
        return false;
    }
}