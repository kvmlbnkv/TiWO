package com.example.tiwo.Exceptions;

public class NoSuchItemException extends RuntimeException{

    public NoSuchItemException(){
        super("Nie ma takiego przedmiotu");
    }
}
