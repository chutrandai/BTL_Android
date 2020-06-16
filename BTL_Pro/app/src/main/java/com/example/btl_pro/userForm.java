package com.example.btl_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class userForm extends AppCompatActivity {
    EditText fUserName;
    EditText fPassWord;
    Button btnSave;
    Button btnCancel;
    loginActionDB loginActionDB;
    ArrayList<userBean> dataList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        constructorArrt();
        bindBtnSave();
        bindBtnCancel();
    }
    public void constructorArrt() {
        fUserName = (EditText) findViewById(R.id.fUserName);
        fPassWord = (EditText) findViewById(R.id.fPassWord);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }
    public void bindBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBeforeSave()) {
                    loginActionDB = new loginActionDB(userForm.this);
                    userBean user = new userBean(fUserName.getText().toString(), fPassWord.getText().toString());
                    long id = loginActionDB.insert(user);
                    if (id > 0L) {
                        Toast.makeText(userForm.this, "Thêm mới thành công!",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(userForm.this, userController.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(userForm.this, "Có lỗi xảy ra!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public Boolean checkBeforeSave() {
        if (commonUtil.isNullOrEmpTy(fUserName.getText().toString())
                || commonUtil.isNullOrEmpTy(fPassWord.getText().toString())) {
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được để trống!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (!checkUserIsExisted()) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public Boolean checkUserIsExisted() {
        loginActionDB = new loginActionDB(userForm.this);
        dataList = loginActionDB.processSearch();
        for (userBean bean : dataList) {
            if(bean.getUserName().equals(fUserName.getText().toString())) {
                return false;
            }
        }
        return true;
    }
    public void bindBtnCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userForm.this, userController.class);

                startActivity(i);
            }
        });
    }
}
