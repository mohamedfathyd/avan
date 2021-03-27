package com.khalej.avan.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.khalej.avan.R;
import com.khalej.avan.model.Apiclient_home;
import com.khalej.avan.model.apiinterface_home;
import com.khalej.avan.model.contact_general_;


import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bottomsheet_driverData_fragment extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
   TextView terms;
   ImageView close;
    private apiinterface_home apiinterface;
    private contact_general_ contact;
    int x = 0;
    int y = 0;
   ImageView a;



    public static bottomsheet_driverData_fragment newInstance() {
        return new bottomsheet_driverData_fragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_driverdata_sheet, null);
        close = (ImageView) contentView.findViewById(R.id.close);
        terms = (TextView) contentView.findViewById(R.id.terms);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        fetchInfo_annonce();
       close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               confirmm(0);
           }
       });
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_> call = apiinterface.getcontacts_g(sharedpref.getString("language","").trim());
        call.enqueue(new Callback<contact_general_>() {
            @Override
            public void onResponse(Call<contact_general_> call, Response<contact_general_> response) {
                contact=response.body();
                try {
                    terms.setText(Html.fromHtml(contact.getPayload().getAbout().getTerms_and_conditions()));
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<contact_general_> call, Throwable t) {

            }
        });
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }

    public void cancell(){
        dismiss();
    }
    public void confirmm( int i){

        dismiss();
    }

}
