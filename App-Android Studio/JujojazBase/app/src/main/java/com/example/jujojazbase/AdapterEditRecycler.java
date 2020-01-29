package com.example.jujojazbase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdapterEditRecycler extends RecyclerView.Adapter<AdapterEditRecycler.viewHolder> {
    public static List<List<String>> data;
    private Context context;
    private boolean btnDownBool = false;

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;
        ImageButton btnDown;
        ViewGroup viewGroup;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEdit);
            detail = itemView.findViewById(R.id.detailEdit);
            foto = itemView.findViewById(R.id.fotoEdit);
            btnDown = itemView.findViewById(R.id.btnDown);
        }
    }

    public AdapterEditRecycler(Context context, List<List<String>> myData) {
        this.context = context;
        data = myData;
    }

    @NonNull
    @Override
    public AdapterEditRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_edit , parent, false);
        AdapterEditRecycler.viewHolder vh = new AdapterEditRecycler.viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterEditRecycler.viewHolder holder, int position) {
        List<String> dataset = data.get(position);
        //Glide.with(context)
        //        .load(dataset.get(0))
        //        .apply(RequestOptions.circleCropTransform())
        //        .into(holder.foto);
        holder.name.setText(dataset.get(1));
        holder.detail.setText(dataset.get(2));
        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnDown", String.valueOf(holder.getAdapterPosition()));
                Log.d("btnDown", "Height : " + holder.viewGroup.getLayoutParams().height + "\nWidth : " + holder.viewGroup.getLayoutParams().width);
                btnDownBool = !btnDownBool;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
