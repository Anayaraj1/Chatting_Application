package com.example.chattingapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapplication.Models.MessageModel;
import com.example.chattingapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;


    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.sample_sender , parent , false);
            return new SenderViewHolder(v);
        }
       else {
            View v = LayoutInflater.from(context).inflate(R.layout.sample_receiver , parent , false);
            return new RecieverViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete the message")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return false;
            }
        });
        if (holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderrMsg.setText(messageModel.getMessage());
        }
        else
        {
            ((RecieverViewHolder)holder).recieverMsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }



    public  class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView recieverMsg,reciverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverMsg =itemView.findViewById(R.id.reciverText);
            reciverTime = itemView.findViewById(R.id.reciverTime);

        }
    }

    public  class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderrMsg, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderrMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
