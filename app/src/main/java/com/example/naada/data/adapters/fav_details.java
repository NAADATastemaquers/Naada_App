package com.example.naada.data.adapters;

import java.util.List;

public class fav_details {



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getSong_img() {
        return song_img;
    }

    public void setSong_img(String song_img) {
        this.song_img = song_img;
    }

    String email;
    String song_name;
    String song_url;
    String song_img;

    public fav_details(String email, String song_name, String song_url, String song_img) {
        this.email = email;
        this.song_name = song_name;
        this.song_url = song_url;
        this.song_img = song_img;
    }



}
