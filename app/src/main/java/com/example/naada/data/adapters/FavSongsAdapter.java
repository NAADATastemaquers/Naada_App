package com.example.naada.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavSongsAdapter extends RecyclerView.Adapter<FavSongsAdapter.FavSongsViewholder> {

    private List<fav_details> details;
    private Context context;

    public FavSongsAdapter(List<fav_details> residents, Context con) {
        this.details = residents;
        this.context = con;
    }

    @NonNull
    @Override
    public FavSongsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_song_card, parent, false);
        FavSongsViewholder evh = new FavSongsViewholder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavSongsViewholder holder, final int position) {

        Picasso.get().load( details.get(position).getSong_img() ).into( holder.favSong_img );
        holder.favSong_name.setText(details.get(position).song_name);
        holder.favSong_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                String sharesub="Hey I'm listening to " + details.get(position).song_name +  "\n\n" + "Find it here "+ details.get(position).song_url  ;
                myintent.putExtra(Intent.EXTRA_TEXT,sharesub);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(myintent,"share using"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class FavSongsViewholder extends RecyclerView.ViewHolder {

        ImageView favSong_img,favSong_share;
        TextView favSong_name;

        FavSongsViewholder(@NonNull View itemView) {
            super(itemView);
            favSong_img = itemView.findViewById(R.id.favSong_img);
            favSong_name = itemView.findViewById(R.id.favSong_name);
            favSong_share = itemView.findViewById(R.id.favSong_share);

        }
    }
}
