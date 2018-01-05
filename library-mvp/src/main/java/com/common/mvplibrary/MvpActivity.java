package com.common.mvplibrary;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/01/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class MvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P presenter;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();//在presenter中解绑释放view
            presenter = null;
        }
        super.onDestroy();
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

    /**
     * 显示加载框
     */
    public abstract void showLoading();

    /**
     * 隐藏加载框
     */
    public abstract void hideLoading();

}
