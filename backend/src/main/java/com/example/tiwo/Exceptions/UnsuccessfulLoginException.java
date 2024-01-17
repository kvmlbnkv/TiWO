package com.example.tiwo.Exceptions;

public class UnsuccessfulLoginException extends RuntimeException{

    public UnsuccessfulLoginException(){
        super("Niepoprawne dane logowania");
    }

}
