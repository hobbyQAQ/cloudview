package com.example.cloudview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudview.Api.UserService;
import com.example.cloudview.R;
import com.example.cloudview.model.UserResult;
import com.example.cloudview.utils.LogUtil;
import com.example.cloudview.utils.RetrofitCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ImageView login_button;
    EditText account_tv;
    EditText password_tv;
    TextView register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initView();
    }

    private void init() {
        login_button=findViewById(R.id.login_button);
        password_tv=findViewById(R.id.password_tv);
        account_tv=findViewById(R.id.account_tv);
        register_button=findViewById(R.id.register_button);
    }

    private void initView() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里直接掉用逻辑控制界面，没有采用MVP的结构
                Retrofit retrofit = RetrofitCreator.getInstance().getRetrofit();
                UserService userService = retrofit.create(UserService.class);
                Call<UserResult> login = userService.login(account_tv.getText().toString(), password_tv.getText().toString());
                login.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call, Response<UserResult> response) {

                        UserResult userResult = response.body();
                        LogUtil.d(LoginActivity.this,userResult.toString());
                        if (userResult != null && userResult.isSuccess()) {
                            //把用户信息保存到SharePrefrecence里面
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("user",userResult.getData());
                            startActivity(intent);
                            // TODO: 2020/6/3 之后MainActivity 的返回界面不是LoginActivity

                        }else{
                            //登录失败的逻辑
                            Toast.makeText(LoginActivity.this, "登录失败,账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                            //网络错误的逻辑
                        Toast.makeText(LoginActivity.this, "服务器没开，或网络未连接", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

}
