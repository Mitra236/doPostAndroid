package com.example.dopostemail.server;

import com.example.dopostemail.model.Contact;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContactsInterface {

    @GET("contacts")
    Call<ArrayList<Contact>> getContacts();

    @GET("contacts/{id}")
    Call<Contact> getContactById(@Path("id") int id);
}
