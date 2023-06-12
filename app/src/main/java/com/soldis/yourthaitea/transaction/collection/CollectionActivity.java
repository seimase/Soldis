package com.soldis.yourthaitea.transaction.collection;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_COLLECTION;
import com.soldis.yourthaitea.transaction.complaint.ComplainActivity;
import com.soldis.yourthaitea.transaction.taking_order.OrderConfirmationActivity;

public class CollectionActivity extends AppCompatActivity {
    TextView txtLabel,
            txtSave,
            text_nama_toko,
            text_alamat,
            //txtTypeName
            txtTgl
                    ;
    Toolbar toolbar;

    CheckBox chkInvoice,
            chkSuratJalan,
            chkTukarFaktur
    ;

    Obj_COLLECTION objCollection;
    ImageView imgAvatar;
    RelativeLayout lyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_collection);

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FillForm();
        Log.w("INVOICE", "Start");
    }

    void InitControl() {
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        text_nama_toko = (TextView)findViewById(R.id.text_nama_toko);
        text_alamat = (TextView)findViewById(R.id.text_alamat);
        txtLabel.setText("Collection");

        text_nama_toko.setText(AppConstant.strCustName);
        text_alamat.setText(AppConstant.strCustAddress);
        chkInvoice = (CheckBox) findViewById(R.id.chkInvoice);
        chkSuratJalan = (CheckBox) findViewById(R.id.chkSuratJalan);
        chkTukarFaktur = (CheckBox) findViewById(R.id.chkTukarFaktur);

        txtTgl = (TextView)findViewById(R.id.text_tgl);

        lyBack = (RelativeLayout)findViewById(R.id.layout_back);

        try{
            txtTgl.setText("Tanggal transaksi : "  + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("Tanggal transaksi : ");
        }

        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(CollectionActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

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
        String INVOICE,
                SURAT_JALAN,
                TUKAR_FAKTUR;

        Log.w("INVOICE", "FillForm");
        try{
            objCollection = new Obj_COLLECTION();
            objCollection = objCollection.getData(AppConstant.strCustNo);

            Log.w("INVOICE", objCollection.getCUSTNO());
            INVOICE = (objCollection.getQ1() == null ? "" : objCollection.getQ1());
            SURAT_JALAN = (objCollection.getQ2() == null ? "" : objCollection.getQ2());
            TUKAR_FAKTUR = (objCollection.getQ3() == null ? "" : objCollection.getQ3());

            chkInvoice.setChecked(false);
            chkSuratJalan.setChecked(false);
            chkTukarFaktur.setChecked(false);

            if (INVOICE.equals("Y")) chkInvoice.setChecked(true);
            if (SURAT_JALAN.equals("Y")) chkSuratJalan.setChecked(true);
            if (TUKAR_FAKTUR.equals("Y")) chkTukarFaktur.setChecked(true);

            Log.w("INVOICE", INVOICE);
            Log.w("SURAT_JALAN", SURAT_JALAN);
            Log.w("TUKAR_FAKTUR", TUKAR_FAKTUR);
        }catch (Exception e){
            Log.w("Error", e.getMessage());
        }


    }

    boolean ValidasiInput(){
        if (!chkInvoice.isChecked() && !chkSuratJalan.isChecked() && !chkTukarFaktur.isChecked()){
            AppController.getInstance().CustomeDialog(CollectionActivity.this, getResources().getString(R.string.custcard_must_be));
            return false;
        }
        return true;
    }

    void SaveData(){
        objCollection = new Obj_COLLECTION();
        objCollection = objCollection.getData(AppConstant.strCustNo);

        if (objCollection.getCUSTNO() != null && !objCollection.getCUSTNO().equals("")){
            Log.w("SaveData", "Update");
            objCollection = new Obj_COLLECTION();
            objCollection.setCUSTNO(AppConstant.strCustNo);
            objCollection.setQ1("N");
            objCollection.setQ2("N");
            objCollection.setQ3("N");

            if (chkInvoice.isChecked()) objCollection.setQ1("Y");
            if (chkSuratJalan.isChecked()) objCollection.setQ2("Y");
            if (chkTukarFaktur.isChecked()) objCollection.setQ3("Y");

            objCollection.UpdateData(objCollection);
        }else{
            Log.w("SaveData", "Create");
            objCollection = new Obj_COLLECTION();
            objCollection.setCUSTNO(AppConstant.strCustNo);
            objCollection.setQ1("N");
            objCollection.setQ2("N");
            objCollection.setQ3("N");

            if (chkInvoice.isChecked()) objCollection.setQ1("Y");
            if (chkSuratJalan.isChecked()) objCollection.setQ2("Y");
            if (chkTukarFaktur.isChecked()) objCollection.setQ3("Y");
            objCollection.CreateData(objCollection);
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
