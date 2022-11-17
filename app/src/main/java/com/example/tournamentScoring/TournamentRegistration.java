package com.example.tournamentScoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TournamentRegistration extends AppCompatActivity {

    EditText inputTournamentName, inputName, inputCategory, inputPhoneNum;
    Button btnRegister, btnCancel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_registration);

        inputTournamentName = findViewById(R.id.Register_tournamentName);
        inputName = findViewById(R.id.Register_name);
        inputCategory = findViewById(R.id.Register_Category);
        inputPhoneNum = findViewById(R.id.Register_phoneNumber);

        btnRegister = findViewById(R.id.registerTournamentBtn);
        btnCancel = findViewById(R.id.cancelTournamentBtn);
        progressDialog = new ProgressDialog(this);
        ProgressBar progressBar;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    PerformAuth();
                }
            });
        }


    private void PerformAuth() {
        String tournamentName = inputTournamentName.getText().toString();
        String name = inputName.getText().toString();
        String category = inputCategory.getText().toString();
        String mobile = inputPhoneNum.getText().toString();

        if (TextUtils.isEmpty(tournamentName)) {
            Toast.makeText(TournamentRegistration.this, "Please Enter Tournament Name", Toast.LENGTH_LONG).show();
            inputTournamentName.setError("Tournament Name is required");
            inputTournamentName.requestFocus();

        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(TournamentRegistration.this, "Please Enter your Name", Toast.LENGTH_LONG).show();
            inputName.setError("Name is required");
            inputName.requestFocus();

        } else if (TextUtils.isEmpty(category)) {
            Toast.makeText(TournamentRegistration.this, "Please Enter your Tournament Category", Toast.LENGTH_LONG).show();
            inputCategory.setError("Tournament Category is required");
            inputCategory.requestFocus();

        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(TournamentRegistration.this, "Please Enter your phone number", Toast.LENGTH_LONG).show();
            inputPhoneNum.setError("Phone no. is required");
            inputPhoneNum.requestFocus();

        } else
        registerTournament(tournamentName,name,category,mobile);
        progressDialog.setMessage("Please Wait While Registration...");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }


    private void registerTournament(String tournamentName, String name, String category, String mobile) {

        Toast.makeText(TournamentRegistration.this,"User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TournamentRegistration.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}