package com.example.cloudview.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudview.R;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.cloudview.base.BaseApplication.getContext;

public class MyInfoActivity extends AppCompatActivity {

    @BindView(R.id.back_tv)
    TextView backTv;
    @BindView(R.id.head_container)
    LinearLayout headContainer;
    @BindView(R.id.nickname_text)
    TextView nicknameText;
    @BindView(R.id.sex_text)
    TextView sexText;
    @BindView(R.id.birthday_text)
    TextView birthdayText;
    @BindView(R.id.signature_text)
    TextView signatureText;
    private Unbinder mBind;
    Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);


    @BindView(R.id.item_my_icon)
    public RelativeLayout mItemMyIcon;

    @BindView(R.id.item_my_nickname)
    public RelativeLayout mItemMyNickName;

    @BindView(R.id.item_my_gender)
    public RelativeLayout mItemMyGender;

    @BindView(R.id.item_my_birthday)
    public RelativeLayout mItemMyBirthday;

    @BindView(R.id.item_my_position)
    public RelativeLayout mItemMyPosition;

    @BindView(R.id.item_my_motto)
    public RelativeLayout mItemMyMotto;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        mBind = ButterKnife.bind(this);//记得解绑
        mContext = getContext();
        initView();
        initListener();
    }

    private void initListener() {
        mItemMyNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(MyInfoActivity.this).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setNickName(myAlertInputDialog.getResult());
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();

            }
        });
        mItemMyMotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(MyInfoActivity.this).builder()
                        .setTitle("请输入")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSignature(myAlertInputDialog.getResult());
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();

            }
        });

        mItemMyBirthday.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MyInfoActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                final String data = mYear + "-"+(month + 1) + "-" + dayOfMonth;
                                datePickHandle(data);
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setSignature(String result) {
        signatureText.setText(result);
    }

    private void datePickHandle(String data) {
        birthdayText.setText(data);
    }

    private void setNickName(String result) {
        nicknameText.setText(result);
    }

    private void initView() {
    }
}
