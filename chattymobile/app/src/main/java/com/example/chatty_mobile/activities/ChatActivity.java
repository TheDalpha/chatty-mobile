package com.example.chatty_mobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.chatty_mobile.Adapter.AdapterList;
import com.example.chatty_mobile.R;
import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.services.ApiService;
import com.example.chatty_mobile.services.MessageService;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "HEEEEEEEEEEEEEEEEJ";
    private AdapterList aListAdapter;
    private ListView listView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        initViews();
        list = new ArrayList<>();
        aListAdapter = new AdapterList(this, list);
        MessageService messageService = new MessageService();
        messageService.getMessages(new ApiService() {
            @Override
            public void onCallback(Message message) {
                addItems(message);

            }
        });

        listView.setAdapter(aListAdapter);
    }

    /**
     * Initiate views
     */
    private void initViews() {
        listView = (ListView) findViewById(R.id.listview);

    }

    /**
     * adds a message item to a list and then notifys the adaoter of changes
     * @param message object that contains messages
     */
    private void addItems(Message message) {

        list.add(message.getMessage());
        aListAdapter.notifyDataSetChanged();

    }


}
