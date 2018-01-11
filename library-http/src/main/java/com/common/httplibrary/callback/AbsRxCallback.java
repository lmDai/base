package com.common.httplibrary.callback;


import com.common.httplibrary.constant.HttpErrorConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Rxjava回调
 */
public abstract class AbsRxCallback<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String errorCode,String errorMsg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable exception) {
        exception.printStackTrace();
        String errorCode = HttpErrorConstants.ERR_NETEXCEPTION_ERROR_CODE;
        String errorMsg;
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            //网络未连接
            errorMsg = HttpErrorConstants.ERR_UNKNOWNHOSTEXCEPTION_ERROR;
        } else if (exception instanceof SocketTimeoutException) {
            //连接超时
            errorMsg = HttpErrorConstants.ERR_SOCKETTIMEOUTEXCEPTION_ERROR;
        } else {
            //其他网络异常
            errorMsg = HttpErrorConstants.ERR_NETEXCEPTION_ERROR;
        }
        onFailure(errorCode,errorMsg);
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
