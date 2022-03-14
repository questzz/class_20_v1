package com.example.blog.models.response;

import com.example.blog.models.common.Response;

public class ResPostById  extends Response {

    public Data data;

    @Override
    public String toString() {
        return "ResPostById{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
