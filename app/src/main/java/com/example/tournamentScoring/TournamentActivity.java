package com.example.tournamentScoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TournamentActivity extends AppCompatActivity {

    private RecyclerView tournamentView;
    private Toolbar toolbar;
    public static List<TournamentModel> tournamentList = new ArrayList<>();
    public static int selected_cat_index = 0;
    public static FirebaseFirestore g_firestore = FirebaseFirestore.getInstance();
    private Dialog progressDialog;
    private TextView DialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        tournamentView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(CategoryFragment.catList.get(selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(TournamentActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        DialogText.setText("Loading");

        progressDialog.show();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        tournamentView.setLayoutManager(layoutManager);

        loadTournamentData(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                TournamentAdapter adapter = new TournamentAdapter(tournamentList);
                tournamentView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(TournamentActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == android.R.id.home){
            TournamentActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void loadTournamentData(MyCompleteListener completeListener) {
        tournamentList.clear();
        g_firestore.collection("TOURNAMENT").document(CategoryFragment.catList.get(selected_cat_index).getDocID())
                .collection("TOURNAMENT_LIST").document("TOURNAMENT_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        int noOfTournament = CategoryFragment.catList.get(selected_cat_index).getNoOfTournament();
                        for (int i = 1 ; i <= noOfTournament; i++)
                        {
                            tournamentList.add(new TournamentModel(
                                    documentSnapshot.getString("TOURNAMENT" + i + "_ID"),0
                            ));
                        }


                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();
                    }
                });
    }

}