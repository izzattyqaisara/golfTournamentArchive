package com.example.tournamentScoring;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    public TextInputLayout textViewFullName, textViewEmail, textViewDoB, textViewMobile;
    public TextView textViewWelcome, fullnameTextView;
    private EditText etName;

    private ProgressBar progressBar;
    public String fullname, email, doB, mobile;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    Button btnUpdate;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        textViewWelcome = view.findViewById(R.id.textview_show_welcome);
        fullnameTextView = view.findViewById(R.id.full_name);
        textViewFullName = (TextInputLayout) view.findViewById(R.id.textview_show_full_name);
        textViewEmail = (TextInputLayout) view.findViewById(R.id.textview_show_email);
        etName = view.findViewById(R.id.etName);
        textViewDoB = (TextInputLayout) view.findViewById(R.id.textview_show_dob);
        textViewMobile = (TextInputLayout) view.findViewById(R.id.textview_show_mobile);
        btnUpdate = (Button) view.findViewById(R.id.btnupdate);
        progressBar = view.findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                showUserProfile(firebaseUser);
            }
        });

        if (firebaseUser == null) {
            Toast.makeText(getActivity(), "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        return view;
    }

    public void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    doB = readUserDetails.doB;
                    mobile = readUserDetails.mobile;

                    textViewWelcome.setText("Welcome,");
                    fullnameTextView.setText(fullname + "!");
                    textViewFullName.getEditText().setText(fullname);
                    textViewEmail.getEditText().setText(email);
                    textViewDoB.getEditText().setText(doB);
                    textViewMobile.getEditText().setText(mobile);
                }else
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

