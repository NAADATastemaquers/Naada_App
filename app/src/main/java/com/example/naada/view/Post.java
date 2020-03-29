package com.example.naada.view;

public class Post {
    private String message;
    private String sender;

    public Post(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getsender() {
        return sender;
    }
}