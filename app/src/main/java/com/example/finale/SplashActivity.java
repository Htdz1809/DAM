package com.example.finale;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // D'abord initialiser le contenu de la vue
        setContentView(R.layout.activity_splash);
        
        // Masquer la barre d'action si elle est présente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        // Configuration du plein écran APRÈS l'initialisation de la vue
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Pour Android 11+ (API 30+)
            getWindow().setDecorFitsSystemWindows(false);
            try {
                WindowInsetsController controller = getWindow().getInsetsController();
                if (controller != null) {
                    controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                    controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                }
            } catch (Exception e) {
                // Méthode de secours en cas d'erreur
                getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
            }
        } else {
            // Pour les versions antérieures d'Android
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Configurer le GIF pour qu'il ne joue qu'une seule fois
        GifImageView gifImageView = findViewById(R.id.splashGif);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.splash_animation);
            gifDrawable.setLoopCount(1); // Ne jouer qu'une seule fois
            gifImageView.setImageDrawable(gifDrawable);
            
            // Calculer la durée approximative du GIF
            int frameDuration = gifDrawable.getDuration() / gifDrawable.getNumberOfFrames();
            int totalDuration = frameDuration * gifDrawable.getNumberOfFrames();
            
            // Passer à l'activité principale après la durée du GIF
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, totalDuration);
            
        } catch (IOException e) {
            // En cas d'erreur avec le GIF, passer directement à l'activité principale
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1000); // Attendre 1 seconde quand même
        }
    }
} 