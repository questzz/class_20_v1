package com.example.blog.models.response;

public class Data {

    public int id;
    public String username;
    public String email;
    public String created;
    public String updated;


    public String title;
    public String content;
    public ResUserDto user;
    public String password;

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", password='" + password + '\'' +
                '}';
    }
}
