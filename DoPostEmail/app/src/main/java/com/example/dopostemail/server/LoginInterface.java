package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginInterface {

    @POST("api/login")
    Call<User> doLogin(@Body User user);

    @GET("api/login")
    Call<ArrayList<User>> getUsers();

    @GET("api/accounts")
    Call<ArrayList<Account>> getAccounts();

    @GET("editAccount")
    Call<Account> editAccount(@Body Account acc);

    @POST("api/login/register")
    Call<User> register(@Body User user);

    @PUT("api/login/changePassword/{id}")
    Call<User> changePassword(@Body User user, @Path("id") Long id);
}
