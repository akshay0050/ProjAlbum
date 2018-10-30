package com.demo.in.demoprojacc.adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.in.demoprojacc.R;
import com.demo.in.demoprojacc.models.ResAlbumList;
import com.demo.in.demoprojacc.viewholders.AlbumViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private List<ResAlbumList> albumList = new ArrayList<ResAlbumList>();
    Context context;
    public AlbumListAdapter(Context context,List<ResAlbumList> albumList) {
        this.context = context;
        this.albumList = albumList;

    }


    @Override
    public AlbumViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_album_detail, viewGroup, false);
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(v);
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder( AlbumViewHolder albumViewHolder, int i) {
        ResAlbumList resAlbumList = albumList.get(i);
        if(resAlbumList!= null){
            albumViewHolder.tvId.setText(String.valueOf(resAlbumList.getId()));
            albumViewHolder.tvUserId.setText(String.valueOf(resAlbumList.getUserId()));
            albumViewHolder.tvTitle.setText(String.valueOf(resAlbumList.getTitle()));
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
