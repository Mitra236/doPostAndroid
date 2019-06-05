package com.example.dopostemail.server;

import com.example.dopostemail.model.Contact;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContactsInterface {

    @GET("contacts")
    Call<ArrayList<Contact>> getContacts();

    @GET("contacts/id")
    Call<Contact> getContactById(@Query("id") int id);

    @POST("contacts/add")
    Call<Contact> addContact(@Body Contact contact);

    @PUT("contacts/edit")
    Call<Contact> editContact(@Query("id") int id, @Body Contact contact);

    @DELETE("contacts/delete")
    Call<Contact> deleteContact(@Query("id") int id);

    @Multipart
    @POST("uploadImage")
    Call<Void> uploadImage(@Part MultipartBody.Part filePart);
}
