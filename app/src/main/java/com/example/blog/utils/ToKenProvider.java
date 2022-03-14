package com.example.blog.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ToKenProvider {

    // todo 기존 코드 수정하기
    public static String getJWTToken(Context context, String fileType, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileType, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
