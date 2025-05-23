package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    //bottom navigation menu:

    private int selectedTab = 1; // number of selected tab (1-home / 2-create / 3-profile)
    private static int animationDuration = 350;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //mAuth = FirebaseAuth.getInstance();
        //String email = mAuth.getCurrentUser().getUid();


        // Layouts:
        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout createLayout = findViewById(R.id.createLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);


        // Image Views:
        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView createImage = findViewById(R.id.createImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        // Texts:
        final TextView homeText = findViewById(R.id.homeText);
        final TextView createText = findViewById(R.id.createText);
        final TextView profileText = findViewById(R.id.profileText);


        // set default fragment to be home fragment:
        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, HomeFragment.class, null)
                        .commit();


        // set listeners:
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if Home tab is already selected or not:
                if(selectedTab != 1){

                    // set Home Fragment:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();


                    // unselect other tabs except home tab:
                    createText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    createImage.setImageResource(R.drawable.baseline_add_24);
                    profileImage.setImageResource(R.drawable.baseline_person_24);

                    createLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // and select home tab:
                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.home_ic_focused);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);
                    findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.homeColor));

                    // create animation:
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(animationDuration);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    // update/set the home tab as the selected one
                    selectedTab = 1;
                }
            }
        });


        createLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if Create tab is already selected or not:
                if(selectedTab != 2){

                    // set Create Fragment:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, CreateQuizFragment.class, null)
                            .commit();


                    // unselect other tabs except Create tab:
                    homeText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.baseline_home_24);
                    profileImage.setImageResource(R.drawable.baseline_person_24);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // and select Create tab:
                    createText.setVisibility(View.VISIBLE);
                    createImage.setImageResource(R.drawable.add_ic_focused);
                    createLayout.setBackgroundResource(R.drawable.round_back_create_100);
                    findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.createColor));

                    // create animation:
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f, Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(animationDuration);
                    scaleAnimation.setFillAfter(true);
                    createLayout.startAnimation(scaleAnimation);

                    // update/set the create tab as the selected one
                    selectedTab = 2;
                }
            }
        });


        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if Profile tab is already selected or not:
                if(selectedTab != 3){

                    // set Profile Fragment;
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();


                    // unselect other tabs except Create tab:
                    homeText.setVisibility(View.GONE);
                    createText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.baseline_home_24);
                    createImage.setImageResource(R.drawable.baseline_add_24);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    createLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // and select Profile tab:
                    profileText.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.user_ic_focused);
                    profileLayout.setBackgroundResource(R.drawable.round_back_profile_100);
                    findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.profileColor));

                    // create animation:
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f, Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(animationDuration);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    // update/set the profile tab as the selected one
                    selectedTab = 3;
                }
            }
        });


    }


    // a function that replaces the current fragment with a new one (fr).
    private void replaceFragment (Fragment fr) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.frameLayoutBottom, fr);
        fragmentTransaction.commit();
    }

}