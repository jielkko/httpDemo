package com.hjl.httpdemo.activity.login;

import android.content.Context;


import com.hjl.lib_http.base.BaseResponse;
import com.hjl.lib_http.progress.ObserverResponseListener;
import com.hjl.lib_http.utils.ExceptionHandle;
import com.hjl.lib_http.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;


public class LoginPresenter extends LoginContract.Presenter {

    private LoginModel model;
    private Context context;

    public LoginPresenter(Context context) {
        this.model = new LoginModel();
        this.context = context;
    }

    @Override
    public void login(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.login(context, map, isDialog, cancelable, getView().bindLifecycle(),new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if(getView() != null){
                    getView().result((BaseResponse<LoginBean>) o);
                    getView().setMsg("请求成功");
                }
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if(getView() != null){
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShort(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }


    @Override
    public void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.logout(context, map, isDialog, cancelable, getView().bindLifecycle(),new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if(getView() != null){
                    getView().logoutResult((BaseResponse<List<LoginBean>>) o);
                    getView().setMsg("请求成功");
                }
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if(getView() != null){
                    ToastUtil.showShort(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void getUserList(HashMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getUserList(context, map, isDialog, cancelable, getView().bindLifecycle(),new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if(getView() != null){
                    getView().getUserList((BaseResponse<List<UserBean>>) o);
                    getView().setMsg("请求成功");
                }
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if(getView() != null){
                    ToastUtil.showShort(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void getChapters(boolean isDialog, boolean cancelable) {
        model.getChapters(context,isDialog, cancelable, getView().bindLifecycle(),new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if(getView() != null){
                    BaseResponse<List<LoginBean>> response = (BaseResponse<List<LoginBean>>) o;
                    if(response.getStatus().getCode() == 0){
                        getView().getChaptersResult(response);
                        getView().setMsg("请求成功");
                    }else {
                        getView().setMsg("请求失败,errorCode: "+response.getStatus().getCode());
                    }
                }
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if(getView() != null){
                    ToastUtil.showShort(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

}

