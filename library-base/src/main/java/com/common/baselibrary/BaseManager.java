package com.common.baselibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.StrictMode;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by maning on 2018/1/4.
 * 管理者：用于初始化相关
 */

public class BaseManager {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    public static void init(Application application, String LogTag,boolean isDebug) {
        sApplication = application;
        /*日志初始化*/
        initLog(LogTag, isDebug);
        /*工具类初始化*/
        initCommonUtils();
        /*数据存储初始化*/
        initCache();
    }

    private static void initCache() {
        Hawk.init(sApplication);
    }

    private static void initCommonUtils() {
        Utils.init(sApplication);
    }

    private static void initLog(String LogTag, final boolean isDebug) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .methodOffset(0)
                .tag(LogTag)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
        Logger.i("----------日志初始化成功---------");
    }
}
