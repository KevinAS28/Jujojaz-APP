package com.example.jujojazbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEditRecycler extends RecyclerView.Adapter<AdapterEditRecycler.viewHolder> {
    String[][] data;

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEdit);
            detail = itemView.findViewById(R.id.detailEdit);
            foto = itemView.findViewById(R.id.fotoEdit);
        }
    }

    public AdapterEditRecycler(String[][] myData) {
        this.data = myData;
    }

    @NonNull
    @Override
    public AdapterEditRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_edit , parent, false);
        AdapterEditRecycler.viewHolder vh = new AdapterEditRecycler.viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEditRecycler.viewHolder holder, int position) {
        String[] dataset = data[position];
        holder.foto.findViewById(Integer.parseInt(dataset[0]));
        holder.name.findViewById(Integer.parseInt(dataset[1]));
        holder.detail.findViewById(Integer.parseInt(dataset[2]));
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
