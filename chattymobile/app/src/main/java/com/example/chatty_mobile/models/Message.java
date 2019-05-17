package com.example.chatty_mobile.models;

public class Message {

    private String _content;

    private User _user;

    private float _time;

    public String getMessage() {
        return _content;
    }

    public String setMessage(String content) {
        return _content = content;
    }

    public User getUser() {
        return _user;
    }

    public float getTime() {
        return _time;
    }

    public float setTime(float time) {
        return _time = time;
    }


}
