package com.common.httplibrary;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取Retrofit对象
 */
public class ApiClient {

    public static String API_SERVER_URL = "http://www.weather.com.cn/";

    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
            builder.readTimeout(15000, TimeUnit.MILLISECONDS);
            builder.writeTimeout(15000, TimeUnit.MILLISECONDS);
            builder.addInterceptor(new ChuckInterceptor(HttpManager.getApp()));
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
