package com.demo.in.demoprojacc.networks;

import com.demo.in.demoprojacc.models.ResAlbum;
import com.demo.in.demoprojacc.models.ResAlbumList;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Mahesh Chunkhade on 6/23/2016.
 */
public interface RestService {

    @GET("/albums")
    Call<List<ResAlbumList>> getAlbumList();




}
