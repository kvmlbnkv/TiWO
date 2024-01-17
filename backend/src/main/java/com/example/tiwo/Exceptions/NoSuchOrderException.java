package com.example.tiwo.Exceptions;

public class NoSuchOrderException extends RuntimeException{

    public NoSuchOrderException(){
        super("Nie ma takiego zamówienia");
    }
}
