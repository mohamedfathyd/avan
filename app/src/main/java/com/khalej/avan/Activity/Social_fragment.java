package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;


import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Social_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    Typeface myTypeface;
    ImageView face,inst,twitter,linkedin;
    ProgressBar progressBar;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_social, container, false);
       // id = getArguments().getInt("id");
        face=view.findViewById(R.id.face);
        inst=view.findViewById(R.id.insta);
        twitter=view.findViewById(R.id.twitter);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        linkedin=view.findViewById(R.id.linkedin);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
       fetchInfo();
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = contact.getPayload().getSocial().getFacebook();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);}catch (Exception e){}
            }
        });
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = contact.getPayload().getSocial().getInstagram();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);}catch (Exception e){}
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = contact.getPayload().getSocial().getTwitter();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);}catch (Exception e){}
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = contact.getPayload().getSocial().getYoutube();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);}catch (Exception e){}
            }
        });
        return view;
    }
    public void fetchInfo() {
        progressBar.setVisibility(View.VISIBLE);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_> call = apiinterface.getcontacts_g(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_>() {
            @Override
            public void onResponse(Call<contact_general_> call, Response<contact_general_> response) {
                progressBar.setVisibility(View.GONE);
                contact=response.body();}

            @Override
            public void onFailure(Call<contact_general_> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
