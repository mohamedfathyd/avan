package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalej.avan.R;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class Orderc_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    Typeface myTypeface;
    AppCompatButton address;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_orderc, container, false);
       // id = getArguments().getInt("id");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        address=view.findViewById(R.id.appCompatButtonRegister);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_fragment main_fragment  =
                        new main_fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, main_fragment)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

}
