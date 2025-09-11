package com.aldob.payment.exception;

public class InvalidCardException extends RuntimeException{
    public InvalidCardException(String message){
        super(message);
    }
}
