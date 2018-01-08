package com.maning.baseapplication.ui;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.common.httplibrary.ApiClient;
import com.common.httplibrary.callback.RetrofitCallback;
import com.common.httplibrary.callback.RxCallback;
import com.maning.baseapplication.R;
import com.maning.baseapplication.http.ApiService;
import com.maning.baseapplication.http.HttpUtils;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.OtherModel;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn01(View view) {

        RetrofitCallback<GankModel> callback = new RetrofitCallback<GankModel>() {
            @Override
            public void onSuccess(GankModel model) {
                Logger.i("onSuccess:" + model.toString());
                ToastUtils.showShort(model.toString());
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                Logger.i("onFailure:" + errorMsg);
                ToastUtils.showShort(errorMsg);
            }

            @Override
            public void onFinish() {
                Logger.i("onFinish");
            }
        };
        HttpUtils.getGankDatas(callback);
    }

    public void btn02(View view) {
        RxCallback<OtherModel> rxCallback = new RxCallback<OtherModel>() {
            @Override
            public void onSuccess(OtherModel model) {
                Logger.i("onSuccess:" + model.toString());
                ToastUtils.showShort(model.toString());
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                Logger.i("onFailure:" + errorMsg);
                ToastUtils.showShort(errorMsg);
            }

            @Override
            public void onFinish() {
                Logger.i("onFinish");
            }
        };
        HttpUtils.getWeatherDatas(rxCallback);
    }

    public void btn03(View view) {
        startActivity(new Intent(this, RefreshActivity.class));
    }

    public void btn04(View view) {
        startActivity(new Intent(this, Refresh2Activity.class));
    }

    public void btn05(View view) {
        startActivity(new Intent(this, MvpTestActivity.class));
    }

}
