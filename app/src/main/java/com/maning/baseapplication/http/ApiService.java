package com.maning.baseapplication.http;

import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.HttpResponse;
import com.maning.baseapplication.model.OtherModel;

import java.util.List;

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
    Call<HttpResponse<List<GankModel>>> getGankDataByRetrofit(@Url String url);

    @GET
    Observable<HttpResponse<List<GankModel>>> getGankDataByRetrofitRxjava(@Url String url);

    //这里使用Url 主要是为了动态切换BaseUrl
    @POST
    Observable<OtherModel> getWeatherDataByRetrofitRxjava(
            @Url String url,
            @Body RequestBody requestBody
    );

}
