package com.hjl.httpdemo.api;

import com.hjl.httpdemo.activity.login.LoginBean;
import com.hjl.httpdemo.activity.login.UserBean;
import com.hjl.lib_http.base.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * api service
 */
public interface ApiService {


    @POST("test/login")
    Observable<BaseResponse<LoginBean>> login(@QueryMap Map<String, String> map);

    @POST("query")
    Observable<BaseResponse<List<LoginBean>>> logout(@QueryMap Map<String, String> map);

    @POST("test/list")
    Observable<BaseResponse<List<UserBean>>> getUserList(@QueryMap Map<String, String> map);

//    // 登录的请求
//    @POST("loginManage/login")
//    Observable<BaseResponse<LoginBean>> login(@QueryMap Map<String, String> map);

//    //上传图片
//    @POST("file/up")
//    @Multipart
//    Observable<BaseResponse<List<UploadFile>>> upload(@Part List<MultipartBody.Part> parts);


    @GET("wxarticle/chapters/json")
    Observable<BaseResponse> getChapters();
}
