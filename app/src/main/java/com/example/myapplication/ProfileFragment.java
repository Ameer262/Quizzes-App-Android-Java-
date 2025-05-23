package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth; // Firebase authentication instance
    private Button logoutButton;
    private TextView userView, emailView, passView;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        setHasOptionsMenu(true); // enable options menu for this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup toolbar from fragment layout
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity ac = (AppCompatActivity) requireActivity();
        ac.setSupportActionBar(toolbar);
        ac.getSupportActionBar().setDisplayShowTitleEnabled(false);

        logoutButton = view.findViewById(R.id.inProfileLogout);
        userView = view.findViewById(R.id.inProfileUsername);
        emailView = view.findViewById(R.id.inProfileEmail);
        passView = view.findViewById(R.id.inProfilePass);

        // get SharedPreferences and load saved email/password if available
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email_sp = sharedPreferences.getString(KEY_EMAIL, null);
        String pass_sp = sharedPreferences.getString(KEY_PASS, null);
        String user_sp = sharedPreferences.getString(KEY_NAME,null);

        if (email_sp != null)
            emailView.setText("E-mail:\n"+email_sp);
        if (pass_sp != null)
            passView.setText("Password: "+ pass_sp);
        if(user_sp != null)
            userView.setText("Username: "+ user_sp);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View dialogView = getLayoutInflater().inflate(R.layout.logout_dialog, null);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // Confirm logout
                dialogView.findViewById(R.id.btnConfirmLogOut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.signOut(); // sign out from Firebase
                        startActivity(new Intent(getActivity(), LoginActivity.class)); // go back to login screen
                        Toast.makeText(getActivity(), "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Cancel logout
                dialogView.findViewById(R.id.btnCancelLogOut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // transparent background
                }
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.logOutOption) {
            // user clicked Logout option
            mAuth = FirebaseAuth.getInstance();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View dialogView = getLayoutInflater().inflate(R.layout.logout_dialog, null);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            // Confirm logout
            dialogView.findViewById(R.id.btnConfirmLogOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut(); // sign out from Firebase
                    startActivity(new Intent(getActivity(), LoginActivity.class)); // go back to login screen
                    Toast.makeText(getActivity(), "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
                }
            });

            // Cancel logout
            dialogView.findViewById(R.id.btnCancelLogOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); // transparent background
            }
            dialog.show();
        }

        else if (itemId == R.id.aboutOption) {
            // user clicked About option (currently empty)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // inflate the menu from XML file (options_menu.xml)
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
