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

public class HomeActivity extends AppCompatActivity {

    private TextView Tournament_Archive, User_Profile, Tournament_Registration, Scoring_Calculator;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

           Tournament_Archive = findViewById(R.id.textview_tournament_archive);
           User_Profile = findViewById(R.id.textview_userprofile);
           Tournament_Registration = findViewById(R.id.textview_tournament_register);
           Scoring_Calculator = findViewById(R.id.textview_tournament_scoring);

           ImageView btnProfile = findViewById(R.id.profilebtn);
           ImageView btnArchive = findViewById(R.id.archiveBtn);
           ImageView btnTournamentReg = findViewById(R.id.tournamentRegBtn);
           ImageView btnTournamentScore = findViewById(R.id.tournamentScoreBtn);

            progressBar = findViewById(R.id.progressBar);


            btnArchive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, TournamentArchive.class));
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, UserActivity.class));
                }
            });

            btnTournamentReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, TournamentRegistration.class));
                }
            });

        btnTournamentScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ScoringCalculator.class));
            }
        });

        }
    }
