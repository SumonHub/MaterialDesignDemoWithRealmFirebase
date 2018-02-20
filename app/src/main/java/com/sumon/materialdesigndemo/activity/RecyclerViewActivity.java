package com.sumon.materialdesigndemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.R;
import com.sumon.materialdesigndemo.adapter.RecyclerViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sumon.materialdesigndemo.adapter.RecyclerViewHolder;
import com.sumon.materialdesigndemo.firebase.FirebaseAdapter;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    private int color = 0;
    private List<String> data;
    private String insertData;
    private boolean loading;
    private int loadTimes;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;

    FirebaseAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //    initData();
        initView();
    }


    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recycler_view);

        if (getScreenWidthDp() >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (getScreenWidthDp() >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }

        mRecyclerView.setHasFixedSize(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("recipes");
        mRecipeAdapter = new FirebaseAdapter(DataModel.class, R.layout.item_recycler_view, RecyclerViewHolder.class, childRef, RecyclerViewActivity.this);
        mRecyclerView.setAdapter(mRecipeAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (color > 4) {
                            color = 0;
                        }
                        mRecipeAdapter.setColor(++color);
                        initView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });

    }

    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecipeAdapter.startListening();
    }

}
