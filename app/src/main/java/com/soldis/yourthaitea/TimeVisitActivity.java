
package com.soldis.yourthaitea;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.entity.Obj_COLLECTION;
import com.soldis.yourthaitea.entity.Obj_COMPLAINT;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_DISPENSER;
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_MAINTENANCE;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_PRODUCT_CATEGORY;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.ApiGoogleMaps;
import com.soldis.yourthaitea.model.Obj_SendData;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Visit;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.DailySalesmanActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/21/2017.
 */

public class TimeVisitActivity extends AppCompatActivity {
    TextView txtTimeGo,
            txtTimeEnd,
            txtWktPergi,
            txtWktPulang,
            txtTglPergi,
            txtTglPulang,
            txtCheckIn,
            txtCheckOut

    ;
    Obj_VISIT objVisit;

    Tbl_Visit tblVisit;
    ProgressDialog progress;

    Obj_SendData objSendData;

    Tbl_Custmst tblCustmst;
    ApiGoogleMaps apiGoogleMaps;
    String GOOGLE_PROVINSI,
            GOOGLE_KABUPATEN,
            GOOGLE_KECAMATAN,
            GOOGLE_KELURAHAN,
            GOOGLE_KODEPOS,
            GOOGLE_ALAMAT
                    ;

    ArrayList<String> adm1  = new ArrayList<String>();
    ArrayList<String> adm2  = new ArrayList<String>();
    ArrayList<String> adm3  = new ArrayList<String>();
    ArrayList<String> adm4  = new ArrayList<String>();
    ArrayList<String> postalCode  = new ArrayList<String>();

    boolean bPhoto, bStart;

    Obj_CUSTMST objEcustmst;

    TextView txtTgl, txtName;
    String sPhotoName;
    ImageView imgAvatar;
    RelativeLayout layout_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_timevisit);
        bStart = true;
        InitControl();

        AppConstant.API_KEY_GOOGLE_MAPS = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                AppConstant.API_KEY_GOOGLE_MAPS = "";

                if (dat.PHOTO != null && !dat.PHOTO.equals("")){
                    AppController.getInstance().displayImage(TimeVisitActivity.this , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, imgAvatar);
                }else{
                    imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                }

                txtName.setText(dat.SLSNAME);
            }
        }

        FillForm();
    }

    void InitControl() {
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtName = (TextView)findViewById(R.id.txtName);
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        txtTimeGo = (TextView) findViewById(R.id.txtTimeGo);
        txtTimeEnd = (TextView) findViewById(R.id.txtTimeEnd);

        txtWktPergi = (TextView) findViewById(R.id.txtWaktuPergi);
        txtWktPulang = (TextView) findViewById(R.id.txtWaktuPulang);
        txtTglPergi = (TextView) findViewById(R.id.txtTglPergi);
        txtTglPulang = (TextView) findViewById(R.id.txtTglPulang);
        txtCheckIn = (TextView) findViewById(R.id.txtCheckin);
        txtCheckOut = (TextView) findViewById(R.id.txtCheckout);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtTimeGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    if (!bStart){
                        /*Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                        if (objCustmst.TotalOut() > 0){
                            CustomeDialog(getResources().getString(R.string.tv_do_you_starvisit),true);
                        }else{
                            AppController.getInstance().CustomeDialog(TimeVisitActivity.this,
                                    "Sync data oulet terlebih dahulu");
                        }*/

                        CustomeDialog(getResources().getString(R.string.tv_do_you_starvisit),true);

                    }
                }else{
                    CustomeDialog(getResources().getString(R.string.text_no_connection));
                }

            }
        });

        txtTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    if (bStart){
                        objEcustmst = new Obj_CUSTMST();
                        ArrayList<Obj_CUSTMST> objCustmsts = new ArrayList<Obj_CUSTMST>();
                        objCustmsts = objEcustmst.getDataListNotComplete();
                        if (objCustmsts.size() > 0){
                            CustomeDialogNotComplete(getResources().getString(R.string.setting_transaction_not_complete));
                        }else{
                            CustomeDialog(getResources().getString(R.string.tv_do_you_endvisit),false);
                        }

                    }
                }else{
                    CustomeDialog(getResources().getString(R.string.text_no_connection));
                }
            }
        });
    }

    void FillForm(){
        objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        txtTglPergi.setText("");
        txtTglPulang.setText("");
        txtWktPergi.setText("");
        txtWktPulang.setText("");

        txtTimeGo.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_blue_selected));
        txtTimeEnd.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_grey_dark));

        /*if (objVisit.getTMBCK() == null || objVisit.getTMBCK().equals(""))
            btnEnd.setVisibility(View.VISIBLE);*/

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("")){
            bStart = false;
            //btnGo.setVisibility(View.VISIBLE);
            //btnEnd.setVisibility(View.INVISIBLE);
        }

        if (objVisit.getTMGO() != null && !objVisit.getTMGO().equals("")){
            bStart = true;
            //txtTimeGo.setText(objVisit.getTMGO());
            txtCheckIn.setText(objVisit.getTMGO());
            txtCheckOut.setText("-");
            txtTglPergi.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
            txtWktPergi.setText(objVisit.getTMGO());

            txtTimeGo.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_grey_dark));
            txtTimeEnd.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_blue_selected));
            //btnGo.setVisibility(View.INVISIBLE);
        }

        if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("")){
            //txtTimeGo.setText("");
            txtCheckIn.setText("-");
            txtTimeEnd.setText("");

            txtTglPergi.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
            txtTglPulang.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(objVisit.getTGL_TRX()));
            txtWktPergi.setText(objVisit.getTMGO());
            txtWktPulang.setText(objVisit.getTMBCK());

            txtCheckIn.setText(objVisit.getTMGO());
            txtCheckOut.setText(objVisit.getTMBCK());

            //btnGo.setVisibility(View.VISIBLE);
            //btnEnd.setVisibility(View.INVISIBLE);

            txtTimeGo.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_blue_selected));
            txtTimeEnd.setBackground(getResources().getDrawable(R.drawable.btn_shape_all_grey_dark));
            bStart = false;

        }

        txtTgl.setText("Tanggal transaksi : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));

    }

     void CustomeDialogStart(String ISI, final boolean bSTART){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_startvisit_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        TextView txtValueStock = (TextView) dialog.findViewById(R.id.txtValueStock);

        Obj_STOCK objStock = new Obj_STOCK();

        double dValueStock = 0;
        for (Obj_STOCK dat : objStock.getDataList()){
            dValueStock += (dat.getSTOCK() * dat.getSELLPRICE1());
        }

        txtValueStock.setText(AppController.getInstance().toCurrencyRp(dValueStock));

        txtDialog.setText(ISI);
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
                if (bSTART){
                    //Start Visit
                    SyncStartVisit();
                }else{
                    //End Visit
                    /*Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                    ArrayList<Obj_CUSTMST> objCustmsts = new ArrayList<>();
                    objCustmsts = objCustmst.getDataListFlagKirim("N");
                    if (objCustmsts.size() > 0){
                        SyncUploadPhoto();
                    }else{
                        SyncEndVisit();
                    }*/

                    SyncEndVisit();
                }

                FillForm();

            }
        });

        dialog.show();
    }

    void CustomeDialog(String ISI, final boolean bSTART){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_endvisit_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        final EditText edtPassword = (EditText) dialog.findViewById(R.id.edt_password);
        txtDialog.setText(ISI);
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
                if (bSTART){
                    //Start Visit
                    String PASSWORD = edtPassword.getText().toString();
                    if (AppConstant.strSlsPass.equals(PASSWORD)){
                        SyncStartVisit();
                    }else{
                        AppController.getInstance().CustomeDialog(TimeVisitActivity.this, getResources().getString(R.string.error_incorrect_password));
                    }
                }else{
                    //End Visit
                    try{
                        String PASSWORD = edtPassword.getText().toString();
                        if (AppConstant.strSlsPass.equals(PASSWORD)){
                            SyncEndVisit();
                        }else{
                            AppController.getInstance().CustomeDialog(TimeVisitActivity.this, getResources().getString(R.string.error_incorrect_password));
                        }
                    }catch (Exception e){
                        AppController.getInstance().CustomeDialog(TimeVisitActivity.this, e.getMessage());
                    }

                }

                FillForm();

            }
        });

        dialog.show();
    }


    void CustomeDialogNotComplete(String ISI){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        txtDialog.setText(ISI);
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
                Intent mIntent = new Intent(TimeVisitActivity.this, DailySalesmanActivity.class);
                startActivity(mIntent);
                finish();

            }
        });

        dialog.show();
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

    void SyncUploadPhoto(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.tv_sync_photo), true);
        bPhoto = false;
        try{
            Obj_CUSTMST objCustmst = new Obj_CUSTMST();
            ArrayList<Obj_CUSTMST> objCustmsts = new ArrayList<>();
            objCustmsts = objCustmst.getDataListFlagKirim("N");
            if (objCustmsts.size() > 0){
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);

                int iIndex = 0;
                for (Obj_CUSTMST dat : objCustmsts){
                    File file = new File(AppConstant.PATH_PHOTO + "/" + dat.getPHOTO_NAME());

                    if (file.exists()){
                        builder.addFormDataPart("files["+iIndex+"]",file.getName(),RequestBody.create(MediaType.parse("image/*"), file));
                        if (dat.getPHOTO_NAME() != null && !dat.getPHOTO_NAME().equals(""))
                        bPhoto = true;
                    }

                    iIndex += 1;
                }

                MultipartBody requestBody = builder.build();
                final Tbl_Master tblMaster;

                try{

                    Call<Tbl_Master> call = NetworkManager.getNetworkService().UploadData(requestBody);
                    call.enqueue(new Callback<Tbl_Master>() {
                        @Override
                        public void onResponse(Call<Tbl_Master> call, Response<Tbl_Master> response) {
                            int code = response.code();
                            progress.dismiss();
                            if (code == 200){
                                SyncEndVisit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Tbl_Master> call, Throwable t) {
                            progress.dismiss();
                            if (bPhoto){
                                AppController.getInstance().CustomeDialog(TimeVisitActivity.this,
                                          "Jaringan internet tidak stabil \n" + t.getMessage());
                            }else{
                                SyncEndVisit();
                            }

                        }
                    });
                }catch (Exception e){
                    progress.dismiss();
                    SyncEndVisit();
                    Log.w("Error", e.getMessage());
                }

            }
        }catch (Exception e){
            progress.dismiss();
            SyncEndVisit();
        }

    }

    void SyncStartVisit(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.tv_sync_start), true);

        try{
            objSendData = new Obj_SendData();
            JSONObject jsonObject = new JSONObject();
            jsonObject = objSendData.sendData_StartVisit();
            String sJson = jsonObject.toString();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(sJson.toString()));
            Call<Tbl_Visit> call = NetworkManager.getNetworkService().StartVisit(body);
            call.enqueue(new Callback<Tbl_Visit>() {
                @Override
                public void onResponse(Call<Tbl_Visit> call, Response<Tbl_Visit> response) {
                    int code = response.code();

                    if (code == 200){
                        tblVisit = response.body();
                        if (!tblVisit.error){
                            objVisit = new Obj_VISIT();
                            objVisit.DeleteData();
                            objVisit.setTMGO(tblVisit.tmgo);
                            objVisit.CreateTimeStartVisit(objVisit);

                            Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                            objCustcard.DeleteData();

                            Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
                            objCustcard1.DeleteData();

                            Obj_DISPENSER objDispenser = new Obj_DISPENSER();
                            objDispenser.DeleteData();


                            Obj_TRX_D objTrxD = new Obj_TRX_D();
                            objTrxD.DeleteData();

                            Obj_TRX_H objTrxH = new Obj_TRX_H();
                            objTrxH.DeleteData();

                            Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                            objCustmst.UpdateFlagPJP();

                            Obj_KAS objKas = new Obj_KAS();
                            objKas.DeleteData();

                            for(Tbl_Visit.Rute dat : tblVisit.rute){
                                objCustmst = new Obj_CUSTMST();
                                objCustmst.UpdateFlagPJP(dat.CUSTNO);
                            }

                            Obj_MOTORIS objMotoris = new Obj_MOTORIS();
                            objMotoris.setTRXNO(Double.parseDouble(tblVisit.trxno));
                            objMotoris.setSEQNO(Integer.parseInt(tblVisit.trxno));
                            objMotoris.setTRX_DATE(tblVisit.tgltrx);

                            for(Tbl_Visit.Datum dat : tblVisit.data){
                                objMotoris.setTARGET_EC(0);
                                objMotoris.setTARGET_EC_MTD(0);
                                objMotoris.setTARGET_SALES(0);
                                objMotoris.setTARGET_SALES_MTD(0);
                                if (dat.TARGET_EC != null){
                                    objMotoris.setTARGET_EC(Integer.parseInt(dat.TARGET_EC));
                                }

                                if (dat.TARGET_EC_MTD != null){
                                    objMotoris.setTARGET_EC_MTD(Integer.parseInt(dat.TARGET_EC_MTD));
                                }

                                if (dat.TARGET_SALES != null){
                                    objMotoris.setTARGET_SALES(Double.parseDouble(dat.TARGET_SALES));
                                }

                                if (dat.TARGET_SALES_MTD != null){
                                    objMotoris.setTARGET_SALES_MTD(Double.parseDouble(dat.TARGET_SALES_MTD));
                                }


                                if (dat.TOTAL_EC != null){
                                    objMotoris.setTOTAL_EC(Integer.parseInt(dat.TOTAL_EC));
                                }else{
                                    objMotoris.setTOTAL_EC(0);
                                }

                                if (dat.TOTAL_SALES != null) {
                                    objMotoris.setTOTAL_SALES(Double.parseDouble(dat.TOTAL_SALES));
                                }else{
                                    objMotoris.setTOTAL_SALES(0);
                                }

                                if (dat.TOTAL_EC != null) {
                                    objMotoris.setTOTAL_EC(Integer.parseInt(dat.TOTAL_EC));
                                }else{
                                    objMotoris.setTOTAL_EC(0);
                                }

                                objMotoris.setTARGET_CALL(Integer.parseInt(dat.TARGET_CALL));
                                objMotoris.setTARGET_CALL_MTD(Integer.parseInt(dat.TARGET_CALL_MTD));

                            }

                            Obj_TYPEOUT objTypeout = new Obj_TYPEOUT();
                            if (tblVisit.typeout.size() > 0){
                                objTypeout.DeleteData();

                                for (Tbl_Visit.Typeout dat : tblVisit.typeout ){
                                    objTypeout = new Obj_TYPEOUT();
                                    objTypeout.setTYPEOUT(dat.TYPEOUT);
                                    objTypeout.setTYPENAME(dat.TYPENAME);
                                    objTypeout.setGROUP_CODE(dat.GROUP_CODE);
                                    objTypeout.setGROUP_NAME(dat.GROUP_NAME);
                                    objTypeout.CreateData(objTypeout);
                                }
                            }



                            objMotoris.UpdateTrxNoSeqNo(objMotoris);

                            Obj_PRODUCT_CATEGORY productCategory = new Obj_PRODUCT_CATEGORY();
                            if(tblVisit.category.size() > 0){
                                productCategory.DeleteData();
                                for (Tbl_Visit.Category dat : tblVisit.category ){
                                    productCategory = new Obj_PRODUCT_CATEGORY();
                                    productCategory.setCATEGORY_ID(dat.CATEGORY_ID);
                                    productCategory.setCATEGORY_NAME(dat.CATEGORY_NAME);
                                    productCategory.CreateDate(productCategory);
                                }
                            }

                            if (tblVisit.product.size() > 0){
                                Obj_MASTER objMaster = new Obj_MASTER();
                                objMaster.DeleteMaster();
                                for(Tbl_Visit.Product dat : tblVisit.product){
                                    objMaster = new Obj_MASTER();
                                    objMaster.setPCode(dat.PCODE);
                                    objMaster.setPCodeName(dat.PCODENAME);
                                    objMaster.setPHOTO_NAME(dat.PHOTO_NAME);
                                    objMaster.setFLAG_SALES(dat.FLAG_SALES);
                                    objMaster.setConvUnit2(dat.CONVUNIT2);
                                    objMaster.setConvUnit3(dat.CONVUNIT3);
                                    objMaster.setUnit1(dat.UNIT1);
                                    objMaster.setUnit2(dat.UNIT2);
                                    objMaster.setUnit3(dat.UNIT3);
                                    objMaster.setSellPrice1(dat.SELLPRICE);
                                    objMaster.setSellPrice2(dat.SELLPRICE);
                                    objMaster.setSellPrice3(dat.SELLPRICE);
                                    objMaster.setPrLin(dat.CATEGORY_ID);
                                    objMaster.CreateMaster(objMaster);
                                }
                            }

                            //Get Parameter--------------------------------------------------------------------
                            objMotoris = new Obj_MOTORIS();
                            objMotoris = objMotoris.getData(AppConstant.strSlsNo);
                            if (objMotoris.getSLSNO() != null) AppConstant.strTglTrx = objMotoris.getTRX_DATE();


                            Obj_STOCK objStock = new Obj_STOCK();

                            if (tblVisit.stock.size() > 0) objStock.DeleteData();
                            for (Tbl_Visit.Stock dat : tblVisit.stock){
                                objStock = new Obj_STOCK();

                                objStock.setTGL_TRX(AppConstant.strTglTrx);
                                objStock.setPCODE(dat.PCODE);
                                objStock.setSTOCK(dat.QTY);
                                objStock.setTIMEIN(AppController.getInstance().getCurrentTime());
                                objStock.setFLAG_IN("A");
                                objStock.CreateDate(objStock);
                            }

                            progress.dismiss();

                            //Clear Log GPS---------------------------------------------------------------------
                            AppController.getInstance().getSessionManager().setListJourney(null);

                            AppController.getInstance().getSessionManager().putStringData(AppConstant.START_ON, "YES");
                            finish();
                        }else{
                            CustomeDialog(tblVisit.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Visit> call, Throwable t) {
                    AppController.getInstance().CustomeDialog(TimeVisitActivity.this,
                            "Jaringan internet tidak stabil");
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void SyncEndVisit(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.tv_sync_end), true);
        objSendData = new Obj_SendData();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject = objSendData.sendDataAll(AppController.getInstance().getCurrentTime());
            String sJson = jsonObject.toString();
            AppController.getInstance().writeLogTrx(sJson);
            Log.w("jsonFinish", sJson);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(sJson.toString()));
            //writeLog(sJson);
            Call<Obj_SendData>call = NetworkManager.getNetworkService().postTransacationWithCustCardCusMst(body);
            call.enqueue(new Callback<Obj_SendData>() {
                @Override
                public void onResponse(Call<Obj_SendData> call, Response<Obj_SendData> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        objSendData = response.body();
                        if (!objSendData.error){
                            objVisit = new Obj_VISIT();
                            objVisit.setTMBCK(objSendData.tmbck);
                            objVisit.setTGL_TRX(objSendData.tglback);
                            objVisit.CreateTimeBackVisit(objVisit);

                            Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                            objCustmst.UpdateDataFlagKirim();


                            Obj_MAINTENANCE objMaintenance = new Obj_MAINTENANCE();
                            objMaintenance.DeleteData();

                            Obj_COLLECTION objCollection = new Obj_COLLECTION();
                            objCollection.DeleteData();

                            Obj_COMPLAINT objComplaint = new Obj_COMPLAINT();
                            objComplaint.DeleteData();

                            AppController.getInstance().getSessionManager().putStringData(AppConstant.START_ON, null);
                            FillForm();
                        }else{
                            AppController.getInstance().CustomeDialog(TimeVisitActivity.this,
                                    objSendData.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Obj_SendData> call, Throwable t) {
                    //writeLog("Respond API : " + t.getMessage());
                    AppController.getInstance().CustomeDialog(TimeVisitActivity.this,
                            "Jaringan internet tidak stabil");
                    progress.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            progress.dismiss();
        }
    }


}
