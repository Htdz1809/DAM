package com.example.finale;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment implements WilayaAdapter.OnWilayaClickListener {

    private RecyclerView recyclerView;
    private WilayaAdapter wilayaAdapter;
    private List<Wilaya> wilayaList;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewWilayas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Create and set up the list of wilayas
        setupWilayaList();
        
        // Initialize the adapter
        wilayaAdapter = new WilayaAdapter(wilayaList, getContext(), this);
        recyclerView.setAdapter(wilayaAdapter);
    }
    
    private void setupWilayaList() {
        wilayaList = new ArrayList<>();
        
        // Add wilayas to the list with bilingual descriptions
        wilayaList.add(new Wilaya(
            getString(R.string.wilaya_algiers),
            getString(R.string.wilaya_algiers_ar),
            getString(R.string.wilaya_algiers_desc_en),
            getString(R.string.wilaya_algiers_desc_ar),
            R.drawable.ic_algiers));
            
        wilayaList.add(new Wilaya(
            getString(R.string.wilaya_oran),
            getString(R.string.wilaya_oran_ar),
            getString(R.string.wilaya_oran_desc_en),
            getString(R.string.wilaya_oran_desc_ar),
            R.drawable.ic_oran));
            
        wilayaList.add(new Wilaya(
            getString(R.string.wilaya_constantine),
            getString(R.string.wilaya_constantine_ar),
            getString(R.string.wilaya_constantine_desc_en),
            getString(R.string.wilaya_constantine_desc_ar),
            R.drawable.ic_constantine));

        wilayaList.add(new Wilaya(
            getString(R.string.wilaya_annaba),
            getString(R.string.wilaya_annaba_ar),
            getString(R.string.wilaya_annaba_desc_en),
            getString(R.string.wilaya_annaba_desc_ar),
            R.drawable.ic_annaba));
    }

    @Override
    public void onWilayaClick(Wilaya wilaya, int position) {
        // Navigate to wilaya detail page
        Intent intent = new Intent(getActivity(), WilayaDetailActivity.class);
        intent.putExtra("wilaya_name", wilaya.getName());
        intent.putExtra("wilaya_arabicName", wilaya.getArabicName());
        intent.putExtra("wilaya_description_en", wilaya.getDescriptionEn());
        intent.putExtra("wilaya_description_ar", wilaya.getDescriptionAr());
        intent.putExtra("wilaya_image", wilaya.getImageResourceId());
        startActivity(intent);
    }
} 