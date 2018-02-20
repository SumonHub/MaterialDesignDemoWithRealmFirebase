package com.sumon.materialdesigndemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sumon.materialdesigndemo.Model.DataModel;
import com.sumon.materialdesigndemo.R;
import com.sumon.materialdesigndemo.activity.ScrollingActivity;

import java.util.ArrayList;

/**
 * Created by zhang on 2016.08.07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataModel> dataModelArrayList;
    private int color = 0;

    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";


    public RecyclerViewAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;

    }

    public RecyclerViewAdapter() {

    }

   /* public void setItems(ArrayList<DataModel> data) {
        this.dataModelArrayList.addAll(data);
        notifyDataSetChanged();
    }
*/
   /* public void addItem(int position, String insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<String> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addFooter() {
        mItems.add(FOOTER);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeFooter() {
        mItems.remove(mItems.size() - 1);
        notifyItemRemoved(mItems.size());
    }*/

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

            recyclerViewHolder.title.setText(dataModelArrayList.get(position).getTitle());
            recyclerViewHolder.subTitle1.setText(dataModelArrayList.get(position).getSubTitle1());
            recyclerViewHolder.subTitle2.setText(dataModelArrayList.get(position).getSubTitle2());
            recyclerViewHolder.digitText.setText(dataModelArrayList.get(position).getTitle().substring(0, 1));

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);

            AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
            aa1.setDuration(400);
            recyclerViewHolder.rela_round.startAnimation(aa1);

            AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
            aa.setDuration(400);

            if (color == 1) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_blue)));
            } else if (color == 2) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_green)));
            } else if (color == 3) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_yellow)));
            } else if (color == 4) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_red)));
            } else {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cyan_dark)));
            }

            recyclerViewHolder.rela_round.startAnimation(aa);

            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    intent.putExtra("color", color);
                    intent.putExtra("title", dataModelArrayList.get(position).getTitle());
                    intent.putExtra("subTitle1", dataModelArrayList.get(position).getSubTitle1());
                    intent.putExtra("subTitle2", dataModelArrayList.get(position).getSubTitle2());
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

/*
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private RelativeLayout rela_round;
        TextView title;
        TextView subTitle1;
        TextView subTitle2;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            rela_round = (RelativeLayout) itemView.findViewById(R.id.rela_round);
            title = (TextView) itemView.findViewById(R.id.recycler_item_1);
            subTitle1 = (TextView) itemView.findViewById(R.id.recycler_item_2);
            subTitle2 = (TextView) itemView.findViewById(R.id.recycler_item_3);

        }
    }*/
}
