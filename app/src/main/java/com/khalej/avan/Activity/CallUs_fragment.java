package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallUs_fragment extends Fragment {
    EditText textInputEditTextname,textInputEditTextphone,textInputEditTextsubject,
            textInputEditTextmessage,textInputEditTextphonee;
    AppCompatButton appCompatButton;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_callus, container, false);
       // id = getArguments().getInt("id");
        textInputEditTextname=view.findViewById(R.id.textInputEditTextname);
        textInputEditTextsubject=view.findViewById(R.id.textInputEditTextsubject);
        textInputEditTextphone=view.findViewById(R.id.textInputEditTextphone);
        textInputEditTextphonee=view.findViewById(R.id.textInputEditTextphonee);
        textInputEditTextmessage=view.findViewById(R.id.textInputEditTextmessage);
        appCompatButton=view.findViewById(R.id.appCompatButtonRegister);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });

        return view;
    }
    public void fetchInfo() {

        progressDialog = ProgressDialog.show(getContext(), "جاري ارسال رسالتك", "Please wait...", false, false);
        progressDialog.show();
        String phone=textInputEditTextphone.getText().toString();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.CallUs(textInputEditTextname.getText().toString(),textInputEditTextphone.getText().toString(),
                textInputEditTextphonee.getText().toString(), textInputEditTextmessage.getText().toString());
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
                message.setText( "تم ارسال رسالتك بنجاح");
                dialog1.show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
