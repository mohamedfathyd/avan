package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khalej.avan.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general;
import com.khalej.avan.model.contact_slider;


import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class main_fragment extends Fragment {
    private apiinterface_home apiinterface;
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    RelativeLayout order;
    int x = 0;
    int y = 0;
    private List<contact_general.media> contactList_annonce ;
    private com.khalej.avan.model.contact_general contact_general;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Typeface myTypeface;
    EditText number;
    ImageView search;
    RelativeLayout charge;
    TextView name;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
       // id = getArguments().getInt("id");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
         number=view.findViewById(R.id.number);
         search=view.findViewById(R.id.search);
         charge=view.findViewById(R.id.order);
         name=view.findViewById(R.id.name);
        order=view.findViewById(R.id.order);
        name.setText(getResources().getString(R.string.welcome)+ sharedpref.getString("name",""));
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ordera_fragment ordera_fragment =
                        new Ordera_fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ordera_fragment)
                        .addToBackStack(null).commit();
            }
        });
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);

        search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(number.getText().toString()!=""){
                   Bundle bundle = new Bundle();
                   bundle.putString("track",number.getText().toString());
                   Track_fragment track_fragment =
                           new Track_fragment();
                   track_fragment.setArguments(bundle);
                   AppCompatActivity activity = (AppCompatActivity) view.getContext();
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, track_fragment)
                           .addToBackStack(null).commit();


               }
           }
       });
        fetchInfo_annonce();
        try {


            final int counter = 100 * 8000;

            countDownTimer = new CountDownTimer(counter, 8000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
                    recyclerView2.smoothScrollToPosition(y);
                    y++;
                    if (y > x) {
                        y = 0;
                    }
                }

                public void onFinish() {
                }

            }.start();
        } catch (Exception e) {
        }
        return view;
    }
    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general> call = apiinterface.getcontacts_generalData();
        call.enqueue(new Callback<contact_general>() {
            @Override
            public void onResponse(Call<contact_general> call, Response<contact_general> response) {
                contact_general=response.body();
                try {
                    contactList_annonce=contact_general.getPayload();
                    if (contactList_annonce.size()!=0||!(contactList_annonce.isEmpty())) {
                        recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_banner(getActivity(), contactList_annonce, recyclerView2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);

                    }


                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<contact_general> call, Throwable t) {

            }
        });
    }
}
