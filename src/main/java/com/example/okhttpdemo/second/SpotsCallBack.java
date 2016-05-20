package com.example.okhttpdemo.second;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuyin on 2016/5/20.
 */
public abstract class SpotsCallBack<T> extends SimpleCallback {

    private SpotsDialog mDialog;


    public SpotsCallBack(Context context){
        super(context);

        initSpotsDialog();

    }


    private  void initSpotsDialog(){

        mDialog = new SpotsDialog(mContext,"拼命加载中...");
    }

    public  void showDialog(){
        mDialog.show();
    }

    public  void dismissDialog(){
        mDialog.dismiss();
    }


    public void setLoadMessage(int resId){
        mDialog.setMessage(mContext.getString(resId));
    }


    @Override
    public void onBeforeRequest(Request request) {
        showDialog();
    }

    @Override
    public void onResponse(Response response) {
        SystemClock.sleep(1000);
        dismissDialog();
    }


}
