package com.example.okhttpdemo.second;



import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuyin on 2016/5/20.
 */
public class OkHttpHelper {

    private Gson mGson;  //解析对象  begin  array

    private Handler mHandler;   //请求数据成功后返回到主线程

    //okhttp实例
    private static OkHttpClient okHttpHelper;

    //私有构造方法
    private OkHttpHelper() {

        mGson = new Gson();

        okHttpHelper = new OkHttpClient();
        //设置请求超时
        okHttpHelper.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpHelper.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        okHttpHelper.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

        mHandler = new Handler(Looper.getMainLooper());
    }


    //获取实例
    public static OkHttpHelper getInstance() {


        return new OkHttpHelper();
    }

    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        doRequest(request, callback);
    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request, callback);
    }

    /**
     * 请求的方法
     *
     * @param request
     */
    public void doRequest(final Request request, final BaseCallback callback) {
        okHttpHelper.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String resultStr = response.body().string();
                    if (callback.mType == String.class) {
                        callbackSuccess(callback,response,resultStr);
                    } else {
                        Object object = mGson.fromJson(resultStr, callback.mType);
                        try {
                            callbackSuccess(callback,response,object);
                        } catch (JsonParseException e) {
                           callbackError(callback, response, e);
                        }
                    }

                } else {
                   callbackError(callback, response, null);
                }

            }
        });
    }

    private Request buildRequest(String url, Map<String, String> params, HttpMethodType methodType) {


        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (methodType == HttpMethodType.GET) {
            builder.get();

        } else if (methodType == HttpMethodType.POST) {
            RequestBody body = buildFormData(params);
            builder.post(body);
        }

        return builder.build();
    }


    /**
     * 构建参数body实体
     *
     * @param params
     * @return
     */
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            /**
             * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
             */
            for (Map.Entry<String, String> map : params.entrySet()) {
                String key = map.getKey().toString();
                String value = null;
                /**
                 * 判断值是否是空的
                 */
                if (map.getValue() == null) {
                    value = "";
                } else {
                    value = map.getValue();
                }
                /**
                 * 把key和value添加到formbody中
                 */
                builder.add(key, value);
            }

        }
        return builder.build();
    }


    private void callbackSuccess(final BaseCallback callback, final Response response, final Object object){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                    callback.onSuccess(response,object);
            }
        });
    }

    private void callbackError(final  BaseCallback callback , final Response response, final Exception e ){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }


    /**
     * 请求方式，在这里只写get和post
     */
    enum HttpMethodType {
        GET, POST
    }

}
