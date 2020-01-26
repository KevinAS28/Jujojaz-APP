package com.example.jujojazbase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.viewHolder> {
    public static List<String> data;
    private Context context;


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

    public AdapterHomeRecycler(Context context, List<String> myData) {
        this.context = context;
        this.data = myData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_home, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        List<String> dataset = Collections.singletonList(data.get(position));
        Glide.with(context)
                .load(dataset.get(0))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.foto);
        holder.name.setText(dataset.get(1));
        holder.detail.setText(dataset.get(2));
        holder.btnMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnMoreThan", String.valueOf(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
