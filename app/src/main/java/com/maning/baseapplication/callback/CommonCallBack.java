package com.maning.baseapplication.callback;

import com.common.httplibrary.callback.AbsOKHttpCallBack;
import com.maning.baseapplication.model.HttpResponse;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class CommonCallBack<T> extends AbsOKHttpCallBack<HttpResponse> {

    public CommonCallBack(Type type) {
        super(type);
    }

    public CommonCallBack() {
        super();
    }

    @Override
    public void onSuccess(HttpResponse result) {
        T data = (T) result.getResults();
        onSuccess2(data);
    }

    /**
     * 成功
     */
    public abstract void onSuccess2(T result);

}
