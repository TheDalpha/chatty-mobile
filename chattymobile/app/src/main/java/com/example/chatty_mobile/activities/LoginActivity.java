package com.example.chatty_mobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatty_mobile.R;
import com.example.chatty_mobile.services.MessageService;

public class LoginActivity extends AppCompatActivity {
  
    private Button createBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        final Intent create = new Intent(this, CreateActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
      
        createBtn = findViewById(R.id.btnCreate);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(create);
            }
        });

        loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(chat);
            }
        });
    }
}
