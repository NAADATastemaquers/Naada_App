package com.example.naada.view;

import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Track {
    // private String company;
    private String title;
    private String artist;
    private int image;

    private static final String TAG = "Background_notification";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference contentRef= db.collection("songs").document("song");
    private static final String KEY_IMAGE="image";

    public Track(String title, String artist, int image) {
        //this.company = company;
        this.title = title;
        this.artist = artist;
        this.image = image;
    }

//    public void setCompany(String company) {
//        this.company = company;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    String album_image_url = documentSnapshot.getString(KEY_IMAGE);
                    Log.d(TAG, "album_image_url: "+album_image_url);
//                    Glide.with().load(album_image_url).centerCrop().load(album_image_url).into(image);
                }
            }
        });
        return image;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

//    public String getCompany() {
//        return company;
//    }
}

