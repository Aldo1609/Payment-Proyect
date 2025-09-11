package com.aldob.payment.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CardTypeDetector {

    private static final List<CardPattern> CARD_PATTERNS = Arrays.asList(
            new CardPattern("VISA", "^4[0-9]{12}(?:[0-9]{3})?$"),
            new CardPattern("MASTERCARD", "^5[1-5][0-9]{14}$"),
            new CardPattern("AMEX", "^3[47][0-9]{13}$")
    );

    public static String detectCardType(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return "unknown";
        }

        String cleanNumber = cardNumber.replaceAll("[\\s-]+", "");

        if (cleanNumber.length() < 13 || cleanNumber.length() > 19) {
            return "unknown";
        }

        for (CardPattern pattern : CARD_PATTERNS) {
            if (Pattern.matches(pattern.regex, cleanNumber)) {
                return pattern.cardType;
            }
        }

        return detectByBIN(cleanNumber);
    }

    private static String detectByBIN(String cardNumber) {
        if (cardNumber.startsWith("4")) return "visa";
        if (cardNumber.startsWith("5")) return "mastercard";
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) return "amex";
        if (cardNumber.startsWith("6")) return "discover";
        return "unknown";
    }

    public static boolean isCardTypeSupported(String cardType) {
        List<String> supportedCards = Arrays.asList("visa", "mastercard", "amex");
        return supportedCards.contains(cardType.toLowerCase());
    }

    private static class CardPattern {
        String cardType;
        String regex;

        CardPattern(String cardType, String regex) {
            this.cardType = cardType;
            this.regex = regex;
        }
    }
}