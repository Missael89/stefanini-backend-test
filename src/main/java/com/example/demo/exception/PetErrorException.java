package com.example.demo.exception;

public class PetErrorException extends RuntimeException{

    public PetErrorException(String message) {
        super(message);
    }
}
