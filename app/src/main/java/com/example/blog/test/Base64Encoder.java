package com.example.blog.test;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class Base64Encoder implements IEncoder {

//    @Override
//    public String encode(String message) {
//        return null;
//    }


    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }
}
