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
            if (wilayaName.equals(getString(R.string.wilaya_algiers))) {
                // Tourist Attractions
                allSites.add(new TouristSite(
                        getString(R.string.casbah_name),
                        getString(R.string.casbah_description),
                        getString(R.string.casbah_description),
                        getString(R.string.casbah_address),
                        getString(R.string.casbah_address),
                        getString(R.string.casbah_phone),
                        getString(R.string.casbah_email),
                        R.drawable.casbah,
                        "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.basilica_name),
                        getString(R.string.basilica_description),
                        getString(R.string.basilica_description),
                        getString(R.string.basilica_address),
                        getString(R.string.basilica_address),
                        getString(R.string.basilica_phone),
                        getString(R.string.basilica_email),
                        R.drawable.notre_dame,
                        "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.great_mosque_name),
                        getString(R.string.great_mosque_description),
                        getString(R.string.great_mosque_description),
                        getString(R.string.great_mosque_address),
                        getString(R.string.great_mosque_address),
                        getString(R.string.great_mosque_phone),
                        getString(R.string.great_mosque_email),
                        R.drawable.great_mosque,
                        "ATTRACTION"
                ));

                // Hotels & Restaurants
                allSites.add(new TouristSite(
                        getString(R.string.sofitel_name),
                        getString(R.string.sofitel_description),
                        getString(R.string.sofitel_description),
                        getString(R.string.sofitel_address),
                        getString(R.string.sofitel_address),
                        getString(R.string.sofitel_phone),
                        getString(R.string.sofitel_email),
                        R.drawable.hotel_sofitel,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.aurassi_name),
                        getString(R.string.aurassi_description),
                        getString(R.string.aurassi_description),
                        getString(R.string.aurassi_address),
                        getString(R.string.aurassi_address),
                        getString(R.string.aurassi_phone),
                        getString(R.string.aurassi_email),
                        R.drawable.hotel_aurassi,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.az_hotel_name),
                        getString(R.string.az_hotel_description),
                        getString(R.string.az_hotel_description),
                        getString(R.string.az_hotel_address),
                        getString(R.string.az_hotel_address),
                        getString(R.string.az_hotel_phone),
                        getString(R.string.az_hotel_email),
                        R.drawable.hotel_az,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.tantra_name),
                        getString(R.string.tantra_description),
                        getString(R.string.tantra_description),
                        getString(R.string.tantra_address),
                        getString(R.string.tantra_address),
                        getString(R.string.tantra_phone),
                        getString(R.string.tantra_email),
                        R.drawable.rest_lyre,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.signature_name),
                        getString(R.string.signature_description),
                        getString(R.string.signature_description),
                        getString(R.string.signature_address),
                        getString(R.string.signature_address),
                        getString(R.string.signature_phone),
                        getString(R.string.signature_email),
                        R.drawable.rest_baie,
                        "HOTEL"
                ));

                // Parks & Gardens
                allSites.add(new TouristSite(
                        getString(R.string.hamma_garden_name),
                        getString(R.string.hamma_garden_description),
                        getString(R.string.hamma_garden_description),
                        getString(R.string.hamma_garden_address),
                        getString(R.string.hamma_garden_address),
                        getString(R.string.hamma_garden_phone),
                        getString(R.string.hamma_garden_email),
                        R.drawable.jardin_hamma,
                        "PARK"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.liberte_park_name),
                        getString(R.string.liberte_park_description),
                        getString(R.string.liberte_park_description),
                        getString(R.string.liberte_park_address),
                        getString(R.string.liberte_park_address),
                        getString(R.string.liberte_park_phone),
                        getString(R.string.liberte_park_email),
                        R.drawable.parc_liberte,
                        "PARK"
                ));

                allSites.add(new TouristSite(
                        getString(R.string.dounia_park_name),
                        getString(R.string.dounia_park_description),
                        getString(R.string.dounia_park_description),
                        getString(R.string.dounia_park_address),
                        getString(R.string.dounia_park_address),
                        getString(R.string.dounia_park_phone),
                        getString(R.string.dounia_park_email),
                        R.drawable.dounia_parks,
                        "PARK"
                ));

            } else if (wilayaName.equals(getString(R.string.wilaya_oran))) {
                // Tourist Attractions
                allSites.add(new TouristSite(
                    getString(R.string.santa_cruz_name),
                    getString(R.string.santa_cruz_description),
                    getString(R.string.santa_cruz_description),
                    getString(R.string.santa_cruz_address),
                    getString(R.string.santa_cruz_address),
                    getString(R.string.santa_cruz_phone),
                    getString(R.string.santa_cruz_email),
                    R.drawable.santacruz,
                    "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.pacha_mosque_name),
                    getString(R.string.pacha_mosque_description),
                    getString(R.string.pacha_mosque_description),
                    getString(R.string.pacha_mosque_address),
                    getString(R.string.pacha_mosque_address),
                    getString(R.string.pacha_mosque_phone),
                    getString(R.string.pacha_mosque_email),
                    R.drawable.pacha_mousque,
                    "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.oran_station_name),
                    getString(R.string.oran_station_description),
                    getString(R.string.oran_station_description),
                    getString(R.string.oran_station_address),
                    getString(R.string.oran_station_address),
                    getString(R.string.oran_station_phone),
                    getString(R.string.oran_station_email),
                    R.drawable.oran_station,
                    "ATTRACTION"
                ));

                // Hotels
                allSites.add(new TouristSite(
                    getString(R.string.sheraton_name),
                    getString(R.string.sheraton_description),
                    getString(R.string.sheraton_description),
                    getString(R.string.sheraton_address),
                    getString(R.string.sheraton_address),
                    getString(R.string.sheraton_phone),
                    getString(R.string.sheraton_email),
                    R.drawable.chiraton,
                    "HOTEL"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.royal_hotel_name),
                    getString(R.string.royal_hotel_description),
                    getString(R.string.royal_hotel_description),
                    getString(R.string.royal_hotel_address),
                    getString(R.string.royal_hotel_address),
                    getString(R.string.royal_hotel_phone),
                    getString(R.string.royal_hotel_email),
                    R.drawable.m_garelly,
                    "HOTEL"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.colombe_name),
                    getString(R.string.colombe_description),
                    getString(R.string.colombe_description),
                    getString(R.string.colombe_address),
                    getString(R.string.colombe_address),
                    getString(R.string.colombe_phone),
                    getString(R.string.colombe_email),
                    R.drawable.colombe,
                    "HOTEL"
                ));

                // Restaurants
                allSites.add(new TouristSite(
                    getString(R.string.comete_name),
                    getString(R.string.comete_description),
                    getString(R.string.comete_description),
                    getString(R.string.comete_address),
                    getString(R.string.comete_address),
                    getString(R.string.comete_phone),
                    getString(R.string.comete_email),
                    R.drawable.lacomete,
                    "HOTEL"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.villa_name),
                    getString(R.string.villa_description),
                    getString(R.string.villa_description),
                    getString(R.string.villa_address),
                    getString(R.string.villa_address),
                    getString(R.string.villa_phone),
                    getString(R.string.villa_email),
                    R.drawable.amb,
                    "HOTEL"
                ));

                // Parks & Natural Sites
                allSites.add(new TouristSite(
                    getString(R.string.canastel_name),
                    getString(R.string.canastel_description),
                    getString(R.string.canastel_description),
                    getString(R.string.canastel_address),
                    getString(R.string.canastel_address),
                    getString(R.string.canastel_phone),
                    getString(R.string.canastel_email),
                    R.drawable.fc,
                    "PARK"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.andalusian_name),
                    getString(R.string.andalusian_description),
                    getString(R.string.andalusian_description),
                    getString(R.string.andalusian_address),
                    getString(R.string.andalusian_address),
                    getString(R.string.andalusian_phone),
                    getString(R.string.andalusian_email),
                    R.drawable.ando,
                    "PARK"
                ));

                allSites.add(new TouristSite(
                    getString(R.string.paradise_beach_name),
                    getString(R.string.paradise_beach_description),
                    getString(R.string.paradise_beach_description),
                    getString(R.string.paradise_beach_address),
                    getString(R.string.paradise_beach_address),
                    getString(R.string.paradise_beach_phone),
                    getString(R.string.paradise_beach_email),
                    R.drawable.pradice_beach,
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