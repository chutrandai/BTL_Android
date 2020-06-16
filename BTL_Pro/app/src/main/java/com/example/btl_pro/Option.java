package com.example.btl_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.Profile;

public class Option extends AppCompatActivity {
    Button btnBuy;
    Button btnSell;
    Button btnBack;
//    String userName;
    Button userLoginManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        constructorArrT();
        bindUserLoginManager();
        bindBtnSell();
        bindBtnBuy();
        bindBtnBack();
//        Profile profile = Profile.getCurrentProfile();
//        if (profile != null) {
//            commonUtil.userName = profile.getName();
//        }
    }

    public void constructorArrT() {
        btnBuy = (Button) findViewById(R.id.btnBuy);
        btnSell = (Button) findViewById(R.id.btnSell);
        btnBack = (Button) findViewById(R.id.btnBack);
        userLoginManager = (Button) findViewById(R.id.userLoginManager);
        String userName = commonUtil.userName;
        if (!commonUtil.isNullOrEmpTy(userName) && userName.equals("admin")) {
            userLoginManager.setVisibility(View.VISIBLE);
        }
    }

    public void bindBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this, login.class);
                startActivity(i);
            }
        });
    }

    public void bindUserLoginManager() {
        userLoginManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this, userController.class);
                startActivity(i);
            }
        });
    }

    public void bindBtnSell() {
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this, productForm.class);
                startActivity(i);
            }
        });
    }
    public void bindBtnBuy() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Option.this, productController.class);
                startActivity(i);
            }
        });
    }
}
