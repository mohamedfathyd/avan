package com.khalej.avan.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalej.avan.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;

public class splashLang extends AppCompatActivity {
    ImageView arrowRight,arrowLeft;
    AppCompatButton appCompatButton;
    TextView langar,langen;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        setContentView(R.layout.activity_splash_lang);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        langar=findViewById(R.id.langar);
        langen=findViewById(R.id.langen);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();


        langar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               edt.putString("language","ar");
                    edt.apply();
                   startActivity(new Intent(splashLang.this,Login.class));
                    finish();

                }});
        langen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putString("language","en");
                edt.apply();
                 startActivity(new Intent(splashLang.this,Login.class));
                finish();

            }});
    }
}