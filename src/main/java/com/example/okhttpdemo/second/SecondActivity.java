package com.example.okhttpdemo.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okhttpdemo.Contants;
import com.example.okhttpdemo.R;

import java.io.IOException;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity {

    @butterknife.Bind(R.id.user_id)
    TextView mUserId;
    @butterknife.Bind(R.id.user_name)
    TextView mUserName;
    @butterknife.Bind(R.id.user_blog)
    TextView mUserBlog;
    @butterknife.Bind(R.id.user_email)
    TextView mUserEmail;
    @butterknife.Bind(R.id.btnQuery)
    Button mBtnQuery;


    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        butterknife.ButterKnife.bind(this);

    }

    User mUser = null;

    @OnClick(R.id.btnQuery)
    public void btnQuery(){
        mHttpHelper.get(Contants.JACK_URL, new BaseCallback<List<User>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, List<User> users) {
                mUser = users.get(0);
                updateUi(mUser);
            }


            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(SecondActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateUi(User mUser){
        mUserId.setText("ID:" + mUser.getId());
        mUserName.setText("名字:" + mUser.getName());
        mUserBlog.setText("博客地址:"+mUser.getBlog());
        mUserEmail.setText("邮箱:"  +mUser.getEmail());
    }

}
