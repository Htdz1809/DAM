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
            if (wilayaName.equals("Algiers")) {
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
                
            }
            else if (wilayaName.equals("Oran")){

                allSites.add(new TouristSite(
                        "Chaple of Santa Cruz | كنيسة سانتا كروز",
                        "The Sanctuary of Santa Cruz was originally a chaple in Oran built on the Aidour below the fort of Santa Cruz.",
                        "كان حرم سانتا كروز في الأصل كنيسة في وهران، بُنيت على نهر أيدور أسفل حصن سانتا كروز.",
                        "chaple santa cruz,Oran",
                        "كنيسة سانتا كروز,وهران",
                        "+213 (0) 770 10 55 66",
                        "jeanpaulvesco@santacruz-oran.com",
                        R.drawable.santacruz,
                        "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                        " Pasha Mosque | مسجد الباشا",
                        "A mosque built in 1796 during the reign of Bey Mohamed El-kbir on the order of Pasha Baba Hassan",
                        "مسجد تم بنائه عام 1796 خلال عهد الباي محمد الكبير بامر من باشا بابا حسن ",
                        "Pacha Mosque,oran",
                        "مسجد الباشا,وهران",
                        "N/A",
                        "N/A",
                        R.drawable.pacha_mousque,
                        "ATTRACTION"
                ));

                allSites.add(new TouristSite(
                        "Oran Station | محطة وهران",
                        "Oran station is a special station in Algeria featuring a modern architecture and a beautiful view for the visitors ",
                        "محطة وهران هي محطة مميزة في الجزائر موفرة تصميم عصري و منظر جميل للزوار.",
                        "71 rue mes boudiaf ,Oran 31000",
                        "71 شارع مس بوضياف ,وهران 31000",
                        "+213 21 71 15 10",
                        "contact@sntf.dz",
                        R.drawable.oran_station,
                        "ATTRACTION"
                ));

                // Hotels & Restaurants
                allSites.add(new TouristSite(
                        "Four points by Sheraton | فندق فور بوينتس شيراطون",
                        "A luxury 5-star hotel located in the charming port city of oran.",
                        "فندق فاخر من فئة 5 نجوم يقع في مدينة بوابة ميناء بوهران .",
                        "Boulevard du 19 mars, Route des falaises, Oran, Algeria,31000",
                        "بولفار 19 مارس ، شارع المنحدرات، وهران، الجزائر ,31000 ",
                        "+213 41 59 02 52",
                        "reservationfp.oran@fourpoints.com",
                        R.drawable.chiraton,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        "Royal Hotel Oran-MGallery | فندق رويال وهران - إم جاليري",
                        "The Royal Hotel Oran - MGallery is a luxurious 5-star hotel located in the heart of Oran, Algeria. Combining historic charm with modern amenities, this elegant property offers stunning views of the Mediterranean Sea",
                        "فندق رويال أوران - إم غاليري هو فندق فاخر من فئة الخمس نجوم يقع في قلب مدينة وهران بالجزائر. يجمع بين سحر التاريخ ووسائل الراحة الحديثة، ويقدم إطلالات رائعة على البحر الأبيض المتوسط.",
                        "Royal Hotel Oran - MGallery,Le Boulevard de la République, Oran, Algeria",
                        "فندق رويال أوران - إم غاليري، بوليفار الجمهورية، أوران، الجزائر",
                        "+213 41 29 17 17",
                        "H9126-RE@ACCOR.COM",
                        R.drawable.m_garelly,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        "Best Western Colombe Hotel | فندق بيست ويسترن كولومب",
                        "Best Western Hotel Colombe is located in the heart of Oran, Algeria, and was fully renovated in 2017. It offers free Wi-Fi, a fitness center, a restaurant with diverse dishes, and 24-hour room service.",
                        "فندق بست ويسترن كولومب يقع في قلب مدينة وهران، الجزائر، وتم تجديده بالكامل في 2017. يتميز بموقع قريب من المطار ووسط المدينة. يقدم خدمات مثل الواي فاي المجاني، مركز لياقة بدنية، مطعم يقدم أطباق متنوعة، وخدمة الغرف على مدار الساعة.",
                        "06 bd zabour larbi hai khaldia, Oran 31000",
                        "6 شارع زابور العربي، حي خالدية، وهران 31000، الجزائر",
                        "+213 41 74 61 75",
                        "reservation@hotelcolombe.com",
                        R.drawable.colombe,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        "La Comète | لا كوميتي",
                        " La Comète is renowned for its classic decor and comfortable, family-friendly atmosphere. The menu features a variety of European-French dishes, including French onion soup, cassoulet, beef bourguignon, chocolate soufflé, and duck confit.",
                        "تشتهر لا كوميتي بديكورها الكلاسيكي وأجوائها المريحة المناسبة للعائلات. يقدم المطعم مجموعة متنوعة من الأطباق الفرنسية الأوروبية، بما في ذلك حساء البصل الفرنسي، الكاسوليه، لحم البقر بورغينيون، سوفليه الشوكولاتة، وتاكين البط.",
                        "1 Rue de la Paix, Oran, Algeria",
                        "1 شارع السلام، وهران، الجزائر",
                        " +213 41 29 45 84",
                        "N/A",
                        R.drawable.lacomete,
                        "HOTEL"
                ));

                allSites.add(new TouristSite(
                        "Ambiance La Villa | أمبيانس لا فيلا",
                        "Ambiance La Villa specializes in European and French cuisine, offering a charming ambiance with tasteful decor and a welcoming atmosphere.",
                        "تتخصص أمبيانس لا فيلا في المأكولات الأوروبية والفرنسية، وتقدم أجواء ساحرة مع ديكور ذوقي وأجواء ترحيبية.",
                        "45 Boulevard Wahrani Boumediene, Oran, Algeria",
                        "45 شارع وهراني بومدين، وهران، الجزائر",
                        "+213 554 04 80 70",
                        "Villaambiance3@gmail.com",
                        R.drawable.amb,
                        "HOTEL"
                ));

                // Parks & Gardens
                allSites.add(new TouristSite(
                        "Canastel Forest | غابة كانستال",
                        " Located to the east of Oran, Canastel Forest spans 120 hectares and serves as the city's ecological lung. The forest is rich in diverse flora, including pine, eucalyptus, and lavender trees",
                        "تقع غابة كانستال إلى الشرق من وهران، وتمتد على مساحة 120 هكتارًا وتعد رئة المدينة البيئية. تتميز الغابة بتنوع نباتي غني يشمل أشجار الصنوبر والأوكالبتوس واللافندر.",
                        "foret de canastel, Oran, Algeria",
                        "غابة كانستال، وهران، الجزائر",
                        "+213 560 04 51 00",
                        "N/A",
                        R.drawable.fc,
                        "PARK"
                ));

                allSites.add(new TouristSite(
                        "Andalusian Garden (Jardin Citadin Méditerranéen) | الحديقة الأندلسية (الحديقة المدنية المتوسطية)",
                        "Situated near the Essedikia Court and the state residence, this expansive 13-hectare botanical garden offers a captivating view of the Mediterranean Sea.",
                        "تقع هذه الحديقة النباتية الشاسعة التي تبلغ مساحتها 13 هكتارًا بالقرب من محكمة السيديكية ومقر الإقامة الرسمي، وتوفر إطلالة ساحرة على البحر الأبيض المتوسط.",
                        "W75, Oran, Algeria",
                        "W75، وهران، الجزائر",
                        "+213 551 50 51 26",
                        "N/A",
                        R.drawable.ando,
                        "PARK"
                ));

                allSites.add(new TouristSite(
                        "Paradise Beach | شاطئ الجنة",
                        "Located in the Baie des Aiguades, Paradise Beach is renowned for its soft golden sands and clear blue waters, making it suitable for swimming year-round.",
                        "يقع شاطئ الجنة في خليج آيغوادس، ويشتهر برماله الذهبية الناعمة ومياهه الزرقاء الصافية، مما يجعله مناسبًا للسباحة على مدار العام.",
                        "Baie des Aiguades, Oran, Algeria",
                        "خليج آيغوادس، وهران، الجزائر",
                        "N/A",
                        "N/A",
                        R.drawable.pradice_beach,
                        "PARK"
                ));

            }
            else {
                // For other wilayas, show a message that only Algiers sites are currently available
                allSites.add(new TouristSite(
                        "Coming Soon",
                        "Currently only Algiers and Oran sites are available in this demo",
                        "حاليًا فقط مواقع الجزائر العاصمة و وهران متاحة في هذا العرض التجريبي",
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