package com.example.tiwo.Exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestControllerAdvice
@ControllerAdvice
@EnableWebMvc
public class CustomExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyRegisteredException.class})
    public ResponseEntity<Object> handleRegistrationException(){
        return new ResponseEntity<>(new UserAlreadyRegisteredException(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UnsuccessfulLoginException.class})
    public ResponseEntity<Object> handleLoginException(){
        return new ResponseEntity<>(new UnsuccessfulLoginException(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NoSuchUserException.class})
    public ResponseEntity<Object> handleUserException(){
        return new ResponseEntity<>(new NoSuchUserException(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchListException.class})
    public ResponseEntity<Object> handleListException(){
        return new ResponseEntity<>(new NoSuchListException(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchOrderException.class})
    public ResponseEntity<Object> handleOrderException(){
        return new ResponseEntity<>(new NoSuchOrderException(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchItemException.class})
    public ResponseEntity<Object> handleItemException(){
        return new ResponseEntity<>(new NoSuchItemException(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


}
