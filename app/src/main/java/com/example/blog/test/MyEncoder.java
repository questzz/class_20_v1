package com.example.blog.test;

public class MyEncoder implements IEncoder{

    @Override
    public String encode(String message) {
        return message + "?/username=10";
    }
}
