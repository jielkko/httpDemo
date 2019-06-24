package com.hjl.httpdemo.api;


import com.hjl.lib_http.base.BaseApi;

/**
 *
 */

public class Api {

    private String baseUrl = "http://192.168.1.107:8989/";
    //private String baseUrl = "http://www.wanandroid.com/";


    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(baseUrl).create(ApiService.class);
    }
}
