package com.example.finale;

/**
 * Model class representing a Wilaya (Algerian province)
 */
public class Wilaya {
    private String name;
    private String arabicName;
    private String descriptionEn;
    private String descriptionAr;
    private int imageResourceId;

    public Wilaya(String name, String arabicName, String descriptionEn, String descriptionAr, int imageResourceId) {
        this.name = name;
        this.arabicName = arabicName;
        this.descriptionEn = descriptionEn;
        this.descriptionAr = descriptionAr;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public String getFullName() {
        return name + " (" + arabicName + ")";
    }

    // Pour la compatibilité avec le code existant
    public String getDescription() {
        // Par défaut, retourne la description en anglais
        return descriptionEn;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
} 