package com.soldis.yourthaitea.transaction.maintenance.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MAINTENANCE;

public class frag_Maintenance extends Fragment {
    TextView
            txtSave
    ;

    EditText edtQ1,
            edtQ2,
            edtQ3,
            edtQ4,
            edtQ5
                    ;

    Obj_MAINTENANCE objMaintenance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction_maintain, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillForm();
    }


    void InitControl(View v) {
        txtSave = (TextView)v.findViewById(R.id.txtSave);

        edtQ1 = (EditText)v.findViewById(R.id.edtQ1);
        edtQ2 = (EditText)v.findViewById(R.id.edtQ2);
        edtQ3 = (EditText)v.findViewById(R.id.edtQ3);
        edtQ4 = (EditText)v.findViewById(R.id.edtQ4);
        edtQ5 = (EditText)v.findViewById(R.id.edtQ5);
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiInput()){
                    CustomeDialog();
                }
            }
        });
    }

    void FillForm(){
        try{
            objMaintenance = new Obj_MAINTENANCE();
            objMaintenance = objMaintenance.getData(AppConstant.strCustNo);
            edtQ1.setText((objMaintenance.getQ1() == null ? "" : objMaintenance.getQ1()));
            edtQ2.setText((objMaintenance.getQ2() == null ? "" : objMaintenance.getQ2()));
            edtQ3.setText((objMaintenance.getQ3() == null ? "" : objMaintenance.getQ3()));
            edtQ4.setText((objMaintenance.getQ4() == null ? "" : objMaintenance.getQ4()));
            edtQ5.setText((objMaintenance.getQ5() == null ? "" : objMaintenance.getQ5()));
        }catch (Exception e){

        }
    }

    boolean ValidasiInput(){
        String sQ1, sQ2, sQ3, sQ4, sQ5;
        sQ1 = edtQ1.getText().toString();
        sQ2 = edtQ2.getText().toString();
        sQ3 = edtQ3.getText().toString();
        sQ4 = edtQ4.getText().toString();
        sQ5 = edtQ5.getText().toString();

        if (sQ1.equals("") && sQ2.equals("")&& sQ3.equals("") && sQ4.equals("") && sQ5.equals("")){
            AppController.getInstance().CustomeDialog(getActivity(), getResources().getString(R.string.custcard_must_be));
            return false;
        }
        return true;
    }

    void SaveData(){
        objMaintenance = new Obj_MAINTENANCE();
        objMaintenance = objMaintenance.getData(AppConstant.strCustNo);

        if (objMaintenance.getCUSTNO() != null && !objMaintenance.getCUSTNO().equals("")){
            Log.w("SaveData", "Update " + objMaintenance.getCUSTNO());
            objMaintenance = new Obj_MAINTENANCE();
            objMaintenance.setCUSTNO(AppConstant.strCustNo);
            objMaintenance.setQ1(edtQ1.getText().toString());
            objMaintenance.setQ2(edtQ2.getText().toString());
            objMaintenance.setQ3(edtQ3.getText().toString());
            objMaintenance.setQ4(edtQ4.getText().toString());
            objMaintenance.setQ5(edtQ5.getText().toString());
            objMaintenance.UpdateData(objMaintenance);
        }else{
            Log.w("SaveData", "Create");
            objMaintenance = new Obj_MAINTENANCE();
            objMaintenance.setCUSTNO(AppConstant.strCustNo);
            objMaintenance.setQ1(edtQ1.getText().toString());
            objMaintenance.setQ2(edtQ2.getText().toString());
            objMaintenance.setQ3(edtQ3.getText().toString());
            objMaintenance.setQ4(edtQ4.getText().toString());
            objMaintenance.setQ5(edtQ5.getText().toString());
            objMaintenance.CreateData(objMaintenance);
        }
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.stock_saved));
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SaveData();
            }
        });

        dialog.show();
    }




}
