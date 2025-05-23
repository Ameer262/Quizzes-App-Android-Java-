package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Fragment showing category buttons (General Knowledge, Sports, etc.)
 * User can click a category to navigate to its detailed page.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Image buttons for different categories
    private ImageButton btnGoToGenK, btnGoToSports, btnGoArt, btnGoScience, btnGoAnimals, btnGoGeo;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listener for General Knowledge category
        btnGoToGenK = view.findViewById(R.id.imageButtonGenKnowledge);
        btnGoToGenK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), GeneralKnowledgeDetailedActivity.class);
                startActivity(go);
            }
        });

        // Set click listener for Sports category
        btnGoToSports = view.findViewById(R.id.imageButtonSports);
        btnGoToSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), SportsDetailedActivity.class);
                startActivity(go);
            }
        });

        // Set click listener for Animals category
        btnGoAnimals = view.findViewById(R.id.imageButtonAnimlas);
        btnGoAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), AnimlasDetailedActivity.class);
                startActivity(go);
            }
        });

        // Set click listener for Science category
        btnGoScience = view.findViewById(R.id.imageButtonScience);
        btnGoScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), ScienceDetailedActivity.class);
                startActivity(go);
            }
        });

        // Set click listener for Geography category
        btnGoGeo = view.findViewById(R.id.imageButtonGeography);
        btnGoGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), GeographyDetailedActivity.class);
                startActivity(go);
            }
        });

        // Set click listener for Art category
        btnGoArt = view.findViewById(R.id.imageButtonArt);
        btnGoArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), ArtDetailedActivity.class);
                startActivity(go);
            }
        });
    }
}
