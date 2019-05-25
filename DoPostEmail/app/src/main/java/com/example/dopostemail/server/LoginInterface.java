package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface LoginInterface {

    @GET("api/service/accounts")
    Call<ArrayList<Account>> getAccounts();

    @GET("editAccount")
    Call<Account> editAccount(@Body Account acc);
}
