package com.maning.baseapplication.mvp;

import com.common.httplibrary.callback.RxCallback;
import com.common.mvplibrary.BasePresenter;
import com.maning.baseapplication.http.HttpUtils;
import com.maning.baseapplication.model.OtherModel;
import com.orhanobut.logger.Logger;

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
        RxCallback<OtherModel> rxCallback = new RxCallback<OtherModel>() {
            @Override
            public void onSuccess(OtherModel model) {
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
        HttpUtils.getWeatherDatas(rxCallback);
        addDisposable(rxCallback);
    }

}
