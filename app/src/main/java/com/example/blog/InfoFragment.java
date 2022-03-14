package com.example.blog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.blog.models.response.ResLogin;
import com.example.blog.repository.BlogService;
import com.example.blog.utils.Define;
import com.example.blog.utils.ToKenProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoFragment extends Fragment {

    private BlogService service;

    public InfoFragment() {
        // Required empty public constructor
    }

    // DI -- 주니에 --> 반드시 알아야 되는 개념

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = BlogService.retrofit.create(BlogService.class);
        // token
        String token = ToKenProvider.getJWTToken(getContext(), Define.FILE_TOKEN, Define.JWT);
        String userId = ToKenProvider.getJWTToken(getContext(), Define.FILE_TOKEN, Define.USER_ID);
        service.getUserInfo(token, Integer.parseInt(userId))
                .enqueue(new Callback<ResLogin>() {
                    @Override
                    public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {

                    }

                    @Override
                    public void onFailure(Call<ResLogin> call, Throwable t) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_info, container, false);
        Button button = itemView.findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences(Define.FILE_TOKEN,  getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Define.JWT,  "");
                editor.apply();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return itemView;
    }
}