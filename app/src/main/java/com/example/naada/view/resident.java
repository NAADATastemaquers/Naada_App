package com.example.naada.view;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class resident {
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("artist_desc")
    @Expose
    private String artist_desc;
    @SerializedName("artist_img")
    @Expose
    private String artist_img;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist_desc() {
        return artist_desc;
    }

    public void setArtist_desc(String artist_desc) {
        this.artist_desc = artist_desc;
    }

    public String getArtist_img() {
        return artist_img;
    }

    public void setArtist_img(String artist_img) {
        this.artist_img = artist_img;
    }
}
