package com.sumon.materialdesigndemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sumon.materialdesigndemo.R;

/**
 * Created by SumOn on 10/25/2017.
 */


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public RelativeLayout rela_round;
    public TextView title;
    public TextView subTitle1;
    public TextView subTitle2;
    public TextView digitText;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        rela_round = (RelativeLayout) itemView.findViewById(R.id.rela_round);
        title = (TextView) itemView.findViewById(R.id.recycler_item_1);
        subTitle1 = (TextView) itemView.findViewById(R.id.recycler_item_2);
        subTitle2 = (TextView) itemView.findViewById(R.id.recycler_item_3);
        digitText = (TextView) itemView.findViewById(R.id.digitText);

    }
}