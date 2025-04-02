package com.example.finale;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SiteDetailActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION_REQUEST = 1001;
    private static final int LOCATION_PERMISSION_REQUEST = 1002;

    private ViewPager2 imageSlider;
    private TabLayout indicator;
    private Toolbar toolbar;
    private TextView siteName;
    private TextView siteDescription;
    private TextView addressTitle;
    private TextView siteAddress;
    private MaterialButton callButton;
    private MaterialButton emailButton;
    private MaterialButton mapButton;
    private TextView imageCounter;
    private LinearLayout customIndicator;
    private FrameLayout leftNavButton;
    private FrameLayout rightNavButton;
    
    private String phoneNumber;
    private String emailAddress;
    private String locationAddress;
    private List<Integer> imageResourceIds;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_detail);

        // Initialize views
        imageSlider = findViewById(R.id.imageSlider);
        indicator = findViewById(R.id.indicator);
        toolbar = findViewById(R.id.toolbar);
        siteName = findViewById(R.id.siteName);
        siteDescription = findViewById(R.id.siteDescription);
        addressTitle = findViewById(R.id.addressTitle);
        siteAddress = findViewById(R.id.siteAddress);
        callButton = findViewById(R.id.callButton);
        emailButton = findViewById(R.id.emailButton);
        mapButton = findViewById(R.id.mapButton);
        imageCounter = findViewById(R.id.imageCounter);
        customIndicator = findViewById(R.id.customIndicator);
        leftNavButton = findViewById(R.id.leftNavButton);
        rightNavButton = findViewById(R.id.rightNavButton);

        // Setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize image list
        imageResourceIds = new ArrayList<>();

        // Get data from intent
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("site_name");
            String description = getIntent().getStringExtra("site_description");
            locationAddress = getIntent().getStringExtra("site_address");
            phoneNumber = getIntent().getStringExtra("site_phone");
            emailAddress = getIntent().getStringExtra("site_email");
            
            // Get main image resource ID
            int mainImageResourceId = getIntent().getIntExtra("site_image", R.drawable.ic_attraction);
            
            // Add main image to slider
            imageResourceIds.add(mainImageResourceId);
            
            // Check if additional images are provided
            int[] additionalImages = getIntent().getIntArrayExtra("site_additional_images");
            if (additionalImages != null) {
                for (int imgId : additionalImages) {
                    imageResourceIds.add(imgId);
                }
            } else {
                // If no additional images are provided, add some placeholder images for demo
                // This can be removed in production when you have real data
                if (mainImageResourceId != R.drawable.ic_attraction) {
                    imageResourceIds.add(R.drawable.ic_attraction);
                    imageResourceIds.add(R.drawable.image_gradient_overlay);
                }
            }

            // Set data to views
            siteName.setText(name);
            siteDescription.setText(description);
            siteAddress.setText(locationAddress);
            
            // Setup image slider
            setupImageSlider();

            // Setup RTL support for Arabic
            String currentLang = getResources().getConfiguration().locale.getLanguage();
            if (currentLang.equals("ar")) {
                siteDescription.setTextDirection(View.TEXT_DIRECTION_RTL);
                siteAddress.setTextDirection(View.TEXT_DIRECTION_RTL);
                addressTitle.setTextDirection(View.TEXT_DIRECTION_RTL);
            }

            // Setup action buttons
            setupCallButton();
            setupEmailButton();
            setupMapButton();

            // Setup navigation buttons
            setupNavigationButtons();
        }
    }
    
    private void setupNavigationButtons() {
        leftNavButton.setOnClickListener(v -> {
            if (currentPosition > 0) {
                imageSlider.setCurrentItem(currentPosition - 1, true);
            }
        });
        
        rightNavButton.setOnClickListener(v -> {
            if (currentPosition < imageResourceIds.size() - 1) {
                imageSlider.setCurrentItem(currentPosition + 1, true);
            }
        });
    }
    
    private void setupImageSlider() {
        // Set up ViewPager with images
        ImageSliderAdapter adapter = new ImageSliderAdapter(imageResourceIds);
        imageSlider.setAdapter(adapter);
        
        // Update initial counter
        updateImageCounter(0);
        
        // Register page change callback
        imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateImageCounter(position);
                updateCustomDots(position);
                updateNavigationButtons(position);
            }
        });
        
        // Setup custom dots
        if (imageResourceIds.size() > 1) {
            setupCustomDots(imageResourceIds.size());
            updateCustomDots(0);
            customIndicator.setVisibility(View.VISIBLE);
        } else {
            customIndicator.setVisibility(View.GONE);
        }
    }
    
    private void updateNavigationButtons(int position) {
        leftNavButton.setAlpha(position == 0 ? 0.3f : 0.7f);
        rightNavButton.setAlpha(position == imageResourceIds.size() - 1 ? 0.3f : 0.7f);
    }
    
    private void updateImageCounter(int position) {
        imageCounter.setText(String.format("%d/%d", position + 1, imageResourceIds.size()));
    }
    
    private void setupCustomDots(int count) {
        customIndicator.removeAllViews();
        
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dot.setImageResource(R.drawable.dot_unselected);
            
            // Add click listener to each dot
            final int position = i;
            dot.setOnClickListener(v -> imageSlider.setCurrentItem(position, true));
            
            customIndicator.addView(dot);
        }
    }
    
    private void updateCustomDots(int position) {
        for (int i = 0; i < customIndicator.getChildCount(); i++) {
            ImageView dot = (ImageView) customIndicator.getChildAt(i);
            dot.setImageResource(i == position ? R.drawable.dot_selected : R.drawable.dot_unselected);
            
            // Animate the selected dot
            if (i == position) {
                dot.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200).start();
            } else {
                dot.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
            }
        }
    }
    
    // Image Slider Adapter
    private class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {
        
        private List<Integer> images;
        
        public ImageSliderAdapter(List<Integer> imageResourceIds) {
            this.images = imageResourceIds;
        }
        
        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_slider, parent, false);
            return new ImageViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(images.get(position));
        }
        
        @Override
        public int getItemCount() {
            return images.size();
        }
        
        class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            
            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.sliderImage);
            }
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, lancer l'appel
                makePhoneCall();
            } else {
                // Permission refusée
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.call_permission_denied),
                        Snackbar.LENGTH_LONG
                ).show();
            }
        } else if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && 
                    grantResults[0] == PackageManager.PERMISSION_GRANTED || 
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, ouvrir la carte
                openMap();
            } else {
                // Permission refusée mais on peut toujours ouvrir Google Maps sans localisation précise
                openMapWithoutLocation();
            }
        }
    }

    private void setupCallButton() {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            callButton.setOnClickListener(v -> {
                // Vérifier si la permission d'appel est accordée
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) 
                        != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission
                    ActivityCompat.requestPermissions(this, 
                            new String[]{Manifest.permission.CALL_PHONE}, 
                            CALL_PHONE_PERMISSION_REQUEST);
                } else {
                    // Permission déjà accordée
                    makePhoneCall();
                }
            });
        } else {
            callButton.setEnabled(false);
            callButton.setAlpha(0.5f);
        }
    }
    
    private void makePhoneCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } catch (SecurityException e) {
            Toast.makeText(this, getString(R.string.call_failed), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.no_phone_app), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupEmailButton() {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            emailButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contact: " + siteName.getText());
                    
                    // Créer un sélecteur pour montrer une liste d'applications
                    Intent chooser = Intent.createChooser(intent, getString(R.string.email_chooser_title));
                    
                    if (chooser.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooser);
                    } else {
                        Toast.makeText(this, getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.email_failed), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            emailButton.setEnabled(false);
            emailButton.setAlpha(0.5f);
        }
    }

    private void setupMapButton() {
        if (locationAddress != null && !locationAddress.isEmpty()) {
            mapButton.setOnClickListener(v -> {
                // Vérifier les permissions de localisation
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                        != PackageManager.PERMISSION_GRANTED || 
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) 
                        != PackageManager.PERMISSION_GRANTED) {
                    // Demander les permissions
                    ActivityCompat.requestPermissions(this, 
                            new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            }, 
                            LOCATION_PERMISSION_REQUEST);
                } else {
                    // Permissions déjà accordées
                    openMap();
                }
            });
        } else {
            mapButton.setEnabled(false);
            mapButton.setAlpha(0.5f);
        }
    }
    
    private void openMap() {
        try {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(locationAddress));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                openMapWithoutLocation();
            }
        } catch (Exception e) {
            openMapWithoutLocation();
        }
    }
    
    private void openMapWithoutLocation() {
        try {
            // Si Google Maps n'est pas installé ou si nous n'avons pas les permissions, ouvrir dans le navigateur
            Uri browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(locationAddress));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.map_failed), Toast.LENGTH_SHORT).show();
        }
    }
} 