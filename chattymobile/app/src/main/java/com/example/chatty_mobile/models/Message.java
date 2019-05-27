package com.example.chatty_mobile.models;

import java.util.Date;

public class Message {

    private String _content;

    private User _user;

    private Boolean _isFile;

    private Date _time;

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

    public Date getTime() {return _time; }

    public Date setTime(Date time) {return _time = time; }


}
