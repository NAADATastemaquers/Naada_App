package com.example.naada.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naada.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class resident_adapter  extends RecyclerView.Adapter<resident_adapter.ViewHolder> {

    private ArrayList<resident> residents=new ArrayList<>();
    private Context context;
    private   View.OnClickListener mclickListner;


    public resident_adapter( Context context,ArrayList<resident> residents) {
        this.residents=residents;
        this.context=context;

    }


    @NonNull
    @Override
    public resident_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.list_item,viewGroup,false);
        return new resident_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(residents.get(i).getArtist());
        viewHolder.desc.setText(residents.get(i).getArtist_desc());

        Picasso.get().load(residents.get(i).getArtist_img()).into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return residents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private ImageView image;
        private TextView name,desc;
        View.OnClickListener clickListner;
        TextView checkbio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            image=(ImageView)itemView.findViewById(R.id.art_image);
            name=(TextView) itemView.findViewById(R.id.artist_name);
            desc=(TextView)itemView.findViewById(R.id.art_desc);
            checkbio=(TextView)itemView.findViewById( R.id.checkbio);
            checkbio.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListner!=null)
                    {
                        int position=getAdapterPosition();

                    }
                }
            } );

        }


    }
    public interface OnItemCLickListner
    {
        void onclick(int position);
    }
    public  void setOnItemClickListner(View.OnClickListener listner)
    {
        mclickListner =listner;
    }
}
