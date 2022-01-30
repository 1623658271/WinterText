package com.example.wintertext.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.beans.Msg;

import java.util.List;

public class MsgRecyclerViewAdapter extends RecyclerView.Adapter<MsgRecyclerViewAdapter.ViewHolder> {

    private List<Msg> msgList;

    public MsgRecyclerViewAdapter(List<Msg> msgList){
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public MsgRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.situation_msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgRecyclerViewAdapter.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType()==Msg.TYPE_A){
            //若为A方战况消息,则隐藏B方消息布局
            holder.aMsg.setVisibility(View.VISIBLE);
            holder.bMsg.setVisibility(View.GONE);
            holder.a_content.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_B){
            //若为B方战况消息,则隐藏A方消息布局
            holder.bMsg.setVisibility(View.VISIBLE);
            holder.aMsg.setVisibility(View.GONE);
            holder.b_content.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout aMsg, bMsg;
        TextView a_content,b_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aMsg = itemView.findViewById(R.id.msg_A_ll);
            bMsg = itemView.findViewById(R.id.msg_B_ll);
            a_content = itemView.findViewById(R.id.msg_A_tv);
            b_content = itemView.findViewById(R.id.msg_B_tv);
        }
    }
}
