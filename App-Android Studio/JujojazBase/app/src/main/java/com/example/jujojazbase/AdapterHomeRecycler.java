package com.example.jujojazbase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.viewHolder> {
    private String[][] data;


    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;
        ImageView btnMoreThan;

        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            foto = itemView.findViewById(R.id.foto);
            btnMoreThan = itemView.findViewById(R.id.btnMoreThan);
        }
    }

    public AdapterHomeRecycler(String[][] myData) {
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
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        String[] dataset = data[position];
        holder.foto.setImageResource(Integer.parseInt(dataset[0]));
        holder.name.setText(dataset[1]);
        holder.detail.setText(dataset[2]);
        holder.btnMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnMoreThan", String.valueOf(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
