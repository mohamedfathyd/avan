package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mission_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    Typeface myTypeface;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about_app, container, false);
       // id = getArguments().getInt("id");
        message=view.findViewById(R.id.message);
        title=view.findViewById(R.id.title);
        myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "Tajawal-Regular.ttf");
        title.setTypeface(myTypeface);
        title.setText(R.string.impo);

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
       fetchInfo();

        return view;
    }
    public void fetchInfo() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_> call = apiinterface.getcontacts_g(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_>() {
            @Override
            public void onResponse(Call<contact_general_> call, Response<contact_general_> response) {
                contact=response.body();
                try {
                myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "Tajawal-Bold.ttf");
                message.setTypeface(myTypeface);
                message.setText(Html.fromHtml(contact.getPayload().getAbout().getVision()));}
                catch (Exception e){}
            }

            @Override
            public void onFailure(Call<contact_general_> call, Throwable t) {

            }
        });
    }

}
