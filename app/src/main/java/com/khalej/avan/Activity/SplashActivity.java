package com.khalej.avan.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalej.avan.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;

public class SplashActivity extends AppCompatActivity {
    ImageView i;
    TextView title,arabic,english;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_gray));
        setContentView(R.layout.activity_splash);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        i = (ImageView) findViewById(R.id.imageView);
        arabic=findViewById(R.id.arabic);
        english=findViewById(R.id.english);
        title=findViewById(R.id.title);
        i.startAnimation(anim);
        title.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(sharedpref.getString("remember","").trim().equals("yes")){
                    edt.putFloat("totalprice",0);
                    edt.apply();
                    if(sharedpref.getString("type","").equals("customer")){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));}
                    else{
                        edt.putString("language","ar");
                        edt.apply();
                         startActivity(new Intent(SplashActivity.this, Main_driver.class));
                    }

                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    arabic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           startActivity(new Intent(SplashActivity.this,intro_slider.class));
            finish();
        }
    });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(SplashActivity.this,intro_slider.class));
               finish();
            }
        });
    }
}