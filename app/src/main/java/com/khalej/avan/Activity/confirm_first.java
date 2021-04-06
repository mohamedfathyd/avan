package com.khalej.avan.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class confirm_first extends AppCompatActivity {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ImageView logout;
    TextView name;
    AppCompatButton one,two,three,four,six;
    EditText message;
    Intent i;
    RelativeLayout a;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        name=findViewById(R.id.name);
        i=getIntent();
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        six=findViewById(R.id.six);
        a=findViewById(R.id.aa);
        message=findViewById(R.id.message);
        if(i.getIntExtra("come_from",1)==1){
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
        }else if(i.getIntExtra("come_from",1)==3){
            six.setVisibility(View.VISIBLE);
        }
        else{
            one.setVisibility(View.GONE);
        }
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo__receive();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo__delever();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setVisibility(View.GONE);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo__assign();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().equals("")){
                    Toast.makeText(confirm_first.this,"من فضلك أدخل سبب الألغاء أولاً",Toast.LENGTH_LONG).show();
                    return;
                }
                fetchInfo__cancel();
            }
        });
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putInt("type",0);
                edt.putFloat("wallet",0);
                edt.putString("token","");
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(confirm_first.this, Login.class));
                finish();
            }
        });

    }

    public void fetchInfo__receive() {

        progressDialog = ProgressDialog.show(confirm_first.this, "جاري تأكيد استلام الطلب", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.markreceived(headers,i.getStringExtra("id"));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(confirm_first.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);

                message.setText( "تم تأكيد أستلام الطلب من افان");
                dialog1.show();
                startActivity(new Intent(confirm_first.this,driver_first.class));
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(confirm_first.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo__delever() {

        progressDialog = ProgressDialog.show(confirm_first.this, "جاري تأكيد تسليم الطلب", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.markdelevir(headers,i.getStringExtra("id"));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(confirm_first.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText( "تم تسليم الطلب للزبون بنجاح");
                dialog1.show();
                startActivity(new Intent(confirm_first.this,driver_first.class));
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(confirm_first.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo__cancel() {

        progressDialog = ProgressDialog.show(confirm_first.this, "جاري تأكيد ألغاء الطلب", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.markcancel(headers,i.getStringExtra("id"),message.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(confirm_first.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);
                message.setText( "تم تأكيد ألغاء تسليم الطلب للزبون");
                dialog1.show();
                startActivity(new Intent(confirm_first.this,driver_first.class));
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(confirm_first.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfo__assign() {

        progressDialog = ProgressDialog.show(confirm_first.this, "جاري اضافة الطلب الى طلباتي", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.content_assign(headers,i.getStringExtra("id"));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                Dialog dialog1;
                dialog1 = new Dialog(confirm_first.this);
                dialog1.setContentView(R.layout.dialog_success);
                dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView message=dialog1.findViewById(R.id.message);

                message.setText( "تم أضافة الطلب الى طلباتك");
                dialog1.show();
                startActivity(new Intent(confirm_first.this,driver_first.class));
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(confirm_first.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
