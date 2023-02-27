package com.example.tournamentScoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ScoringCalculator extends AppCompatActivity {

    EditText name, handicap, category, gross_score, net_score, rank, cat1, cat2, cat3;
    Button btn1,btn2,btnBack;
    private FirebaseAuth authScore;
    public static FirebaseFirestore g_firestore;
    private List<CategoryModel> catList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_calculator);
        name = findViewById(R.id.name);
        handicap = findViewById(R.id.handicap);
        gross_score = findViewById(R.id.gross_score);
        net_score = findViewById(R.id.net_score);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btnBack = findViewById(R.id.btnBack);
        authScore = FirebaseAuth.getInstance();
        CheckBox cat1 = findViewById(R.id.category1);
        CheckBox cat2 = findViewById(R.id.category2);
        CheckBox cat3 = findViewById(R.id.category3);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoringCalculator.this, HomeActivity.class));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculation();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    private void clear() {

        name.setText("");
        handicap.setText("");
        category.setText("");
        gross_score.setText("");
        net_score.setText("");
        rank.setText("");

        name.requestFocus();

    }


    public void calculation() {
        int m1, m2, tot;
        m1 = Integer.parseInt(handicap.getText().toString());
        m2 = Integer.parseInt(gross_score.getText().toString());

        tot = m2 - m1;



    }

//    private void loadCategories()
//    {
//        catList.clear();
//        catList.add(new CategoryModel("1", "Rajawali", noOfTournament));
//        catList.add(new CategoryModel("2", "Helang", noOfTest));
//        catList.add(new CategoryModel("3", "Merak", noOfTest));
//    }


    }


