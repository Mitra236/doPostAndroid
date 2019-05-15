package com.example.dopostemail.server;

import com.example.dopostemail.model.Contact;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactsInterface {

    @GET("contacts")
    Call<ArrayList<Contact>> getContacts();

    @GET("contacts/{id}")
    Call<Contact> getContactById(@Path("id") int id);

    @POST("contacts/add")
    Call<Contact> addContact(@Body Contact contact);

    @PUT("contacts/edit")
    Call<Contact> editContact(@Body String params);

    @DELETE("contacts/{id}")
    Call<Contact> deleteContact(@Path("id") int id);
}
