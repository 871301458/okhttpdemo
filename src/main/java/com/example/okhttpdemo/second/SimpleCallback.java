package com.example.okhttpdemo.second;

import android.content.Context;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuyin on 2016/5/20.
 */
public  abstract class SimpleCallback<T> extends BaseCallback<T> {

    protected Context mContext;

    public SimpleCallback(Context context){

        mContext = context;

    }
    @Override
    public void onBeforeRequest(Request request) {

    }


    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) {
    }

}
