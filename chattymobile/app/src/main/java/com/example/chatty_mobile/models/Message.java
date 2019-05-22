package com.example.chatty_mobile.models;

public class Message {

    private String _content;

    private User _user;

    private Boolean _isFile;

    private float _time;

    public Boolean getIsFile() {
        return _isFile;
    }

    public Boolean setIsFile(Boolean isFile) {
        return _isFile = isFile;
    }

    public String getMessage() {
        return _content;
    }

    public String setMessage(String content) {
        return _content = content;
    }

    public User getUser() {
        return _user;
    }

    public User setUser(User user) { return _user = user;}

    public float getTime() {return _time; }

    public float setTime(float time) {return _time = time; }


}
