package com.malong.myliveshow.http;

import com.malong.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Malong
 * on 18/7/31.
 */
public interface HttpService {

    @GET("index")
    Call<LoginBean> getTopNews(@Query("type") String top, @Query("key") String appkey);
}
