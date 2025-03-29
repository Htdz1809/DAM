package com.example.finale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private RadioGroup languageRadioGroup;
    private RadioButton radioEnglish, radioArabic;
    private SwitchMaterial themeSwitch;
    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            // Initialiser les préférences partagées
            if (getActivity() != null) {
                sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
            }

            // Initialiser les vues
            languageRadioGroup = view.findViewById(R.id.languageRadioGroup);
            radioEnglish = view.findViewById(R.id.radioEnglish);
            radioArabic = view.findViewById(R.id.radioArabic);
            themeSwitch = view.findViewById(R.id.themeSwitch);

            if (languageRadioGroup != null && radioEnglish != null && radioArabic != null) {
                // Définir la langue actuellement sélectionnée
                String currentLang = Locale.getDefault().getLanguage();
                if (currentLang.equals("ar")) {
                    radioArabic.setChecked(true);
                } else {
                    radioEnglish.setChecked(true);
                }

                // Gérer le changement de langue
                languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    try {
                        String languageCode;
                        if (checkedId == R.id.radioArabic) {
                            languageCode = "ar";
                        } else {
                            languageCode = "en";
                        }

                        // Appliquer le changement de langue immédiatement
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).changeLanguage(languageCode);

                            // Afficher un message de confirmation
                            try {
                                String message = languageCode.equals("ar") ? "تم تغيير اللغة" : "Language changed";
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                // Ignorer l'erreur si le toast échoue
                            }
                        }
                    } catch (Exception e) {
                        // Ignorer les erreurs pour éviter les crashs
                    }
                });
            }

            // Configurer le switch de thème
            if (themeSwitch != null) {
                setupThemeSwitch();
            }
        } catch (Exception e) {
            // Capturer toutes les exceptions pour éviter les crashes
        }
    }

    private void setupThemeSwitch() {
        try {
            // Récupérer le mode actuel
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            boolean isDarkModeOn = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

            // Définir l'état initial du switch
            themeSwitch.setChecked(isDarkModeOn);

            // Listener pour le changement de thème
            themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                try {
                    if (isChecked) {
                        // Activer le mode sombre
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        // Activer le mode clair
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }

                    // Sauvegarder la préférence
                    if (sharedPreferences != null) {
                        sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply();
                    }
                } catch (Exception e) {
                    // Ignorer les erreurs pour éviter les crashs
                }
            });
        } catch (Exception e) {
            // Ignorer les erreurs pour éviter les crashs
        }
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Sauvegarder la préférence de langue
        Context context = requireActivity().getApplicationContext();
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
                .edit()
                .putString("language", languageCode)
                .apply();
    }

    /**
     * Met à jour la direction du texte pour tous les éléments visibles
     */
    private void updateTextDirection(String languageCode) {
        try {
            View rootView = getView();
            if (rootView == null) return;

            int direction = languageCode.equals("ar") ?
                    View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR;

            // Appliquer la direction à la vue racine et forcer une mise à jour
            rootView.setLayoutDirection(direction);
            rootView.requestLayout();
        } catch (Exception e) {
            // Ignorer les erreurs pour éviter les crashs
        }
    }
} 