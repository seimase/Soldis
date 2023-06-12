package com.soldis.yourthaitea.admin.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_Message;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 11/09/2017.
 */

public class Frag_Generate extends Fragment {

    LinearLayout lyProses;

    ProgressDialog progress;

    Tbl_Message tblMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_generate, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    private void InitControl(final View v) {
        lyProses = (LinearLayout)v.findViewById(R.id.layout_GenOutlet);
        lyProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncData();
            }
        });
    }

    void SyncData(){
        progress = ProgressDialog.show(getActivity(), getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);

        Log.w("Generate", " Start" );
        try{
            Call<Tbl_Message> call = NetworkManager.getNetworkService().ImportFromSFA(AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_Message>() {
                @Override
                public void onResponse(Call<Tbl_Message> call, Response<Tbl_Message> response) {
                    int code = response.code();
                    progress.dismiss();
                    Log.w("Generate", " " + code );
                    if (code == 200){
                        tblMessage = response.body();
                        AppController.getInstance().CustomeDialog(getActivity(), tblMessage.message);
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Message> call, Throwable t) {
                    Log.w("Generate", " t " + t.getMessage() );
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            Log.w("Generate", " e " + e.getMessage() );
            progress.dismiss();
        }
    }
}
