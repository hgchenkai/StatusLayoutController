package com.chuck.statuslayoutcontroller;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chuck.library.ICallback;
import com.chuck.library.StatusListController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StatusListController statusListController;
    private List<String> dataLists = new ArrayList<>();
    private Adapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        statusListController.showLoadingView();
        post(3000);
    }

    private void post(long time) {
        handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                statusListController.showContentView();
                for (int i = 0; i < 10; i++) {
                    dataLists.add("item " + i);
                }

                adapter.update(dataLists);
            }
        }, time);
    }

    private void initViews() {
        RecyclerView rv = findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        rv.setAdapter(adapter);
        statusListController = new StatusListController.Builder(rv)
                .callback(new ICallback() {
                    @Override
                    public void refreshCallback() {
                        post(0);
                    }

                    @Override
                    public void reloadCallback() {
                        post(0);
                    }
                })
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_loading:
                // 加载中
                statusListController.showLoadingView();
                break;
            case R.id.menu_empty:
                // 空数据
                statusListController.showEmptyView();
                break;
            case R.id.menu_error:
                // 加载失败
                statusListController.showErrorView();
                break;
            case R.id.menu_success:
                // 加载成功，显示原布局
                statusListController.showContentView();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
