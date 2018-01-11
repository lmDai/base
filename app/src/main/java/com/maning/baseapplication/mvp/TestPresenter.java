package com.maning.baseapplication.mvp;

import com.common.httplibrary.callback.AbsRxCallback;
import com.common.mvplibrary.BasePresenter;
import com.maning.baseapplication.http.HttpUtils;
import com.maning.baseapplication.model.GankModel;
import com.maning.baseapplication.model.HttpResponse;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestPresenter extends BasePresenter<TestView> {

    public TestPresenter(TestView testView) {
        attachView(testView);
    }

    public void getDatas() {
        AbsRxCallback<HttpResponse<List<GankModel>>> rxCallback = new AbsRxCallback<HttpResponse<List<GankModel>>>() {
            @Override
            public void onSuccess(HttpResponse<List<GankModel>> model) {
                mView.showToast("onSuccess:" + model.toString());
                Logger.i("onSuccess:" + model.toString());
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                mView.showToast("onFailure:" + errorMsg);
                Logger.i("onFailure:" + errorMsg);
            }

            @Override
            public void onFinish() {
                Logger.i("onFinish");
            }
        };
        HttpUtils.getOtherDatas(rxCallback);
        addDisposable(rxCallback);
    }

}
