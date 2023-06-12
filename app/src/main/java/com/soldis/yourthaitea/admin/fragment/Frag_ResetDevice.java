package com.soldis.yourthaitea.admin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


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

public class Frag_ResetDevice extends Fragment {
    EditText edtSlsno, edtShipto;
    LinearLayout lyProses;
    ProgressDialog progress;

    Tbl_Message tblMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_resetdevice, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }


    private void InitControl(final View v) {
        edtSlsno = (EditText)v.findViewById(R.id.edtSlsno);
        edtShipto = (EditText)v.findViewById(R.id.edtShipto);
        lyProses = (LinearLayout)v.findViewById(R.id.layout_proses);

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

        try{
            Call<Tbl_Message> call = NetworkManager.getNetworkService().ResetDevice(edtSlsno.getText().toString(),
                    edtShipto.getText().toString().trim());
            call.enqueue(new Callback<Tbl_Message>() {
                @Override
                public void onResponse(Call<Tbl_Message> call, Response<Tbl_Message> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMessage = response.body();
                        AppController.getInstance().CustomeDialog(getActivity(), tblMessage.message);
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Message> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

}
