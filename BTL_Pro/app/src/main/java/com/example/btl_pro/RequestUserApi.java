package com.example.btl_pro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestUserApi {
    @GET("get-user-list/")
    Call<userBean> ApiGetUserList();

}
