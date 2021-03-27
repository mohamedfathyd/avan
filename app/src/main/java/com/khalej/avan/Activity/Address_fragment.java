package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_address;
import com.khalej.avan.model.contact_general_;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    AppCompatButton appCompatButtonRegister;
    RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    com.khalej.avan.model.contact_address contact_address;
    List<contact_address.Address> addresses;
    private RecyclerView recyclerView3;
    Intent intent;
    ProgressBar progressBar;
    String type,typeR;
    private RecyclerView.LayoutManager layoutManager;
    Typeface myTypeface;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_address, container, false);
       // id = getArguments().getInt("id");
        appCompatButtonRegister=view.findViewById(R.id.appCompatButtonRegister);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                New_address_fragment new_address_fragment =
                        new New_address_fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new_address_fragment)
                        .addToBackStack(null).commit();
            }
        });
        recyclerView3=view.findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        fetchInfo_annonce();
        return view;
    }
    public void fetchInfo_annonce() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView3.setAdapter(null);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_address> call = apiinterface.user_address(headers);
        call.enqueue(new Callback<contact_address>() {
            @Override
            public void onResponse(Call<contact_address> call, Response<contact_address> response) {
                progressBar.setVisibility(View.GONE);
                contact_address=response.body();
                try {
                    addresses=contact_address.getPayload();
                    if (addresses.size() != 0 || !(addresses.isEmpty())) {
                        recyclerAdapter_first_annonce= new RecyclerAdapter_first_annonce(getActivity(), addresses,Address_fragment.this);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<contact_address> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    public void selectedAddress(String addressId){
        Bundle bundle = new Bundle();
        bundle.putString("address",addressId);
        Orderb_fragment orderb_fragment =
                new Orderb_fragment();
        orderb_fragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, orderb_fragment)
                .addToBackStack(null).commit();
    }
}
