package com.example.chatty_mobile;
import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class CreateActivity extends AppCompatActivity {

    private EditText mail, username, password1, password2;
    private Button createBtn;
    private String imageURL = "https://firebasestorage.googleapis.com/v0/b/chatty-dev-e0191.appspot.com/o/avatars%2Favatar1.png?alt=media&token=bea84094-ad9c-49ac-b7ee-1eacd458591d";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        mail = findViewById(R.id.emailEt);
        username = findViewById(R.id.usernameEt);
        password1 = findViewById(R.id.password1Et);
        password2 = findViewById(R.id.password2Et);
        createBtn = findViewById(R.id.btnCreate);

        auth = FirebaseAuth.getInstance();
    }

    private void register(String mail, String username, String password, String avatarUrl) {
        String url = "http://my-json-feed";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (DownloadManager.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
