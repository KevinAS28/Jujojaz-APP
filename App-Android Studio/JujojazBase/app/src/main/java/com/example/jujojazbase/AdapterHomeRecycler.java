package com.example.jujojazbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.viewHolder> {
    public static List<ModelHomeActivity> data = new ArrayList<>();
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

    public AdapterHomeRecycler(Context context, List<ModelHomeActivity> myData) {
        this.context = context;
        data = myData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_home, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        ModelHomeActivity dataset = data.get(position);
        //Glide.with(context)
        //        .load(dataset.get(0))
        //        .apply(RequestOptions.circleCropTransform())
        //        .into(holder.foto);
        //holder.foto.setImageBitmap(stringToBitmap(dataset.getImage()));
        holder.name.setText(dataset.getTitle());
        holder.detail.setText(dataset.getDetail());
        holder.btnMoreThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnMoreThan", String.valueOf(holder.getAdapterPosition()));
                Intent intent = new Intent(context, EditVehicle.class);
                intent.putExtra("POSITION", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Bitmap stringToBitmap(String encodeString) {
        try {
            byte[] encodeByte = Base64.decode(encodeString, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
