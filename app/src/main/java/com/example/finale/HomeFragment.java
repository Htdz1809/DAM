package com.example.finale;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class HomeFragment extends Fragment {

    private VideoView videoView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize views
        videoView = view.findViewById(R.id.videoView);
        toolbar = view.findViewById(R.id.toolbar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        
        // Configure toolbar
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
        
        // Set title in CollapsingToolbarLayout
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        
        // Configure video
        prepareVideoView();
    }
    
    private void prepareVideoView() {
        try {
            // Lecture de la vidéo depuis les ressources raw
            String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.algeria_panorama;
            
            videoView.setVideoURI(Uri.parse(videoPath));
            
            // Configuration du lecteur vidéo
            videoView.setOnPreparedListener(mp -> {
                // Mettre le son à zéro (muet) car c'est une vidéo décorative
                mp.setVolume(0f, 0f);
                
                // Activer la lecture en boucle
                mp.setLooping(true);
                
                // Baisser la luminosité de la vidéo pour que le texte soit lisible
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                
                // Démarrer la vidéo
                videoView.start();
            });
            
            // Gestion des erreurs
            videoView.setOnErrorListener((mp, what, extra) -> {
                // En cas d'erreur, on ne fait rien mais on empêche le crash
                return true;
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume video playback if it was playing
        if (videoView != null && !videoView.isPlaying()) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause video if it is playing
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
} 