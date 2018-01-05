package com.maning.baseapplication.http;

import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.OtherModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by maning on 2018/1/4.
 */

public interface ApiService {

    @GET
    Call<GankModel> loadDataByRetrofit(@Url String url);

    @POST
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtherModel> loadDataByRetrofitRxjava(
            @Url String url,
            @Body RequestBody requestBody
    );

}
