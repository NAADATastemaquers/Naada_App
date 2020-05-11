package com.tastemaquers.naada.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class artist_shows {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("show_name")
    @Expose
    private String showName;

    public artist_shows(String date, String showName, String showDesc) {
        this.date = date;
        this.showName = showName;
        this.showDesc = showDesc;
    }

    @SerializedName("show_desc")
    @Expose
    private String showDesc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowDesc() {
        return showDesc;
    }

    public void setShowDesc(String showDesc) {
        this.showDesc = showDesc;
    }

}