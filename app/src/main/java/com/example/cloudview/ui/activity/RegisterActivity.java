package com.example.cloudview.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.back_tv)
    TextView backTv;
    @BindView(R.id.head_container)
    LinearLayout headContainer;
    @BindView(R.id.account_tv)
    EditText accountTv;
    @BindView(R.id.container_account)
    LinearLayout containerAccount;
    @BindView(R.id.verification)
    EditText verification;
    @BindView(R.id.get_verification)
    Button getVerification;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.register_button)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_verification, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verification:
                break;
            case R.id.register_button:
                break;
        }
    }
}
