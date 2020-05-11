package com.tastemaquers.naada.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tastemaquers.naada.R;
import com.tastemaquers.naada.data.models.ResponseMessage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    private List<ResponseMessage> responseMessages;
    private Context context;

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView name;
        TextView timeStamp;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
            name=itemView.findViewById(R.id.other_name);
            timeStamp= itemView.findViewById(R.id.timeStamp);
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
            holder.timeStamp.setText(responseMessages.get(position).getTimestamp());
        }catch(Exception e){}
    }
}
