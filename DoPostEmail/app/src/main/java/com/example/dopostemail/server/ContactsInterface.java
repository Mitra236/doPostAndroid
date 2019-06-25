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

    @GET("api/contacts")
    Call<ArrayList<Contact>> getContacts();

    @GET("contacts/{id}")
    Call<Contact> getContact(@Path("id") Long id);

    @POST("api/contacts")
    Call<Contact> saveContact(@Body Contact contact);

    @PUT("api/contacts/{id}")
    Call<Contact> updateContact(@Body Contact contact, @Path("id") Long id);

    @DELETE("api/contacts/{id}")
    Call<Void> deleteContact(@Path("id") Long id);

    @Multipart
    @POST("uploadImage")
    Call<Void> uploadImage(@Part MultipartBody.Part filePart);
}
