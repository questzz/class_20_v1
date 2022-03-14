package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.blog.databinding.ActivityWriteBinding;
import com.example.blog.models.request.ReqPost;
import com.example.blog.models.response.ResPostById;
import com.example.blog.repository.BlogService;
import com.example.blog.utils.Define;
import com.example.blog.utils.ToKenProvider;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {

    private ActivityWriteBinding binding;

    private BlogService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = BlogService.retrofit.create(BlogService.class);

        binding.saveButton.setOnClickListener(v -> {

            String title = binding.titleEditText.getText().toString();
            String content = binding.detailContentEt.getText().toString();
            if(title.length() > 3 && content.length() > 3) {
                requestPost(title, content);
            }
        });

    }

    // todo 파싱오류
    private void requestPost(String title, String content) {
        String token = ToKenProvider.getJWTToken(this, Define.FILE_TOKEN, Define.JWT);
        service.savePost(token, new ReqPost(title, content))
                .enqueue(new Callback<ResPostById>() {
                    @Override
                    public void onResponse(Call<ResPostById> call, Response<ResPostById> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            String serverMsg = response.body().msg;
                            moveActivity(serverMsg);
                        } else {
                            Snackbar.make(binding.getRoot(),
                                    "네트워크가 불완정합니다.",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResPostById> call, Throwable t) {
                        Log.d("TAG", t.getMessage());
                    }
                });
    }


    private void moveActivity(String severMsg) {
        Intent intent  = new Intent();
        intent.putExtra("msg", severMsg);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}