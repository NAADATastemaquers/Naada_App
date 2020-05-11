package com.tastemaquers.naada.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tastemaquers.naada.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class resident_adapter  extends RecyclerView.Adapter<resident_adapter.ViewHolder> {

    private ArrayList<resident> residents = new ArrayList<>();
    private Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void CheckBioClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public resident_adapter(Context context, ArrayList<resident> residents, OnItemClickListener listener) {
        this.residents = residents;
        this.context = context;
        mListener = listener;

    }


    @Override
    public  resident_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item, parent, false);
        resident_adapter.ViewHolder evh = new  resident_adapter.ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText( residents.get( i ).getArtist() );
        viewHolder.desc.setText( residents.get( i ).getArtist_desc() );

        Picasso.get().load( residents.get( i ).getArtist_img() ).into( viewHolder.image );

    }

    @Override
    public int getItemCount() {
        return residents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private ImageView image;
        private TextView name, desc;
        View.OnClickListener clickListner;
        TextView checkbio;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super( itemView );


            image = (ImageView) itemView.findViewById( R.id.art_image );
            name = (TextView) itemView.findViewById( R.id.artist_name );
            desc = (TextView) itemView.findViewById( R.id.art_desc );
            checkbio = (TextView) itemView.findViewById( R.id.checkbio );
            checkbio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.CheckBioClick(position);
                        }
                    }
                }
            });
        }


    }
}

