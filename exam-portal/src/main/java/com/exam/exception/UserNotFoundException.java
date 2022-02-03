package com.exam.exception;

public class UserNotFoundException extends  Exception{

    public UserNotFoundException(){
        super("User with this Username is not present in DB!! try with another credentials!");
    }

    public UserNotFoundException(String msg){
        super(msg);
    }
}
