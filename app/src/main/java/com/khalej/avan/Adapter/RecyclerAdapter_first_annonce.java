package com.khalej.avan.Adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.avan.Activity.Address_fragment;
import com.khalej.avan.Activity.Ordera_fragment;
import com.khalej.avan.Activity.Track_fragment;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_address;


import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_first_annonce extends RecyclerView.Adapter<RecyclerAdapter_first_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_address.Address> contactslist;
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String type;
    Address_fragment fragment;
    public RecyclerAdapter_first_annonce(Context context, List<contact_address.Address> contactslist, Address_fragment fragment){
        this.contactslist=contactslist;
        this.context=context;
        this.fragment=fragment;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addresslist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            if(contactslist.get(position).getType().equals("home"))
            holder.name.setText("منزل");
            if(contactslist.get(position).getType().equals("office"))
                holder.name.setText("مكتب");

        }else{
            holder.name.setText(contactslist.get(position).getType());

        }
        holder.address.setText( "شقة" + contactslist.get(position).getFlat_number()+" الدور -" +contactslist.get(position).getFloor()+
                "- مبني رقم" + contactslist.get(position).getBuilding_number());


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo(contactslist.get(position).getId());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fragment.selectedAddress(contactslist.get(position).getId());
                }catch (Exception e){}}
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,address;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.cancel_button);
            name=itemView.findViewById(R.id.title);
            address=itemView.findViewById(R.id.text);

        }
    }

    public void fetchInfo(String id) {
        progressDialog = ProgressDialog.show(context, "جاري مسح العنوان", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.deleteAddress(headers,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText( "تم مسج العنوان بنجاح");
                dialog1.show();
                fragment.fetchInfo_annonce();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}