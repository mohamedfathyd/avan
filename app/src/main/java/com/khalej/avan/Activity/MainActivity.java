package com.khalej.avan.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;

import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private apiinterface_home apiinterface;
    int x=0;
    String token="";
    LinearLayout social,feedback,address,whous,callus,see,impo,terms,logOut;
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
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        setContentView(R.layout.activity_main);
        main=findViewById(R.id.main);
        myorders=findViewById(R.id.orders);
        token=  FirebaseInstanceId.getInstance().getToken();
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
                Fragment fragment;
                fragment = new main_fragment();
                loadFragment(fragment);
                x=0;

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
                Fragment fragment;
                fragment = new Track_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("track","");
                fragment.setArguments(bundle);
                loadFragment(fragment);
                x=1;
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
                Fragment fragment;
                fragment = new Ordera_fragment();
                loadFragment(fragment);
                x=1;
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
                Fragment fragment;
                fragment = new Profile_fragment();
                loadFragment(fragment);
                x=1;
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
        Fragment fragment = new main_fragment();
        loadFragment(fragment);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        address=header.findViewById(R.id.address);
        feedback=header.findViewById(R.id.feedback);
        social=header.findViewById(R.id.social);
        whous=header.findViewById(R.id.whous);
        callus=header.findViewById(R.id.callus);
        see=header.findViewById(R.id.see);
        impo=header.findViewById(R.id.impo);
        terms=header.findViewById(R.id.terms);
        logOut=header.findViewById(R.id.logOut);
        setToken();
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new Address_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new Social_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        whous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new aboutApp_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new CallUs_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new terms_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new vision_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new Note_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        impo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new mission_fragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                x=1;
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putInt("type",0);
                edt.putString("token","");
                edt.putFloat("wallet",0);
                edt.putString("status", "");
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });
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
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(x==0){
            finish();}
        else{
            Fragment fragment;
            fragment = new main_fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle);
            loadFragment(fragment);

            x=0;
        }
    }
    public void setToken(){
       apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<ResponseBody> call = apiinterface.updateToken(headers,token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
