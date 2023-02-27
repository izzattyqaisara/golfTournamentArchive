package com.example.tournamentScoring;

import static com.example.tournamentScoring.CategoryFragment.loadCategories;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainframe;
    private TextView drawerProfileName, drawerProfileText;
    private View header;
    private FirebaseAuth authProfile;

//    public static ProfileModel myProfile = new ProfileModel("NA", null);

    private NavigationBarView.OnItemSelectedListener OnItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.category:
                            setFragment(new CategoryFragment());
                            return true;
                        case R.id.acc:
                            setFragment(new UserFragment());
                            return true;
                        case R.id.leaderboard:
                            setFragment(new LeaderboardFragment());
                            return true;
                    }

                    return false;
                }
            };


    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragment(new HomeFragment());
        } else if (id == R.id.nav_archive) {
            Intent i = new Intent(MainActivity.this, TournamentArchive.class);
            startActivity(i);
        } else if (id == R.id.nav_registration) {
            Intent i = new Intent(MainActivity.this, TournamentRegistration.class);
            startActivity(i);
        } else if (id == R.id.nav_calculator) {
            Intent i = new Intent(MainActivity.this, ScoringCalculator.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            logoutMenu(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutMenu(MainActivity mainActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void getUserData(MyCompleteListener completeListener){

       FirebaseUser firebaseUser = null;
       String userID = firebaseUser.getUid();

       DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
       referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
               if (readUserDetails != null) {
                   firebaseUser.getDisplayName();
                   firebaseUser.getEmail();

                   completeListener.onSuccess();

            }else
                 completeListener.onFailure();
       }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               completeListener.onFailure();
           }
       });
   }
   public static void loadData(MyCompleteListener completeListener){
       loadCategories(new MyCompleteListener() {
           @Override
           public void onSuccess() {
               getUserData(completeListener);
           }

           @Override
           public void onFailure() {
               completeListener.onFailure();
           }
       });

   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottonbar);
        mainframe = findViewById(R.id.mainframe);

        bottomNavigationView.setOnItemSelectedListener(OnItemSelectedListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        authProfile = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ProfileModel myProfile = new ProfileModel("NA", null);
        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.userProfilePic);

// Assuming you have a FirebaseUser object for the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            // Update the ProfileModel object with the retrieved name
            myProfile.setName(name);
            // Update the drawer UI with the retrieved name
            drawerProfileName.setText(name);
            drawerProfileText.setText(name.toUpperCase().substring(0,1));
        }

        setFragment(new CategoryFragment());



//        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.userName);
//        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.userProfilePic);
//
//
//        String name = myProfile.getName();
//        drawerProfileName.setText(name);
//
//        drawerProfileText.setText(name.toUpperCase().substring(0,1));
    }
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mainframe.getId(),fragment);
        transaction.commit();
    }
}