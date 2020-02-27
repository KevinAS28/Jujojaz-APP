package com.example.jujojazbase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterEditRecycler extends RecyclerView.Adapter<AdapterEditRecycler.viewHolder> {
    public static List<ModelEditVehicle> data;
    private Context context;

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name, detail;
        ImageView foto;
        ImageButton btnDown;
        RelativeLayout relativeLayoutEdit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEdit);
            detail = itemView.findViewById(R.id.detailEdit);
            foto = itemView.findViewById(R.id.fotoEdit);
            btnDown = itemView.findViewById(R.id.btnDown);
            relativeLayoutEdit = itemView.findViewById(R.id.relativeLayoutEdit);
        }
    }

    public AdapterEditRecycler(Context context, List<ModelEditVehicle> myData) {
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
    public void onBindViewHolder(@NonNull final AdapterEditRecycler.viewHolder holder, final int position) {
        final ModelEditVehicle dataset = data.get(position);
        //Glide.with(context)
        //        .load(dataset.get(0))
        //        .apply(RequestOptions.circleCropTransform())
        //        .into(holder.foto);
        //holder.foto.setImageBitmap(stringToBitmap(dataset.getImage()));
        holder.name.setText(dataset.getTitle());
        holder.detail.setText(dataset.getDetail());
        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AdapterEditRecycler", "btnDown");
                dataset.setExpand(!dataset.isExpand());
                Log.d("AdapterEditRecycler", "dataset : " + dataset.isExpand());
                notifyItemChanged(holder.getAdapterPosition());
            }
        }); 
        boolean isExpand = dataset.isExpand();
        Log.d("AdapterEditRecycler", "isExpand : " + String.valueOf(isExpand));
        holder.relativeLayoutEdit.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        holder.btnDown.animate().rotation(isExpand ? 180 : 0).start();
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
