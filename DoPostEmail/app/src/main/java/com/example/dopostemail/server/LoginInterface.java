package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginInterface {

    @GET("accounts")
    Call<ArrayList<Account>> getAccounts();
}
