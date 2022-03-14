package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.blog.databinding.ActivityBlogDetailBinding;
import com.example.blog.models.request.ReqPost;
import com.example.blog.models.response.Data;
import com.example.blog.models.response.ResDelete;
import com.example.blog.models.response.ResPostById;
import com.example.blog.repository.BlogService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends AppCompatActivity {

    private static final String TAG = BlogDetailActivity.class.getName();
    private ActivityBlogDetailBinding binding;
    private BlogService service;
    private String token;
    private String userName;
    private int postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEventListener();

        service = BlogService.retrofit.create(BlogService.class);

        if (getIntent() != null) {
            SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
            token = preferences.getString("jwt", "");
            userName = preferences.getString("username", "");
            postId = getIntent().getIntExtra("postId", 0);

            Log.d(TAG, "postId : " + postId);
            service.postGetById(token, postId)
                    .enqueue(new Callback<ResPostById>() {
                        @Override
                        public void onResponse(Call<ResPostById> call, Response<ResPostById> response) {
                            if (response.isSuccessful()) {
                                ResPostById resPostById = response.body();
                                Log.d(TAG, resPostById.toString());
                                // 메서드
                                binding.titleTextView.setText(resPostById.data.title);
                                binding.detailContentEt.setText(resPostById.data.content);
                                // 버튼 보여주기 / 안보여주기
                                if (userName.equals(resPostById.data.user.getUsername())) {
                                    binding.updateButton.setVisibility(View.VISIBLE);
                                    binding.deleteButton.setVisibility(View.VISIBLE);
                                    binding.detailContentEt.setEnabled(true);
                                } else {
                                    binding.updateButton.setVisibility(View.GONE);
                                    binding.deleteButton.setVisibility(View.GONE);
                                    binding.detailContentEt.setEnabled(false);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResPostById> call, Throwable t) {

                        }
                    });

        }
    }

    private void addEventListener() {
        binding.updateButton.setOnClickListener(v -> {
            String title = binding.titleTextView.getText().toString();
            String content = binding.detailContentEt.getText().toString();
            service.updatePost(token, postId, new ReqPost(title, content))
                    .enqueue(new Callback<ResPostById>() {
                        @Override
                        public void onResponse(Call<ResPostById> call, Response<ResPostById> response) {
                            // 결과 처리
                            Log.d(TAG, response.code() +  "< ---- code ");
                        }

                        @Override
                        public void onFailure(Call<ResPostById> call, Throwable t) {

                        }
                    });
        });

        binding.deleteButton.setOnClickListener(v -> {
            service.deletePost(token, postId)
                    .enqueue(new Callback<ResDelete>() {
                        @Override
                        public void onResponse(Call<ResDelete> call, Response<ResDelete> response) {
                            Log.d(TAG, "status code :" + response.code() + "");
                            if (response.isSuccessful()) {
                                // 삭제 완료했으면
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResDelete> call, Throwable t) {

                        }
                    });

        });
    }

}