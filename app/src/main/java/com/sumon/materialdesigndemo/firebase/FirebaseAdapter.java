package com.sumon.materialdesigndemo.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.R;
import com.sumon.materialdesigndemo.activity.ScrollingActivity;
import com.sumon.materialdesigndemo.adapter.RecyclerViewHolder;

import java.security.AccessControlContext;

/**
 * Created by SumOn on 10/25/2017.
 */

public class FirebaseAdapter extends FirebaseRecyclerAdapter<DataModel , RecyclerViewHolder> {

    public Context context;
    public int color =0 ;

    public FirebaseAdapter(Class<DataModel> modelClass, int modelLayout, Class<RecyclerViewHolder> viewHolderClass, Query ref, Context mContext) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = mContext;
    }

    @Override
    protected void populateViewHolder(RecyclerViewHolder viewHolder, final DataModel model, int position) {

        viewHolder.title.setText(model.getTitle());
        viewHolder.subTitle1.setText(model.getSubTitle1());
        viewHolder.subTitle2.setText(model.getSubTitle2());
        viewHolder.digitText.setText(model.getTitle().substring(0,1));

      //  Glide.with(context).load(model.getRecipeImageUrl()).into(viewHolder.recipeImage);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        viewHolder.mView.startAnimation(animation);

        AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
        aa1.setDuration(400);
        viewHolder.rela_round.startAnimation(aa1);

        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(400);

        if (color == 1) {
            viewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_blue)));
        } else if (color == 2) {
            viewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_green)));
        } else if (color == 3) {
            viewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_yellow)));
        } else if (color == 4) {
            viewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_red)));
        } else {
            viewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cyan_dark)));
        }

        viewHolder.rela_round.startAnimation(aa);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScrollingActivity.class);
                intent.putExtra("color", color);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("subTitle1", model.getSubTitle1());
                intent.putExtra("subTitle2", model.getSubTitle2());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onDataChanged() {
        // Called each time there is a new_icon data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }


    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }
}
