package com.example.chatty_mobile.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatty_mobile.R;
import com.example.chatty_mobile.services.UserService;

public class CreateActivity extends AppCompatActivity {

    private EditText mail, username, password1, password2;
    private Button createBtn;
    private String imageURL = "https://example.com/avatar1.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        final Intent login = new Intent(this, LoginActivity.class);

        // Textfields
        mail = findViewById(R.id.emailEt);
        username = findViewById(R.id.usernameEt);
        password1 = findViewById(R.id.password1Et);
        password2 = findViewById(R.id.password2Et);

        // Buttons
        createBtn = findViewById(R.id.createBtn);


        //Initializes the UserService
        final UserService uService = new UserService();


        /**
         * When button Create User is clicked
         */
       createBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Checks if the two passwords are the same
               if (password1.getText().toString().equals(password2.getText().toString())) {
                   //Sends a request to UserService
                   uService.create(mail.getText().toString(), password1.getText().toString(),
                           username.getText().toString(), imageURL);
                   //Redirect to login page
                   startActivity(login);
                   //If it is succesfully created, display a message to the user.
                   Toast.makeText(CreateActivity.this, "Create Success",
                           Toast.LENGTH_SHORT).show();
               } else {
                   // If create fails, display a message to the user.
                   Toast.makeText(CreateActivity.this, "Create failed: \nPasswords do not match",
                           Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
}
