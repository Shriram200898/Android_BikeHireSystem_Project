package com.example.bikehire.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikehire.Model.ChatModel;
import com.example.bikehire.Model.MsgModel;
import com.example.bikehire.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatModel> chatModelArrayList;
    private Context context;

    public ChatAdapter(ArrayList<ChatModel> chatModelArrayList, Context context) {
        this.chatModelArrayList = chatModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
       switch (viewType){
           case 0:
               view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item,parent,false);
               return new UserViewHolder(view);

           case 1:
               view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item,parent,false);
               return new BotViewHolder(view);
       }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModelArrayList.get(position);
        switch (chatModel.getSender()){
            case "user":
                ((UserViewHolder)holder).userTV.setText(chatModel.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).botTV.setText(chatModel.getMessage());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatModelArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userTV;
        public UserViewHolder(@NonNull View itemview){
            super(itemview);
            userTV=itemview.findViewById(R.id.txtusermsg);

        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView botTV;
        public BotViewHolder(@NonNull View itemview){
            super(itemview);
            botTV=itemview.findViewById(R.id.txtmsgbot);

        }
    }
}
