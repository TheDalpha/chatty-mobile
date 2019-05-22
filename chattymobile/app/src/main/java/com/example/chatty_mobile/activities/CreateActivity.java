package com.example.chatty_mobile.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatty_mobile.R;
import com.example.chatty_mobile.services.UserService;

public class CreateActivity extends AppCompatActivity {

    private EditText mail, username, password1, password2;
    private Button createBtn;
    private String imageURL = "";
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

        UserService uService = new UserService();

        uService.create("tizz3n@hotmail.com", "sutmig",
                "MissTizz", "https://example.com/avatar1.png");



    }
}
