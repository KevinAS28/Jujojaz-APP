package com.example.jujojazbase;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEditRecycler extends RecyclerView.Adapter<AdapterEditRecycler.viewHolder> {
    String[][] data;

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;
        ImageButton btnDown;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEdit);
            detail = itemView.findViewById(R.id.detailEdit);
            foto = itemView.findViewById(R.id.fotoEdit);
            btnDown = itemView.findViewById(R.id.btnDown);
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
    public void onBindViewHolder(@NonNull final AdapterEditRecycler.viewHolder holder, int position) {
        String[] dataset = data[position];
        holder.foto.setImageResource(Integer.parseInt(dataset[0]));
        holder.name.setText(dataset[1]);
        holder.detail.setText(dataset[2]);
        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnDown", String.valueOf(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
