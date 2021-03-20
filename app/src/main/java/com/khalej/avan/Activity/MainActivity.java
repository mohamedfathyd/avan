package com.khalej.avan.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.khalej.avan.R;

import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout main, copon, myorders, profile;
    ImageView sideMenu;
    String lang;
    Intent intent;
    private ActionBar toolbar;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    BottomNavigationView navigation;
    DrawerLayout drawer;
    TextView text1,text2,text3,text4;
    ImageView image1,image2,image3,image4;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang = sharedpref.getString("language", "").trim();
        if (lang.equals(null)) {
            edt.putString("language", "ar");
            lang = "en";
            edt.apply();
        }
        intent = getIntent();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        main=findViewById(R.id.main);
        myorders=findViewById(R.id.orders);
        copon=findViewById(R.id.copon);
        profile=findViewById(R.id.profile);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               main.setBackgroundResource(R.drawable.backshapeblue);
               myorders.setBackground(null);
               copon.setBackground(null);
               profile.setBackground(null);
               text1.setVisibility(View.VISIBLE);
               text2.setVisibility(View.GONE);
               text3.setVisibility(View.GONE);
               text4.setVisibility(View.GONE);
               image1.setBackgroundResource(R.drawable.iconionicioshome);
               image2.setBackgroundResource(R.drawable.group10);
               image3.setBackgroundResource(R.drawable.group10);
               image4.setBackgroundResource(R.drawable.iconmaterialaccountcircle);

            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorders.setBackgroundResource(R.drawable.backshapeblue);
                main.setBackground(null);
                copon.setBackground(null);
                profile.setBackground(null);
                text3.setVisibility(View.VISIBLE);
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                image1.setBackgroundResource(R.drawable.group11);
                image3.setBackgroundResource(R.drawable.group13);
                image2.setBackgroundResource(R.drawable.group10);
                image4.setBackgroundResource(R.drawable.iconmaterialaccountcircle);
            }
        });
        copon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copon.setBackgroundResource(R.drawable.backshapeblue);
                main.setBackground(null);
                myorders.setBackground(null);
                profile.setBackground(null);
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                image1.setBackgroundResource(R.drawable.group11);
                image3.setBackgroundResource(R.drawable.group10);
                image2.setBackgroundResource(R.drawable.new2);
                image4.setBackgroundResource(R.drawable.iconmaterialaccountcircle);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setBackgroundResource(R.drawable.backshapeblue);
                main.setBackground(null);
                myorders.setBackground(null);
                copon.setBackground(null);
                text4.setVisibility(View.VISIBLE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                image1.setBackgroundResource(R.drawable.group11);
                image3.setBackgroundResource(R.drawable.group10);
                image2.setBackgroundResource(R.drawable.group10);
                image4.setBackgroundResource(R.drawable.iconmaterialaccountcircle1);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.name, R.string.name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        sideMenu = findViewById(R.id.sidemenu);
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    drawer.openDrawer(Gravity.LEFT);
                } catch (Exception e) {
                    drawer.openDrawer(Gravity.RIGHT);
                }

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
