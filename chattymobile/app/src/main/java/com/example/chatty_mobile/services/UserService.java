package com.example.chatty_mobile.services;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class UserService {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    final OkHttpClient client = new OkHttpClient();


    public void create(String email, String password, String username, String avatarUrl) {

        JSONObject postData = new JSONObject();

        try {
            postData.put("email", email);
            postData.put("password", password);
            postData.put("userName", username);
            postData.put("avatarURL", avatarUrl);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        // Request body ...
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), postData.toString());

        final Request request = new Request.Builder()
                .url("https://us-central1-chatty-dev-e0191.cloudfunctions.net/api/users/create")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // something went wrong right off the bat
                Log.d(TAG, "Something went wrong!" + e.getMessage() + "-----------------------------------------------------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // response successful ....
                // refers to response.status('200') or ('500')
                int responseCode = response.code();
                Log.d(TAG, "response: " + responseCode);
            }
        });
    }

    /**
     * Gets all avatar names from firebase documents
     * then gets the download url of the avatar from firestorage
     * @param iUserService
     */
    public void getAvatars(final IUserService iUserService) {
        db.collection("avatars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> map = document.getData();
                        String avatarName = map.get("fileName") + "";
                        storageRef.child("avatars/" + avatarName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d(TAG, "onSuccess: " + uri.toString() + "-------------------------------------------------------");
                                iUserService.onCallback(uri.toString());
                            }
                        });

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

}
