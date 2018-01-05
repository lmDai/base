package com.maning.baseapplication.http;

import com.common.httplibrary.ApiClient;
import com.common.httplibrary.callback.RetrofitCallback;
import com.common.httplibrary.callback.RxCallback;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.OtherModel;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        Call<GankModel> call = ApiClient.retrofit().create(ApiService.class).loadDataByRetrofit("http://gank.io/api/data/Android/10/1");
        call.enqueue(callback);
    }

    public static void getWeatherDatas(Observer observer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", "4");
            jsonObject.put("pageNo", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        ApiClient.retrofit()
                .create(ApiService.class)
                .loadDataByRetrofitRxjava("http://www.1v1.one:9191/hxj_srv/spread/v2/get_list/", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
