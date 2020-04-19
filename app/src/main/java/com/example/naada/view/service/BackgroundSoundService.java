package com.example.naada.view.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.net.URI;

public class BackgroundSoundService extends Service {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference contentRef= db.collection("Stream").document("Stream_URL");
    public String url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3";
    private static final String SONG_URL="URL";


        private static final String TAG = "Music_stream_link";
        public static MediaPlayer player;

        public IBinder onBind(Intent arg0) {

            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            try{
                contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        final String URL;
                        URL=documentSnapshot.getString(SONG_URL);
                        Log.d(TAG,URL);
                        url=URL;
                    }
                });
            }catch (Exception ignored){}
            Log.d("service", "onCreate");
            player = new MediaPlayer();
            try {
                player.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);

        }

    public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("service", "onStartCommand");
            try {
                player.prepare();
//               player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return START_STICKY;
        }

        public void onStart(Intent intent, int startId) {
            // TO DO
            player.start();

        }

        public IBinder onUnBind(Intent arg0) {
            // TO DO Auto-generated method
            return null;
        }

        public void onStop() {

        }

        public void onPause() {
            player.pause();
        }

        @Override
        public void onDestroy() {
            player.stop();
            player.release();
        }

        @Override
        public void onLowMemory() {

        }
    }