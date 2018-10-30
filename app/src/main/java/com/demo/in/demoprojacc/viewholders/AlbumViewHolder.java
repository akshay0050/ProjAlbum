package com.demo.in.demoprojacc.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.in.demoprojacc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_user_id) public TextView tvUserId;
    @BindView(R.id.tv_id) public TextView tvId;
    @BindView(R.id.tv_title) public TextView tvTitle;
    public AlbumViewHolder( View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
