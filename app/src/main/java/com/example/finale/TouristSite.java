package com.example.finale;

/**
 * Model class representing a tourist site in a wilaya
 */
public class TouristSite {
    private String name;
    private String descriptionEn;
    private String descriptionAr;
    private String address;
    private String addressAr;
    private String phoneNumber;
    private String email;
    private int imageResourceId;
    private String category; // "ATTRACTION", "HOTEL", "PARK"

    public TouristSite(String name, String descriptionEn, String descriptionAr, String address, String addressAr, 
                     String phoneNumber, String email, int imageResourceId, String category) {
        this.name = name;
        this.descriptionEn = descriptionEn;
        this.descriptionAr = descriptionAr;
        this.address = address;
        this.addressAr = addressAr;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageResourceId = imageResourceId;
        this.category = category;
    }

    // Constructor pour la compatibilité avec le code existant
    public TouristSite(String name, String descriptionEn, String descriptionAr, String address, String addressAr, 
                     int imageResourceId, String category) {
        this(name, descriptionEn, descriptionAr, address, addressAr, "", "", imageResourceId, category);
    }

    public String getName() {
        return name;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getAddressAr() {
        return addressAr;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Returns the description in the current device language
     */
    public String getLocalizedDescription(String languageCode) {
        if (languageCode.equals("ar")) {
            return descriptionAr;
        } else {
            return descriptionEn;
        }
    }
    
    /**
     * Returns the address in the current device language
     */
    public String getLocalizedAddress(String languageCode) {
        if (languageCode.equals("ar")) {
            return addressAr;
        } else {
            return address;
        }
    }
} 