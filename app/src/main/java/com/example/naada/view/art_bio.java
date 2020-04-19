package com.example.naada.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class art_bio {
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("artist_img")
    @Expose
    private String artistImg;
    @SerializedName("artist_desc")
    @Expose
    private String artistDesc;
    @SerializedName("artist_shows")
    @Expose
    private List<artist_shows> artistShows = null;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(String artistImg) {
        this.artistImg = artistImg;
    }

    public String getArtistDesc() {
        return artistDesc;
    }

    public void setArtistDesc(String artistDesc) {
        this.artistDesc = artistDesc;
    }

    public List<artist_shows> getArtistShows() {
        return artistShows;
    }

    public void setArtistShows(List<artist_shows> artistShows) {
        this.artistShows = artistShows;
    }
}
