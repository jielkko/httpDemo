package com.hjl.lib_http.base;

import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hjl.lib_http.gson.DoubleDefault0Adapter;
import com.hjl.lib_http.gson.IntegerDefault0Adapter;
import com.hjl.lib_http.gson.LongDefault0Adapter;
import com.hjl.lib_http.utils.HttpApplicationUtil;
import com.hjl.lib_http.utils.NetWorkUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 网络对象层
 */
public class BaseApi {
    private String TAG = "BaseApi";
    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 15;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 15;
    private static Gson gson;
    /**
     * 无超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @return retrofit
     */
    public Retrofit getSimpleRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0,
                // RxJava1.x则为RxJavaCallAdapterFactory.create()
                .build();
        return retrofit;
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @return retrofit
     */
    public Retrofit getRetrofit(String baseUrl) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(HttpApplicationUtil.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")//设置允许请求json数据
//                        .addHeader("","")//自定义 配置header信息
                        .build();
                return chain.proceed(build);
            }
        };

        //创建一个OkHttpClient并设置超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)//没网的情况下
                //.addNetworkInterceptor(interceptor)//有网的情况下
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                //.addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addConverterFactory(GsonConverterFactory.create(buildGson()))//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0,
                // RxJava1.x则为RxJavaCallAdapterFactory.create()
                .build();
        return retrofit;

    }

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .create();
        }
        return gson;
    }

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 请求访问quest    打印日志
     * response拦截器
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();

            //Logger.wtf(TAG, "----------Request Start----------------");
            //Logger.e(TAG, "| " + request.toString() + "===========" + request.headers().toString());
            Logger.json(content);
            //Logger.wtf(TAG, "----------Request End:" + duration + "毫秒----------");

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();//拦截器获取请求
            String cacheControl = request.cacheControl().toString();//服务器的缓存策略
            if (!NetWorkUtil.isNetConnected(HttpApplicationUtil.getContext())) {//断网时配置缓存策略
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ?
                                CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
//                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);


            if (NetWorkUtil.isNetConnected(HttpApplicationUtil.getContext())) {//在线缓存
//                KLog.e("在线缓存2分钟");
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl)//应用服务端配置的缓存策略
//                        .header("Cache-Control", "public, max-age=" + 60 * 2)//有网的时候连接服务器请求,缓存(时间)
                        .build();
            } else {//离线缓存
                /**
                 * only-if-cached:(仅为请求标头)
                 *　 请求:告知缓存者,我希望内容来自缓存，我并不关心被缓存响应,是否是新鲜的.
                 * max-stale:
                 *　 请求:意思是,我允许缓存者，发送一个,过期不超过指定秒数的,陈旧的缓存.
                 *　 响应:同上.
                 * max-age:
                 *   请求:强制响应缓存者，根据该值,校验新鲜性.即与自身的Age值,与请求时间做比较.如果超出max-age值,
                 *   则强制去服务器端验证.以确保返回一个新鲜的响应.
                 *   响应:同上.
                 */
                //需要服务端配合处理缓存请求头，不然会抛出： HTTP 504 Unsatisfiable Request (only-if-cached)
//                KLog.e("离线缓存"+CACHE_STALE_SEC+"秒");
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)//CACHE_STALE_SEC
                        .build();
            }
        }
    };
}