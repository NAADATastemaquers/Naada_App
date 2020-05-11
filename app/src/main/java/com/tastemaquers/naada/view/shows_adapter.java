package com.tastemaquers.naada.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tastemaquers.naada.R;

import java.util.ArrayList;


public class shows_adapter extends RecyclerView.Adapter<shows_adapter.ViewHolder> {
    private ArrayList<artist_shows> artist_shows=new ArrayList<>();
    private Context context;


    public shows_adapter(Context context, ArrayList<artist_shows> carsModels) {
        this.artist_shows=carsModels;
        this.context=context;
    }


    @NonNull
    @Override
    public shows_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.shows,viewGroup,false);
        return new shows_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.date.setText(artist_shows.get(i).getDate().substring( 0,2 ));
        viewHolder.month.setText(artist_shows.get(i).getDate().substring( 5 ));
        viewHolder.name.setText(artist_shows.get(i).getShowName());
        viewHolder.desc.setText(artist_shows.get(i).getShowDesc());
    }


    @Override
    public int getItemCount() {
        return artist_shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date,month,name,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date=(TextView) itemView.findViewById( R.id.date1);
            month=(TextView) itemView.findViewById( R.id.month);
            desc=(TextView) itemView.findViewById( R.id.s_desc);
            name=(TextView)itemView.findViewById( R.id.show_name);
        }
    }
}

