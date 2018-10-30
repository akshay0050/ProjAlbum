package com.demo.in.demoprojacc;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.demo.in.demoprojacc.adapters.AlbumListAdapter;
import com.demo.in.demoprojacc.baseclass.BaseActivity;
import com.demo.in.demoprojacc.customviews.AVLoadingIndicatorDialog;
import com.demo.in.demoprojacc.models.ResAlbumList;
import com.demo.in.demoprojacc.networks.NetworkService;
import com.demo.in.demoprojacc.utils.NetworkUtils;
import com.demo.in.demoprojacc.utils.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rv_album) RecyclerView rvAlbum;
    @BindView(R.id.cv_no_data) CardView cvNoData;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout swiperefresh;
    List<ResAlbumList> albumList = new ArrayList<>();
    AlbumListAdapter albumListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUi();
        setData();
    }

    private void initUi(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        rvAlbum.setLayoutManager(mLayoutManager);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
    }
    private void setData(){
        if(!NetworkUtils.isNetworkAvailable(activity)){
            rvAlbum.setVisibility(View.GONE);
            cvNoData.setVisibility(View.VISIBLE);
            UiUtils.showToast(activity,getString(R.string.no_conncection));
            swiperefresh.setRefreshing(false);
            return;
        }
        final AVLoadingIndicatorDialog dialog = new AVLoadingIndicatorDialog(activity);
        dialog.setMessage("Processing your request");
        dialog.show();


        NetworkService.getInstance().getAlbumList(new Callback<List<ResAlbumList>>() {
            @Override
            public void onResponse(Call<List<ResAlbumList>> call, Response<List<ResAlbumList>> response) {
                dialog.dismiss();
                swiperefresh.setRefreshing(false);
                if(response.isSuccessful()){
                    albumList = response.body();
                    if(albumList != null && albumList.size()>0){
                        cvNoData.setVisibility(View.GONE);
                        rvAlbum.setVisibility(View.VISIBLE);
                        getListSortByTitle();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResAlbumList>> call, Throwable t) {
                dialog.dismiss();
                swiperefresh.setRefreshing(false);
                cvNoData.setVisibility(View.VISIBLE);
                rvAlbum.setVisibility(View.GONE);
            }
        });


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.by_title:
                if(albumList != null && albumList.size()>0) {
                    getListSortByTitle();
                }
                break;
            case R.id.by_id:
                if(albumList != null && albumList.size()>0) {
                    getListSortById();
                }
                break;

        }
        return true;
    }


    public void getListSortById(){
        ArrayList<String> titleList = new ArrayList<String>();
        for (ResAlbumList resAlbumList: albumList){
            titleList.add(resAlbumList.getTitle().trim());
        }

        Collections.sort(albumList, new Comparator() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Object objkOne, Object objTwo) {
                //use instanceof to verify the references are indeed of the type in question
                return Integer.compare(((ResAlbumList)objkOne).getId(),(((ResAlbumList)objTwo).getId()));
            }

        });
        albumListAdapter = new AlbumListAdapter(context,albumList);
        rvAlbum.setAdapter(albumListAdapter);

    }

    public void getListSortByTitle(){
        ArrayList<String> titleList = new ArrayList<String>();
        for (ResAlbumList resAlbumList: albumList){
            titleList.add(resAlbumList.getTitle().trim());
        }

        Collections.sort(albumList, new Comparator() {
            @Override
            public int compare(Object objkOne, Object objTwo) {
                //use instanceof to verify the references are indeed of the type in question
                return ((ResAlbumList)objkOne).getTitle()
                        .compareTo(((ResAlbumList)objTwo).getTitle());
            }

        });
        albumListAdapter = new AlbumListAdapter(context,albumList);
        rvAlbum.setAdapter(albumListAdapter);

    }

}
