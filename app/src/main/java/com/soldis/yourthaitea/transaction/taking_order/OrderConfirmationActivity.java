package com.soldis.yourthaitea.transaction.taking_order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProduct;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductTmp;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterOrderConfimation;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class OrderConfirmationActivity extends AppCompatActivity {
    TextView txtLabel,
            txtNamaToko,
            txtAlamat,
            txtSave,
            txtAddMoreItems,
            //txtTypeName,
            txtOrderNo,
            txtTotalPrice,
                    txtDate
    ;
    EditText txtRemark, edtMejaNo;

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    RelativeLayout layout_back;
    String sCustNo, sCustName, sAddress, sTgl, sOrderNo;

    double dSubtotal = 0;
    long lQty = 0;
    boolean bStatus;
    String TIMEIN = "";
    String sLat, sLng;
    String sSpeed;

    boolean bDone = false;
    int iTotalSKU = 0;
    double dTotalPrice = 0;

    Obj_MASTER objEmaster = new Obj_MASTER();
    public static ArrayList<Tmp_MASTER> tmpMasters;
    ImageView imgAvatar;

    Obj_TRX_D trxD = new Obj_TRX_D();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_order_confirmation);

        InitControl();

        TIMEIN = AppController.getInstance().getTime();
        sLat = "0.0";
        sLng = "0.0";
        sSpeed = "0.0";
        try {
            sCustNo = AppConstant.strCustNo;
            sCustName = AppConstant.strCustName;
            sAddress = AppConstant.strCustAddress;
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);
        }catch (Exception e){
            bStatus = false;
            txtNamaToko.setText("");
            txtAlamat.setText("");
        }

        try{
            sOrderNo = getIntent().getExtras().getString("ORDERNO");
            AppConstant.strOrderNo = sOrderNo;
        }catch (Exception e){
            sOrderNo = "";
        }


        txtLabel.setText("ORDER CONFIRMATION");
        txtOrderNo.setText(sOrderNo);

        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(OrderConfirmationActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }


        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        objCustcard = objCustcard.getData(sCustNo, sOrderNo);

        if (objCustcard.getFLAG_KIRIM() != null){
            if (objCustcard.getFLAG_KIRIM().equals("Y")) txtSave.setVisibility(View.GONE);
        }

        objEmaster = new Obj_MASTER();
        if(bStatus) {
            if (AppConstant.objMasters != null){
                if (AppConstant.objMasters.size() == 0){
                    AppConstant.objMasters = objEmaster.getDataListStock(sCustNo, sOrderNo);
                }
            }else{
                AppConstant.objMasters = objEmaster.getDataListStock(sCustNo, sOrderNo);
            }

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FillGrid();

        AppController.getInstance().hideKeyboardFrom(OrderConfirmationActivity.this);
    }

    void InitControl(){
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        txtAddMoreItems = (TextView)findViewById(R.id.txtAddMoreItems);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtNamaToko = (TextView)findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        edtMejaNo = (EditText)findViewById(R.id.txtRemark) ;

        //txtTypeName = (TextView)findViewById(R.id.txtTypeName);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
        txtRemark = (EditText)findViewById(R.id.txtRemark);
        txtDate = (TextView)findViewById(R.id.text_tgl);
        //txtTypeName.setText(AppConstant.strCustTypeName);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        RelativeLayout layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtDate.setText(getResources().getString(R.string.profile_transaction_date) + " " +
                AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx) );

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sMejaNo = edtMejaNo.getText().toString().trim();

                if (sMejaNo.equals("") || sMejaNo.equals("0")){
                    AppController.getInstance().CustomeDialog(OrderConfirmationActivity.this, "Nomor meja belum diisi!!");
                }else{
                    Intent mIntent = new Intent(OrderConfirmationActivity.this, OrderPaymentActivity.class);
                    AppConstant.strMejaNo = sMejaNo;
                    OrderPaymentActivity.tmpMasters = tmpMasters;
                    startActivity(mIntent);
                }

            }
        });

        txtAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bStatus){
                    Intent mIntent = new Intent(OrderConfirmationActivity.this, InputProductActivity.class);

                    InputProductActivity.tmpMasters = tmpMasters;
                    mIntent.putExtra("CUSTNO", AppConstant.strCustNo);
                    mIntent.putExtra("ORDERNO", sOrderNo);
                    mIntent.putExtra("CUSTNAME", AppConstant.strCustName);
                    mIntent.putExtra("ADDRESS", AppConstant.strCustAddress);
                    mIntent.putExtra("STATUS", true);
                    startActivity(mIntent);
                }
                finish();

            }
        });

    }

    void FillGrid(){
        dTotalPrice = 0;
        for (Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getQTY_TRX() > 0) {
                iTotalSKU += 1;
                dTotalPrice += (dat.getQTY_TRX() * dat.getSellPrice1()) ;
            }
        }

        txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dTotalPrice));

        mAdapter = new AdapterOrderConfimation(this, AppConstant.objMasters, false, new AdapterOrderConfimation.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked() {
                dSubtotal = 0;
                lQty = 0;
                iTotalSKU = 0;
                dTotalPrice = 0;
                for (Obj_MASTER dat : AppConstant.objMasters){
                    dSubtotal += dat.getTOTAL();
                    lQty += dat.getQTY_TRX();
                    if (dat.getQTY_TRX() > 0) {
                        iTotalSKU += 1;
                        dTotalPrice += (dat.getQTY_TRX() * dat.getSellPrice1()) ;
                    }
                }
                txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dTotalPrice));
            }

        });

        mRecyclerView.setAdapter(mAdapter);

    }

    void CustomeDialogDelete(String ISI, final int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        TextView txtLabel = (TextView)dialog.findViewById(R.id.textLabel);

        txtDialog.setText(ISI);
        txtLabel.setText("Input Produk");
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
                tmpMasters.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });

        dialog.show();
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
                if (bDone){
                    //lyAdd.setVisibility(View.VISIBLE);
                    //lySave.setVisibility(View.GONE);
                    //AppController.getInstance().CustomeDialog(InputProductActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                    AppController.getInstance().BackUpDB(OrderConfirmationActivity.this);
                    setResult(RESULT_OK);
                    CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
                }else{
                    AppController.getInstance().CustomeDialog(OrderConfirmationActivity.this, getResources().getString(R.string.daily_product_not_selected));
                }

            }
        });

        dialog.show();
    }

    void CustomeDialogProses(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_data_in_process));
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
                Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                objCustcard.UpdateFlagProses(sCustNo);
                AppController.getInstance().CustomeDialog(OrderConfirmationActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                AppController.getInstance().BackUpDB(OrderConfirmationActivity.this);
            }
        });

        dialog.show();
    }


    void CustomeDialogTidakBeli(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_not_so_transaction));
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
                //SaveCustCard1("N", "R1");
                AppConstant.bRefresh = true;
                setResult(RESULT_OK);
                finish();
            }
        });

        dialog.show();
    }


    void SaveData(){
        Log.w("SaveOrder", String.valueOf(bStatus));
        AppController.getInstance().OpenDatabase(OrderConfirmationActivity.this);

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        Obj_TRX_D objTrxD = new Obj_TRX_D();

        bDone = false;

        int iSKU = 0;
        double dInvAmount = 0 ;

        for(Tmp_MASTER dat : tmpMasters){
            objTrxD = new Obj_TRX_D();
            objTrxD.setORDERNO(sOrderNo);
            objTrxD.setPCODE(dat.PRODUCT);
            objTrxD.setQTY_B(0);
            objTrxD.setQTY_T(0);
            objTrxD.setQTY_K(1);
            objTrxD.setSELLPRICE1(0);
            objTrxD.setSELLPRICE2(0);
            objTrxD.setSELLPRICE2(0);
            objTrxD.setQTY_PCS(1);
            objTrxD.setAMOUNT(0);
            objTrxD.CreateData(objTrxD);

            bDone = true;
        }

        if (bDone){
            iSKU = tmpMasters.size();
            if (!bStatus){
                //Insert CustCard-------------------------------------------
                objCustcard.setCUSTNO(sCustNo);
                objCustcard.setDOCNO(sOrderNo);
                objCustcard.setFLAG_KIRIM("N");
                objCustcard.setLATITUDE(sLat);
                objCustcard.setLONGITUDE(sLng);
                objCustcard.setTGL_TRX(AppConstant.strTglTrx);
                objCustcard.setTIMEIN(TIMEIN);

                objCustcard.setTIMEOUT(AppController.getInstance().getTime());
                objCustcard.CreateData(objCustcard);

                //Insert Header------------------------------------------------
                objTrxH.setORDERNO(sOrderNo);
                objTrxH.setCUSTNO(sCustNo);
                objTrxH.setORDERDATE(AppConstant.strTglTrx);
                objTrxH.setSKU(iSKU);
                objTrxH.setINVAMOUNT(dInvAmount);
                objTrxH.setREMARK(txtRemark.getText().toString().trim());
                objTrxH.CreateData(objTrxH);

                //Update TRXNO-------------------------------------------------
                Obj_MOTORIS objMotoris = new Obj_MOTORIS();
                objMotoris.setTRXNO(Double.parseDouble(sOrderNo));
                objMotoris.UpdateTrxNo(objMotoris);

                AppController.getInstance().AddGPSLog(sCustName, sLat, sLng, sSpeed, AppController.getInstance().BatteryLevel());

            }else{
                //Insert Header------------------------------------------------
                objTrxH.setORDERNO(sOrderNo);
                objTrxH.setCUSTNO(sCustNo);
                objTrxH.setORDERDATE(AppConstant.strTglTrx);
                objTrxH.setSKU(iSKU);
                objTrxH.setINVAMOUNT(dInvAmount);
                objTrxH.setREMARK(txtRemark.getText().toString().trim());
                objTrxH.CreateData(objTrxH);
            }
        }
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
                //ValidasiTransaksi();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    boolean ValidasiGPS(){
        GPSTracker gps;
        // create class object
        gps = new GPSTracker(getBaseContext());

        String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!providerGps.contains("gps")){ //if gps is disabled
            DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                    getResources().getString(R.string.text_gps_is_off));
            return false;
        }
        sLat = "0.0";
        sLng= "0.0";

        // create class object
        gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            double speed = gps.getSpeed();
            sLat = Double.toString(latitude);
            sLng = Double.toString(longitude);
            //sSpeed = Double.toString(speed);

            if (sLat.equals("0.0")){
                CustomeDialog(getResources().getString(R.string.text_gps_not_locked));
                return false;
            }
            // \n is for new line
        }else{
            gps.showSettingsAlert();
        }
        return true;
    }

    private void DisplayDialogGPS(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(android.R.string.ok),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }

    void CustomeDialog(String ISI){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void CustomeDialogSave(String ISI){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    boolean ValidasiTransaksi(){

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        objCustcard = objCustcard.getData(sCustNo);

        if (objCustcard.getCUSTNO() != null && !objCustcard.getCUSTNO().equals("")){
            AppConstant.bRefresh = true;
            finish();
            return true;
        }else {
            if (ValidasiGPS()){
                CustomeDialogTidakBeli();
            }
            return false;
        }
    }

    void SaveCustCard1(String FLAG_EC, String REASON){
        //InsertCustcard1----------------------------------------------
        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        objCustcard1 = objCustcard1.getData(sCustNo);

        if (objCustcard1.getCUSTNO() != null && !objCustcard1.getCUSTNO().equals("")){
            //Update
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            objCustcard1.setTIMEOUT(AppController.getInstance().getCurrentTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.UpdateFlagEC(objCustcard1);
        }else{
            //Insert
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            //objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.CreateData(objCustcard1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_CALL) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

            return true;
        }else if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }

        return false;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(AppConstant.bClose) finish();
    }
}
