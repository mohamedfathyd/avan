package com.khalej.avan.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.khalej.avan.Adapter.MyViewPagerAdapter;
import com.khalej.avan.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class intro_slider extends AppCompatActivity {
    private LinearLayout dots;
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private TextView[]mDots;
    private TextView mNextbtn;
    private TextView mSkipbtn;
    private int currentpage;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String lang;
    Intent intent;
    @TargetApi(29)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.header_gray));
        sharedpref = getSharedPreferences("Education",Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang=sharedpref.getString("language","").trim();
        if(lang.equals(null)){
            edt.putString("language","ar");
            lang="en";
            edt.apply();
        }
        intent=getIntent();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_intro_slider);
        dots=(LinearLayout)findViewById(R.id.dots_layout);
        viewPager=(ViewPager)findViewById(R.id.slideer_viewpager);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("remember","").trim().equals("yes")){
            edt.putFloat("totalprice",0);
            edt.apply();

            startActivity(new Intent(intro_slider.this, MainActivity.class));

            finish();
        }
        mNextbtn=findViewById(R.id.button_next);
        mSkipbtn= findViewById(R.id.button_skip);
        Typeface T1;
        T1 = Typeface.createFromAsset(getAssets(), "Tajawal-Bold.ttf");
        mSkipbtn.setTypeface(T1);
        adapter=new MyViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        addDotsIndecator(0);
        viewPager.addOnPageChangeListener(viewListener);

        mNextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentpage+1);

            }
        });

        mSkipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(intro_slider.this,splashLang.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public void addDotsIndecator(int position){

        mDots=new TextView[3];//number of slides dots
        dots.removeAllViews();

        for(int i=0 ; i<mDots.length ;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(30);
            mDots[i].setTextColor(getResources().getColor(R.color.red));
            dots.addView(mDots[i]);


        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }

    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndecator(i);
            currentpage=i;
            if(i==0){
                mNextbtn.setEnabled(true);
                mSkipbtn.setEnabled(true);
                mNextbtn.setVisibility(View.GONE);
                mNextbtn.setText("التالي");
                mSkipbtn.setText("تخطي");
            }
            else if(i==2){
                mNextbtn.setEnabled(true);
                mSkipbtn.setEnabled(true);
                mNextbtn.setVisibility(View.GONE);
                mNextbtn.setText("");
                mSkipbtn.setText("تخطي");


            }
            else {
                mNextbtn.setEnabled(true);
                mSkipbtn.setEnabled(true);
                mNextbtn.setVisibility(View.GONE);
                mNextbtn.setText("التالي");
                mSkipbtn.setText("تخطي");

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
