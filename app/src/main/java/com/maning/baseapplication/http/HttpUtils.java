package com.maning.baseapplication.http;

import com.common.httplibrary.OkHttpUtils;
import com.common.httplibrary.RetrofitClient;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpUtils {

    public static void getGankDatas(Callback callback) {
        Call<HttpResponse<List<GankModel>>> call = RetrofitClient.retrofit().create(ApiService.class).getGankDataByRetrofit("http://gank.io/api/data/Android/10/1");
        call.enqueue(callback);
    }

    public static void getOtherDatas(DisposableObserver observer) {
        String url = "http://gank.io/api/data/Android/10/1";
        RetrofitClient.retrofit()
                .create(ApiService.class)
                .getGankDataByRetrofitRxjava(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getOtherDatas2(okhttp3.Callback callback) {
        String url = "http://gank.io/api/data/Android/10/1";
        OkHttpUtils.get(url, callback);
    }


    public static void getListDatas(DisposableObserver observer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", "4");
            jsonObject.put("pageNo", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = createRequestBody(jsonObject.toString());
        String url = "http://xxxxx/hxj_srv/spread/v2/get_list/";
        RetrofitClient.retrofit()
                .create(ApiService.class)
                .getWeatherDataByRetrofitRxjava(url, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static RequestBody createRequestBody(String requestStr) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr);
    }


}
