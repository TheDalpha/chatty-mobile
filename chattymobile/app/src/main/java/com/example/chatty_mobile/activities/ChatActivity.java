package com.example.chatty_mobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chatty_mobile.Adapter.AdapterList;
import com.example.chatty_mobile.R;
import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.models.User;
import com.example.chatty_mobile.services.IMessageService;
import com.example.chatty_mobile.services.MessageService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "";
    private EditText msgText;
    private FirebaseAuth mAuth;
    private ImageButton sendMsg;
    private AdapterList aListAdapter;
    private ListView listView;
    private List<Message> messageList;
    final MessageService messageService = new MessageService();

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
    }

    /**
     * Initiate views
     */
    private void initViews() {
        listView = (ListView) findViewById(R.id.listview);
        msgText = findViewById(R.id.txtSend);
        sendMsg = findViewById(R.id.btnSend);

    }

    /**
     * adds a message item to a list, sort it by date and then notifies the adaoter of changes
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
}
