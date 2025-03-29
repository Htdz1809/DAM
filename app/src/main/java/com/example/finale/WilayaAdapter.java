package com.example.finale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WilayaAdapter extends RecyclerView.Adapter<WilayaAdapter.WilayaViewHolder> {

    private List<Wilaya> wilayaList;
    private Context context;
    private OnWilayaClickListener listener;

    // Interface for click listener
    public interface OnWilayaClickListener {
        void onWilayaClick(Wilaya wilaya, int position);
    }

    public WilayaAdapter(List<Wilaya> wilayaList, Context context, OnWilayaClickListener listener) {
        this.wilayaList = wilayaList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WilayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wilaya, parent, false);
        return new WilayaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WilayaViewHolder holder, int position) {
        Wilaya wilaya = wilayaList.get(position);
        
        // Afficher le nom en fonction de la langue de l'appareil
        String currentLang = context.getResources().getConfiguration().locale.getLanguage();
        if (currentLang.equals("ar")) {
            holder.wilayaName.setText(wilaya.getArabicName());
            holder.wilayaDescription.setText(wilaya.getDescriptionAr());
            // Pour l'arabe, aligner le texte à droite
            holder.wilayaName.setTextDirection(View.TEXT_DIRECTION_RTL);
            holder.wilayaDescription.setTextDirection(View.TEXT_DIRECTION_RTL);
        } else {
            holder.wilayaName.setText(wilaya.getName());
            holder.wilayaDescription.setText(wilaya.getDescriptionEn());
            holder.wilayaName.setTextDirection(View.TEXT_DIRECTION_LTR);
            holder.wilayaDescription.setTextDirection(View.TEXT_DIRECTION_LTR);
        }
        
        holder.wilayaImage.setImageResource(wilaya.getImageResourceId());
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWilayaClick(wilaya, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wilayaList.size();
    }

    static class WilayaViewHolder extends RecyclerView.ViewHolder {
        ImageView wilayaImage;
        TextView wilayaName;
        TextView wilayaDescription;

        public WilayaViewHolder(@NonNull View itemView) {
            super(itemView);
            wilayaImage = itemView.findViewById(R.id.wilayaImage);
            wilayaName = itemView.findViewById(R.id.wilayaName);
            wilayaDescription = itemView.findViewById(R.id.wilayaDescription);
        }
    }
} 