package com.example.finale;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WilayaDetailActivity extends AppCompatActivity {

    private ImageView wilayaImage;
    private TextView wilayaName, wilayaDescription;
    private Chip filterAll, filterAttractions, filterHotels, filterParks;
    private RecyclerView sitesRecyclerView;
    private TouristSiteAdapter siteAdapter;
    private List<TouristSite> allSites = new ArrayList<>();
    private String currentCategory = "ALL";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilaya_detail);
        
        // Setup back button
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> onBackPressed());
        }
        
        // Initialize views
        wilayaImage = findViewById(R.id.wilayaImage);
        wilayaName = findViewById(R.id.wilayaName);
        wilayaDescription = findViewById(R.id.wilayaDescription);
        filterAll = findViewById(R.id.filterAll);
        filterAttractions = findViewById(R.id.filterAttractions);
        filterHotels = findViewById(R.id.filterHotels);
        filterParks = findViewById(R.id.filterParks);
        sitesRecyclerView = findViewById(R.id.sitesRecyclerView);
        
        // Get wilaya data from intent
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("wilaya_name");
            String arabicName = getIntent().getStringExtra("wilaya_arabicName");
            String descriptionEn = getIntent().getStringExtra("wilaya_description_en");
            String descriptionAr = getIntent().getStringExtra("wilaya_description_ar");
            int imageResourceId = getIntent().getIntExtra("wilaya_image", R.drawable.ic_algiers);
            
            // Set data to views based on current language
            String currentLang = getResources().getConfiguration().locale.getLanguage();
            if (currentLang.equals("ar")) {
                wilayaName.setText(arabicName);
                wilayaDescription.setText(descriptionAr);
                wilayaDescription.setTextDirection(View.TEXT_DIRECTION_RTL);
            } else {
                wilayaName.setText(name);
                wilayaDescription.setText(descriptionEn);
                wilayaDescription.setTextDirection(View.TEXT_DIRECTION_LTR);
            }
            
            wilayaImage.setImageResource(imageResourceId);
        }
        
        // Setup RecyclerView
        sitesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Load sample data based on wilaya
        loadSampleSites();
        
        // Initialize adapter with all sites
        siteAdapter = new TouristSiteAdapter(allSites);
        sitesRecyclerView.setAdapter(siteAdapter);
        
        // Setup filter chips
        setupFilterChips();
    }
    
    private void setupFilterChips() {
        // Set initial selection
        filterAll.setChecked(true);
        
        // Setup click listeners
        filterAll.setOnClickListener(v -> filterSites("ALL"));
        filterAttractions.setOnClickListener(v -> filterSites("ATTRACTION"));
        filterHotels.setOnClickListener(v -> filterSites("HOTEL"));
        filterParks.setOnClickListener(v -> filterSites("PARK"));
    }
    
    private void filterSites(String category) {
        currentCategory = category;
        List<TouristSite> filteredList = new ArrayList<>();
        
        if (category.equals("ALL")) {
            filteredList.addAll(allSites);
        } else {
            for (TouristSite site : allSites) {
                if (site.getCategory().equals(category)) {
                    filteredList.add(site);
                }
            }
        }
        
        siteAdapter.updateData(filteredList);
    }
    
    private void loadSampleSites() {
        // Get wilaya name from intent
        String wilayaName = getIntent().getStringExtra("wilaya_name");
        
        // Add sample sites based on the wilaya
        if (wilayaName != null) {
            if (wilayaName.equals("Algiers") || wilayaName.equals("Alger")) {
                // Tourist Attractions
                allSites.add(new TouristSite(
                    "The Casbah of Algiers | قصبة الجزائر", 
                    "A historic district with narrow streets and Ottoman architecture, listed as a UNESCO World Heritage Site.", 
                    "حي تاريخي بأزقة ضيقة وهندسة عثمانية، مدرج ضمن قائمة التراث العالمي لليونسكو.",
                    "Casbah, Algiers, Algeria",
                    "القصبة، الجزائر، الجزائر", 
                    "+213 21 98 76 54",
                    "casbah@tourism.dz",
                    R.drawable.casbah,
                    "ATTRACTION"
                ));
                
                allSites.add(new TouristSite(
                    "Basilica of Our Lady of Africa | كاتدرائية السيدة الإفريقية", 
                    "A 19th-century church offering a panoramic view of Algiers Bay.", 
                    "كنيسة من القرن التاسع عشر تطل على خليج الجزائر بمنظر بانورامي رائع.",
                    "Rue de Zighara, Algiers, Algeria",
                    "شارع زيغارة، الجزائر، الجزائر", 
                    "+213 21 21 54 90",
                    "basilica@tourism.dz",
                    R.drawable.notre_dame,
                    "ATTRACTION"
                ));
                
                allSites.add(new TouristSite(
                    "The Great Mosque of Algiers | المسجد الأعظم", 
                    "The world's third-largest mosque, featuring the tallest minaret (265m) and modern architecture.", 
                    "ثالث أكبر مسجد في العالم، يحتوي على أطول مئذنة (265 م) وتصميم معماري حديث.",
                    "Mohammadia, Algiers, Algeria",
                    "المحمدية، الجزائر، الجزائر", 
                    "+213 21 30 82 55",
                    "greatmosque@tourism.dz",
                    R.drawable.great_mosque,
                    "ATTRACTION"
                ));
                
                // Hotels & Restaurants
                allSites.add(new TouristSite(
                    "Sofitel Algiers Hamma Garden | فندق سوفيتيل الجزائر حديقة الحامة", 
                    "A luxury 5-star hotel near the botanical garden.", 
                    "فندق فاخر من فئة 5 نجوم بجوار الحديقة النباتية.",
                    "172, Rue Hassiba Ben Bouali, Algiers, Algeria",
                    "172، شارع حسيبة بن بوعلي، الجزائر، الجزائر", 
                    "+213 21 68 52 10",
                    "reservations@sofitel-algiers.com",
                    R.drawable.hotel_sofitel,
                    "HOTEL"
                ));
                
                allSites.add(new TouristSite(
                    "El Aurassi Hotel | فندق الأوراسي", 
                    "A high-end hotel with a stunning bay view and multiple restaurants.", 
                    "فندق فاخر يطل على الخليج، يضم عدة مطاعم.",
                    "2, Boulevard Frantz Fanon, Algiers, Algeria",
                    "2، شارع فرانتز فانون، الجزائر، الجزائر", 
                    "+213 21 74 82 52",
                    "contact@elaurassi.com",
                    R.drawable.hotel_aurassi,
                    "HOTEL"
                ));
                
                allSites.add(new TouristSite(
                    "AZ Hotel Kouba | فندق AZ كوبّا", 
                    "A modern 4-star hotel in Kouba with international cuisine.", 
                    "فندق عصري من فئة 4 نجوم في كوبّا، يقدم مأكولات عالمية.",
                    "Avenue Mohamed Rabia, Kouba, Algiers, Algeria",
                    "شارع محمد رابيا، كوبّا، الجزائر، الجزائر", 
                    "+213 23 75 70 70",
                    "reservation@azhotel.dz",
                    R.drawable.hotel_az,
                    "HOTEL"
                ));
                
                allSites.add(new TouristSite(
                    "Le Tantra | مطعم التانترا", 
                    "A stylish restaurant serving a mix of local and international dishes.", 
                    "مطعم أنيق يقدم مزيجًا من الأطباق المحلية والعالمية.",
                    "36, Rue Mohamed Khoudi, El Mouradia, Algiers, Algeria",
                    "36، شارع محمد خودي، المرادية، الجزائر، الجزائر", 
                    "+213 21 69 20 20",
                    "reservations@tantra.dz",
                    R.drawable.rest_lyre,
                    "HOTEL"
                ));
                
                allSites.add(new TouristSite(
                    "Restaurant Signature | مطعم سيجنتشر", 
                    "A fine-dining restaurant known for its gourmet cuisine and elegant decor.", 
                    "مطعم راقٍ يشتهر بمأكولاته الفاخرة وديكوره الأنيق.",
                    "Val d'Hydra, Algiers, Algeria",
                    "فال د'حيدرة، الجزائر، الجزائر", 
                    "+213 21 60 37 73",
                    "info@signature-restaurant.dz",
                    R.drawable.rest_baie,
                    "HOTEL"
                ));
                
                // Parks & Gardens
                allSites.add(new TouristSite(
                    "Jardin d'Essai du Hamma | حديقة التجارب الحامة", 
                    "A famous botanical garden with diverse plants, ideal for a stroll.", 
                    "حديقة نباتية شهيرة تضم نباتات متنوعة، مثالية للنزهات.",
                    "Rue Hassiba Ben Bouali, Algiers, Algeria",
                    "شارع حسيبة بن بوعلي، الجزائر، الجزائر", 
                    "+213 21 77 14 64",
                    "contact@jardin-essai.dz",
                    R.drawable.jardin_hamma,
                    "PARK"
                ));
                
                allSites.add(new TouristSite(
                    "Parc de la Liberté | منتزه الحرية", 
                    "A peaceful green park in the city center, perfect for relaxation.", 
                    "منتزه هادئ وسط المدينة، مثالي للاسترخاء.",
                    "Rue Didouche Mourad, Algiers, Algeria",
                    "شارع ديدوش مراد، الجزائر، الجزائر", 
                    "+213 21 63 05 97",
                    "info@parc-liberte.dz",
                    R.drawable.parc_liberte,
                    "PARK"
                ));
                
                allSites.add(new TouristSite(
                    "Parc Dounia | حديقة الدنيا", 
                    "A large park with leisure areas, children's playgrounds, and walking trails.", 
                    "حديقة واسعة تضم مناطق ترفيهية وأماكن لعب للأطفال ومسارات للمشي.",
                    "Cheraga, Algiers, Algeria",
                    "الشراقة، الجزائر، الجزائر", 
                    "+213 21 36 12 05",
                    "contact@dounia-park.dz",
                    R.drawable.dounia_parks,
                    "PARK"
                ));
                
            } else {
                // For other wilayas, show a message that only Algiers sites are currently available
                allSites.add(new TouristSite(
                    "Algiers Sites Only", 
                    "Currently only Algiers sites are available in this demo",
                    "حاليًا فقط مواقع الجزائر العاصمة متاحة في هذا العرض التجريبي", 
                    "N/A", 
                    "غير متوفر",
                    R.drawable.ic_attraction, 
                    "ATTRACTION"
                ));
            }
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 