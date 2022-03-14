package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.blog.databinding.ActivityJoinBinding;
import com.example.blog.models.request.ReqJoin;
import com.example.blog.models.response.ResJoin;
import com.example.blog.repository.BlogService;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private ActivityJoinBinding binding;
    private BlogService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        service = BlogService.retrofit.create(BlogService.class);

        binding.joinButton.setOnClickListener(v -> {

            String id = binding.joinEditText.getText().toString();
            String pw = binding.joinPasswordEditText.getText().toString();
            String email = binding.joinEmailEditText.getText().toString();

            if (id.length() > 3 && pw.length() > 3 && email.length() > 3) {

                service.join(new ReqJoin(id, pw, email))
                        .enqueue(new Callback<ResJoin>() {
                            @Override
                            public void onResponse(Call<ResJoin> call, Response<ResJoin> response) {
                                if (response.isSuccessful()) {
                                    Snackbar.make(v, response.body().msg, Snackbar.LENGTH_SHORT).show();
                                    binding.moveLoginTextView.callOnClick();
                                    // 옵저버 팬턴 만들어서 (response.body().msg)
                                } else {

                                }
                            }

                            @Override
                            public void onFailure(Call<ResJoin> call, Throwable t) {
                                //
                            }
                        });
            }
        });

        binding.moveLoginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}