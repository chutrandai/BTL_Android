package com.example.btl_pro;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class login extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    Button login;
    Button cancel;
    LoginButton login_button;
    loginActionDB loginActionDB;
    ArrayList<userBean> dataList = new ArrayList<>();
    CallbackManager callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        constructorAttr();
        bindOnclickLogin();
        bindOnclickCancel();
        loginFB();
//        new readJ().execute("http://127.0.0.1:8900/get-user-list");
    }

    public void constructorAttr() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            LoginManager.getInstance().logOut();
        }
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        login    = (Button) findViewById(R.id.btnLogin);
        cancel    = (Button) findViewById(R.id.btnCancel);
        login_button = (LoginButton) findViewById(R.id.login_button);
        loginActionDB = new loginActionDB(this);
        dataList = loginActionDB.processSearch();
    }

    /**
     * bat su kien button login
     */
    public void bindOnclickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLoginBefore()) {
                    if ((dataList != null
                            && checkAcceptLogin(dataList))) {
                        Intent i = new Intent(com.example.btl_pro.login.this, Option.class);
                        commonUtil.userName = userName.getText().toString();
                        startActivity(i);
                    } else {
                        Toast.makeText(com.example.btl_pro.login.this, "Tài khoản hoặc mật khẩu không đúng!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * kiem tra cho phep login vao he thong
     * @param userList
     * @return
     */
    private boolean checkAcceptLogin(ArrayList<userBean> userList) {
        for (userBean bean: userList) {
            String username = bean.getUserName();
            String etxtUsername = userName.getText().toString();
            String password = bean.getPassWord();
            String etxtPassword = passWord.getText().toString();
            if(username.equals(etxtUsername) && password.equals(etxtPassword)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkUserLoginApi() {
        Retrofit retrofit = APIClient.getClient();
        RequestUserApi callApi = retrofit.create(RequestUserApi.class);
        Call<userBean> call = callApi.ApiGetUserList();
        call.enqueue(new Callback<userBean>() {
            @Override
            public void onResponse(Call<userBean> call, Response<userBean> response) {
                Toast.makeText(login.this, response.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<userBean> call, Throwable t) {
                Toast.makeText(login.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        if (call != null) {
            return true;
        }
        return false;
    }

    /**
     * kiem tra validate username + password
     * @return
     */
    public boolean checkLoginBefore() {
        if (commonUtil.isNullOrEmpTy(userName.getText().toString())) {
                userName.setError("username is required!");
                return false;
        }
        if (commonUtil.isNullOrEmpTy(passWord.getText().toString()))
        {
            passWord.setError("password is required!");
            return false;
        }
        return true;
    }

    /**
     * bat su kien button cancel
     */
    public void bindOnclickCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setText("");
                passWord.setText("");
            }
        });
    }

    public void loginFB() {
        callbackManager = CallbackManager.Factory.create();
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(login.this, Option.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(login.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(login.this, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class readJ extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader =
                        new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(login.this, "string: " + s, Toast.LENGTH_SHORT).show();
            Log.e("loginClass", s);
        }
    }
}

