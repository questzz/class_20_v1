package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.blog.databinding.ActivityLoginBinding;
import com.example.blog.models.request.ReqLogin;
import com.example.blog.models.response.ResLogin;
import com.example.blog.repository.BlogService;
import com.example.blog.utils.Define;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding binding;
    BlogService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 레트로핏 초기화
        service = BlogService.retrofit.create(BlogService.class);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = binding.loginEditText.getText().toString();
                String pw = binding.loginPasswordEditText.getText().toString();

                if(id.length() > 3 && pw.length() > 3) {
                    service.login(new ReqLogin(id, pw))
                            .enqueue(new Callback<ResLogin>() {
                                @Override
                                public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                                    if (response.isSuccessful()) {
                                        ResLogin resLogin = response.body();
                                        SharedPreferences preferences = getSharedPreferences(Define.FILE_TOKEN, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(Define.JWT,  response.headers().get("Authorization"));
                                        editor.putString("username", resLogin.data.username);
                                        editor.putString("email", resLogin.data.email);
                                        editor.putString(Define.USER_ID, String.valueOf(resLogin.data.id));
                                        editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResLogin> call, Throwable t) {
                                    Log.d("TAG", t.getMessage());
                                }
                            });


                }
            }
        });

        binding.moveSignUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(this, JoinActivity.class);
            startActivity(intent);
        });

    }
}