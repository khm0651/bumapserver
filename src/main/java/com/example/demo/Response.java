package com.example.demo;

public class Response<T> {

    public String message;
    public T data;

    public Response(T data, String message){
        this.data = data;
        this.message = message;
    }

}
