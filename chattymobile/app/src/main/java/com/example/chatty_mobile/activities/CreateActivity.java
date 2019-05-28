package com.example.chatty_mobile.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.chatty_mobile.Adapter.AvatarAdapter;
import com.example.chatty_mobile.R;
import com.example.chatty_mobile.services.IUserService;
import com.example.chatty_mobile.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "";
    private EditText mail, username, password1, password2;
    private GridView gridView;
    private AvatarAdapter avatarAdapter;
    private View _view;
    private Button createBtn;
    private List<String> imageUrl;
    private int avatarPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        final Intent login = new Intent(this, LoginActivity.class);

        gridView = findViewById(R.id.gridView);
        // Textfields
        mail = findViewById(R.id.emailEt);
        username = findViewById(R.id.usernameEt);
        password1 = findViewById(R.id.password1Et);
        password2 = findViewById(R.id.password2Et);

        // Buttons
        createBtn = findViewById(R.id.createBtn);

        imageUrl = new ArrayList<>();

        //Initializes the UserService
        final UserService uService = new UserService();
        avatarAdapter = new AvatarAdapter(this, imageUrl);
        uService.getAvatars(new IUserService() {
            @Override
            public void onCallback(String avatarUrl) {
                addAvatar(avatarUrl);
            }
        });

        gridView.setAdapter(avatarAdapter);

        /**
         * When button Create User is clicked
         */
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int passMinLenght = 6;
                //Checks if the password is minimum 6 characters long and if both password match
                if (password1.length() >= passMinLenght) {
                    if (password1.getText().toString().equals(password2.getText().toString())) {
                        //Sends a request to UserService
                        uService.create(mail.getText().toString(), password1.getText().toString(),
                                username.getText().toString(), imageUrl.get(avatarPosition));
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
                } else {
                    // displays a text to user if password is not long enough
                    Toast.makeText(CreateActivity.this, "Create failed: \nPassword need to be atleast 6 characters long",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (_view != null) {
                    _view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                _view = view;
                view.setBackgroundColor(Color.parseColor("#35B7AD"));
                avatarPosition = position;
            }
        });
    }

    /**
     * adds avatar URL to a list and then notifies the adapter of changes
     *
     * @param avatarUrl
     */
    public void addAvatar(String avatarUrl) {

        imageUrl.add(avatarUrl);
        avatarAdapter.notifyDataSetChanged();
    }
}
