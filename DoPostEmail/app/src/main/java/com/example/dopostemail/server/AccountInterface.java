package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountInterface {

    @POST("api/accounts")
    Call<Account> addAccount(@Body Account account);

    @PUT("api/accounts/{id}")
    Call<Account> editAccount(@Body Account account, @Path("id") Long id);

    @DELETE("api/accounts/{id}")
    Call<Void> deleteAccount(@Path("id") Long id);

//    @DELETE("api/accounts")
//    Call<Account> deleteAccount(@Body Account account);

}
