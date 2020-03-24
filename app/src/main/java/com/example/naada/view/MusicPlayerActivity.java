package com.example.naada.view;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.naada.R;
import com.example.naada.view.service.BackgroundSoundService;
import com.example.naada.view.service.OnClearFromRecentService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity implements playable, Dialog.DialogListener {
    Intent svc;
    ImageButton play;
    ImageButton message;
    Track track;
    ImageButton share;
    String url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3";
    MediaPlayer mediaPlayer;
    boolean isPlaying=false;
    TextView artist,details,song;
    NotificationManager notificationManager;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        artist=(TextView) findViewById(R.id.artist_name);
        details=(TextView) findViewById(R.id.other_details);
        song=(TextView) findViewById(R.id.song_name);
        share=(ImageButton) findViewById(R.id.share);
        message=(ImageButton)findViewById(R.id.message);
        svc=new Intent(this,BackgroundSoundService.class);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (isMyServiceRunning(BackgroundSoundService.class)) {
            //stopService(new Intent(MusicPlayerActivity.this, BackgroundSoundService.class));

        } else {
            startService(new Intent(svc));
        }

//        startService(svc);

        try{
            track=new Track(song.getText().toString(),artist.getText().toString(),R.drawable.player);
        }catch(Exception ignored){
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            createChannel();
            registerReceiver(broadcastReceiver,new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
        play=(ImageButton) findViewById(R.id.play);
        try{
            if(BackgroundSoundService.player.isPlaying())
            {
                play.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            }
        }
        catch (Exception ignore){}

        try{
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPlaying){
                        onTrackPause();
                    }else{
                        onTrackPlay();
                    }
                }
            });
        }catch(Exception ignored){
        }
        try{
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent=new Intent(Intent.ACTION_SEND);
                    myintent.setType("text/plain");
                    String shareBody=url;
                    String sharesub="your Subject here";
                    myintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                    myintent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    startActivity(Intent.createChooser(myintent,"share using"));
                }
            });
        }
        catch(Exception ignored){

        }
        try{
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(acct!=null){
                        Intent newIntent=new Intent(MusicPlayerActivity.this,MessageActivity.class);
                        startActivity(newIntent);
                        finish();
                    }else{
                        Dialog dialog=new Dialog();
                        dialog.show(getSupportFragmentManager(),"login dialog");
                    }
                }
            });
        }
        catch(Exception ignored){

        }

        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Toast.makeText(MusicPlayerActivity.this,"Media Buffering Complete..",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onYesClicked() {
        Intent login = new Intent(MusicPlayerActivity.this,LoginActivity.class);
        startActivity(login);
        finish();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    private void createChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CreateNotification.Channel_id,"Naada", NotificationManager.IMPORTANCE_LOW);

            notificationManager=getSystemService(NotificationManager.class);
            if(notificationManager!=null)
            {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getExtras().getString("actionname");

            switch(action)
            {
                case CreateNotification.action_play:
                    if(isPlaying)
                    {
                        onTrackPause();
                    }
                    else
                    {
                        onTrackPlay();
                    }
                    break;
            }
        }
    };
    @Override
    public void onTrackPlay() {
        CreateNotification.createNotification(MusicPlayerActivity.this, track,R.drawable.ic_pause_circle_outline_black_24dp,1);
        play.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        isPlaying=true;
        BackgroundSoundService.player.start();
        //mediaPlayer.start();
    }
    @Override
    public void onTrackPause() {
        CreateNotification.createNotification(MusicPlayerActivity.this, track,R.drawable.ic_play_circle_outline_black_24dp,1);
        play.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        isPlaying=false;
        BackgroundSoundService.player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }
        unregisterReceiver(broadcastReceiver);
    }
}