package com.hjl.httpdemo.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjl.httpdemo.R;

import com.hjl.httpdemo.activity.login.LoginBean;
import com.hjl.httpdemo.activity.login.LoginContract;
import com.hjl.httpdemo.activity.login.UserBean;
import com.hjl.httpdemo.activity.login.LoginPresenter;
import com.hjl.lib_http.base.BaseFragment;
import com.hjl.lib_http.base.BaseResponse;
import com.hjl.lib_http.utils.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.HashMap;
import java.util.List;


import io.reactivex.ObservableTransformer;

/**
 * 作者：senon on 2017/12/27 16:36
 * 邮箱：a1083911695@163.com
 */

public class LoginFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    private Button mFragmentCheckBtn;
    private TextView mFragmentMsgTv;




    @Override
    public int getLayoutId() {
        return R.layout.fragment_layout;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        mFragmentCheckBtn = (Button) getView().findViewById(R.id.fragment_check_btn);
        mFragmentMsgTv = (TextView) getView().findViewById(R.id.fragment_msg_tv);

        mFragmentCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentMsgTv.setText("");
                HashMap<String,String> map = new HashMap<>();
                map.put("type","yuantong");
                map.put("postid","11111111111");
                getPresenter().login(map,true,false);
            }
        });
    }

    @Override
    public void result(BaseResponse<LoginBean> data) {
        mFragmentMsgTv.setText(data.getData().toString());
    }

    @Override
    public void getUserList(BaseResponse<List<UserBean>> data) {
        mFragmentMsgTv.setText(data.getData().toString());
    }

    @Override
    public void getChaptersResult(BaseResponse<List<LoginBean>> data) {
        mFragmentMsgTv.setText(data.getData().toString());
    }

    @Override
    public void logoutResult(BaseResponse data) {

    }

    @Override
    public void setMsg(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }


}
