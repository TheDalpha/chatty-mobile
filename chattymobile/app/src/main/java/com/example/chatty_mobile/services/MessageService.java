package com.example.chatty_mobile.services;

import android.util.Log;

import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.models.User;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MessageService {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final OkHttpClient client = new OkHttpClient();

    /**
     * Creates the JSON object for message and then a request for the requestCall
     * @param message Message to be sent
     */
    public void sendMessage(Message message) {

        JSONObject postData = new JSONObject();
        JSONObject senderData = new JSONObject();

        try {
            postData.put("content", message.getMessage());
            postData.put("isFile", message.getIsFile());
            senderData.put("avatarUrl", message.getUser().getAvatar());
            senderData.put("userName", message.getUser().getUsername());
            postData.put("sender",senderData);
            postData.put("time", message.getTime().getTime());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), postData.toString());

        final Request request = new Request.Builder()
                .url("https://us-central1-chatty-dev-e0191.cloudfunctions.net/api/message")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: Message not sent" + e.getMessage() + "--------------------------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int responseCode = response.code();
                Log.d(TAG, "response: " + responseCode);

            }
        });
    }

    /**
     * Adds a listener to the messages collection on firebase and then creates the message object to be shown on view.
     * @param IMessageService
     */
    public void getNewMessage(final IMessageService IMessageService) {
        db.collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "onEvent: ",e );
                    return;
            }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Map<String, Object> map = dc.getDocument().getData();
                            Message message = new Message();
                            User user = new User();
                            message.setMessage(map.get("content") + "");
                            message.setIsFile((Boolean) map.get("isFile"));
                            Map<String, Object> Sender = (Map) map.get("sender");
                            user.setUsername(Sender.get("userName") + "");
                            user.setAvatar(Sender.get("avatarUrl") + "");
                            Date date = new Date(Long.parseLong(map.get("time") + ""));
                            message.setTime(date);
                            message.setUser(user);
                            IMessageService.onCallback(message);
                            break;
                        case MODIFIED:
                            Log.d("TAG", "Modified Msg: " + dc.getDocument().toObject(Message.class));
                            break;
                        case REMOVED:
                            Log.d("TAG", "Removed Msg: " + dc.getDocument().toObject(Message.class));
                            break;
                    }
        }
    }
    });
    }
}


