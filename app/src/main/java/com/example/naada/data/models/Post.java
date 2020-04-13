package com.example.naada.data.models;

public class Post {
    private String message;
    private String sender;
    private String timestamp;

    public Post(String message, String sender,String timestamp) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getsender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }
}