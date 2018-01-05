package com.common.httplibrary;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * Created by maning on 2018/1/4.
 */

public class HttpManager {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("HttpManager should init first");
    }

}
