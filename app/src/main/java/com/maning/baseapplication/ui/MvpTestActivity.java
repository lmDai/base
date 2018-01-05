package com.maning.baseapplication.ui;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.maning.baseapplication.R;
import com.maning.baseapplication.base.BaseActivity;
import com.maning.baseapplication.mvp.TestPresenter;
import com.maning.baseapplication.mvp.TestView;

/**
 * 测试Mvp
 */
public class MvpTestActivity extends BaseActivity<TestPresenter> implements TestView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);

    }

    public void btn01(View view){
        presenter.getDatas();
    }

    @Override
    public TestPresenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }
}
