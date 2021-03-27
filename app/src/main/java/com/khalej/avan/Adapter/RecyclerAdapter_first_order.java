package com.khalej.avan.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.khalej.avan.R;
import com.khalej.avan.model.Orders;
import com.khalej.avan.model.apiinterface_home;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_first_order extends RecyclerView.Adapter<RecyclerAdapter_first_order.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Orders.order_data> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    public RecyclerAdapter_first_order(Context context, List<Orders.order_data> contactslist ){
        this.contactslist=contactslist;
        this.context=context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blue_addresslist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        holder.address.setText(contactslist.get(position).getSender_address());
        holder.address_receiver.setText(contactslist.get(position).getReceiver_address());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView address,address_receiver;

        public MyViewHolder(View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address_from);
            address_receiver=itemView.findViewById(R.id.address_to);




        }
    }


}