package com.maning.baseapplication;

import android.app.Application;

import com.common.baselibrary.BaseManager;
import com.common.httplibrary.HttpManager;
import com.common.refreshlibrary.CommonRefreshLayout;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by maning on 2018/1/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //初始化
        BaseManager.init(this, "---LogTag---", true);
        HttpManager.init(this);
        CommonRefreshLayout.initRefresh();
    }
}
