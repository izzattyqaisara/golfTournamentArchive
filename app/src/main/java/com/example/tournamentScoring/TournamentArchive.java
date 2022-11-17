package com.example.tournamentScoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TournamentArchive extends AppCompatActivity {

    private TextView archive_TournamentName, archive_FullName, archive_DateJoined, archive_AwardsReceived, archive_tournamentDesc;
    private ProgressBar progressBar;
    private String tournamentName, name, dateJoined, awardsReceived, tournamentDesc;
    private Button btnHome, btnProfile;

    private FirebaseAuth authData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_archive);

        archive_TournamentName = findViewById(R.id.textview_tournament_name);
        archive_FullName = findViewById(R.id.tournament_fullname);
        archive_DateJoined = findViewById(R.id.tournament_dateJoined);
        archive_AwardsReceived = findViewById(R.id.tournament_awardReceived);
        archive_tournamentDesc = findViewById(R.id.tournament_desc);
        progressBar = findViewById(R.id.progressBar);

        btnHome = findViewById(R.id.homebtn);
        btnProfile = findViewById(R.id.profilebtn);

        authData = FirebaseAuth.getInstance();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TournamentArchive.this, HomeActivity.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TournamentArchive.this, UserActivity.class));
            }
        });

    }
}