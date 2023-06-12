package com.soldis.yourthaitea.transaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.TimeVisitActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.adapter.AdapterDailySalesman;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/11/2017.
 */

public class DailySalesmanActivity extends AppCompatActivity {
    static int REQ_CODE_KELURAHAN = 100;
    static int REQ_CODE_SORT = 200;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    RelativeLayout  lyFilter, layout_back;

    LinearLayout lySearch, lySearchDetail, lyKelurahan,
                lyBatal, lyCari, lySync,
                lyPjp, lyNonPjp
    ;
    TextView  txtKelurahan, txtSync, txtTotalKunjungan, txtTotalEC, txtNotEc,
            txtDate, txtPjp, txtNonPjp
    ;
    EditText edtKeyWord, edtSearch;

    ProgressDialog progress;
    Tbl_Custmst tblCustmst;

    FloatingActionButton fabAdd;

    View vwPjp, vwNonPjp;
    String FLAG_PJP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_daily_motorist);
        FLAG_PJP = "Y";
        InitControl();
        AppController.getInstance().hideKeyboardFrom(DailySalesmanActivity.this);
        AppController.getInstance().OpenDatabase(DailySalesmanActivity.this);

        AppConstant.bRefresh = false;
        FillGrid("","");

    }

    private void InitControl(){
        txtPjp = (TextView)findViewById(R.id.txtPjp);
        txtNonPjp = (TextView)findViewById(R.id.txtNonPjp);
        lyFilter = (RelativeLayout)findViewById(R.id.layout_filter);
        lySearch = (LinearLayout)findViewById(R.id.layout_search);
        lySearchDetail = (LinearLayout)findViewById(R.id.layout_search_detail);
        lyKelurahan = (LinearLayout)findViewById(R.id.layout_kelurahan);
        lyBatal = (LinearLayout)findViewById(R.id.layout_batal);
        lyCari = (LinearLayout)findViewById(R.id.layout_cari);
        lySync = (LinearLayout)findViewById(R.id.layout_sync);
        lyPjp = (LinearLayout)findViewById(R.id.layout_pjp);
        lyNonPjp = (LinearLayout)findViewById(R.id.layout_non_pjp);

        lySync.setVisibility(View.GONE);

        vwPjp = (View)findViewById(R.id.vwPJP);
        vwNonPjp = (View)findViewById(R.id.vwNonPJP);
        vwNonPjp.setVisibility(View.GONE);

        edtKeyWord = (EditText)findViewById(R.id.edt_keyword);
        edtSearch = (EditText)findViewById(R.id.edtSearch);
        txtKelurahan = (TextView)findViewById(R.id.txtKelurahan);
        txtSync = (TextView)findViewById(R.id.txtSync);
        txtTotalKunjungan = (TextView)findViewById(R.id.txtTotalKunjungan);
        txtTotalEC = (TextView)findViewById(R.id.txtTotalEC);
        txtNotEc = (TextView)findViewById(R.id.txtNotEc);
        txtDate = (TextView)findViewById(R.id.textDate);

        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        lySearch.setVisibility(View.VISIBLE);
        lySearchDetail.setVisibility(View.GONE);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx) );

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    //fabAdd.hide();
                } else{
                    //fabAdd.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VisitValidation()){
                    GPSTracker gps;
                    // create class object
                    gps = new GPSTracker(getBaseContext());

                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled

                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        Intent mIntent = new Intent(getBaseContext(), CustcardActivity.class);
                        startActivity(mIntent);
                    }else{
                        gps.showSettingsAlert();
                    }
                }else{
                    Intent mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }


            }
        });

        lyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), DialogSortBy.class);
                startActivityForResult(mIntent, REQ_CODE_SORT);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    String KeyWord = edtSearch.getText().toString().trim();
                    if (KeyWord.equals("")) KeyWord = "0";
                    FillGrid(KeyWord,"");
                }

                return false;
            }
        });

        lyBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtKeyWord.setText("");
                lySearch.setVisibility(View.VISIBLE);
                lySearchDetail.setVisibility(View.GONE);
            }
        });

        lyKelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), ListKelurahan.class);
                startActivityForResult(mIntent, REQ_CODE_KELURAHAN);
            }
        });

        txtSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    SyncOutlet();
                }else{
                    AppController.getInstance().CustomeDialog(DailySalesmanActivity.this,"Tidak ada koneksi internet");
                }
            }
        });

        lyPjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FLAG_PJP = "Y";
                FillGrid("","");
                vwPjp.setVisibility(View.VISIBLE);
                vwNonPjp.setVisibility(View.GONE);
            }
        });

        lyNonPjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FLAG_PJP = "";
                FillGrid("","");
                vwNonPjp.setVisibility(View.VISIBLE);
                vwPjp.setVisibility(View.GONE);
            }
        });


        objEcustmst = new Obj_CUSTMST();
        if (objEcustmst.TotalOutPJP() > 0){
            txtPjp.setText("  (" + AppController.getInstance().toCurrency(objEcustmst.TotalOutPJP()) + ")");
        }

        if (objEcustmst.TotalOutNonPJP() > 0){
            txtNonPjp.setText("  (" +AppController.getInstance().toCurrency(objEcustmst.TotalOutNonPJP()) + ")");
        }
    }


    public void FillGrid(String KEYWORD, String KELURAHAN){
        progress = ProgressDialog.show(this, "Informasi",
                "Sinkronisasi data", true);

        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();

        KELURAHAN = "SEMUA";
        objEcustmsts = objEcustmst.getDataList(KEYWORD,KELURAHAN, FLAG_PJP);

        mAdapter = new AdapterDailySalesman(this, objEcustmsts, new AdapterDailySalesman.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String TYPENAME, String PHOTONAME) {

                if (VisitValidation()){
                    GPSTracker gps;
                    // create class object
                    gps = new GPSTracker(getBaseContext());

                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled
                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
                        objCustcard1 = objCustcard1.getData(CUSTNO);
                        if (objCustcard1.getCUSTNO() != null && !objCustcard1.getCUSTNO().equals("")){
                            Intent mIntent = new Intent(getBaseContext(), CustcardActivity.class);
                            mIntent.putExtra("CUSTNO", CUSTNO);
                            mIntent.putExtra("CUSTNAME", CUSTNAME);
                            mIntent.putExtra("ADDRESS", ADDRESS);
                            mIntent.putExtra("TYPENAME", TYPENAME);
                            mIntent.putExtra("PHOTONAME", PHOTONAME);
                            mIntent.putExtra("STATUS", false);
                            startActivity(mIntent);
                        }else{
                            CustomeDialog(CUSTNO, CUSTNAME, ADDRESS, TYPENAME);
                        }


                    }else{
                        gps.showSettingsAlert();
                    }
                }else{
                    Intent mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        Obj_TRX_H objTrxH = new Obj_TRX_H();
        double dSales = 0;
        for (Obj_TRX_H dat : objTrxH.getDataListDashboard()){
            dSales += dat.getINVAMOUNT();
        }

        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        int TotalEC = objCustcard1.getDataListEC().size();

        txtTotalKunjungan.setText(AppController.getInstance().toCurrency(objCustcard1.getDataList().size()));
        txtTotalEC.setText(AppController.getInstance().toCurrency(TotalEC));
        txtNotEc.setText(AppController.getInstance().toCurrency(objCustcard1.getDataList().size() - TotalEC));

        AppConstant.bRefresh = false;
        progress.dismiss();
    }

    void FillGridTransaction(boolean TRANSACTION){
        progress = ProgressDialog.show(this, "Informasi",
                "Sinkronisasi data", true);

        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();

        objEcustmsts = objEcustmst.getDataListTransaction(TRANSACTION);

        mAdapter = new AdapterDailySalesman(this, objEcustmsts, new AdapterDailySalesman.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String TYPENAME, String PHOTO_NAME) {

                if (VisitValidation()){
                    GPSTracker gps;
                    // create class object
                    gps = new GPSTracker(getBaseContext());

                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled
                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        Intent mIntent = new Intent(getBaseContext(), CustcardActivity.class);
                        mIntent.putExtra("CUSTNO", CUSTNO);
                        mIntent.putExtra("CUSTNAME", CUSTNAME);
                        mIntent.putExtra("ADDRESS", ADDRESS);
                        mIntent.putExtra("TYPENAME", TYPENAME);
                        mIntent.putExtra("PHOTONAME", PHOTO_NAME);
                        mIntent.putExtra("STATUS", false);
                        startActivity(mIntent);
                    }else{
                        gps.showSettingsAlert();
                    }
                }else{
                    Intent mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        /*Obj_TRX_H objTrxH = new Obj_TRX_H();

        if (objTrxH.getDataListTrx().size() > 0){
            txtTotalKunjungan.setText(AppController.getInstance().toCurrency(objTrxH.getDataListTrx().size()));
        }else{
            txtTotalKunjungan.setText("0");
        }*/

        progress.dismiss();
    }

    void FillGridNotComplete(){
        progress = ProgressDialog.show(this, "Informasi",
                "Sinkronisasi data", true);

        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();

        objEcustmsts = objEcustmst.getDataListNotComplete();

        mAdapter = new AdapterDailySalesman(this, objEcustmsts, new AdapterDailySalesman.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String TYPENAME, String PHOTO_NAME) {

                if (VisitValidation()){
                    GPSTracker gps;
                    // create class object
                    gps = new GPSTracker(getBaseContext());

                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled
                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        Intent mIntent = new Intent(getBaseContext(), CustcardActivity.class);
                        mIntent.putExtra("CUSTNO", CUSTNO);
                        mIntent.putExtra("CUSTNAME", CUSTNAME);
                        mIntent.putExtra("ADDRESS", ADDRESS);
                        mIntent.putExtra("TYPENAME", TYPENAME);
                        mIntent.putExtra("PHOTONAME", PHOTO_NAME);
                        mIntent.putExtra("STATUS", false);
                        startActivity(mIntent);
                    }else{
                        gps.showSettingsAlert();
                    }
                }else{
                    Intent mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        /*Obj_TRX_H objTrxH = new Obj_TRX_H();

        if (objTrxH.getDataListTrx().size() > 0){
            txtTotalKunjungan.setText(AppController.getInstance().toCurrency(objTrxH.getDataListTrx().size()));
        }else{
            txtTotalKunjungan.setText("0");
        }*/

        progress.dismiss();
    }


    void SyncOutlet(){
        progress = ProgressDialog.show(this, "Informasi",
                "Sinkronisasi data", true);
        try{
            Call<Tbl_Custmst> call = NetworkManager.getNetworkService().ListOutlet(AppConstant.strMitraID,
                    AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_Custmst>() {
                @Override
                public void onResponse(Call<Tbl_Custmst> call, Response<Tbl_Custmst> response) {
                    int code = response.code();
                    if(code == 200){
                        tblCustmst = response.body();
                        if (!tblCustmst.error){
                            objEcustmst = new Obj_CUSTMST();
                            objEcustmst.DeleteData();
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
                            FillGrid("","");
                        }else{
                            progress.dismiss();

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


    @Override
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQ_CODE_KELURAHAN){
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                String KELURAHAN = res.getString("KELURAHAN");
                txtKelurahan.setText(KELURAHAN);

            }
        }else if (requestCode == REQ_CODE_SORT){
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                String SORTBY = res.getString("SORTBY");
                switch (SORTBY){
                    case "ALL":
                        FillGrid("","");
                        break;
                    case "TRANSACTION":
                        FillGridTransaction(true);
                        break;
                    case "NO_TRANSACTION":
                        FillGridTransaction(false);
                        break;
                    case "NOT_COMPLETE":
                        FillGridNotComplete();
                        break;

                }

            }
        }

    }

    private void DisplayDialogGPS(String title,String msg)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(msg);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (AppConstant.bRefresh)
        FillGrid("","");
    }

    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") )
            return false;

        if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") )
            return false;

        return true;
    }

    void CustomeDialog(final String CUSTNO, final String CUSTNAME, final String ADDRESS, final String TYPENAME){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialogkunjungan_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtName = (TextView)dialog.findViewById(R.id.txtName);
        TextView txtAddress = (TextView)dialog.findViewById(R.id.txtAddress);

        txtName.setText(CUSTNAME);
        txtAddress.setText(ADDRESS);
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
                Intent mIntent = new Intent(getBaseContext(), CustcardActivity.class);
                mIntent.putExtra("CUSTNO", CUSTNO);
                mIntent.putExtra("CUSTNAME", CUSTNAME);
                mIntent.putExtra("ADDRESS", ADDRESS);
                mIntent.putExtra("TYPENAME", TYPENAME);
                mIntent.putExtra("STATUS", false);
                startActivity(mIntent);
            }
        });

        dialog.show();
    }


}
