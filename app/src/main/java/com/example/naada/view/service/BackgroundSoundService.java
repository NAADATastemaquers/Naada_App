package com.example.naada.view.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class BackgroundSoundService extends Service {
    private String url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3";


        private static final String TAG = null;
        public static MediaPlayer player;

        public IBinder onBind(Intent arg0) {

            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
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