package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout; // import  drawer layout , navigation view , toggle for side navigation bar, tab layout , tab-items , viewpager and fragment manger
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    fragmentmaneger fragmentManeger;

    //     EditText    editTextText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FireBase Notification Setup Process Code
//editTextText = findViewById(R.id.editTextText);
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        System.out.println( "Fetching FCM registration token failed");
//                        return;
//                    }
//
//                    // Get new FCM registration token
//                    String token = task.getResult();
//
//                    // Log and toast
//
//                    System.out.println(token);
//                    Toast.makeText(MainActivity.this,"Your Registration Token Is "+token
//                            , Toast.LENGTH_SHORT).show();
//
//                    editTextText.setText(token);
//                });
//
//
//
//


        // For Toolbar
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Remove the title from the Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(""); // Set an empty string as the title
        }


        // Firebase Message  Method
        //     FirebaseMessaging.getInstance().subscribeToTopic("Vision")
        //           .addOnCompleteListener(task -> {
        //             String msg = task.isSuccessful() ? "Subscription successful" : "Subscription failed";
        //           Log.d("FirebaseMessaging", msg);

        //     });


// implement and find the tab items
        tabLayout = findViewById(R.id.tab1);
        viewPager2 = findViewById(R.id.pageholder2);
        drawerLayout =  findViewById(R.id.mydrawer);
        navigationView = findViewById(R.id.cnav);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Initialize FragmentManager correctly
        fragmentManeger = new fragmentmaneger(this);
        viewPager2.setAdapter(fragmentManeger);
//All  Menu Tabs are Created Here
        // Attach ViewPager2 to TabLayout
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            // Home Tab
                            tab.setIcon(R.drawable.baseline_home_24); // Set your icon resource here
                            tab.setText("Home");
                            break;
                        case 1:
                            // Search Tab
                            tab.setIcon(R.drawable.baseline_search_24); // Set your icon resource here
                            tab.setText("Search");
                            break;
                        case 2:
                           //Books Tab
                            tab.setIcon(R.drawable.baseline_library_books_24); // Set your icon resource here
                            tab.setText("Books");
                            break;
                        // Add more cases for additional tabs if needed
// By  applying  switch case we can add  diffrent tabs
                    }
                    // Set tab names if needed
                    // tab.setText("Tab " + (position + 1));
                }).attach();
        // Loop through tabs after attaching them and set their icon tint color
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                // Set text color
                View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                if (tabView instanceof TextView) {
                    ((TextView) tabView).setTextColor(ContextCompat.getColor(this, R.color.white));
                }

                // Set icon tint
                Drawable tabIcon = tab.getIcon();
                if (tabIcon != null) {
                    tabIcon = DrawableCompat.wrap(tabIcon);
                    DrawableCompat.setTint(tabIcon, ContextCompat.getColor(this, R.color.white));
                }
            }
        }

// Adding the selected listener For tabs to select particular tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO document why this method is empty
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO document why this method is empty
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Handle page selection if needed
            }
        });
// Function For Drawer layout Navigation, Here All  Drawer Pages Are  insert
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
             if (itemId == R.id.nav_Profile) {
                Intent ProfileIntent = new Intent(MainActivity.this, Profile.class);
                startActivity(ProfileIntent);
            }
             else if (itemId == R.id.nav_Devoloper) {
                Intent devoloperIntent = new Intent(MainActivity.this, devoloperdetails.class);
                startActivity(devoloperIntent);
            }
             else if (itemId == R.id.nav_about){
                 Intent aboutIntent = new Intent(MainActivity.this, About.class);
                 startActivity(aboutIntent);
             } else if (itemId == R.id.nav_message) {
                 Intent messageIntent = new Intent(MainActivity.this, chatai.class);
                 startActivity(messageIntent);
             }





            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        // Listener to track page changes in ViewPager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Update toolbar visibility based on the selected tab position
                if (position != 0) { // If not on the Home tab
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide(); // Hide the toolbar
                    }
                } else {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show(); // Show the toolbar on the Home tab
                    }
                }
            }
        });
        // Set initial visibility of toolbar based on the first selected tab
        if (viewPager2.getCurrentItem() != 0) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide(); // Hide the toolbar initially if not on the Home tab
            }
        }
    }
    }

