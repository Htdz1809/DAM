package com.example.finale;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class SiteDetailActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION_REQUEST = 1001;
    private static final int LOCATION_PERMISSION_REQUEST = 1002;

    private ImageView siteImage;
    private ImageView backButton;
    private TextView siteName;
    private TextView siteDescription;
    private TextView addressTitle;
    private TextView siteAddress;
    private MaterialButton callButton;
    private MaterialButton emailButton;
    private MaterialButton mapButton;
    
    private String phoneNumber;
    private String emailAddress;
    private String locationAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_detail);

        // Initialize views
        siteImage = findViewById(R.id.siteImage);
        backButton = findViewById(R.id.backButton);
        siteName = findViewById(R.id.siteName);
        siteDescription = findViewById(R.id.siteDescription);
        addressTitle = findViewById(R.id.addressTitle);
        siteAddress = findViewById(R.id.siteAddress);
        callButton = findViewById(R.id.callButton);
        emailButton = findViewById(R.id.emailButton);
        mapButton = findViewById(R.id.mapButton);

        // Setup back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Get data from intent
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("site_name");
            String description = getIntent().getStringExtra("site_description");
            locationAddress = getIntent().getStringExtra("site_address");
            phoneNumber = getIntent().getStringExtra("site_phone");
            emailAddress = getIntent().getStringExtra("site_email");
            int imageResourceId = getIntent().getIntExtra("site_image", R.drawable.ic_attraction);

            // Set data to views
            siteName.setText(name);
            siteDescription.setText(description);
            siteAddress.setText(locationAddress);
            siteImage.setImageResource(imageResourceId);

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