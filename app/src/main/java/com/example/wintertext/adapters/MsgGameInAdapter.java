package com.example.wintertext.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.beans.Msg;

import java.util.List;

/**
 * description ： TODO:游戏内消息的适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/3 15:25
 */
public class MsgGameInAdapter extends RecyclerView.Adapter<MsgGameInAdapter.ViewHolder> {
    private List<Msg> msgList;

    public MsgGameInAdapter(List<Msg> msgList){
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public MsgGameInAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamein_situation_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgGameInAdapter.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType()==Msg.TYPE_A){
            //若为A方操作,则隐藏B方和C方
            holder.A_msg.setVisibility(View.VISIBLE);
            holder.B_msg.setVisibility(View.GONE);
            holder.C_msg.setVisibility(View.GONE);
            holder.A_msg.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_B){
            //若为B方操作,则隐藏A方和C方
            holder.B_msg.setVisibility(View.VISIBLE);
            holder.A_msg.setVisibility(View.GONE);
            holder.C_msg.setVisibility(View.GONE);
            holder.B_msg.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_C){
            //若为C方操作,则隐藏A方和B方
            holder.C_msg.setVisibility(View.VISIBLE);
            holder.A_msg.setVisibility(View.GONE);
            holder.B_msg.setVisibility(View.GONE);
            holder.C_msg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView A_msg,B_msg,C_msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            A_msg = itemView.findViewById(R.id.gamein_A_msg);
            B_msg = itemView.findViewById(R.id.gamein_B_msg);
            C_msg = itemView.findViewById(R.id.gamein_C_msg);
        }
    }
}
