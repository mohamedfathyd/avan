package com.khalej.avan.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.avan.R;

public class task_details extends AppCompatActivity implements OnMapReadyCallback {
Intent intent;
TextView address_from,address_to,date,time,amount,qunt,wight,phone,type;
    private GoogleMap mMap;
    int comeFrom;
    AppCompatButton scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        address_from=findViewById(R.id.address_from);
        address_to=findViewById(R.id.address_to);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        date=findViewById(R.id.date);
        wight=findViewById(R.id.wigth);
        amount=findViewById(R.id.amount);
        phone=findViewById(R.id.phone);
        qunt=findViewById(R.id.qunt);
        scan=findViewById(R.id.scan);
        type=findViewById(R.id.type);
        intent=getIntent();
        address_from.setText(intent.getStringExtra("address_from"));
        address_to.setText(intent.getStringExtra("address_to"));
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));
        amount.setText(intent.getStringExtra("amount"));
        wight.setText(intent.getStringExtra("wight"));
        phone.setText(intent.getStringExtra("phone"));
        if(!intent.getStringExtra("payment_method").equals(null)) type.setText(intent.getStringExtra("payment_method"));
        qunt.setText(intent.getStringExtra("qunt"));
        comeFrom=intent.getIntExtra("come_from",1);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(task_details.this,ScanQR.class);
                i.putExtra("come_from",comeFrom);
                startActivity(i);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng sydney;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
               checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        if(intent.getDoubleExtra("lat",0.0)==0.0){
            sydney = new LatLng(30.033333, 31.233334);
           // Toast.makeText(task_details.this,"من فضلك تأكد ان الخريطة تحديد الموقع يعمل بشكل جيد عن طريق فتح جوجل ماب وغلقه لكي يمكننا تحديد موقعك الحالي", Toast.LENGTH_LONG).show();

        }
        else{
            sydney = new LatLng(intent.getDoubleExtra("lat",0.0), intent.getDoubleExtra("lng",0.0));
        }
         mMap.addMarker(new MarkerOptions().position(sydney).title("Avan"));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,17);
         mMap.animateCamera(location);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
