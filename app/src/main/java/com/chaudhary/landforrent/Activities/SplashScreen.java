package com.chaudhary.landforrent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.chaudhary.landforrent.R;

public class SplashScreen extends AppCompatActivity
{
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottieAnimationView=findViewById(R.id.lottie);




        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();


            }
        }, 5000);
    }
}