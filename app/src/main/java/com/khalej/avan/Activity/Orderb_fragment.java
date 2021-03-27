package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orderb_fragment extends Fragment {
   TextView title,message;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    EditText desc;
    Typeface myTypeface;
    AppCompatButton confirm;
    String address;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_orderb, container, false);
        address = getArguments().getString("address");

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        confirm=view.findViewById(R.id.appCompatButtonRegister);
        desc=view.findViewById(R.id.desc);
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
    public void fetchInfo() {

        progressDialog = ProgressDialog.show(getContext(), "جاري ارسال طلبك", "Please wait...", false, false);
        progressDialog.show();
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.confirmShipment(desc.getText().toString(),address);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                     Toast.makeText(getActivity(),jObjError.toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(getContext());
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText( "تم ارسال طلبك بنجاح");
                dialog1.show();
                Orderc_fragment orderc_fragment  =
                        new Orderc_fragment();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, orderc_fragment)
                        .addToBackStack(null).commit();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
