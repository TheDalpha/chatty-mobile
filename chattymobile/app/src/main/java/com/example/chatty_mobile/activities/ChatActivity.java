package com.example.chatty_mobile.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chatty_mobile.Adapter.AdapterList;
import com.example.chatty_mobile.R;
import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.models.Picture;
import com.example.chatty_mobile.models.User;
import com.example.chatty_mobile.services.IMessageService;
import com.example.chatty_mobile.services.MessageService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private EditText msgText;
    private FirebaseAuth mAuth;
    private ImageButton sendMsg;
    private AdapterList aListAdapter;
    private ListView listView;
    private List<Message> messageList;
    final MessageService messageService = new MessageService();
    private ImageButton takePic;
    private Uri pFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        initViews();
        mAuth = FirebaseAuth.getInstance();
        messageList = new ArrayList<>();
        aListAdapter = new AdapterList(this, messageList);

        messageService.getNewMessage(new IMessageService() {
            @Override
            public void onCallback(Message message) {
                addItems(message);
                scrollListViewToBottom();

            }
        });

        listView.setAdapter(aListAdapter);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser fuser = mAuth.getCurrentUser();
                Message message = new Message();
                User user = new User();

                user.setAvatar(fuser.getPhotoUrl().toString());
                user.setUsername(fuser.getDisplayName());
                message.setUser(user);
                message.setIsFile(false);
                message.setMessage(msgText.getText().toString());
                message.setTime(new Date());

                messageService.sendMessage(message);
                msgText.getText().clear();

            }
        });

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    /**
     * Initiate views
     */
    private void initViews() {
        listView = (ListView) findViewById(R.id.listview);
        msgText = findViewById(R.id.txtSend);
        sendMsg = findViewById(R.id.btnSend);
        takePic = findViewById(R.id.btnPicture);
    }

    /**
     * adds a message item to a list, sort it by date and then notifies the adapter of changes
     * @param message object that contains messages
     */
    private void addItems(Message message) {

        messageList.add(message);
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        aListAdapter.notifyDataSetChanged();

    }

    /**
     * Sets the ListView to the last message sent.
     */
    private void scrollListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(aListAdapter.getCount() - 1);
            }
        });
    }

    /**
     * Opens camera to take picture
     */
    private void takePicture() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, pFile);

        if (camera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(camera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Log.d(TAG, "camera app could NOT be started");
        }
    }

    /**
     * Gets the result from camera activity and sent data to model
     * @param requestCode code of the request
     * @param resultCode code of the result
     * @param data picture data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Picture picture = new Picture();
            Bundle uri = data.getExtras();
            picture.setSize(uri.size());
            picture.setType("image/png");
            User user = new User();
            user.setUsername(mAuth.getCurrentUser().getDisplayName());
            user.setAvatar(mAuth.getCurrentUser().getPhotoUrl().toString());
            picture.setUser(user);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            picture.setBase64File(getBase64(photo));
            messageService.uploadImage(picture);
        }
    }


    /**
     * converts the bitmap of the picture to base64
     * @param photo bitmap of picture
     * @return base64 string of file
     */
    private String getBase64(Bitmap photo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
