package com.example.blog.models.request;

public class ReqLogin {

    public String username;
    public String password;

    public ReqLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
