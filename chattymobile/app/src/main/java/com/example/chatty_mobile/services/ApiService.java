package com.example.chatty_mobile.services;

import com.example.chatty_mobile.models.Message;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("message")
    Call<ArrayList<Message>> getMessages();
}
