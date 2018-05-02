package com.sumon.materialdesigndemo.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class FirebaseAdapter extends FirebaseRecyclerAdapter<DataModel, RecyclerViewHolder> {

    public Context context;
    public int color = 0;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    /*public FirebaseAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options) {
        super(options);
    }*/
    public FirebaseAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull final DataModel model) {

        holder.title.setText(model.getTitle());
        holder.subTitle1.setText(model.getSubTitle1());
        holder.subTitle2.setText(model.getSubTitle2());
        holder.digitText.setText(model.getTitle().substring(0, 1));

        //  Glide.with(context).load(model.getRecipeImageUrl()).into(viewHolder.recipeImage);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
        aa1.setDuration(400);
        holder.rela_round.startAnimation(aa1);

        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(400);

        if (color == 1) {
            holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_blue)));
        } else if (color == 2) {
            holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_green)));
        } else if (color == 3) {
            holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_yellow)));
        } else if (color == 4) {
            holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_red)));
        } else {
            holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cyan_dark)));
        }

        holder.rela_round.startAnimation(aa);

        holder.mView.setOnClickListener(new View.OnClickListener() {
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
        super.onDataChanged();
       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("hay what's up!")
                .setContentText("something new for you has been added :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
// notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, mBuilder.build());*/
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

}