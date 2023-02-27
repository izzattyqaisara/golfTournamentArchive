package com.example.tournamentScoring;

import android.accessibilityservice.AccessibilityService;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {

    private Object MyCompleteListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    private GridView catView;
    public static FirebaseFirestore g_firestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> catList = new ArrayList<>();
    public static int selected_cat_index = 0;

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_category, container, false);

        catView= view.findViewById(R.id.catGrid);

        CategoryAdapter adapter = new CategoryAdapter(catList);

        catView.setAdapter(adapter);

        catView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_cat_index = position;
                updateToolbar();
            }
        });


        return view;
    }

    private void updateToolbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(catList.get(selected_cat_index).getName());
        }
    }


    public static void loadCategories( final MyCompleteListener completeListener)
    {
//        catList.clear();

        g_firestore.collection("TOURNAMENT")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            docList.put(doc.getId(), doc);
                        }
                        QueryDocumentSnapshot catListDoc = docList.get("Categories");

                        long catCount = catListDoc.getLong("COUNT");

                        for(int i = 1; i <= catCount; i++)
                        {
                            String catID = catListDoc.getString("CAT" + i + "_ID");

                            QueryDocumentSnapshot catDoc = docList.get(catID);

                            int noOfTournament = catDoc.getLong("NO_OF_TOURNAMENT").intValue();

                            String catName = catDoc.getString("NAME");

                            catList.add((new CategoryModel(catID,catName,noOfTournament)));
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