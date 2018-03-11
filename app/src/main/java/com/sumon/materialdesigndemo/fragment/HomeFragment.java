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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by SumOn on 2017.08.07.
 */
public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
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

    ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
    ;


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

        jsonFromAssets();

    }

    private void jsonFromAssets() {

        try {
            JSONObject object = new JSONObject(readJSONFromAsset());
            JSONArray recipes = object.getJSONArray("recipes");
            for (int i = 0; i < recipes.length(); i++) {
                JSONObject jsonObject = recipes.getJSONObject(i);
                String subTitle1 = jsonObject.getString("subTitle1");
                String subTitle2 = jsonObject.getString("subTitle2");
                String title = jsonObject.getString("title");
                DataModel dataModelClass = new DataModel(title, subTitle1, subTitle2);

                dataModelArrayList.add(dataModelClass);
                Log.d(TAG, "readJsonFromAssets: " + dataModelArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("test.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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

        adapter = new RecyclerViewAdapter(getContext(), dataModelArrayList);
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
