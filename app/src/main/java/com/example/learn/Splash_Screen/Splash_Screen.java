package com.example.learn.Splash_Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learn.MainActivity;
import com.example.learn.R;

public class Splash_Screen extends AppCompatActivity {
Animation animation, animation222;
ImageView imageView;
TextView textView;
private  static int SPLASH_SCREEN=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        animation222= AnimationUtils.loadAnimation(this,R.anim.animationtwo);

        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        imageView.setAnimation(animation);
        textView.setAnimation(animation222);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}