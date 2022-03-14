package com.example.blog.adapter;

import android.annotation.SuppressLint;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blog.R;
import com.example.blog.interfaces.OnPageRequest;
import com.example.blog.models.response.Data;
import com.example.blog.models.response.ResPost;

import java.util.ArrayList;
import java.util.List;

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {


    private List<Data> list = new ArrayList<>();
    private OnPageRequest onPageRequest;

    // 생성자, 메서드로 주소 받기
    public BlogListAdapter(OnPageRequest onPageRequest) {
        this.onPageRequest = onPageRequest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_blog_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = list.get(position);
        // 이미지 그림을 그려야 합니다.
        holder.titleTextView.setText(data.title);
        holder.userNameTextView.setText(data.user.getUsername());
        holder.contentTextView.setText(data.content);
        Glide.with(holder.imageView.getContext())
                .load("https://picsum.photos/200/300?random=" + position)
                .centerCrop()
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageRequest.onPageChange(data.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void refreshItems(List<Data> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<Data> dataList) {
        this.list.addAll(this.list.size(), dataList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imageView;
        TextView titleTextView;
        TextView userNameTextView;
        TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.blogImageView);
            titleTextView = itemView.findViewById(R.id.blogTitleTextView);
            userNameTextView = itemView.findViewById(R.id.blogUserNameTextView);
            contentTextView = itemView.findViewById(R.id.blogContentTextView);
        }
    }

}
