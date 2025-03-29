package com.example.finale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<Place> placeList;
    private Context context;

    public PlaceAdapter(List<Place> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);
        
        holder.placeName.setText(place.getName());
        holder.placeDescription.setText(place.getDescription());
        holder.placeImage.setImageResource(place.getImageResourceId());
        
        // Configurer les boutons d'action
        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + place.getPhoneNumber()));
            context.startActivity(intent);
        });
        
        holder.btnSms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + place.getPhoneNumber()));
            intent.putExtra("sms_body", "Je suis intéressé(e) par " + place.getName());
            context.startActivity(intent);
        });
        
        holder.btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + place.getEmail()));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Information sur " + place.getName());
            intent.putExtra(Intent.EXTRA_TEXT, "Bonjour,\n\nJe souhaiterais obtenir plus d'informations sur " + place.getName() + ".\n\nCordialement,");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName, placeDescription;
        ImageButton btnCall, btnSms, btnEmail;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeDescription = itemView.findViewById(R.id.placeDescription);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnSms = itemView.findViewById(R.id.btnSms);
            btnEmail = itemView.findViewById(R.id.btnEmail);
        }
    }
} 