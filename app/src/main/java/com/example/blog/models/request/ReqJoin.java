package com.example.blog.models.request;

public class ReqJoin {

    public String username;
    public String password;
    public String email;

    public ReqJoin(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
