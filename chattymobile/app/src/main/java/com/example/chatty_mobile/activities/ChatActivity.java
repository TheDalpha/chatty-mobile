package com.example.chatty_mobile.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chatty_mobile.R;
import com.example.chatty_mobile.models.Message;
import com.example.chatty_mobile.services.MessageService;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "HEEEEEEEEEEEEEEEEJ";
    private ListView listView;
    ArrayList<Message> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);

        MessageService messageService = new MessageService();
        arrayList = messageService.getMessages();
        Date date = new Date();
        Log.d(TAG, date.getTime() + "-------------------------------------- AFTER -------------------------------");
        Log.d(TAG, arrayList.size() + "--------------------------------------------------------------------------------------------------------------------------");
        ArrayList list = new ArrayList();

        

        for (int i = 0; i < arrayList.size(); i++) {
            list.add(arrayList.get(i).getMessage());
    }
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list));


}
}
