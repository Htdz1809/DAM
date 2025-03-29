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
        wilayaList.add(new Wilaya("Algiers", "الجزائر العاصمة", 
            "Algiers, the capital of Algeria, is a vibrant city where history and modernity blend seamlessly. Known for its stunning white-washed buildings overlooking the Mediterranean, Algiers boasts iconic landmarks such as the Casbah (a UNESCO World Heritage Site), the Martyrs' Memorial, and the scenic Bay of Algiers. Visitors can explore museums, historical palaces, and lively markets while enjoying a mix of French colonial and traditional Algerian architecture.",
            "الجزائر العاصمة مدينة نابضة بالحياة حيث يلتقي التاريخ بالحداثة. تشتهر بمبانيها البيضاء المطلة على البحر الأبيض المتوسط، وتضم معالم أيقونية مثل القصبة (موقع تراث عالمي لليونسكو)، ومقام الشهيد، وخليج الجزائر الساحر. يمكن للزوار استكشاف المتاحف والقصور التاريخية والأسواق الحيوية، والاستمتاع بالمزيج الفريد بين العمارة الاستعمارية الفرنسية والتقاليد الجزائرية.",
            R.drawable.ic_algiers));
            
        wilayaList.add(new Wilaya("Oran", "وهران", 
            "Oran, the \"Pearl of the West,\" is one of Algeria's most dynamic cities. Famous for its musical heritage, especially Rai music, Oran offers a lively atmosphere with beautiful Mediterranean beaches, historic sites like Fort Santa Cruz, and the majestic Great Mosque. The city's Spanish and French influences are reflected in its architecture, and its vibrant nightlife and cultural scene make it a must-visit destination.",
            "وهران، \"لؤلؤة الغرب\"، هي واحدة من أكثر المدن ديناميكية في الجزائر. تشتهر بتراثها الموسيقي، خاصة موسيقى الراي، وتوفر أجواءً حيوية مع شواطئها المتوسطية الجميلة، ومعالمها التاريخية مثل حصن سانتا كروز، والمسجد الكبير الرائع. تنعكس التأثيرات الإسبانية والفرنسية في هندستها المعمارية، وتضفي الحياة الليلية النابضة والمشهد الثقافي المتنوع سحرًا خاصًا على المدينة.",
            R.drawable.ic_oran));
            
        wilayaList.add(new Wilaya("Constantine", "قسنطينة", 
            "Constantine, known as the \"City of Bridges,\" is one of Algeria's most breathtaking destinations. Built on dramatic cliffs and connected by stunning suspension bridges, the city is a true architectural marvel. It is rich in history, with ancient ruins, Ottoman palaces, and the famous Emir Abdelkader Mosque. The city is also a hub of Algerian culture, renowned for its traditional Andalusian music and exquisite local cuisine.",
            "قسنطينة، المعروفة باسم \"مدينة الجسور\"، هي واحدة من أروع الوجهات في الجزائر. بُنيت على منحدرات صخرية مذهلة وتربطها جسور معلقة رائعة، مما يجعلها تحفة معمارية فريدة. تزخر المدينة بتاريخ عريق يضم أطلالًا قديمة وقصورًا عثمانية، إضافة إلى جامع الأمير عبد القادر الشهير. تُعد قسنطينة أيضًا مركزًا للثقافة الجزائرية، حيث تشتهر بالموسيقى الأندلسية التقليدية ومأكولاتها المحلية الفريدة.",
            R.drawable.ic_constantine));
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