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

import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.R;
import com.sumon.materialdesigndemo.adapter.RecyclerViewAdapter;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

/**
 * Created by zhang on 2016.08.07.
 */
public class HomeFragment extends Fragment {


    private AlphaAnimation alphaAnimation, alphaAnimationShowIcon;
    private NativeExpressAdView adView;
    private CardView card_ad;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    private int color = 0;
    private String insertData;
    private boolean loading;
    private int loadTimes;

    ArrayList<DataModel> dataModelArrayList;



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

        initData();
        initView();
    }

    private void initData() {

        String title[], subtitle1[] , subtitle2[];
        title = getResources().getStringArray(R.array.title_array);
        subtitle1 = getResources().getStringArray(R.array.subtitle1_array);
        subtitle2 = getResources().getStringArray(R.array.subtitle2_array);


      /*  String[] title = {"title1", "title2", "title3","title4", "title5", "title6","title7", "title8", "title9","title10", "title11", "title12"};
        String[] subtitle1 = {"sub1Title1", "sub1Title2", "sub1Title3", "sub1Title4", "sub1Title5", "sub1Title6", "sub1Title7", "sub1Title8", "sub1Title9", "sub1Title10", "sub1Title11", "sub1Title12"};
        String[] subtitle2 = {"sub2Title1", "sub2Title2", "sub2Title3", "sub2Title4", "sub2Title5", "sub2Title6", "sub2Title7", "sub2Title8", "sub2Title9", "sub2Title10", "sub2Title11", "sub2Title12",};
*/
        int limit = title.length;
        dataModelArrayList = new ArrayList<>();
        for (int i = 0; i <limit; i++) {
            DataModel dataModelClass = new DataModel(title[i], subtitle1[i] , subtitle2[i]);
            dataModelArrayList.add(dataModelClass);

            Log.i("INTI_DATA", "initData: " + dataModelClass + title.length);
        }

        insertData = "0";
        loadTimes = 0;
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
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        adapter = new RecyclerViewAdapter(getContext() , dataModelArrayList);
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

       // mRecyclerView.addOnScrollListener(scrollListener);
    }
/*

    RecyclerView.OnScrollListener scrollListener = new_icon RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {

                new_icon Handler().postDelayed(new_icon Runnable() {
                    @Override
                    public void run() {
                        if (loadTimes <= 5) {
                            adapter.removeFooter();
                            loading = false;
                            adapter.addItems(data);
                            adapter.addFooter();
                            loadTimes++;
                        } else {
                            adapter.removeFooter();
                            Snackbar.make(mRecyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).setCallback(new_icon Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    super.onDismissed(transientBottomBar, event);
                                    loading = false;
                                    adapter.addFooter();
                                }
                            }).show();
                        }
                    }
                }, 1500);

                loading = true;
            }
        }
    };
*/

    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

}
