package com.example.studentregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    TextView heading;
    Animation top,bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        logo=findViewById(R.id.logo);
        heading=findViewById(R.id.heading);
        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top);
        bottom=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom);
        logo.setAnimation(top);
        heading.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        },4000);
    }
}