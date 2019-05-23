package com.example.chatty_mobile.services;

import com.example.chatty_mobile.models.Message;

public interface ApiService {

    /**
     * A method that waits for answer from firebase so all messages loads in.
     * @param message
     */
    void onCallback(Message message);
}
