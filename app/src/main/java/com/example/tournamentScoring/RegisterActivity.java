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

public class RegisterActivity extends AppCompatActivity {

    EditText inputFullname,inputEmail,inputPassword,inputConfirmPassword,inputDoB,inputMobile;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullname = findViewById(R.id.editext_register_fullname);
        inputEmail = findViewById(R.id.editext_register_email);
        inputDoB = findViewById(R.id.editext_register_dob);
        inputMobile = findViewById(R.id.editext_register_mobile);
        inputPassword = findViewById(R.id.editext_register_password);
        inputConfirmPassword = findViewById(R.id.editext_register_confirmpassword);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnRegister = findViewById(R.id.registerbtn);
        progressDialog = new ProgressDialog(this);

        TextView btn = findViewById(R.id.haveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });
    }
    private void PerformAuth(){
        String fullname = inputFullname.getText().toString();
        String email = inputEmail.getText().toString();
        String dob = inputDoB.getText().toString();
        String mobile = inputMobile.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(RegisterActivity.this, "Please Enter your full name", Toast.LENGTH_LONG).show();
            inputFullname.setError("Full name is required");
            inputFullname.requestFocus();

        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please Enter a valid email", Toast.LENGTH_LONG).show();
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();

        } else if (!email.matches(emailPattern)){
            Toast.makeText(RegisterActivity.this, "Please Enter a valid email", Toast.LENGTH_LONG).show();
            inputEmail.setError("Valid email is required");
            inputEmail.requestFocus();


        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(RegisterActivity.this, "Please Enter your date of birth", Toast.LENGTH_LONG).show();
            inputDoB.setError("Date of Birth is required");
            inputDoB.requestFocus();

        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(RegisterActivity.this, "Please Enter your phone number", Toast.LENGTH_LONG).show();
            inputMobile.setError("Phone no. is required");
            inputMobile.requestFocus();


        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Please Enter your password", Toast.LENGTH_LONG).show();
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();

        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
            inputPassword.setError("Password too weak");
            inputPassword.requestFocus();

        } else if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
            inputConfirmPassword.setError("Password confirmation is required");
            inputConfirmPassword.requestFocus();

        } else if (password.equals((confirmPassword))) {
            Toast.makeText(RegisterActivity.this, "Correct Password!", Toast.LENGTH_LONG).show();
            inputConfirmPassword.requestFocus();
            inputPassword.clearComposingText();
            inputConfirmPassword.clearComposingText();

        } else
            progressBar.setVisibility(View.VISIBLE);
            registerUser(fullname,email,dob,mobile,password);
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            }

    private void registerUser(String fullname, String email, String dob, String mobile, String password) {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                sendUserToNextActivity();

                FirebaseUser firebaseUser = auth.getCurrentUser();
                Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_SHORT).show();

                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fullname).build();
                firebaseUser.updateProfile(profileChangeRequest);

                ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(dob,mobile);

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
                referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            firebaseUser.sendEmailVerification();

                            Toast.makeText(RegisterActivity.this,"User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            } else
            {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Registration Failed" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        }
    });
    }


        private void sendUserToNextActivity(){
            Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            }
    }



