package com.maning.baseapplication.base;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.common.mvplibrary.BasePresenter;
import com.common.mvplibrary.MvpActivity;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity<P extends BasePresenter> extends MvpActivity<P> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

    @Override
    public void showLoading() {
        ToastUtils.showShort("showLoading");
    }

    @Override
    public void hideLoading() {
        ToastUtils.showShort("hideLoading");
    }

}
