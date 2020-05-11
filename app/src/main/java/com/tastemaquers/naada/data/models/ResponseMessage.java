package com.tastemaquers.naada.data.models;

public class ResponseMessage {

    String text;
    String name;
    boolean isMe;
    String timestamp;



    public ResponseMessage(String text, boolean isMe, String name, String timestamp) {
        this.text = text;
        this.isMe = isMe;
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public void setTime(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}