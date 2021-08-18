package com.ar.core;

public class DbException extends Exception {


    private String message;

    //构造函数
    public DbException(String message){
        super(message);
        this.message = message;
    }
}