package com.tastemaquers.naada.view;

public class ResponseMessage {

    String text;
    String name;
    boolean isMe;

    public ResponseMessage(String text, boolean isMe, String name) {
        this.text = text;
        this.isMe = isMe;
        this.name = name;
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
}