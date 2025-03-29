package com.example.finale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Charger les préférences
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        
        // Charger le thème avant le setContentView
        loadTheme();
        
        // Charger la langue
        loadLocale();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set status bar color to match the theme
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary, getTheme()));
        
        // Initialiser la bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.navigation_explore) {
                    loadFragment(new ExploreFragment());
                    return true;
                } else if (itemId == R.id.navigation_settings) {
                    loadFragment(new SettingsFragment());
                    return true;
                }
                return false;
            });
            
            // Charger le fragment par défaut
            if (savedInstanceState == null) {
                loadFragment(new HomeFragment());
            }
        }
    }

    private void loadTheme() {
        try {
            // Récupérer les préférences
            boolean isDarkMode = sharedPreferences.getBoolean("dark_mode", false);
    
            // Appliquer le thème
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } catch (Exception e) {
            // Fallback au thème par défaut
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
    
    private void loadLocale() {
        try {
            String languageCode = sharedPreferences.getString("language", "en");
            setLocale(languageCode);
        } catch (Exception e) {
            // Fallback à l'anglais
            setLocale("en");
        }
    }
    
    private void setLocale(String languageCode) {
        try {
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        } catch (Exception e) {
            // Ignorer les erreurs
        }
    }
    
    /**
     * Change l'application dans une nouvelle langue sans redémarrer
     * @param languageCode code de langue (ex: "en", "ar")
     */
    public void changeLanguage(String languageCode) {
        try {
            // Sauvegarder la préférence
            sharedPreferences.edit()
                    .putString("language", languageCode)
                    .apply();
            
            // Appliquer la nouvelle langue
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = new Configuration(resources.getConfiguration());
            config.setLocale(locale);
            
            // Définir explicitement la direction du texte selon la langue
            try {
                if (languageCode.equals("ar")) {
                    config.setLayoutDirection(locale);
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                } else {
                    config.setLayoutDirection(locale);
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }
            } catch (Exception e) {
                // Ignorer les erreurs
            }
            
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            getBaseContext().getResources().updateConfiguration(config, resources.getDisplayMetrics());
            
            // Mettre à jour tous les fragments sans redémarrer l'application
            recreateAllFragments();
        } catch (Exception e) {
            // Ignorer les erreurs pour éviter les crashs
        }
    }
    
    /**
     * Recrée tous les fragments pour appliquer la nouvelle langue
     */
    private void recreateAllFragments() {
        try {
            // Récupérer le fragment actuel
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment == null) return;
            
            // Forcer la mise à jour de la direction pour toute l'activité
            try {
                getWindow().getDecorView().requestLayout();
            } catch (Exception e) {
                // Ignorer les erreurs
            }
            
            // Créer une nouvelle instance du fragment actuel
            Fragment newFragment;
            if (currentFragment instanceof HomeFragment) {
                newFragment = new HomeFragment();
            } else if (currentFragment instanceof ExploreFragment) {
                newFragment = new ExploreFragment();
            } else if (currentFragment instanceof SettingsFragment) {
                newFragment = new SettingsFragment();
            } else {
                // Si c'est un autre type de fragment
                return;
            }
            
            // Remplacer le fragment actuel
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
            
            // Mettre à jour le texte du bottomNavigationView
            if (bottomNavigationView != null) {
                try {
                    bottomNavigationView.getMenu().clear();
                    bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
                    
                    // Mettre l'item correct comme sélectionné
                    if (currentFragment instanceof HomeFragment) {
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    } else if (currentFragment instanceof ExploreFragment) {
                        bottomNavigationView.setSelectedItemId(R.id.navigation_explore);
                    } else if (currentFragment instanceof SettingsFragment) {
                        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);
                    }
                } catch (Exception e) {
                    // Ignorer les erreurs
                }
            }
        } catch (Exception e) {
            // Ignorer les erreurs
        }
    }

    private void loadFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } catch (Exception e) {
            // Ignorer les erreurs
        }
    }
}