package com.example.bikehire.Api;

import com.example.bikehire.Model.MsgModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<MsgModel> getMessage(@Url String url);
}
