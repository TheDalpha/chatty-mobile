package com.example.chatty_mobile.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.value.IntegerValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MessageService {

    public ArrayList<Message> messages = new ArrayList<Message>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /* public ArrayList<Message> shit(final Context c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5000/chatty-dev-e0191/us-central1/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ArrayList<Message>> call = apiService.getMessages();

        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(c, response.code(), Toast.LENGTH_LONG);
                    return;
                }

                messages = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                Toast.makeText(c, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
        return messages;
    }*/

    public ArrayList<Message> getMessages() {
        db.collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d(TAG, document.getId() + " => " + document.getData());
                        Map<String, Object> map = document.getData();
                        Message message = new Message();
                        message.setMessage(map.get("content") + "");
                        Log.d(TAG, message + " ------------MESSAGE----------------------");
                        messages.add(message);


                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        Date date = new Date();
        Log.d(TAG, date.getTime() + "-------------------------------- IN PROGRESS ----------------------------");
        Log.d(TAG, messages.size() + "----------------------------------------------------------------------------------------------------------");
        return messages;
    }

}
