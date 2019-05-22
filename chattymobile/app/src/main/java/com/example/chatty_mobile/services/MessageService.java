package com.example.chatty_mobile.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.chatty_mobile.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MessageService {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Connects to the firestore dabase and gets all messages
     * @param apiService
     */
    public void getMessages(final ApiService apiService) {
        db.collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> map = document.getData();
                        Message message = new Message();
                        message.setMessage(map.get("content") + "");
                        apiService.onCallback(message);

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

}
