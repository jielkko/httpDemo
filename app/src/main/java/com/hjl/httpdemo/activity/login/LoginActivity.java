package com.hjl.httpdemo.activity.login;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hjl.httpdemo.R;

import com.hjl.httpdemo.fragment.LoginFragment;
import com.hjl.httpdemo.listener.OnMultiClickListener;
import com.hjl.lib_http.base.BaseActivity;
import com.hjl.lib_http.base.BaseResponse;
import com.hjl.lib_http.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableTransformer;

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {


    private Button mMainCheckBtn;
    private Button mMainCheck2Btn;
    private TextView mMainMsgTv;
    private FrameLayout mFrameLay;



    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {

        mMainCheckBtn = (Button) findViewById(R.id.main_check_btn);
        mMainCheck2Btn = (Button) findViewById(R.id.main_check2_btn);
        mMainMsgTv = (TextView) findViewById(R.id.main_msg_tv);
        mFrameLay = (FrameLayout) findViewById(R.id.frame_lay);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frame_lay, new LoginFragment()).
                commitAllowingStateLoss();

        mMainCheckBtn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "yuantong");
                map.put("password", "11111111111");
                getPresenter().login(map, true, false);
            }

        });

        mMainCheck2Btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "yuantong");
                map.put("password", "11111111111");
                getPresenter().getUserList(map, true, false);
            }

        });

    }


    @Override
    public void result(BaseResponse<LoginBean> data) {
        mMainMsgTv.setText(data.getData().toString());
    }

    @Override
    public void logoutResult(BaseResponse<List<LoginBean>> data) {
        ////todo 第二个请求结果。。。
        mMainMsgTv.setText(data.getData().toString());
    }

    @Override
    public void getUserList(BaseResponse<List<UserBean>> data) {

        mMainMsgTv.setText(data.getData().toString());
    }

    @Override
    public void getChaptersResult(BaseResponse<List<LoginBean>> data) {
        mMainMsgTv.setText(data.getData().toString());
    }

    @Override
    public void setMsg(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
//        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }
}
