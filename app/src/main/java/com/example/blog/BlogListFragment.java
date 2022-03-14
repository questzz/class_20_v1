package com.example.blog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blog.adapter.BlogListAdapter;
import com.example.blog.databinding.FragmentBlogListBinding;
import com.example.blog.interfaces.OnPageRequest;
import com.example.blog.interfaces.OnRefreshFragment;
import com.example.blog.models.response.ResPost;
import com.example.blog.repository.BlogService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogListFragment extends Fragment implements OnPageRequest, OnRefreshFragment {

    private FragmentBlogListBinding binding;
    private BlogListAdapter adapter;
    private BlogService service;
    private String token;

    public BlogListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = BlogService.retrofit.create(BlogService.class);

        SharedPreferences preferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("jwt", "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlogListBinding.inflate(inflater, container, false);
        initRecyclerView();
        requestBlogList(false);

        // 이벤트 처리 refreshLayout
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 1. 다시 화면을 그려 줘야 한다.
                // 2. 하지만 서버에서 새로운 데이터를 가지고 와서 갱신해야 한다.
                requestBlogList(true);
                Log.d("TAG", "이벤트 확인 되었습니다.");
                binding.refreshLayout.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }



    private void initRecyclerView() {

        adapter = new BlogListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.blogListRecyclerView.setAdapter(adapter);
        binding.blogListRecyclerView.setLayoutManager(linearLayoutManager);
        binding.blogListRecyclerView.hasFixedSize();
        binding.blogListRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    public void requestBlogList(Boolean isRefresh) {
        service.postList(token).enqueue(new Callback<ResPost>() {
            @Override
            public void onResponse(Call<ResPost> call, Response<ResPost> response) {
                if (response.isSuccessful()) {
                    ResPost resPost = response.body();
                    if (isRefresh) {
                        // 추가적으로 받는 데이터와
                        adapter.refreshItems(resPost.data);
                    } else {
                        // 새롭게 갱신해야 되는 데이터 처리를 구분
                        adapter.addItems(resPost.data);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResPost> call, Throwable t) {

            }
        });
    }


    /**
     *
     * @param postId : 해당 게시글 아이디 값
     */
    @Override
    public void onPageChange(int postId) {
        Intent intent = new Intent(getContext(), BlogDetailActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        requestBlogList(true);
    }
}