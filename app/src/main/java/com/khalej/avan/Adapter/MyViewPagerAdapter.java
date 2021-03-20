package com.khalej.avan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.khalej.avan.R;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    RelativeLayout slide_layout;

    private String json_code;
    private String json_code1;
    private JSONArray jsonArray;
    private JSONArray jsonArray1;
    private String about, vision, mission, about_desc, vision_desc, mission_desc;

    public MyViewPagerAdapter(Context context) {
        this.context = context;
    } //main class
//-------------------------------------------------------------------------------------------------
    //arrays


    public String[] headers = { //HEADERS
//            "حدد المتجر",
            "أضف للسلة",
            "الدفع",
            "التوصيل"
    };

    public int[] images = { //IMAGES

//            R.drawable.ic_shop,
            R.drawable.intro1,
            R.drawable.intro2,
            R.drawable.intro3
    };

    public String[] descr = { //descriptions
            "مع افان يمكنك تتبع شحنتك بسهولة" ,
           "يمكنك البحث عن شحناتك المرسلة حديثاً",
           "إمكانية اضافة عناوين التوصيل "
    };
//--------------------------------------------------------------------------------------------------
    //get count method

    @Override
    public int getCount() {
        return headers.length;
    }

    //--------------------------------------------------------------------------------------------------
    //clear view layout type
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }
//--------------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {



        //............................................................................................
        // inflate layout
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide1, container, false);
        //.............................................................................................
        //casting items

        ImageView imageView = (ImageView) view.findViewById(R.id.image_slider);
        TextView header = (TextView) view.findViewById(R.id.header_text);
        TextView desc = (TextView) view.findViewById(R.id.desc_text);
        slide_layout = (RelativeLayout) view.findViewById(R.id.slide_layout);


        Typeface T1;
        Typeface T2;
        T1 = Typeface.createFromAsset(context.getAssets(), "Tajawal-Bold.ttf");
        T2 = Typeface.createFromAsset(context.getAssets(), "Tajawal-Bold.ttf");
        desc.setTypeface(T2);
        header.setTypeface(T1);

        //...................................................................................
        //change background color

        //set resorce
        imageView.setImageResource(images[position]);
        header.setText(headers[position]);
        desc.setText(descr[position]);

        //......................................................................................
        //load the view
        container.addView(view);
        //......................................................................................
        //return view
        return view;

    }//instantiateItem end

    //-------------------------------------------------------------------------------------------------
    //destroy view method
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }


    //-------------------------------------------------------------------------------------------------
    //get data from db


}


