package com.maning.baseapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.common.httplibrary.callback.AbsOKHttpCallBack;
import com.common.httplibrary.callback.AbsRetrofitCallback;
import com.common.httplibrary.callback.AbsRxCallback;
import com.maning.baseapplication.R;
import com.maning.baseapplication.callback.CommonCallBack;
import com.maning.baseapplication.http.HttpUtils;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.HttpResponse;
import com.orhanobut.logger.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn01(View view) {

        AbsRetrofitCallback<HttpResponse<List<GankModel>>> callback = new AbsRetrofitCallback<HttpResponse<List<GankModel>>>() {
            @Override
            public void onSuccess(HttpResponse<List<GankModel>> model) {
                Logger.i("onSuccess:" + model.getResults().toString());
                ToastUtils.showShort(model.getResults().toString());
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
        AbsRxCallback<HttpResponse<List<GankModel>>> rxCallback = new AbsRxCallback<HttpResponse<List<GankModel>>>() {
            @Override
            public void onSuccess(HttpResponse<List<GankModel>> model) {
                Logger.i("onSuccess:" + model.getResults().toString());
                ToastUtils.showShort(model.getResults().toString());
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
        HttpUtils.getOtherDatas(rxCallback);
    }

    public void btn06(View view) {
        AbsOKHttpCallBack<HttpResponse<List<GankModel>>> OKHttpCallBack = new AbsOKHttpCallBack<HttpResponse<List<GankModel>>>(HttpResponse.class) {

            @Override
            public void onSuccess(HttpResponse<List<GankModel>> model) {
                Logger.i("onSuccess:" + model.getResults().toString());
                ToastUtils.showShort(model.getResults().toString());
            }

            @Override
            public void onFail(String errorCode, String errorMsg) {
                Logger.i("onFailure:" + errorMsg);
                ToastUtils.showShort(errorMsg);
            }

            @Override
            public void onFinish() {
                Logger.i("onFinish");
            }
        };

        CommonCallBack<List<GankModel>> okHttpCallBack = new CommonCallBack<List<GankModel>>(HttpResponse.class) {

            @Override
            public void onFail(String errorCode, String errorMsg) {
                Logger.i("onFailure:" + errorMsg);
                ToastUtils.showShort(errorMsg);
            }

            @Override
            public void onFinish() {
                Logger.i("onFinish");
            }

            @Override
            public void onSuccess2(List<GankModel> result) {
                Logger.i("onSuccess:" + result.toString());
                ToastUtils.showShort(result.toString());
            }
        };
        HttpUtils.getOtherDatas2(okHttpCallBack);
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
