package com.example.btl_pro;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class userController extends AppCompatActivity {
    EditText etSearch;
    ListView userList;
    Button btnAdd;
    Button btnDelete;
    Button btnBack;
    ArrayList<userBean> dataList = new ArrayList<>();
    loginActionDB loginActionDB;
    userAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        constructorArrt();
        binBtnAdd();
        bindBtnDelete();
        bindBtnBack();
        getOnchangeTextSearch();
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(userController.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void constructorArrt() {
        etSearch = (EditText) findViewById(R.id.search);
        userList = (ListView) findViewById(R.id.lvUser);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        loginActionDB = new loginActionDB(userController.this);
        dataList = loginActionDB.processSearch();
        myAdapter = new userAdapter(this, dataList);
        userList.setAdapter(myAdapter);
    }
    public void bindBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userController.this, Option.class);
                startActivity(i);
            }
        });
    }
    public void bindBtnDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (userBean bean:dataList) {
                    if(bean.isStatus()) {
                        loginActionDB = new loginActionDB(userController.this);
                        loginActionDB.delete(bean.getUserName());
                        constructorArrt();
                    }
                }
            }
        });
    }
    public void binBtnAdd() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userController.this, userForm.class);
                startActivity(i);
            }
        });
    }
    public void getOnchangeTextSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                loginActionDB = new loginActionDB(userController.this);
//                dataList = loginActionDB.processSearch(s.toString());
//                myAdapter = new userAdapter(userController.this, dataList);
//                userList.setAdapter(myAdapter);
//                Toast.makeText(userController.this, s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<userBean> dataListAfter = new ArrayList<>();
                for (userBean bean: dataList) {
                    if (bean.getUserName().contains(s)) {
                        dataListAfter.add(bean);
                    }
                }
                myAdapter = new userAdapter(userController.this, dataListAfter);
                userList.setAdapter(myAdapter);
            }
        });
    }
}
