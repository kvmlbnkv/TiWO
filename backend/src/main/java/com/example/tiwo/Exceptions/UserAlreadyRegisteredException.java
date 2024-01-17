package com.example.tiwo.Exceptions;


public class UserAlreadyRegisteredException extends RuntimeException{



    public UserAlreadyRegisteredException(){
        super("Email zajęty");
    }

}
