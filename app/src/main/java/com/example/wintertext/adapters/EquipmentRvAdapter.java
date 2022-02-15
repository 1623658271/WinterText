package com.example.wintertext.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.fragments.FragmentStore;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * description ： TODO:游戏装备rv的适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/4 11:00
 */
public class EquipmentRvAdapter extends RecyclerView.Adapter<EquipmentRvAdapter.InnorHolder> {

    private List<String> equipments;
    private List<Integer> resources;

    public EquipmentRvAdapter(List<String> equipments,List<Integer> resources){
        this.equipments = equipments;
        this.resources = resources;
    }

    @NonNull
    @Override
    public EquipmentRvAdapter.InnorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_rv_item,parent,false);
        return new InnorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentRvAdapter.InnorHolder holder, int position) {
        holder.image.setBackgroundResource(resources.get(position));
        holder.name.setText(equipments.get(position));
    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }

    public class InnorHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public InnorHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.eqm_image);
        }
    }
}
