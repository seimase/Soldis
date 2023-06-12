package com.soldis.yourthaitea.transaction.taking_order;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.master.ListCategory;
import com.soldis.yourthaitea.transaction.CustcardActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProduct;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductCategory;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductTmp;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class InputProductActivity extends AppCompatActivity {
    static int REQ_NEXT = 100;
    static int REQ_CATEGORY = 110;
    TextView txtLabel,
            badge_notification_1,
            text_amount1,

            txtOrderNo,
            txtHot,
            txtIce
    ;

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;


    Obj_MASTER objEmaster;

    String sCustNo, sCustName, sAddress, sTgl, sOrderNo,
        stsFromConfirm;

    double dSubtotal = 0;
    long lQty = 0;
    boolean bStatus;
    //LinearLayout lySave, lyAdd, lyProcess, lyButton;
    String TIMEIN = "";
    String sLat, sLng;
    String sSpeed;

    boolean bDone = false;

    LinearLayout layoutReview;
    RelativeLayout layout_back;
    ProgressDialog progress;

    LinearLayout lyM, lyHot;
    View vwM, vwHot;
    String CATEGORY_ID = "";


    Tmp_MASTER tmpMaster;
    public static ArrayList<Tmp_MASTER> tmpMasters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_input_product_new);
        InitControl();
        TIMEIN = AppController.getInstance().getTime();
        sLat = "0.0";
        sLng = "0.0";
        sSpeed = "0.0";
        CATEGORY_ID = "10";
        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            sCustName = getIntent().getExtras().getString("CUSTNAME");
            sAddress = getIntent().getExtras().getString("ADDRESS");
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            //txtSubtotal.setText("");
        }catch (Exception e){
            //txtSubtotal.setText("");
        }

        try{
            sOrderNo = getIntent().getExtras().getString("ORDERNO");
        }catch (Exception e){
            sOrderNo = "";
        }

        if (bStatus){
            txtLabel.setText(getResources().getString(R.string.dash_sales_data_label));
        }else{
            txtLabel.setText(getResources().getString(R.string.daily_sales_input));
        }

        txtOrderNo.setText(sOrderNo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.getInstance().hideKeyboardFrom(InputProductActivity.this);


        if(!bStatus) {
            objEmaster = new Obj_MASTER();
            AppConstant.objMasters = new ArrayList<>();
            AppConstant.objMasters = objEmaster.getDataListStock(sCustNo, sOrderNo);
        }

    }

    void InitControl(){
        vwM = (View)findViewById(R.id.vwM);
        vwHot = (View)findViewById(R.id.vwHot);
        lyM = (LinearLayout)findViewById(R.id.lyM);
        lyHot = (LinearLayout)findViewById(R.id.lyHot);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layoutReview = (LinearLayout)findViewById(R.id.layoutReview);
        txtHot = (TextView)findViewById(R.id.txtHot);
        txtIce = (TextView)findViewById(R.id.txtIce);

        badge_notification_1 = (TextView)findViewById(R.id.badge_notification_1);
        text_amount1 = (TextView)findViewById(R.id.text_amount1);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        layoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(InputProductActivity.this, OrderConfirmationActivity.class);
                OrderConfirmationActivity.tmpMasters = tmpMasters;
                mIntent.putExtra("STATUS", bStatus);
                mIntent.putExtra("ORDERNO", sOrderNo);
                startActivityForResult(mIntent, REQ_NEXT);
            }
        });

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vwM.setVisibility(View.VISIBLE);
        vwHot.setVisibility(View.GONE);
        txtIce.setTextColor(getResources().getColor(R.color.black));
        txtHot.setTextColor(getResources().getColor(R.color.grey_dark));

        lyM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtIce.setTextColor(getResources().getColor(R.color.black));
                txtHot.setTextColor(getResources().getColor(R.color.grey_dark));
                vwM.setVisibility(View.VISIBLE);
                vwHot.setVisibility(View.GONE);
                CATEGORY_ID = "10";
                FillGrid(CATEGORY_ID);
            }
        });

        lyHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtIce.setTextColor(getResources().getColor(R.color.grey_dark));
                txtHot.setTextColor(getResources().getColor(R.color.black));
                vwM.setVisibility(View.GONE);
                vwHot.setVisibility(View.VISIBLE);
                CATEGORY_ID = "11";
                FillGrid(CATEGORY_ID);
            }
        });
    }


    void FillGrid(String sCATEGORY_ID){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);

        Log.w("FillGrid", "Load");
        objEmaster = new Obj_MASTER();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        objTrxH = objTrxH.getDataCustNo(sCustNo);
        String Status = "";
        boolean bTRX = false;

        //Init Fisrt--------------------------------------------------------------------------------
        dSubtotal = 0;
        lQty = 0;
        for (Obj_MASTER dat : AppConstant.objMasters){
            dSubtotal += dat.getTOTAL();
            lQty += dat.getQTY_TRX();
        }

        if (lQty == 0){
            layoutReview.setVisibility(View.GONE);
        }else{
            layoutReview.setVisibility(View.VISIBLE);
        }

        badge_notification_1.setText(Long.toString(lQty));
        text_amount1.setText(AppController.getInstance().toCurrencyRp(dSubtotal));

        //------------------------------------------------------------------------------------------

       //AppController.getInstance().CustomeDialog(InputProductActivity.this, Status);
        mAdapter = new AdapterInputProductCategory(this, AppConstant.objMasters, bTRX, sCATEGORY_ID, new AdapterInputProductCategory.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(){
                dSubtotal = 0;
                lQty = 0;
                badge_notification_1.setText("0");
                for (Obj_MASTER dat : AppConstant.objMasters){
                    dSubtotal += (dat.getQTY_TRX() * dat.getSellPrice1());
                    lQty += dat.getQTY_TRX();
                }

                badge_notification_1.setText(Long.toString(lQty));
                text_amount1.setText(AppController.getInstance().toCurrencyRp(dSubtotal));

                if (lQty == 0){
                    layoutReview.setVisibility(View.GONE);
                }else{
                    layoutReview.setVisibility(View.VISIBLE);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        progress.dismiss();
    }

   /* void FillGrid(){
        if (tmpMasters.size() > 0) {
            text_amount1.setText(tmpMasters.size() + " items");
            layoutReview.setVisibility(View.VISIBLE);
        }else{
            layoutReview.setVisibility(View.GONE);
        }
        mAdapter = new AdapterInputProductTmp(this, tmpMasters, new AdapterInputProductTmp.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(int Position){
                if (tmpMasters.size() > 0) {
                    layoutReview.setVisibility(View.VISIBLE);
                }else{
                    layoutReview.setVisibility(View.GONE);
                }
                CustomeDialogDelete("Hapus produk : " +
                                    tmpMasters.get(Position).PRODUCT + " ?", Position);

            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }*/

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
                    AppController.getInstance().BackUpDB(InputProductActivity.this);
                    CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
                }else{
                    AppController.getInstance().CustomeDialog(InputProductActivity.this, getResources().getString(R.string.daily_product_not_selected));
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
                AppController.getInstance().CustomeDialog(InputProductActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                AppController.getInstance().BackUpDB(InputProductActivity.this);
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
        TextView txtLabel = (TextView)dialog.findViewById(R.id.textLabel);

        txtLabel.setText(getResources().getString(R.string.text_confirmation));
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
                finish();
            }
        });

        dialog.show();
    }


    void SaveData(){
        AppController.getInstance().OpenDatabase(InputProductActivity.this);

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        Obj_TRX_D objTrxD = new Obj_TRX_D();
        Obj_MOTORIS objMotoris = new Obj_MOTORIS();

        double dTrxNo = 0;
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);
        String sTrxNo = "" ;
        if (objMotoris.getSLSNO() != null){
            dTrxNo = objMotoris.getTRXNO() + 1;
            sTrxNo = Long.toString((long)dTrxNo);
        }

        bDone = false;

        int iSKU = 0;
        double dInvAmount = 0 ;
        for(Obj_MASTER dat : AppConstant.objMasters){
            //if (dat.getTOTAL() > 0){
            if (dat.getINP_UOM1().equals("")) dat.setINP_UOM1("0");
            if (dat.getINP_UOM2().equals("")) dat.setINP_UOM2("0");
            if (dat.getINP_UOM3().equals("")) dat.setINP_UOM3("0");

            int iQty_B = Integer.parseInt(dat.getINP_UOM1());
            int iQty_T = Integer.parseInt(dat.getINP_UOM2());
            int iQty_K = Integer.parseInt(dat.getINP_UOM3());

                if (iQty_K > 0){
                    iSKU += 1;
                    dInvAmount += dat.getTOTAL();


                    long iQtyTotal = (iQty_B * dat.getConvUnit2() * dat.getConvUnit3()) +
                            (iQty_T * dat.getConvUnit2()) +
                            (iQty_K)
                            ;

                    //Insert Deatil------------------------------------------------
                    objTrxD = new Obj_TRX_D();
                    objTrxD.setORDERNO(sTrxNo);
                    objTrxD.setPCODE(dat.getPCode());
                    objTrxD.setQTY_B(Integer.parseInt(dat.getINP_UOM1()));
                    objTrxD.setQTY_T(Integer.parseInt(dat.getINP_UOM2()));
                    objTrxD.setQTY_K(Integer.parseInt(dat.getINP_UOM3()));
                    objTrxD.setSELLPRICE1(dat.getSellPrice1());
                    objTrxD.setSELLPRICE2(dat.getSellPrice2());
                    objTrxD.setSELLPRICE2(dat.getSellPrice3());
                    objTrxD.setQTY_PCS((int)iQtyTotal);
                    objTrxD.setAMOUNT(dat.getTOTAL());
                    objTrxD.CreateData(objTrxD);

                    bDone = true;
                }

            //}
        }

        if (bDone){
            //Insert CustCard-------------------------------------------
            objCustcard.setCUSTNO(sCustNo);
            objCustcard.setDOCNO(sTrxNo);
            objCustcard.setFLAG_KIRIM("N");
            objCustcard.setLATITUDE(sLat);
            objCustcard.setLONGITUDE(sLng);
            objCustcard.setTGL_TRX(objMotoris.getTRX_DATE());
            objCustcard.setTIMEIN(TIMEIN);

            objCustcard.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard.CreateData(objCustcard);

            SaveCustCard1("Y", "");

            //Insert Header------------------------------------------------
            objTrxH.setORDERNO(sTrxNo);
            objTrxH.setCUSTNO(sCustNo);
            objTrxH.setORDERDATE(objMotoris.getTRX_DATE());
            objTrxH.setSKU(iSKU);
            objTrxH.setINVAMOUNT(dInvAmount);
            objTrxH.CreateData(objTrxH);

            //Update TRXNO-------------------------------------------------
            objMotoris = new Obj_MOTORIS();
            objMotoris.setTRXNO(dTrxNo);
            objMotoris.UpdateTrxNo(objMotoris);

            AppController.getInstance().AddGPSLog(sCustName, sLat, sLng, sSpeed, AppController.getInstance().BatteryLevel());

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
                ValidasiTransaksi();
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
            objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.UpdateFlagEC(objCustcard1);
        }else{
            //Insert
            /*objCustcard1.setCUSTNO(sCustNo);
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.CreateData(objCustcard1);*/
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
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQ_NEXT){
            if (resultCode == RESULT_OK) {
                finish();
            }
        }else if (requestCode == REQ_CATEGORY){
            if (resultCode == RESULT_OK) {

            }
        }
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
                FillGrid(CATEGORY_ID);
                //mAdapter.notifyDataSetChanged();
            }
        });

        dialog.show();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        FillGrid(CATEGORY_ID);

        if(AppConstant.bClose) finish();
    }
}
