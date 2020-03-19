package com.example.naada.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.naada.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    private List<ResponseMessage> responseMessages;
    private Context context;

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView name;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
            name=itemView.findViewById(R.id.other_name);
        }
    }

    public MessageAdapter(List<ResponseMessage> responseMessages, Context context) {
        this.responseMessages = responseMessages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessages.get(position).isMe()){
            return R.layout.chat_item_right;
        }
        return R.layout.chat_item_left;
    }

    @Override
    public int getItemCount() {
        return  responseMessages.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        try{
            holder.textView.setText(responseMessages.get(position).getText());
            holder.name.setText(responseMessages.get(position).getName());
        }catch(Exception e){}

    }
}
