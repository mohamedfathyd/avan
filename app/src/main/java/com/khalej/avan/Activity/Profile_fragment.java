package com.khalej.avan.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_profile;
import com.khalej.avan.model.contact_general_user_update;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_fragment extends Fragment {
    EditText textInputEditTextname,textInputEditTextphone,email,nationalId,password;
    TextView nationalDate;
    AppCompatButton appCompatButton;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    contact_general_profile contactList;
    DatePickerDialog picker;
    String date;
    contact_general_user_update userData;
    ProgressBar progressBar;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
       // id = getArguments().getInt("id");
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        textInputEditTextname=view.findViewById(R.id.textInputEditTextname);
        email=view.findViewById(R.id.email);
        textInputEditTextphone=view.findViewById(R.id.textInputEditTextphone);
        nationalId=view.findViewById(R.id.nationalId);
        nationalDate=view.findViewById(R.id.nationaldate);
        password=view.findViewById(R.id.password);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        appCompatButton=view.findViewById(R.id.appCompatButtonRegister);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getString("remember","").equals("yes")){
                    fetchwithoutImage();}
                else{
                    Toast.makeText(getContext(),"قم بتسجل الدخول أولاَ" ,Toast.LENGTH_LONG).show();
                }
            }
        });
        fetchProfile();
        nationalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String month="";
                                if((monthOfYear+1)<10){
                                    month="0"+(monthOfYear+1);
                                }
                                else{
                                    month= String.valueOf((monthOfYear+1));
                                }
                                String day="";
                                if((dayOfMonth+1)<10){
                                    day="0"+(dayOfMonth);
                                }
                                else{
                                    day= String.valueOf((dayOfMonth));
                                }
                                nationalDate.setText(year + "-" + month+ "-" + day);
                                date=year + "-" + month+ "-" + day;
                            }
                        }, day, month, year);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });
        return view;
    }
    public void fetchwithoutImage(){
        progressDialog = ProgressDialog.show(getContext(), "جاري تعديل البيانات", "Please wait...", false, false);
        progressDialog.show();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.updateProfile(headers,textInputEditTextname.getText().toString()
                ,email.getText().toString(),textInputEditTextphone.getText().toString(),
                nationalDate.getText().toString(),nationalId.getText().toString());
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
                progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                     Toast.makeText(getContext(),jObjError.toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();
                //  Toast.makeText(getContext(),userData.isStatus()+"",Toast.LENGTH_LONG).show();
                edt.putString("name",userData.getPayload().getFull_name());
                edt.putString("phone",userData.getPayload().getPhone());
                edt.apply();
                Toast.makeText(getContext(),"تم  حفظ التعديلات بنجاح ",Toast.LENGTH_LONG).show();
                if(password.getText().length()<=0){
                  //  Toast.makeText(getContext(),"لم يتم تعديل كلمة المرور ادخل كلمة مرور قوية اكثر من 6 احرف او أرقام",Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().length()<6){
                    Toast.makeText(getContext(),"لم يتم تعديل كلمة المرور ادخل كلمة مرور قوية اكثر من 6 احرف او أرقام",Toast.LENGTH_LONG).show();
                    return;
                }
                fetchchangepassword();


            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void fetchchangepassword(){
//        progressDialog = ProgressDialog.show(EditProfile.this, "جاري تعديل البيانات", "Please wait...", false, false);
//        progressDialog.show();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user_update> call = apiinterface.updateProfile_pass(headers,password.getText().toString(),
                password.getText().toString());
        call.enqueue(new Callback<contact_general_user_update>() {
            @Override
            public void onResponse(Call<contact_general_user_update> call, Response<contact_general_user_update> response) {
                //   progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(EditProfile.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                userData=response.body();


            }

            @Override
            public void onFailure(Call<contact_general_user_update> call, Throwable t) {
                //   progressDialog.dismiss();
            }
        });
    }
 public void fetchProfile(){
     progressBar.setVisibility(View.VISIBLE);

     apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
         HashMap<String, String> headers = new HashMap<String, String>();
         headers.put("Accept","application/json");
         headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

         Call<contact_general_profile> call = apiinterface.getcontacts_profile(headers);
         call.enqueue(new Callback<contact_general_profile>() {
             @Override
             public void onResponse(Call<contact_general_profile> call, Response<contact_general_profile> response) {
                 progressBar.setVisibility(View.GONE);

                 contactList = response.body();
                 try {
                     textInputEditTextname.setText(contactList.getPayload().getName());
                     textInputEditTextphone.setText(contactList.getPayload().getPhone());
                     email.setText(contactList.getPayload().getEmail());
                     nationalDate.setText(contactList.getPayload().getNational_id_expiry_date());
                     nationalId.setText(contactList.getPayload().getNational_id());

                 } catch (Exception e) {
                     progressBar.setVisibility(View.GONE);
                 }
             }

             @Override
             public void onFailure(Call<contact_general_profile> call, Throwable t) {
                 progressBar.setVisibility(View.GONE);
             }
         });
     }
 }

