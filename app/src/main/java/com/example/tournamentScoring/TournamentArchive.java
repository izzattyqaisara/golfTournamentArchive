package com.example.tournamentScoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TournamentArchive extends AppCompatActivity {

    private TextView archive_TournamentName, archive_FullName, archive_Category, archive_Date, archive_tournamentDesc;
    private ProgressBar progressBar;
    private String tournamentName, name, category, date, tournamentDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_archive);

        archive_TournamentName = findViewById(R.id.textview_tournament_name);
        archive_FullName = findViewById(R.id.tournament_fullname);
        archive_Category = findViewById(R.id.tournament_category);
        archive_Date= findViewById(R.id.tournament_dateJoined);
        archive_tournamentDesc = findViewById(R.id.tournament_desc);
        progressBar = findViewById(R.id.progressBar);

        Button btnHome = findViewById(R.id.homebtn);
        Button btnProfile = findViewById(R.id.profilebtn);

        FirebaseAuth authTournament = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authTournament.getCurrentUser();

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

        if (firebaseUser == null){
            Toast.makeText(TournamentArchive.this, "Something went wrong! Tournament's details are not available at the moment", Toast.LENGTH_LONG).show();
        } else{
//            progressBar.setVisibility(View.VISIBLE);
            showTournamentArchive(firebaseUser);
        }
    }

    private void showTournamentArchive(FirebaseUser firebaseUser) {
        String tournamentID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Tournament details"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Tournament details");
        referenceProfile.child(tournamentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteTournamentDetails readTournamentDetails = snapshot.getValue(ReadWriteTournamentDetails.class);
                if (readTournamentDetails != null) {
                    name = readTournamentDetails.name;
                    tournamentName = readTournamentDetails.tournamentName;
                    category = readTournamentDetails.category;
                    date = readTournamentDetails.date;
                    tournamentDesc = readTournamentDetails.tournamentDesc;

                    archive_TournamentName.setText(tournamentName);
                    archive_FullName.setText(name);
                    archive_Category.setText(category);
                    archive_Date.setText(date);
                    archive_tournamentDesc.setText(tournamentDesc);

                }
              //  progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournamentArchive.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }
    }
