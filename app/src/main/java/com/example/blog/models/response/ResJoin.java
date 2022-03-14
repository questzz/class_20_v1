package com.example.blog.models.response;

import com.example.blog.models.common.Response;

public class ResJoin extends Response {

    public Data data;

    @Override
    public String toString() {
        return "ResJoin{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
