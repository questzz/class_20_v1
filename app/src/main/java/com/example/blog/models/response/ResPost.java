package com.example.blog.models.response;

import com.example.blog.models.common.Response;

import java.util.List;

public class ResPost  extends Response {


    public List<Data> data;

    @Override
    public String toString() {
        return "ResPost{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
