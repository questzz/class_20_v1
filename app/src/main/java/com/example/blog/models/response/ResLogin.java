package com.example.blog.models.response;

import com.example.blog.models.common.Response;

public class ResLogin  extends Response {


    public Data data;

    @Override
    public String toString() {
        return "ResLogin{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
