package com.sumon.materialdesigndemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.R;
import com.sumon.materialdesigndemo.adapter.RecyclerViewHolder;
import com.sumon.materialdesigndemo.firebase.FirebaseAdapter;

import java.util.ArrayList;

/**
 * Created by SumOn on 2016.08.07.
 */
public class NewReleaseFragment extends Fragment {


    private AlphaAnimation alphaAnimation, alphaAnimationShowIcon;
    private NativeExpressAdView adView;
    private CardView card_ad;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private FirebaseAdapter adapter;
    private int color = 0;
    private String insertData;
    private boolean loading;
    private int loadTimes;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
       /* img_main_card_1.startAnimation(alphaAnimation);
        img_main_card_2.startAnimation(alphaAnimation);*/

        alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimationShowIcon.setDuration(500);


        initView();
    }

    private void initView() {
        if (getScreenWidthDp() >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (getScreenWidthDp() >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
            mLayoutManager.setStackFromEnd(true);
            mLayoutManager.setReverseLayout(true);
            mRecyclerView.setLayoutManager(mLayoutManager);

        }

        Query query = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://bangla-movie-songs-855af.firebaseio.com/recipes")
                .limitToLast(50);

        FirebaseRecyclerOptions<DataModel> options =
                new FirebaseRecyclerOptions.Builder<DataModel>()
                        .setQuery(query, DataModel.class)
                        .build();

        adapter = new FirebaseAdapter(options,getContext());
        mRecyclerView.setAdapter(adapter);


        //adapter.setItems(dataModelArrayList);
        // adapter.addFooter();*/


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
                        adapter.setColor(++color);
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
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
