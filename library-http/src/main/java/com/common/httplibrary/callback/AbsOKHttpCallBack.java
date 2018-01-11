package com.common.httplibrary.callback;

import android.os.Handler;
import android.util.Log;

import com.common.httplibrary.constant.HttpErrorConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class AbsOKHttpCallBack<T extends Object> implements Callback {

    private static final String TAG = "OKhttpCallBack";

    private Handler handler;
    private Type type;

    public AbsOKHttpCallBack(Type type) {
        handler = new Handler();
        this.type = type;
    }

    public AbsOKHttpCallBack() {
        handler = new Handler();
    }

    @Override
    public void onFailure(final Call call, final IOException exception) {
        Log.i(TAG, "onFailure:" + exception.toString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (call.isCanceled()) {
                    //取消请求
                    onFinish();
                    return;
                }
                //处理错误信息
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
                onFail(errorCode, errorMsg);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (200 == response.code()) {
            String body = response.body().string();
            String url = response.request().url().toString();
            Log.i(TAG, "onResponse-----url:" + url + "code:" + response.code() + "\nbody:" + body);
            //解析
            T result;
            boolean isGsonException = false;
            try {
                if (type != null) {
                    result = new Gson().fromJson(body, type);
                } else {
                    result = (T) new Gson().fromJson(body, getClasses());
                }
            } catch (Exception e) {
                isGsonException = true;
                result = null;
            }
            final T resultFinal = result;
            final boolean isGsonExceptionFinal = isGsonException;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (resultFinal != null) {
                        onSuccess(resultFinal);
                    } else {
                        if (isGsonExceptionFinal) {
                            //解析异常
                            onFail(HttpErrorConstants.ERR_HTTPRESPONSE_JSONPARSE_ERROR_CODE, HttpErrorConstants.ERR_HTTPRESPONSE_JSONPARSE_ERROR);
                        } else {
                            onFail(HttpErrorConstants.ERR_HTTPRESPONSE_ERROR_CODE, HttpErrorConstants.ERR_HTTPRESPONSE_ERROR);
                        }
                    }
                }
            });
        } else {
            String url = response.request().url().toString();
            Log.i(TAG, "onResponse-----url:" + url + "code:" + response.code());
            //接口异常
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //解析异常
                    onFail(response.code() + "", HttpErrorConstants.ERR_NETEXCEPTION_ERROR);
                }
            });
        }
        //完成
        onFinish();
    }

    private Class getClasses() {
        Type t = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        Class<T> cls = (Class<T>) params[0];
        return cls;
    }

    /**
     * 成功
     */
    public abstract void onSuccess(T result);

    /**
     * 失败
     */
    public abstract void onFail(String errorCode, String errorMsg);

    /**
     * 完成
     */
    public abstract void onFinish();

}
