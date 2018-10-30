package com.demo.in.demoprojacc.networks;




import com.demo.in.demoprojacc.BuildConfig;
import com.demo.in.demoprojacc.models.ResAlbum;
import com.demo.in.demoprojacc.models.ResAlbumList;
import com.demo.in.demoprojacc.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Akshay More.
 * This call handle all the network request made by an app
 * all the request routes through this class
 */
public class NetworkService {

    private static NetworkService networkService;
    private static Retrofit retrofitRequest;


    /**
     * private constructor to create singleton object
     */
    private NetworkService() {

    }

    /**
     * This is method of which return single instance of object
     * throughout the lifecycle of application
     *
     * @return
     */
    public static NetworkService getInstance() {

        if (networkService == null) {

            networkService = new NetworkService();
        }

        if (retrofitRequest == null) {
            retrofitRequest = RetrofitUtils.getRetrofitRequestBase();
        }

        return networkService;
    }

    /**
     * Call to request diagnostic centers from server
     *
     * @param callback
     */


    public void getAlbumList(Callback <List<ResAlbumList>> callback){
        RestService restService = RetrofitUtils.getRetrofitRequestBase().create(RestService.class);
        Call<List<ResAlbumList>> hint = restService.getAlbumList();
        hint.enqueue(callback);
    }
}