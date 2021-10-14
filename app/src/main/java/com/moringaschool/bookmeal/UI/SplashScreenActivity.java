package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.R;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo;
    Button getStarted;
    private  static  int SPLASH_SCREEN=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image=findViewById(R.id.image_logo);
        logo=findViewById(R.id.splach_hero_text);
        getStarted=findViewById(R.id.getStartedButton);
        image.setAnimation(topAnim);
        logo.setAnimation(topAnim);
        getStarted.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }

    @Override
    public void onClick(View view) {
        if(view==getStarted){
            Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    }
}