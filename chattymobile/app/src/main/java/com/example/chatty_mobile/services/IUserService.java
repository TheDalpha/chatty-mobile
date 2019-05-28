package com.example.chatty_mobile.services;

public interface IUserService {

    /**
     * A method that waits for answer from firebase so all avatar urls loads in.
     *
     * @param avatarUrl
     */
    void onCallback(String avatarUrl);
}
