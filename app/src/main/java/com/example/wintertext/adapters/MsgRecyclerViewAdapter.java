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

/**
 * description ： TODO:游戏外消息的适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:34
 */
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
            holder.game_number.setVisibility(View.GONE);
            holder.aMsg.setVisibility(View.VISIBLE);
            holder.bMsg.setVisibility(View.GONE);
            holder.a_content.setText(msg.getContent());
            holder.result.setVisibility(View.GONE);
        }else if(msg.getType()==Msg.TYPE_B){
            holder.game_number.setVisibility(View.GONE);
            holder.bMsg.setVisibility(View.VISIBLE);
            holder.aMsg.setVisibility(View.GONE);
            holder.b_content.setText(msg.getContent());
            holder.result.setVisibility(View.GONE);
        }else if(msg.getType()==Msg.TYPE_C){
            holder.game_number.setVisibility(View.VISIBLE);
            holder.bMsg.setVisibility(View.GONE);
            holder.aMsg.setVisibility(View.GONE);
            holder.game_number.setText(msg.getContent());
            holder.result.setVisibility(View.GONE);
        }else if(msg.getType()==Msg.TYPE_D){
            holder.result.setVisibility(View.VISIBLE);
            holder.game_number.setVisibility(View.GONE);
            holder.bMsg.setVisibility(View.GONE);
            holder.aMsg.setVisibility(View.GONE);
            holder.result.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout aMsg, bMsg;
        TextView a_content,b_content,game_number,result;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aMsg = itemView.findViewById(R.id.msg_A_ll);
            bMsg = itemView.findViewById(R.id.msg_B_ll);
            a_content = itemView.findViewById(R.id.msg_A_tv);
            b_content = itemView.findViewById(R.id.msg_B_tv);
            game_number = itemView.findViewById(R.id.game_number);
            result = itemView.findViewById(R.id.result);
        }
    }
}
