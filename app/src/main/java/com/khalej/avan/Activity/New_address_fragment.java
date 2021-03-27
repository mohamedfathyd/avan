package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class New_address_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private contact_general_ contact;
    Typeface myTypeface;
    Spinner type_address;
    double lat=0.0;
    double lng=0.0;
    EditText address,phone,details,num_place,num_room,floor;
    AppCompatButton confirm;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_address, container, false);
       // id = getArguments().getInt("id");
        address=view.findViewById(R.id.address);
        type_address=view.findViewById(R.id.type_address);
        confirm=view.findViewById(R.id.appCompatButtonRegister);

        phone=view.findViewById(R.id.phone);
        details=view.findViewById(R.id.details);
        num_place=view.findViewById(R.id.place_num);
        num_room=view.findViewById(R.id.room_num);
        floor=view.findViewById(R.id.floor);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Places.initialize(getContext().getApplicationContext(), "AIzaSyCt61evVuEP5REjsrfMPE8gLKEB_vx5wV4");
        address.setFocusable(false);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(getActivity());
                startActivityForResult(intent,100);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("remember","").equals("yes")){
                    fetchInfo();}
                else{
                    Toast.makeText(getContext(),"قم بتسجل الدخول أولاَ" ,Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 ){
            Place place = Autocomplete.getPlaceFromIntent(data);
            address.setText(place.getAddress());
            lat=place.getLatLng().latitude;
            lng=place.getLatLng().longitude;
        }
    }

    public void fetchInfo() {
        String h="home";
        if(type_address.getSelectedItemPosition()==0){
            h="home";
        }
        else{
            h="office";
        }
        progressDialog = ProgressDialog.show(getContext(), "جاري أضافة العنوان الجديد", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.add_newAddress(headers,address.getText().toString(),
                Integer.parseInt(num_place.getText().toString())
                ,Integer.parseInt(num_room.getText().toString()), 0
                ,details.getText().toString(),floor.getText().toString(),
                h,1 ,lat,lng ,"d073b45b-a739-4221-8e30-7045a3b7adc1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(getContext());
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText( "تم اضافة العنوان بنجاح");
                dialog1.show();
                Address_fragment address_fragment =
                        new Address_fragment();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, address_fragment)
                        .addToBackStack(null).commit();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
