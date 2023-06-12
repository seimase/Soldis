package com.soldis.yourthaitea;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.admin.AdminActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_CUSTMST_GEN;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_PRLIN;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.ApiGoogleMaps;
import com.soldis.yourthaitea.model.Obj_SendData;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_CustmstGen;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_SendDataCustNotGPS;
import com.soldis.yourthaitea.model.Tbl_Version;
import com.soldis.yourthaitea.model.net.NetworkManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 17/10/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView txtLabel,
            txtDateSyncProduct,
            txtDateSyncOutlet,
            txtUpdate,
            txtTotalData,
            txtGenerate,
            txtSendData,
            txtKey,
            txtAdmin
                    ;

    EditText edtToken;
    LinearLayout
            lySyncToko,
            lySyncProduct,
            lyAdmin,
            lyAdmin2,
            lyGetData,
            lyProses,
            lyRestore,
            lyRestoreDetail
                    ;

    ImageView imgArrow;

    ProgressDialog progress;
    Tbl_Karyawan tblMotoris;
    Tbl_Custmst tblCustmst;
    Tbl_CustmstGen tblCustmstGen;
    Tbl_Master tblMaster;
    Tbl_Version  tblVersion;
    Tbl_SendDataCustNotGPS tblSendDataCustNotGPS;

    Obj_CUSTMST objEcustmst;
    Obj_CUSTMST_GEN objCustmstGen;
    Obj_MASTER objEmaster;
    Obj_PRLIN objPrlin;

    ApiGoogleMaps apiGoogleMaps;
    String GOOGLE_PROVINSI,
            GOOGLE_KABUPATEN,
            GOOGLE_KECAMATAN,
            GOOGLE_KELURAHAN,
            GOOGLE_KODEPOS,
            GOOGLE_ALAMAT
                    ;
    Obj_MOTORIS objMotoris;
    ArrayList<String> adm1  = new ArrayList<String>();
    ArrayList<String> adm2  = new ArrayList<String>();
    ArrayList<String> adm3  = new ArrayList<String>();
    ArrayList<String> adm4  = new ArrayList<String>();
    ArrayList<String> postalCode  = new ArrayList<String>();

    Obj_SendData objSendData;

    boolean bSHOW = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.green));
        }
        setContentView(R.layout.activity_setting);

        InitControl();
        AppController.getInstance().OpenDatabase(SettingsActivity.this);

        AppConstant.API_KEY_GOOGLE_MAPS = "";
        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                AppConstant.API_KEY_GOOGLE_MAPS = "";
            }
        }
        adm1.add("administrative_area_level_1");
        adm1.add("political");

        adm2.add("administrative_area_level_2");
        adm2.add("political");

        adm3.add("administrative_area_level_3");
        adm3.add("political");

        adm4.add("administrative_area_level_4");
        adm4.add("political");
        postalCode.add("postal_code");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FillForm();

        Random rand = new Random();
        int i ;
        int n = Integer.parseInt(AppController.getInstance().getCurrentTimeAdd());
        i = rand.nextInt(n+10);
        txtKey.setText(Integer.toString(i));
    }

    void InitControl(){
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtDateSyncProduct = (TextView)findViewById(R.id.txtDateSyncProduct);
        txtDateSyncOutlet = (TextView)findViewById(R.id.txtDateSyncOutlet);
        txtUpdate = (TextView)findViewById(R.id.text_update);
        txtGenerate = (TextView)findViewById(R.id.text_generate);
        txtTotalData = (TextView)findViewById(R.id.text_total_data);
        txtSendData = (TextView)findViewById(R.id.text_send);
        txtKey = (TextView)findViewById(R.id.text_key);
        edtToken = (EditText)findViewById(R.id.edt_token);

        lySyncToko = (LinearLayout)findViewById(R.id.layout_sync_toko);
        lySyncProduct = (LinearLayout)findViewById(R.id.layout_sync_product);
        lyAdmin = (LinearLayout)findViewById(R.id.layout_admin);
        lyAdmin2 = (LinearLayout)findViewById(R.id.layout_admin2);
        txtAdmin = (TextView)findViewById(R.id.text_admin);
        lyGetData = (LinearLayout)findViewById(R.id.layout_getdata);
        lyProses = (LinearLayout)findViewById(R.id.layout_proses);
        lyRestore = (LinearLayout)findViewById(R.id.layout_restore);
        lyRestoreDetail = (LinearLayout)findViewById(R.id.layout_restore_detail);

        imgArrow = (ImageView)findViewById(R.id.img_arrow);

        txtLabel.setText(getResources().getString(R.string.setting_setting));

        lySyncToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidation()){
                    if (AppController.getInstance().isOnline()){
                        CustomeDialogSyncOutlet();
                    }else{
                        AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this, getResources().getString(R.string.setting_transaction_not_complete));
                }

            }
        });

        lySyncProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    CustomeDialogSyncProduct();
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this, getResources().getString(R.string.text_no_connection));
                }
            }
        });

        if (!AppConstant.strLevel.equals("7")){
            lyAdmin.setVisibility(View.GONE);
            lyAdmin2.setVisibility(View.GONE);
        }else{
            lyAdmin.setVisibility(View.VISIBLE);
            lyAdmin2.setVisibility(View.VISIBLE);
        }

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidation()){
                    if (AppController.getInstance().isOnline()){
                        SyncVersion();
                    }else{
                        AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this, getResources().getString(R.string.setting_transaction_not_complete));
                }
            }
        });


        lyGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    GetDataOutletNoGPS();
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.text_no_connection));
                }
            }
        });

        txtGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objMotoris = new Obj_MOTORIS();
                objMotoris = objMotoris.getData(AppConstant.strSlsNo);
                long iDummyCode;
                if (objMotoris.getSLSNO() != null) {
                    iDummyCode = (long) objMotoris.getSEQNO() + 1;
                    objMotoris = new Obj_MOTORIS();
                    objMotoris.setSEQNO(iDummyCode);
                    objMotoris.UpdateSeqNo(objMotoris);
                }
                if (AppController.getInstance().isOnline()){
                    Obj_CUSTMST_GEN custmstGen = new Obj_CUSTMST_GEN();
                    if (custmstGen.getDataList().size() > 0){
                        custmstGen = new Obj_CUSTMST_GEN();
                        custmstGen = custmstGen.getDataGPS();
                        SyncGoogleMaps(custmstGen);
                    }
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.text_no_connection));
                }
            }
        });


        txtSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    objSendData = new Obj_SendData();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = objSendData.sendDataCustNotGPS();
                    String sJson = jsonObject.toString();

                    AppController.getInstance().writeLog(sJson);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(sJson.toString()));
                    SendDataToServer(body);
                }catch (Exception e){
                    Log.w("Cust not GPS", e.getMessage());
                }
            }
        });


        lyProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String GENERATECODE = AppController.getInstance().GenerateCode(txtKey.getText().toString());
                if (GENERATECODE.equals(edtToken.getText().toString())){
                    AppController.getInstance().RestoreDB(SettingsActivity.this);
                    bSHOW = false;
                    lyRestoreDetail.setVisibility(View.GONE);
                    System.exit(0);
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this,
                            getResources().getString(R.string.setting_token_not_valid));
                }

            }
        });

        lyRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bSHOW){
                    imgArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
                    lyRestoreDetail.setVisibility(View.GONE);
                    bSHOW = false;
                }else{
                    imgArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
                    lyRestoreDetail.setVisibility(View.VISIBLE);
                    bSHOW = true;
                }

            }
        });

        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SettingsActivity.this, AdminActivity.class);
                startActivity(mIntent   );
            }
        });
    }

    void FillForm(){
        txtDateSyncOutlet.setText(AppController.getInstance().getSessionManager().getStringData(AppConstant.DATE_SYNC_OUTLET));
        txtDateSyncProduct.setText(AppController.getInstance().getSessionManager().getStringData(AppConstant.DATE_SYNC_PRODUCT));

        objCustmstGen = new Obj_CUSTMST_GEN();
        txtTotalData.setText(AppController.getInstance().toCurrency(objCustmstGen.TotalDataNotGPS()) + "/" + AppController.getInstance().toCurrency(objCustmstGen.TotalData()));
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

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.setting_logout_app));
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
                if (VisitValidation()){
                    if (AppController.getInstance().isOnline()){
                        LogOut();
                    }else{
                        AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    AppController.getInstance().CustomeDialog(SettingsActivity.this,getResources().getString(R.string.setting_transaction_not_complete));
                }

            }
        });

        dialog.show();
    }

    void LogOut(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);
        try{
            Call<Tbl_Karyawan> call = NetworkManager.getNetworkService().logOutUser(AppConstant.strSlsNo, AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Karyawan>() {
                @Override
                public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMotoris = response.body();
                        if (!tblMotoris.error){
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Karyawan> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void SyncOutlet(){

        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);
        try{
            Call<Tbl_Custmst> call = NetworkManager.getNetworkService().ListOutlet(AppConstant.strMitraID,
                    AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_Custmst>() {
                @Override
                public void onResponse(Call<Tbl_Custmst> call, Response<Tbl_Custmst> response) {
                    int code = response.code();
                    if(code == 200){
                        tblCustmst = response.body();
                        objEcustmst = new Obj_CUSTMST();
                        objEcustmst.DeleteData();
                        if (!tblCustmst.error){

                            //Data New Outlet
                            for (Tbl_Custmst.DataNew dat : tblCustmst.data_new){
                                objEcustmst = new Obj_CUSTMST();
                                objEcustmst.setCUSTNO(dat.CUSTNO);
                                objEcustmst.setCUSTADD1(dat.CUSTADD1);
                                objEcustmst.setKELURAHAN(dat.GOOGLE_KELURAHAN);
                                objEcustmst.setPROVINSI(dat.GOOGLE_PROVINSI);
                                objEcustmst.setKABUPATEN(dat.GOOGLE_KABUPATEN);
                                objEcustmst.setKECAMATAN(dat.GOOGLE_KECAMATAN);
                                objEcustmst.setKODEPOS(dat.GOOGLE_KODEPOS);
                                objEcustmst.setALAMAT(dat.GOOGLE_ALAMAT);
                                objEcustmst.setLATITUDE(dat.LATITUDE);
                                objEcustmst.setLONGITUDE(dat.LONGITUDE);
                                objEcustmst.setCPHONE1(dat.CPHONE1);
                                objEcustmst.setCCONTACT(dat.CCONTACT);
                                objEcustmst.setCUSTNAME(dat.CUSTNAME);
                                objEcustmst.setPHOTO_NAME(dat.PHOTO);
                                objEcustmst.setFLAG_KIRIM("Y");
                                objEcustmst.setTOTAL_KUNJUNGAN(dat.TOTAL_KUNJUNGAN);
                                objEcustmst.setLAST_TRANSACTION(dat.LAST_TRANSACTION);
                                objEcustmst.setFLAGOUT(dat.FLAGOUT);
                                objEcustmst.setTYPEOUT(dat.TYPEOUT);
                                objEcustmst.setFLAGCUST("");
                                objEcustmst.setCOMPANY_NAME(dat.COMPANY_NAME);
                                objEcustmst.setNUMBER_OF_BRANCH(dat.NUMBER_OF_BRANCH);
                                objEcustmst.setDATE_NEXT_VISIT(dat.DATE_NEXT_VISIT);
                                objEcustmst.setKTP_ID(dat.KTP_ID);
                                objEcustmst.setKTP_NAME(dat.KTP_NAME);
                                objEcustmst.setKTP_ADDRESS(dat.KTP_ADDRESS);
                                objEcustmst.setNPWP_ID(dat.NPWP_ID);
                                objEcustmst.setNPWP_NAME(dat.NPWP_NAME);
                                objEcustmst.setNPWP_ADDRESS(dat.NPWP_ADDRESS);
                                objEcustmst.setPAYMENT_TYPE(dat.PAYMENT_TYPE);
                                objEcustmst.setPERIODE_ORDER(dat.PERIODE_ORDER);
                                objEcustmst.setSTATUS_CONTRACT(dat.STATUS_CONTRACT);
                                objEcustmst.setSTARTDATE_CONTRACT(dat.STARTDATE_CONTRACT);
                                objEcustmst.setENDDATE_CONTRACT(dat.ENDDATE_CONTRACT);

                                objEcustmst.CreateData(objEcustmst);
                            }

                            //Data Outlet
                            for (Tbl_Custmst.DataCust dat : tblCustmst.data_cust){
                                objEcustmst = new Obj_CUSTMST();
                                objEcustmst.setCUSTNO(dat.CUSTNO);
                                objEcustmst.setCUSTADD1(dat.CUSTADD1);
                                objEcustmst.setKELURAHAN(dat.GOOGLE_KELURAHAN);
                                objEcustmst.setPROVINSI(dat.GOOGLE_PROVINSI);
                                objEcustmst.setKABUPATEN(dat.GOOGLE_KABUPATEN);
                                objEcustmst.setKECAMATAN(dat.GOOGLE_KECAMATAN);
                                objEcustmst.setKODEPOS(dat.GOOGLE_KODEPOS);
                                objEcustmst.setALAMAT(dat.GOOGLE_ALAMAT);
                                objEcustmst.setLATITUDE(dat.LATITUDE);
                                objEcustmst.setLONGITUDE(dat.LONGITUDE);
                                objEcustmst.setCPHONE1(dat.CPHONE1);
                                objEcustmst.setCCONTACT(dat.CCONTACT);
                                objEcustmst.setCUSTNAME(dat.CUSTNAME);
                                objEcustmst.setPHOTO_NAME(dat.PHOTO);
                                objEcustmst.setFLAG_KIRIM("Y");
                                objEcustmst.setTOTAL_KUNJUNGAN(dat.TOTAL_KUNJUNGAN);
                                objEcustmst.setLAST_TRANSACTION(dat.LAST_TRANSACTION);
                                objEcustmst.setFLAGOUT(dat.FLAGOUT);
                                objEcustmst.setTYPEOUT(dat.TYPEOUT);
                                objEcustmst.setFLAGCUST("1");
                                objEcustmst.setCOMPANY_NAME(dat.COMPANY_NAME);
                                objEcustmst.setNUMBER_OF_BRANCH(dat.NUMBER_OF_BRANCH);
                                objEcustmst.setDATE_NEXT_VISIT(dat.DATE_NEXT_VISIT);
                                objEcustmst.setKTP_ID(dat.KTP_ID);
                                objEcustmst.setKTP_NAME(dat.KTP_NAME);
                                objEcustmst.setKTP_ADDRESS(dat.KTP_ADDRESS);
                                objEcustmst.setNPWP_ID(dat.NPWP_ID);
                                objEcustmst.setNPWP_NAME(dat.NPWP_NAME);
                                objEcustmst.setNPWP_ADDRESS(dat.NPWP_ADDRESS);
                                objEcustmst.setPAYMENT_TYPE(dat.PAYMENT_TYPE);
                                objEcustmst.setPERIODE_ORDER(dat.PERIODE_ORDER);
                                objEcustmst.setSTATUS_CONTRACT(dat.STATUS_CONTRACT);
                                objEcustmst.setSTARTDATE_CONTRACT(dat.STARTDATE_CONTRACT);
                                objEcustmst.setENDDATE_CONTRACT(dat.ENDDATE_CONTRACT);

                                objEcustmst.CreateData(objEcustmst);
                            }

                            progress.dismiss();
                            String sDate = AppController.getInstance().getDateYYYYMMDD();
                            sDate = AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(sDate) + " " + AppController.getInstance().getCurrentTime();

                            AppController.getInstance().getSessionManager().putStringData(AppConstant.DATE_SYNC_OUTLET, sDate);

                            FillForm();

                            AppController.getInstance().CustomeDialog(SettingsActivity.this, getResources().getString(R.string.setting_sync_complete));
                        }else{
                            progress.dismiss();
                            AppController.getInstance().CustomeDialog(SettingsActivity.this,
                                    tblCustmst.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Custmst> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void SyncDataProduct(){
        Tbl_Karyawan tblMotoris;
        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        String KODECABANG = "";
        for(Tbl_Karyawan.Datum dat : tblMotoris.data){
            KODECABANG = dat.MITRA_ID;
        }

        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);


        try{
            Log.w("SyncProduct", "KODECABANG : " +  KODECABANG + " SALES_TYPE : " + AppConstant.iType + "  APPLICATION_TYPE : " +
                    AppConstant.strApplicationType);
            Call<Tbl_Master> call = NetworkManager.getNetworkService().MasterProductRev7(KODECABANG,
                    Integer.toString(AppConstant.iType),
                    AppConstant.strApplicationType);
            call.enqueue(new Callback<Tbl_Master>() {
                @Override
                public void onResponse(Call<Tbl_Master> call, Response<Tbl_Master> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMaster = response.body();
                        objEmaster = new Obj_MASTER();
                        objEmaster.DeleteMaster();
                        if (!tblMaster.error){
                            //Insert Master Product
                            for(Tbl_Master.Master datMaster : tblMaster.master){
                                Log.w("SyncProduct", "PCODE : " + datMaster.PCODE);
                                objEmaster = new Obj_MASTER();

                                objEmaster.setPCode(datMaster.PCODE);
                                objEmaster.setPCodeName(datMaster.PCODENAME);
                                objEmaster.setUnit1(datMaster.UNIT1);
                                objEmaster.setUnit2(datMaster.UNIT2);
                                objEmaster.setUnit3(datMaster.UNIT3);
                                objEmaster.setConvUnit2(datMaster.CONVUNIT2);
                                objEmaster.setConvUnit3(datMaster.CONVUNIT3);
                                objEmaster.setPrLin(datMaster.CATEGORY_ID);
                                objEmaster.setFLAG_SALES(datMaster.FLAG_SALES);
                                objEmaster.setPHOTO_NAME(datMaster.PHOTO_NAME);
                                objEmaster.setSellPrice1(datMaster.SELLPRICE);
                                objEmaster.setSellPrice2(datMaster.SELLPRICE);
                                objEmaster.setSellPrice3(datMaster.SELLPRICE);
                                objEmaster.CreateMaster(objEmaster);
                            }

                            for(Tbl_Master.MasterDispenser datMaster : tblMaster.master_dispenser){
                                objEmaster = new Obj_MASTER();
                                objEmaster.setPCode(datMaster.PCODEUCI);
                                objEmaster.setPCodeName(datMaster.PCODEUCINAME);
                                objEmaster.setFLAG_SALES("N");
                                objEmaster.setPHOTO_NAME(datMaster.PHOTO_NAME);
                                objEmaster.CreateMaster(objEmaster);
                            }

                            //Insert Product Line
                            for(Tbl_Master.ProductLine datPrlin : tblMaster.product_line){
                                objPrlin = new Obj_PRLIN();
                                objPrlin.setPRLIN(datPrlin.PRLIN);
                                objPrlin.setPRLINAME(datPrlin.PRLINAME);
                                objPrlin.setKOMPFLAG(datPrlin.KOMPLAG);
                                objPrlin.CreateDate(objPrlin);
                            }

                            String sDate = AppController.getInstance().getDateYYYYMMDD();
                            sDate = AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(sDate) + " " + AppController.getInstance().getCurrentTime();
                            AppController.getInstance().getSessionManager().putStringData(AppConstant.DATE_SYNC_PRODUCT, sDate);

                            FillForm();

                            AppController.getInstance().CustomeDialog(SettingsActivity.this, getResources().getString(R.string.setting_sync_complete));
                        }else{
                            progress.dismiss();
                            AppController.getInstance().CustomeDialog(SettingsActivity.this,
                                    tblMaster.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Master> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void SyncVersion(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_check_for_update), true);
        try{
            Call<Tbl_Version> call = NetworkManager.getNetworkService().CheckVersion(AppConstant.strMitraID,
                    AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_Version>() {
                @Override
                public void onResponse(Call<Tbl_Version> call, Response<Tbl_Version> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblVersion = response.body();
                        if (!tblVersion.error){
                            for(Tbl_Version.Datum dat : tblVersion.data){
                                String sMainVersion = BuildConfig.VERSION_NAME.replace(".","");
                                String sNewVersion = dat.VERSION_ID.replace(".","");

                                if (Long.parseLong(sNewVersion) > Long.parseLong(sMainVersion)){
                                    CustomeDialogSyncVersion(dat.VERSION_ID, dat.APK_NAME);
                                }else{
                                    AppController.getInstance().CustomeDialog(SettingsActivity.this, "Up to date");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Version> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void CustomeDialogSyncVersion(final String VERSION, final String APK_NAME){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText("Download new version : " + VERSION + " ?");
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
                Intent mIntent = new Intent(SettingsActivity.this, DownloadActivity.class);
                mIntent.putExtra("VERSION", VERSION);
                mIntent.putExtra("APK_NAME", APK_NAME);
                startActivity(mIntent);
            }
        });

        dialog.show();
    }


    void CustomeDialogSyncProduct(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.setting_sync_data_product));
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
                SyncDataProduct();
            }
        });

        dialog.show();
    }

    void CustomeDialogSyncOutlet(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.setting_sync_data_outlet));
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
                SyncOutlet();
            }
        });

        dialog.show();
    }


    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") ){
            return true;
        }else{
            if (objVisit.getTMGO() != null){
                if (objVisit.getTMBCK() == null || objVisit.getTMBCK().equals("")){
                    return false;
                }
            }
        }

        return true;
    }

    void GetDataOutletNoGPS(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);
        try{
            Call<Tbl_CustmstGen> call = NetworkManager.getNetworkService().ListOutletGen(AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_CustmstGen>() {
                @Override
                public void onResponse(Call<Tbl_CustmstGen> call, Response<Tbl_CustmstGen> response) {

                    int code = response.code();
                    if (code == 200){
                        tblCustmstGen = response.body();
                        if (!tblCustmstGen.error){
                            objCustmstGen = new Obj_CUSTMST_GEN();
                            objCustmstGen.DeleteData();
                            for (Tbl_CustmstGen.Datum dat : tblCustmstGen.data){
                                objCustmstGen = new Obj_CUSTMST_GEN();
                                objCustmstGen.setCUSTNO(dat.CUSTNO);
                                objCustmstGen.setLATITUDE(dat.LATITUDE);
                                objCustmstGen.setLONGITUDE(dat.LONGITUDE);
                                objCustmstGen.setSLSNO(dat.SLSNO);
                                objCustmstGen.setKODECABANG(dat.KODECABANG);
                                objCustmstGen.setTGL(dat.TGL);
                                objCustmstGen.CreateData(objCustmstGen);
                            }
                        }
                    }
                    progress.dismiss();
                }

                @Override
                public void onFailure(Call<Tbl_CustmstGen> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void SyncGoogleMaps(final Obj_CUSTMST_GEN objCustmst){
        /*progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);
*/
        Log.w("GenGPS", " 0 " + objCustmst.getLATITUDE() + "," + objCustmst.getLONGITUDE() );
        String sLatLng = objCustmst.getLATITUDE() + "," +objCustmst.getLONGITUDE();
        try{
            Call<ApiGoogleMaps> call = NetworkManager.getNetworkServiceGoogle(this).getMapsAPI(sLatLng
                    , "AIzaSyCvtMcDPUMGX_GwL23vTPNvYvAX1xYCIMw"
            );
            Log.w("GenGPS", " KEY " + "AIzaSyCvtMcDPUMGX_GwL23vTPNvYvAX1xYCIMw");
            call.enqueue(new Callback<ApiGoogleMaps>() {
                @Override
                public void onResponse(Call<ApiGoogleMaps> call, Response<ApiGoogleMaps> response) {
                    //progress.dismiss();
                    int code = response.code();
                    Log.w("GenGPS", " CODE " + code );
                    int iResult = 0;
                    if (code == 200){
                        apiGoogleMaps = response.body();
                        try{
                            if (apiGoogleMaps.status.equals("OK")){
                                Log.w("GenGPS", "Size " + apiGoogleMaps.results.size());

                                for(ApiGoogleMaps.Result dat: apiGoogleMaps.results){
                                    //Log.w("GenGPS", " Mulai " );
                                    if (iResult == 0){
                                        for(ApiGoogleMaps.AddressComponent addressComponent : dat.address_components){
                                            try{
                                                if (addressComponent.types.equals(adm1)){
                                                    GOOGLE_PROVINSI = addressComponent.short_name;
                                                }
                                            }catch (Exception e){
                                                GOOGLE_PROVINSI = "";
                                                Log.w("GenGPS", " GOOGLE_PROVINSI " );
                                            }


                                            try{
                                                if (addressComponent.types.equals(adm2)){
                                                    GOOGLE_KABUPATEN = addressComponent.short_name;
                                                }
                                            }catch (Exception e){
                                                GOOGLE_KABUPATEN = "";
                                                Log.w("GenGPS", " GOOGLE_KABUPATEN " );
                                            }


                                            try{
                                                if (addressComponent.types.equals(adm3)){
                                                    GOOGLE_KECAMATAN = addressComponent.short_name;
                                                }
                                            }catch (Exception e){
                                                GOOGLE_KECAMATAN = "";
                                                Log.w("GenGPS", " GOOGLE_KECAMATAN " );
                                            }


                                            try{
                                                if (addressComponent.types.equals(adm4)){
                                                    GOOGLE_KELURAHAN = addressComponent.short_name;
                                                }
                                            }catch (Exception e){
                                                GOOGLE_KELURAHAN = "";
                                                Log.w("GenGPS", " GOOGLE_KELURAHAN " );
                                            }


                                            try{
                                                if (addressComponent.types.equals(postalCode)){
                                                    GOOGLE_KODEPOS = addressComponent.short_name;
                                                }
                                            }catch (Exception e){
                                                GOOGLE_KODEPOS = "";
                                                Log.w("GenGPS", " GOOGLE_KODEPOS " );
                                            }

                                        }
                                        try{
                                            GOOGLE_ALAMAT = dat.formatted_address;
                                        }catch (Exception e){
                                            GOOGLE_ALAMAT = "";
                                        }


                                        Log.w("GenGPS", " UpdateDB" );
                                        objCustmstGen = new Obj_CUSTMST_GEN();
                                        objCustmstGen.setKODECABANG(objCustmst.getKODECABANG());
                                        objCustmstGen.setSLSNO(objCustmst.getSLSNO());
                                        objCustmstGen.setTGL(objCustmst.getTGL());
                                        objCustmstGen.setCUSTNO(objCustmst.getCUSTNO());
                                        objCustmstGen.setALAMAT((GOOGLE_ALAMAT == null ? "" : GOOGLE_ALAMAT));
                                        objCustmstGen.setKABUPATEN(GOOGLE_KABUPATEN == null ? "" : GOOGLE_KABUPATEN);
                                        objCustmstGen.setKECAMATAN(GOOGLE_KECAMATAN == null ? "" : GOOGLE_KECAMATAN);
                                        objCustmstGen.setKELURAHAN(GOOGLE_KELURAHAN == null ? "" : GOOGLE_KELURAHAN);
                                        objCustmstGen.setKODEPOS(GOOGLE_KODEPOS == null ? "" : GOOGLE_KODEPOS);
                                        objCustmstGen.setPROVINSI(GOOGLE_PROVINSI == null ? "" : GOOGLE_PROVINSI);
                                        objCustmstGen.UpdateData(objCustmstGen);
                                    }
                                    iResult = 1;

                                }


                            }else{
                                objCustmstGen = new Obj_CUSTMST_GEN();
                                objCustmstGen.setKODECABANG(objCustmst.getKODECABANG());
                                objCustmstGen.setSLSNO(objCustmst.getSLSNO());
                                objCustmstGen.setTGL(objCustmst.getTGL());
                                objCustmstGen.setCUSTNO(objCustmst.getCUSTNO());
                                objCustmstGen.setALAMAT("NA");
                                objCustmstGen.setKABUPATEN("NA");
                                objCustmstGen.setKECAMATAN("NA");
                                objCustmstGen.setKELURAHAN("NA");
                                objCustmstGen.setKODEPOS("NA");
                                objCustmstGen.setPROVINSI("NA");
                                objCustmstGen.UpdateData(objCustmstGen);

                            }

                            Obj_CUSTMST_GEN custmstGen = new Obj_CUSTMST_GEN();
                            if (custmstGen.getDataList().size() > 0){
                                custmstGen = new Obj_CUSTMST_GEN();
                                custmstGen = custmstGen.getDataGPS();
                                SyncGoogleMaps(custmstGen);
                            }else{
                                AppController.getInstance().CustomeDialog(SettingsActivity.this,
                                        getResources().getString(R.string.setting_sync_complete));
                            }
                        }catch (Exception e){
                            Log.w("GenGPS", " Error " +  e.getMessage() );
                        }

                    }else{
                        //AppController.getInstance().CustomeDialog(AddOutletActivity.this, apiGoogleMaps.status);
                        Log.w("GenGPS", " 1" + apiGoogleMaps.status );
                    }
                }

                @Override
                public void onFailure(Call<ApiGoogleMaps> call, Throwable t) {
                    //progress.dismiss();
                    Log.w("GenGPS", " 2" +  t.getMessage() );
                }
            });
        }catch (Exception e){
            Log.w("GenGPS", " 3" +  e.getMessage() );
            //progress.dismiss();
        }
    }


    void SendDataToServer(RequestBody sData){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);
        try{
            Call<Tbl_SendDataCustNotGPS> call = NetworkManager.getNetworkService().GenerateDataCustNotGPS(sData);
            call.enqueue(new Callback<Tbl_SendDataCustNotGPS>() {
                @Override
                public void onResponse(Call<Tbl_SendDataCustNotGPS> call, Response<Tbl_SendDataCustNotGPS> response) {
                    progress.dismiss();
                    int code = response.code();
                    if(code == 200){
                        tblSendDataCustNotGPS = response.body();
                        if (!tblSendDataCustNotGPS.error){
                            Obj_CUSTMST_GEN custmstGen = new Obj_CUSTMST_GEN();
                            custmstGen.DeleteData();
                            AppController.getInstance().CustomeDialog(SettingsActivity.this, tblSendDataCustNotGPS.message);
                        }else{
                            AppController.getInstance().CustomeDialog(SettingsActivity.this, tblSendDataCustNotGPS.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_SendDataCustNotGPS> call, Throwable t) {
                    progress.dismiss();
                    AppController.getInstance().CustomeDialog(SettingsActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){
            progress.dismiss();
            AppController.getInstance().CustomeDialog(SettingsActivity.this, e.getMessage());
        }

    }
}
