package com.example.naada.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.example.naada.data.models.Track;
import com.example.naada.view.MusicPlayerActivity;
import com.example.naada.view.service.NotificationActionServices;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class CreateNotification {

    public static final String Channel_id="channel1";
    public  static final String action_play= "actionplay";
    public static Notification noticication;
    private static final String KEY_NAME="name";
    private static final String KEY_ARTIST= "artist";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private static DocumentReference contentRef=contentRef= db.collection("songs").document("song");;
    private static String song,artist;

    private static final String TAG = "createNotification";

    public static void createNotification(final Context context, Track track, final int playbutton, int pos)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            final NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
            final MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(context,"tag");

            final Bitmap icon= BitmapFactory.decodeResource(context.getResources(), track.getImage());

            Intent intentplay=new Intent(context, NotificationActionServices.class).setAction(action_play);

            final PendingIntent pendingIntentPLay=PendingIntent.getBroadcast(context,0,intentplay,PendingIntent.FLAG_UPDATE_CURRENT);

            contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        final String song_name =documentSnapshot.getString(KEY_NAME);
                        final String artist_name =documentSnapshot.getString(KEY_ARTIST);
                        Log.d(TAG, "song name: "+song_name);
                        Log.d(TAG, "artist_name: "+artist_name);
                        song=song_name;
                        artist=artist_name;

                        noticication=new NotificationCompat.Builder(context,Channel_id)
                                .setSmallIcon( R.drawable.ic_music_note)
//                    .setContentText(track.getCompany())
                                .setContentTitle(song)
                                .setContentText(artist)
                                .setLargeIcon(icon)
                                .setOnlyAlertOnce(true)
                                .setShowWhen(false)
                                .addAction(playbutton,"Play",pendingIntentPLay)
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setMediaSession(mediaSessionCompat.getSessionToken()))

                                .setPriority(NotificationCompat.PRIORITY_LOW)
                                .build();

                        notificationManagerCompat.notify(1,noticication);
                    }
                }
            });
        }
    }
}