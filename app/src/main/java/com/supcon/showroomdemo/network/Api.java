package com.supcon.showroomdemo.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supcon.showroomdemo.App;
import com.supcon.showroomdemo.util.IPUtil;
import com.yaobing.module_middleware.Utils.LogUtil;
import com.yaobing.module_middleware.network.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : yaobing
 * @date : 2020/12/15 10:48
 * @desc :
 */
public class Api {
    public Retrofit retrofit;
    public ApiService service;
    private static Interceptor networkInterceptor;//自定义的网络拦截器，处理重定向
    private static Interceptor headInterceptor;//自定义的head拦截器，模块单独调试的时候用
    private static Interceptor responseInterceptor;//自定义的head拦截器，模块单独调试的时候用
    private boolean isDebug = true;
    private static boolean followRedirects = true;
    private final int DEFAULT_TIME_OUT_SECONDS = 30;
    private int mTimeOut = DEFAULT_TIME_OUT_SECONDS;

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //构造方法私有
    private Api() {
        build();
    }

    private void build() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(message);
            }
        });
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        logInterceptor.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .followRedirects(followRedirects)
                .readTimeout(mTimeOut, TimeUnit.SECONDS)
                .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                .writeTimeout(mTimeOut, TimeUnit.SECONDS)
//                .addInterceptor(headInterceptor == null?new HeadInterceptor():headInterceptor)
//                .addInterceptor(new RedirectInterceptor())
//                .addInterceptor(responseInterceptor==null?new LoginInterceptor():responseInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
//                .cookieJar(new LocalCookieJar())
//                .hostnameVerifier(SSLSocketFactoryHelper.getInstance().getHostnameVerifier())
//                .sslSocketFactory(SSLSocketFactoryHelper.getInstance().getmSslSocketFactory(),
//                        SSLSocketFactoryHelper.getInstance().getmTrustManager())
                ;

        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(IPUtil.getHost(App.getAppContext()))
                .build();
        service = retrofit.create(ApiService.class);
    }

    public void rebuild() {
        build();
    }

    public void setTimeOut(int timeOut) {
        this.mTimeOut = timeOut;
        rebuild();
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
        rebuild();
    }

    /**
     * 模块单独调试的时候用来导入自定义的HeadInterceptor
     *
     * @param interceptor 拦截器
     */
    public static void setHeadInterceptor(Interceptor interceptor) {
        headInterceptor = interceptor;
    }

    /**
     * networkInterceptor用来处理重定向等操作
     *
     * @param interceptor 网络拦截器
     */
    public static void setNetworkInterceptor(Interceptor interceptor) {
        networkInterceptor = interceptor;
    }

    /**
     * 模块单独调试的时候用来导入自定义的HeadInterceptor
     *
     * @param interceptor 拦截器
     */
    public static void setResponseInterceptor(Interceptor interceptor) {
        responseInterceptor = interceptor;
    }
}
