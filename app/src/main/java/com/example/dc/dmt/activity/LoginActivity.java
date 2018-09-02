package com.example.dc.dmt.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dc.dmt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DC on 2018/9/2.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_pwd_eye)
    CheckBox cbPwdEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    protected void initVariables() {
        super.initVariables ();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView (R.layout.activity_login);
        ButterKnife.bind (this);
        cbPwdEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    Editable editable = etPsd.getText();
                    Selection.setSelection(editable, editable.length());
                } else {
                    etPsd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Editable editable = etPsd.getText();
                    Selection.setSelection(editable, editable.length());
                }
            }
        });

    }

    @Override
    public void loadData() {
        super.loadData ();
        //Todo:加载数据
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty (etUserName.getText ())) {
                    Toast.makeText (this, "请输入用户名或邮箱", Toast.LENGTH_SHORT).show ();
                } else if (TextUtils.isEmpty (etPsd.getText ())) {
                    Toast.makeText (this, "请输入密码", Toast.LENGTH_SHORT).show ();
                } else {
                    //todo:登录
                    Toast.makeText (this, "登录", Toast.LENGTH_SHORT).show ();
                }
                break;

        }

    }

}
