package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;
import com.khalej.avan.model.content_track;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Track_fragment extends Fragment {
    EditText textInputEditTextname,textInputEditTextphone,textInputEditTextsubject,
            textInputEditTextmessage;
    AppCompatButton appCompatButton;
    private apiinterface_home apiinterface;
    LinearLayout aa,bb,cc,dd;
    EditText number;
    String track;
    ProgressDialog progressDialog;
    private content_track contact;
    TextView track_num;
    ImageView search;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_trackorder, container, false);
        track = getArguments().getString("track");
        aa=view.findViewById(R.id.aa);
        bb=view.findViewById(R.id.bb);
        cc=view.findViewById(R.id.cc);
        dd=view.findViewById(R.id.dd);
        aa.setAlpha((float) 0.1);
        bb.setAlpha((float) 0.1);
        cc.setAlpha((float) 0.1);
        dd.setAlpha((float) 0.1);
        number=view.findViewById(R.id.number);
        track_num=view.findViewById(R.id.track_num);
        search=view.findViewById(R.id.search);
        number.setText(track);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!number.getText().toString().equals("")){
                    fetchInfo();
                }
            }
        });

        return view;
    }
    public void fetchInfo() {

        progressDialog = ProgressDialog.show(getContext(), "جاري تتبع الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<content_track> call = apiinterface.content_track(number.getText().toString());
        call.enqueue(new Callback<content_track>() {
            @Override
            public void onResponse(Call<content_track> call, Response<content_track> response) {
                progressDialog.dismiss();
                try{
              contact=response.body();
              track_num.setText(contact.getPayload().getTrack_code());
              if(contact.getPayload().isIn_warehouse()){
                        aa.setAlpha((float) 1);
                        bb.setAlpha((float) 0.1);
                        cc.setAlpha((float) 0.1);
                        dd.setAlpha((float) 0.1);
                    }
               if(contact.getPayload().isWith_driver()){
                        aa.setAlpha((float) 1);
                        bb.setAlpha((float) 1);
                        cc.setAlpha((float) 0.1);
                        dd.setAlpha((float) 0.1);
                    }
               if(contact.getPayload().isIs_delivered()){
                        aa.setAlpha((float) 1);
                        bb.setAlpha((float) 1);
                        cc.setAlpha((float) 1);
                        dd.setAlpha((float) 0.1);
                    }
              if(contact.getPayload().isIs_paid()){
                  aa.setAlpha((float) 1);
                  bb.setAlpha((float) 1);
                  cc.setAlpha((float) 1);
                  dd.setAlpha((float) 1);
              }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<content_track> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
