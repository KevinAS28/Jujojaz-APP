package com.example.jujojazbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapterRecyclerView extends RecyclerView.Adapter<adapterRecyclerView.viewHolder> {
    private String[] data;


    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;

        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            foto = itemView.findViewById(R.id.foto);
        }
    }

    public adapterRecyclerView(String[] myData) {
        this.data = myData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_home , parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //wait
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
