package com.soldis.yourthaitea.transaction.complaint;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_COLLECTION;
import com.soldis.yourthaitea.entity.Obj_COMPLAINT;
import com.soldis.yourthaitea.transaction.collection.CollectionActivity;
import com.soldis.yourthaitea.transaction.taking_order.ListOrderActivity;

public class ComplainActivity extends AppCompatActivity {
    TextView txtLabel,
            txtSave,
            text_nama_toko,
            text_alamat,
            txtTgl
            //txtTypeName
                    ;

    EditText edtQ1,
            edtQ2,
            edtQ3,
            edtQ4,
            edtQ5
            ;
    Toolbar toolbar;

    Obj_COMPLAINT objComplaint;
    ImageView imgAvatar;
    RelativeLayout lyBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_complain);

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FillForm();
    }

    void InitControl() {
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        text_nama_toko = (TextView)findViewById(R.id.text_nama_toko);
        text_alamat = (TextView)findViewById(R.id.text_alamat);
        txtTgl = (TextView)findViewById(R.id.text_tgl);

        lyBack = (RelativeLayout)findViewById(R.id.layout_back);
        //txtTypeName = (TextView)findViewById(R.id.txtTypeName);
        //txtTypeName.setText(AppConstant.strCustTypeName);
        txtLabel.setText("Complaint");

        try{
            txtTgl.setText("Tanggal transaksi : "  + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("Tanggal transaksi : ");
        }

        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(ComplainActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        edtQ1 = (EditText)findViewById(R.id.edtQ1);
        edtQ2 = (EditText)findViewById(R.id.edtQ2);
        edtQ3 = (EditText)findViewById(R.id.edtQ3);
        edtQ4 = (EditText)findViewById(R.id.edtQ4);
        edtQ5 = (EditText)findViewById(R.id.edtQ5);

        text_nama_toko.setText(AppConstant.strCustName);
        text_alamat.setText(AppConstant.strCustAddress);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiInput()){
                    CustomeDialog();
                }
            }
        });

        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void FillForm(){
        try{
            objComplaint = new Obj_COMPLAINT();
            objComplaint = objComplaint.getData(AppConstant.strCustNo);
            edtQ1.setText((objComplaint.getQ1() == null ? "" : objComplaint.getQ1()));
            edtQ2.setText((objComplaint.getQ2() == null ? "" : objComplaint.getQ2()));
            edtQ3.setText((objComplaint.getQ3() == null ? "" : objComplaint.getQ3()));
            edtQ4.setText((objComplaint.getQ4() == null ? "" : objComplaint.getQ4()));
            edtQ5.setText((objComplaint.getQ5() == null ? "" : objComplaint.getQ5()));
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
            AppController.getInstance().CustomeDialog(ComplainActivity.this, getResources().getString(R.string.custcard_must_be));
            return false;
        }
        return true;
    }

    void SaveData(){
        objComplaint = new Obj_COMPLAINT();
        objComplaint = objComplaint.getData(AppConstant.strCustNo);

        if (objComplaint.getCUSTNO() != null && !objComplaint.getCUSTNO().equals("")){
            Log.w("SaveData", "Update " + objComplaint.getCUSTNO());
            objComplaint = new Obj_COMPLAINT();
            objComplaint.setCUSTNO(AppConstant.strCustNo);
            objComplaint.setQ1(edtQ1.getText().toString());
            objComplaint.setQ2(edtQ2.getText().toString());
            objComplaint.setQ3(edtQ3.getText().toString());
            objComplaint.setQ4(edtQ4.getText().toString());
            objComplaint.setQ5(edtQ5.getText().toString());
            objComplaint.UpdateData(objComplaint);
        }else{
            Log.w("SaveData", "Create");
            objComplaint = new Obj_COMPLAINT();
            objComplaint.setCUSTNO(AppConstant.strCustNo);
            objComplaint.setQ1(edtQ1.getText().toString());
            objComplaint.setQ2(edtQ2.getText().toString());
            objComplaint.setQ3(edtQ3.getText().toString());
            objComplaint.setQ4(edtQ4.getText().toString());
            objComplaint.setQ5(edtQ5.getText().toString());
            objComplaint.CreateData(objComplaint);
        }
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
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
                finish();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
