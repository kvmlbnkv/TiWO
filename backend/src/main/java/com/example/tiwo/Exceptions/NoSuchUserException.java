package com.example.tiwo.Exceptions;

public class NoSuchUserException extends RuntimeException{

    public NoSuchUserException(){
        super("Nie ma takiego użytkownika");
    }
}
