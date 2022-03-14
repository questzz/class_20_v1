package com.example.blog;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.blog.databinding.ActivityMainBinding;
import com.example.blog.interfaces.OnRefreshFragment;
import com.example.blog.models.request.ReqJoin;
import com.example.blog.models.request.ReqLogin;
import com.example.blog.models.response.ResJoin;
import com.example.blog.models.response.ResLogin;
import com.example.blog.models.response.ResPost;
import com.example.blog.models.response.ResUserDto;
import com.example.blog.repository.BlogService;
import com.example.blog.utils.FragmentType;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;
    private BlogService service;
    // 액티비티에서 다른 액티비티에 결과를 받기 위해 등록하는 녀석
    private OnRefreshFragment onRefreshFragment;
    private BlogListFragment blogListFragment;

    private ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    Intent resultData = result.getData();
                    String msg = resultData.getStringExtra("msg");
                    Log.d(TAG, "결과값이 잘 돌아 왔습니다.");
                    Log.d(TAG, "msg :" + msg);
                    Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();

                    if (blogListFragment != null) {
                       blogListFragment.requestBlogList(true);
                    }

                    //replaceFragment(FragmentType.LIST);

                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 통신 성공 여부 테스트
        service = BlogService.retrofit.create(BlogService.class);
        replaceFragment(FragmentType.LIST);
        addBottomNaviListener();
        addTopAppBarListener();
    }

    private void addTopAppBarListener() {
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                moveWriteActivity();
                return true;
            }
        });
    }

    private void moveWriteActivity() {
        Intent intent = new Intent(this, WriteActivity.class);
        // startActivity(intent);
        startActivityResult.launch(intent);

    }


    private void replaceFragment(FragmentType type) {
        Fragment fragment = null;
        switch (type) {
            case LIST:
                blogListFragment = new BlogListFragment();
                fragment = blogListFragment;
                break;
            case INFO:
                fragment = new InfoFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.mainContainer.getId(), fragment);
        transaction.commit();
    }

    private void addBottomNaviListener() {
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        replaceFragment(FragmentType.LIST);
                        break;
                    case R.id.page_2:
                        replaceFragment(FragmentType.INFO);
                        break;
                }
                return true;
            }
        });
    }
}











