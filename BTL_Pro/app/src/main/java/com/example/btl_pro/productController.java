package com.example.btl_pro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class productController extends AppCompatActivity implements contactNow {
    EditText etSearch;
    ListView lvProduct;
    Button backToHome;
    Button adDelete;
    ArrayList<productBean> dataList = new ArrayList<>();
    productActionDB productActionDB;
    productAdapter productAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        constructorArrT();
        getOnchangeTextSearch();

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(productController.this, Option.class);
                startActivity(intent);
            }
        });
    }

    public void constructorArrT() {
        etSearch = (EditText) findViewById(R.id.searchPro);
        lvProduct = (ListView) findViewById(R.id.lv);
        backToHome = (Button) findViewById(R.id.back_to_home);
        adDelete = (Button) findViewById(R.id.adDelete);
        productActionDB = new productActionDB(productController.this);
        dataList = productActionDB.processSearch();
        productAdapter = new productAdapter(productController.this, dataList, this);
        lvProduct.setAdapter(productAdapter);
        if (commonUtil.userName.equals("admin")) {
            adDelete.setVisibility(View.VISIBLE);
        }

        bindAdDelete();

    }
    public void bindAdDelete() {
        adDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (productBean bean:dataList) {
                    if(bean.isStatus()) {
                        productActionDB = new productActionDB(productController.this);
                        productActionDB.delete(bean.getProductId());
                        constructorArrT();
                    }
                }
            }
        });
    }
    public void getOnchangeTextSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<productBean> dataListAfter = new ArrayList<>();
                for (productBean bean: dataList) {
                    if (bean.getNamePro().contains(s)) {
                        dataListAfter.add(bean);
                    }
                }
                productAdapter = new productAdapter(productController.this, dataListAfter, productController.this);
                lvProduct.setAdapter(productAdapter);
            }
        });
    }

    public void lienHeNgay(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void onClickContactNow(String strPhone) {
        lienHeNgay(strPhone);
    }

    @Override
    public void onclickEdit(productBean productBean) {
        Intent intent = new Intent(productController.this, productForm.class);
        intent.putExtra("dTenSp", productBean.getNamePro());
        intent.putExtra("dSdt", productBean.getPhone());
        intent.putExtra("dGiatien", productBean.getCost().toString());
        intent.putExtra("dSrcImage", productBean.getSrcImage());
        startActivity(intent);
    }

    @Override
    public void onclickStatus(ArrayList<productBean> dataList) {
        if (dataList != null) {
            for (productBean bean :
                    dataList) {
                if (bean.isStatus()) {
                    adDelete.setVisibility(View.VISIBLE);
                    return;
                }
            }
        }
        adDelete.setVisibility(View.VISIBLE);
    }
}
