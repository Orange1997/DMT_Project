package com.example.dc.dmt.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dc.dmt.R;
import com.example.dc.dmt.ui.CenterTitleActionbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DC on 2018/9/2.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CenterTitleActionbar toolbar;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.cb_pwd_eye)
    CheckBox cbPwdEye;
    @BindView(R.id.et_confirm_psd)
    EditText etConfirmPsd;
    @BindView(R.id.cb_pwd_eye_confirm)
    CheckBox cbPwdEyeConfirm;
    @BindView(R.id.btn_register)
    Button btnRegister;

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
        setContentView (R.layout.activity_register);
        ButterKnife.bind (this);
        initActionBar (toolbar);
        toolbar.setTitle ("注  册");
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
        cbPwdEyeConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etConfirmPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    Editable editable = etPsd.getText();
                    Selection.setSelection(editable, editable.length());
                } else {
                    etConfirmPsd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Editable editable = etPsd.getText();
                    Selection.setSelection(editable, editable.length());
                }
            }
        });
        etPsd.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cbPwdEye.setVisibility (View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty (etPsd.getText ())){
                    cbPwdEye.setVisibility (View.GONE);
                }else {
                    cbPwdEye.setVisibility (View.VISIBLE);
                }
            }
        });
        etConfirmPsd.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cbPwdEyeConfirm.setVisibility (View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty (etPsd.getText ())){
                    cbPwdEyeConfirm.setVisibility (View.GONE);
                }else {
                    cbPwdEyeConfirm.setVisibility (View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData ();
        //Todo:加载数据
    }

    @OnClick({R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.btn_register:
                if (TextUtils.isEmpty (etUserName.getText ())) {
                    Toast.makeText (this, "请输入用户名", Toast.LENGTH_SHORT).show ();
                } else if (TextUtils.isEmpty (etEmail.getText ())) {
                    Toast.makeText (this, "请输入邮箱", Toast.LENGTH_SHORT).show ();
                } else if (TextUtils.isEmpty (etPsd.getText ())) {
                    Toast.makeText (this, "请输入密码", Toast.LENGTH_SHORT).show ();
                } else if (TextUtils.isEmpty (etConfirmPsd.getText ())) {
                    Toast.makeText (this, "请输入确认密码", Toast.LENGTH_SHORT).show ();
                } else if (!etPsd.getText ().toString ().equals (etConfirmPsd.getText ().toString ())) {
                    Toast.makeText (this, "两次密码不一致", Toast.LENGTH_SHORT).show ();
                } else {
                    //todo:注册
                    Toast.makeText (this, "注册", Toast.LENGTH_SHORT).show ();
                }
                break;
        }

    }

}
