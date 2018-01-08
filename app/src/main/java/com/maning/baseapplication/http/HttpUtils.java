package com.maning.baseapplication.http;

import com.common.httplibrary.ApiClient;
import com.common.httplibrary.callback.RetrofitCallback;
import com.common.httplibrary.callback.RxCallback;
import com.common.mvplibrary.BasePresenter;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.OtherModel;
import com.maning.baseapplication.mvp.TestPresenter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
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

    public static RequestBody createRequestBody(String requestStr) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr);
    }

    public static void getGankDatas(Callback callback) {
        Call<GankModel> call = ApiClient.retrofit().create(ApiService.class).getGankDataByRetrofit("http://gank.io/api/data/Android/10/1");
        call.enqueue(callback);
    }

    public static void getWeatherDatas(DisposableObserver observer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", "4");
            jsonObject.put("pageNo", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = createRequestBody(jsonObject.toString());
        String url = "http://www.1v1.one:9191/hxj_srv/spread/v2/get_list/";
        ApiClient.retrofit()
                .create(ApiService.class)
                .getWeatherDataByRetrofitRxjava(url, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
