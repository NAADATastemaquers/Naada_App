package com.tastemaquers.naada.data.models;

public class ScreenItem {

    String description;
    int ScreenImg;

    public ScreenItem(String description, int screenImg) {
        this.description = description;
        ScreenImg = screenImg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getDescription() {
        return description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
