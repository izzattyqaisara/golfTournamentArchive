package com.example.tournamentScoring;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//public class DbQuery {
//
//    public static FirebaseFirestore g_firestore;
//    public static List<CategoryModel> g_catList; = new ArrayList<>();


//    public static void saveResult(String score, MyCompleteListener completeListener) {
//        Map<String, Object> userData = new ArrayMap<>();
//        userData.put ("EMAIL_ID", email);
//        userData.put ("NAME", name);
//        userData.put ("TOTAL_SCORE", 0);
//
//        WriteBatch batch = g_firestore.batch();
//
//
//        DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid());
//        batch.update(userDoc,"TOTAL_SCORE", score);
//
//        if (score >
//
//    }
//}

//    public static int g_selected_cat_index = 0;
//
//    public static <RankModel> g_usersList = new ArrayList<>()
//    public static int g_usersCount = 0 ;
//
//
//}
//
//public static void getTopUsers(MyCompleteListener completeListener)
//{
//    g_usersList.clear
//    g_firestore.collection("USERS")
//            .whereGreaterThan("TOTAL_SCORE", 0)
//            .orderBy("TOTAL_SCORE", Query.Direction.DESCENDING)
//            .limit(20)
//            .get()
//            .addOnSuccessListener(new )
//}
