package com.example.tournamentScoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import io.grpc.okhttp.internal.framed.FrameReader;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation top_Anim, bottom_Anim;
    ImageView gng;
    TextView  splashName, copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        top_Anim = AnimationUtils.loadAnimation(this,R.anim.animation_top);
        bottom_Anim = AnimationUtils.loadAnimation(this,R.anim.animation_bottom);

        gng = findViewById(R.id.gng_logo);
        splashName = findViewById(R.id.splash_name);
        copyright = findViewById(R.id.copyright);

        gng.setAnimation(top_Anim);
        splashName.setAnimation(bottom_Anim);
        copyright.setAnimation(bottom_Anim);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(gng,"transition_logoimg");
                pairs[1] = new Pair<View, String>(splashName,"transition_logotxt");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);

                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);

    }
}