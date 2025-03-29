package com.example.finale;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class TouristSiteAdapter extends RecyclerView.Adapter<TouristSiteAdapter.SiteViewHolder> {

    private List<TouristSite> sites;

    public TouristSiteAdapter(List<TouristSite> sites) {
        this.sites = sites;
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourist_site, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        TouristSite site = sites.get(position);
        
        // Set site data to views
        holder.siteName.setText(site.getName());
        
        // Set description and address based on current language
        String currentLang = holder.itemView.getContext().getResources().getConfiguration().locale.getLanguage();
        if (currentLang.equals("ar")) {
            holder.siteDescription.setText(site.getDescriptionAr());
            holder.siteAddress.setText(site.getAddressAr());
            holder.siteDescription.setTextDirection(View.TEXT_DIRECTION_RTL);
            holder.siteAddress.setTextDirection(View.TEXT_DIRECTION_RTL);
        } else {
            holder.siteDescription.setText(site.getDescriptionEn());
            holder.siteAddress.setText(site.getAddress());
            holder.siteDescription.setTextDirection(View.TEXT_DIRECTION_LTR);
            holder.siteAddress.setTextDirection(View.TEXT_DIRECTION_LTR);
        }
        
        holder.siteImage.setImageResource(site.getImageResourceId());
        
        // Set category icon
        switch (site.getCategory()) {
            case "ATTRACTION":
                holder.categoryIcon.setImageResource(R.drawable.ic_attraction);
                break;
            case "HOTEL":
                holder.categoryIcon.setImageResource(R.drawable.ic_hotel);
                break;
            case "PARK":
                holder.categoryIcon.setImageResource(R.drawable.ic_park);
                break;
            default:
                holder.categoryIcon.setImageResource(R.drawable.ic_attraction);
                break;
        }

        // Set click listener to open site detail page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), SiteDetailActivity.class);
            
            // Pass site data to detail activity
            intent.putExtra("site_name", site.getName());
            
            // Pass appropriate localized description and address
            if (currentLang.equals("ar")) {
                intent.putExtra("site_description", site.getDescriptionAr());
                intent.putExtra("site_address", site.getAddressAr());
            } else {
                intent.putExtra("site_description", site.getDescriptionEn());
                intent.putExtra("site_address", site.getAddress());
            }
            
            intent.putExtra("site_phone", site.getPhoneNumber());
            intent.putExtra("site_email", site.getEmail());
            intent.putExtra("site_image", site.getImageResourceId());
            intent.putExtra("site_category", site.getCategory());
            
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }
    
    /**
     * Updates the data in the adapter and notifies for UI refresh
     */
    public void updateData(List<TouristSite> newSites) {
        this.sites = newSites;
        notifyDataSetChanged();
    }

    static class SiteViewHolder extends RecyclerView.ViewHolder {
        ImageView siteImage;
        ImageView categoryIcon;
        TextView siteName;
        TextView siteDescription;
        TextView siteAddress;

        public SiteViewHolder(@NonNull View itemView) {
            super(itemView);
            siteImage = itemView.findViewById(R.id.siteImage);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            siteName = itemView.findViewById(R.id.siteName);
            siteDescription = itemView.findViewById(R.id.siteDescription);
            siteAddress = itemView.findViewById(R.id.siteAddress);
        }
    }
} 