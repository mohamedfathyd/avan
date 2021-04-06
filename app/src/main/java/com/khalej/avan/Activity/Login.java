package com.khalej.avan.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_user;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.realm.Realm;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView skip,signUp,forgetPassword,signUpFani;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    private contact_general_user contactList;
    private apiinterface_home apiinterface;

    String codee;
    LoginButton loginButton;
    AppCompatButton appCompatButtonRegisterservcies;
    CallbackManager callbackManager;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ACCESS_COARSE_LOCATION =2;
    double lat=0.0,lng=0.0;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final int REQUEST_LOCATION = 1;
    Realm realm;
    EditText textInputEditTextpassword,textInputEditTextemail;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));

        setContentView(R.layout.activity_login);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        realm = Realm.getDefaultInstance();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }



        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        edt.putString("lat", String.valueOf(lat));
        edt.putString("lng", String.valueOf(lng));
        edt.apply();


        if(sharedpref.getString("remember","").trim().equals("yes")){
            edt.putFloat("totalprice",0);
            edt.apply();
            if(sharedpref.getString("type","").equals("customer")){
            startActivity(new Intent(Login.this, MainActivity.class));}
            else{
              //  startActivity(new Intent(Login.this, DriverActivity.class));
            }

            finish();
        }

        textInputEditTextemail=findViewById(R.id.textInputEditTextemail);
        textInputEditTextpassword=findViewById(R.id.textInputEditTextpassword);
        Typeface T1;
        T1 = Typeface.createFromAsset(getAssets(), "Tajawal-Regular.ttf");
        textInputEditTextemail.setTypeface(T1);
        textInputEditTextpassword.setTypeface(T1);
        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
        forgetPassword=findViewById(R.id.forgetPassword);
        signUp=findViewById(R.id.signUp);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Login.this, Register.class));

            }
        });
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInputEditTextemail.getText().toString().equals("")||textInputEditTextemail.getText().toString()==null){
                   startActivity(new Intent(Login.this, MainActivity.class));
                }
                else{
                   // startActivity(new Intent(Login.this, MainActivity.class));
                    fetchInfo();}
            }
        });
    }

    public void fetchInfo(){
        progressDialog = ProgressDialog.show(Login.this,"جاري تسجيل الدخول","Please wait...",false,false);
        progressDialog.show();
        String email=textInputEditTextemail.getText().toString();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call= apiinterface.getcontacts_login(email,
                textInputEditTextpassword.getText().toString());
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                progressDialog.dismiss();
                if (response.code() == 404) {
                    Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري ",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                if(response.isSuccessful()){

                    contactList = response.body();
                    try {
                        progressDialog.dismiss();
                        edt.putString("id",contactList.getPayload().getUser_info().getId());
                        edt.putString("name",contactList.getPayload().getUser_info().getFull_name());
                        edt.putString("phone",contactList.getPayload().getUser_info().getPhone());
                        edt.putString("address",contactList.getPayload().getUser_info().getEmail());
                        edt.putString("token",contactList.getPayload().getToken());
                        edt.putString("type",contactList.getPayload().getUser_info().getType());
                        edt.putString("remember","yes");
                        edt.apply();

                        Dialog dialog1;
                        dialog1 = new Dialog(Login.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TextView message=dialog1.findViewById(R.id.message);
                        message.setText("تم تسجيل الدخول بنجاح ");
                        dialog1.show();
                        if(contactList.getPayload().getUser_info().getType().equals("customer")){
                            startActivity(new Intent(Login.this, MainActivity.class));}
                        else{
                            startActivity(new Intent(Login.this, driver_first.class));
                        }


                    }
                    catch (Exception e){
                        Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري /",Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                Toast.makeText(Login.this,"هناك خطأ فى الهاتف او الرقم السري",Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }
        });
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat=mLastLocation.getLatitude();
            lng=mLastLocation.getLongitude();

        }
    };


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Login.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();

            } else  if (location1 != null) {
                lat = location1.getLatitude();
                lng = location1.getLongitude();


            } else  if (location2 != null) {
                lat = location2.getLatitude();
                lng = location2.getLongitude();


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
            if(lat==0){
             /*   String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 23.3728831, 85.3372199);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                Toast.makeText(Login.this,"قمنا بفتح جوجل ماب لتحديد موقعك الحالي",Toast.LENGTH_LONG).show();*/
                getLocation();
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        //     startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat=location.getLatitude();
                                    lng=location.getLongitude();

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //  getLastLocation();
            }
        }
    }
}