package com.sumon.materialdesigndemo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.Model.RealmHelper;
import com.sumon.materialdesigndemo.R;

import io.realm.Realm;

public class ScrollingActivity extends AppCompatActivity {

    private TextView title, subTitle1, subTitle2;
    private ImageView image_scrolling_top;

    String getTitle, getSubTitle1, getSubTitle2;


    FloatingActionButton bookmark;
    DataModel isSave;

    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
/*
        intent.putExtra("color", color);
        intent.putExtra("title" , dataModels.get(position).getTitle());
        intent.putExtra("subTitle1" , dataModels.get(position).getTitle());
        intent.putExtra("subTitle2" , dataModels.get(position).getTitle());*/

        getTitle = getIntent().getExtras().getString("title");
        getSubTitle1 = getIntent().getExtras().getString("subTitle1");
        getSubTitle2 = getIntent().getExtras().getString("subTitle2");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getTitle);


        subTitle1 = (TextView) findViewById(R.id.subTitle1);
        subTitle2 = (TextView) findViewById(R.id.subTitle2);
        subTitle1.setText(getSubTitle1);
        subTitle2.setText(getSubTitle2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_scrolling);
        fab.setOnClickListener(new View.OnClickListener() {

            public String APP_URL = "https://play.google.com/store/apps/details?id=" + getPackageName();
            public String DESIGNED_BY = "NextAppsBD";
            public String SHARE_CONTENT = "ভাল লাগার মত একটি অ্যাপ.... :\n" + APP_URL + "\n- " + DESIGNED_BY;

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
                Log.i("TAG", "share_with execute");
            }
        });


        bookmark = (FloatingActionButton) findViewById(R.id.fab_scrolling2);

        isSave = new DataModel(getTitle, getSubTitle1, getSubTitle2);

        realmHelper = new RealmHelper(realm);
        realmHelper.isSave(isSave);

        if (realmHelper.isSave(isSave)) {
            bookmark.setImageResource(R.drawable.un_bookmark);
        } else {
            bookmark.setImageResource(R.drawable.bookmark);
        }
        bookmark.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (realmHelper.isSave(isSave)) {
                    //   realmHelper.delete(isSave);
                    //   bookmark.setImageResource(R.drawable.un_bookmark);

                    bookmark.setImageResource(R.drawable.bookmark);

                    Snackbar snackbar = Snackbar
                            .make(v, "Add to favorite || " + isSave.getTitle(), Snackbar.LENGTH_LONG);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);

                    sbView.setBackgroundColor(getColor(R.color.colorPrimary));
                    snackbar.show();


                    RealmHelper h = new RealmHelper(realm);
                    h.save(isSave);

                    Log.i("SAVE", "onFavoriteChanged: " + isSave.getTitle());
                } else {
                    // realmHelper.save(isSave);
                    // bookmark.setImageResource(R.drawable.bookmark);

                    bookmark.setImageResource(R.drawable.un_bookmark);

                    Snackbar snackbar = Snackbar
                            .make(v, "Remove from favorite || " + isSave.getTitle(), Snackbar.LENGTH_LONG);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);

                    sbView.setBackgroundColor(getColor(R.color.colorPrimary));
                    snackbar.show();


                    RealmHelper h = new RealmHelper(realm);
                    h.delete(isSave);

                    Log.i("DELETE", "onFavoriteChanged: " + isSave.getTitle());
                }
            }
        });

        image_scrolling_top = (ImageView) findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.drawable.material_design_3).fitCenter().into(image_scrolling_top);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}
