package com.example.naada.data.adapters;

import android.content.Context;
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
    public void onBindViewHolder(@NonNull FavSongsViewholder holder, int position) {

        Picasso.get().load( details.get(position).song_img ).into( holder.favSong_img );
        holder.favSong_name.setText(details.get(position).song_name);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class FavSongsViewholder extends RecyclerView.ViewHolder {

        ImageView favSong_img;
        TextView favSong_name;

        public FavSongsViewholder(@NonNull View itemView) {
            super(itemView);
            favSong_img = itemView.findViewById(R.id.favSong_img);
            favSong_name = itemView.findViewById(R.id.favSong_name);

        }
    }
}
