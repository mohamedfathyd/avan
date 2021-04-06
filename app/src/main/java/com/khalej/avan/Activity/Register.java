package com.khalej.avan.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_user;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Register extends AppCompatActivity implements bottomsheet_driverData_fragment.ItemClickListener {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    private contact_general_user contactList;
    private apiinterface_home apiinterface;
    EditText firstName,secondName,phone,password,confirmPassword,nationalId,email;
    TextView nationalDate,nationalPhoto,ShowTerms;
    AppCompatButton register;
    String date="";
    CheckBox Terms;
    DatePickerDialog picker;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST_User = 1;
    String mediaPath;
    String imagePath;
    private  static final int IMAGEUser = 100;
    int x;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        setContentView(R.layout.activity_register);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        firstName=findViewById(R.id.firstName);
        secondName=findViewById(R.id.secondName);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        nationalId=findViewById(R.id.nationalId);
        nationalDate=findViewById(R.id.nationaldate);
        nationalPhoto=findViewById(R.id.nationalPhoto);
        ShowTerms=findViewById(R.id.showterms);
        email=findViewById(R.id.email);
        Terms=findViewById(R.id.check);
        register=findViewById(R.id.appCompatButtonRegisterservcies);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        nationalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Register.this,
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
        nationalPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialog();
                }
            }
        });
        ShowTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDriver();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){
                    Toast.makeText(Register.this,"كلمة المرور وتأكيد كلمة المرور غير متطابقان" ,Toast.LENGTH_LONG).show();
                   return;
                }
                if(firstName.getText().toString().equals("")||secondName.getText().toString().equals("")
                        ||phone.getText().toString().equals("")||nationalId.getText().toString().equals("")||nationalDate.getText().toString().equals("")
                        || nationalPhoto.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(Register.this,"من فضلك ادخل بيانات التسجيل كامله" ,Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().length()<6){
                    Toast.makeText(Register.this,"لم يتم تعديل كلمة المرور ادخل كلمة مرور قوية اكثر من 6 احرف او أرقام",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!Terms.isChecked()){
                    Toast.makeText(Register.this,"قم بالموافقه على الشروط والاحكام أولاً" ,Toast.LENGTH_LONG).show();
                }
                    fetchInfo();
            }
        });
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, IMAGEUser);

    }

    private void takePhotoFromCamera() {
        x=1;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(Register.this, getPackageName(), image);
            imagePath=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_User);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_User && resultCode == Activity.RESULT_OK)
        {

            mediaPath = imagePath;
            String name=random();

            mediaPath=resizeAndCompressImageBeforeSend(Register.this,mediaPath  ,name);
            nationalPhoto.setText(mediaPath);
        }
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPath=resizeAndCompressImageBeforeSend(Register.this,mediaPath,namee);

            nationalPhoto.setText(mediaPath);
            cursor.close();
        }
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 300 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                try {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){}
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }}catch (Exception e){}
    }

    public void fetchInfo() {
        progressDialog = ProgressDialog.show(Register.this, "جاري انشاء الحساب", "Please wait...", false, false);
        progressDialog.show();
        String image="";
        File file = null;
        try{
            file = new File(mediaPath);}
        catch (Exception e){
            Toast.makeText(Register.this,"من فضلك قم بتحديد صوره الهوية",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        MultipartBody.Part fileToUpload  = null;
        try {
            // Parsing any Media type file
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("national_id_photocopy", file.getName(), requestBodyId);
        }
        catch (Exception e){
            Toast.makeText(Register.this,"من فضلك قم بتحديد صوره شخصيه",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        RequestBody firstname = RequestBody.create(MediaType.parse("text/plain"),firstName.getText().toString());
        RequestBody secondname = RequestBody.create(MediaType.parse("text/plain"),secondName.getText().toString());
        RequestBody nationalid = RequestBody.create(MediaType.parse("text/plain"),nationalId.getText().toString());
        RequestBody phoneR=RequestBody.create(MediaType.parse("text/plain"),phone.getText().toString());
        RequestBody emailR=RequestBody.create(MediaType.parse("text/plain"),email.getText().toString());
        RequestBody passwordR=RequestBody.create(MediaType.parse("text/plain"),password.getText().toString());
        RequestBody nationaldate=RequestBody.create(MediaType.parse("text/plain"), nationalDate.getText().toString());
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_user> call = apiinterface.getcontact_newaccount(fileToUpload,firstname,secondname,emailR,passwordR,phoneR,
                nationalid,nationaldate);
        call.enqueue(new Callback<contact_general_user>() {
            @Override
            public void onResponse(Call<contact_general_user> call, Response<contact_general_user> response) {
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(Register.this,"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                contactList = response.body();

                try{
                    progressDialog.dismiss();
                    edt.putString("id",contactList.getPayload().getUser_info().getId());
                    edt.putString("name",contactList.getPayload().getUser_info().getFull_name());
                    edt.putString("phone",contactList.getPayload().getUser_info().getPhone());
                    edt.putString("address",contactList.getPayload().getUser_info().getEmail());
                    edt.putString("type","customer");
                    edt.putString("token",contactList.getPayload().getToken());
                    edt.putString("remember","yes");
                    edt.apply();
                    Dialog dialog1;
                    dialog1 = new Dialog(Register.this);
                    dialog1.setContentView(R.layout.dialog_success);
                    dialog1.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView message=dialog1.findViewById(R.id.message);
                    message.setText("تم تفعيل حسابك بنجاح");
                    dialog1.show();
                    startActivity(new Intent(Register.this,MainActivity.class));}
                catch (Exception e){
                    Toast.makeText(Register.this, e+"", Toast.LENGTH_LONG).show();
                    Log.d("message2",e+"");
                }


            }

            @Override
            public void onFailure(Call<contact_general_user> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, "هناك خطأ بالتسجيل حاول مره أخري", Toast.LENGTH_LONG).show();


            }
        });
    }
    public void showBottomSheetDriver() {
        bottomsheet_driverData_fragment addPhotoBottomDialogFragment =
                bottomsheet_driverData_fragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                bottomsheet_driverData_fragment.TAG);
    }

    @Override
    public void onItemClick(String item) {}
}
