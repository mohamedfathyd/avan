package com.khalej.avan.Activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.khalej.avan.Adapter.RecyclerAdapter_first_order;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.Orders;
import com.khalej.avan.model.apiinterface_home;

import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlankFragment3 extends Fragment {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_first_order recyclerAdapter;
    private Orders contactList;
    private List<Orders.order_data> order_data;
    private apiinterface_home apiinterface;
    ImageView notification;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        fetchInfo();
        return view;
    }

    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Log.d("token:",sharedpref.getString("token",""));
        Call<Orders> call = apiinterface.userOrders_recevier(headers);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                progressBar.setVisibility(View.GONE);

                contactList = response.body();
                order_data=contactList.getPayload();
                try{
                    if(order_data.size()!=0||!(order_data.isEmpty())) {
                        progressBar.setVisibility(View.GONE);
                        recyclerAdapter=new RecyclerAdapter_first_order(getActivity(),order_data);
                        recyclerView.setAdapter(recyclerAdapter);}}
                catch (Exception e){
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}