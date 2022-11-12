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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewFullName, textViewEmail, textViewDoB, textViewMobile;
    private ProgressBar progressBar;
    private String fullname, email, doB, mobile;
    private Button btnHome;
    private ImageView imageView;
    private FirebaseAuth  authProfile;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        getSupportActionBar().setTitle("Home");

        textViewWelcome = findViewById(R.id.textview_show_welcome);
        textViewFullName = findViewById(R.id.textview_show_full_name);
        textViewEmail = findViewById(R.id.textview_show_email);
        textViewDoB = findViewById(R.id.textview_show_dob);
        textViewMobile = findViewById(R.id.textview_show_mobile);
        progressBar = findViewById(R.id.progressBar);

        btnHome = findViewById(R.id.homebtn);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(UserActivity.this, HomeActivity.class));
                }
            });
        
        if (firebaseUser == null){
            Toast.makeText(UserActivity.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_LONG).show();
        } else{
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("golfAuth");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    doB = readUserDetails.doB;
                    mobile = readUserDetails.mobile;

                    textViewWelcome.setText("Welcome, " + fullname + "!");
                    textViewFullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewMobile.setText(mobile);
                }
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}