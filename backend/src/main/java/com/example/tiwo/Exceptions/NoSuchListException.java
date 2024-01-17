package com.example.tiwo.Exceptions;

public class NoSuchListException extends RuntimeException{

    public NoSuchListException(){
        super("Nie ma takiej listy");
    }
}
