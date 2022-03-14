package com.example.blog.test;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class MainTest {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {


        // 1 . encoding -->
        String url = "http://lalacoding.site/init/user";

//        Base64Encoder base64Encoder = new Base64Encoder();
//        String result1 = base64Encoder.encode(url);
//        System.out.println(result1);

//        UrlEncoder urlEncoder = new UrlEncoder();
//        String result2 = urlEncoder.encode(url);
//        System.out.println(result2);

        // 2. 다형성 이용
//        IEncoder iEncoder1 = new Base64Encoder();
//        IEncoder iEncoder2 = new UrlEncoder();
//        String result1 = iEncoder1.encode(url);
//        String result2 = iEncoder2.encode(url);

        // 스트래티지 패턴 -->

        // 높은 수준으로 클래스 설계 !!!
        // DI 개념을 추가
//        Encoder encoder = new Encoder(new Base64Encoder());
//        String result = encoder.encode(url);
//        System.out.println(result);

        Encoder encoder = new Encoder(new MyEncoder());
        String result = encoder.encode(url);
        System.out.println(result);

    }
}
