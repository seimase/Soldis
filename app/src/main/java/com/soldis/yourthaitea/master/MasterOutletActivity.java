package com.soldis.yourthaitea.master;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.master.adapter.AdapterMasterOutletFrag;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.ListKelurahan;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterOutletActivity extends AppCompatActivity {
    static int REQ_CODE_KELURAHAN = 100;
    static int REQ_CODE_SORT = 200;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    LinearLayout lySearch, lySearchDetail, lyKelurahan,
            lyBatal, lyCari,
            lyTotalOutlet,
            lyTotalPreReg,
            lyTotalRegister

            ;

    RelativeLayout lyAdd, lyBack, layout_sync;

    TextView txtKelurahan,
            txtTotalOutlet,
            txtTotalPreReg,
            txtTotalRegister
    ;
    EditText edtKeyWord, edtSearch;

    ProgressDialog progress;
    Tbl_Custmst tblCustmst;

    View vwTotal,
        vwProspek,
        vwRegister
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_master_outlet);
        InitControl();

        objEcustmst = new Obj_CUSTMST();
        txtTotalOutlet.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOut()));
        txtTotalPreReg.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutPreReg()));
        txtTotalRegister.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutRegister()));
        AppController.getInstance().hideKeyboardFrom(MasterOutletActivity.this);
        AppConstant.bRefresh = false;
        //FillGrid("","", true);
        FillGrid("0","", false);
    }


    private void InitControl() {
        txtTotalOutlet = (TextView)findViewById(R.id.txtTotalOutlet);
        txtTotalPreReg = (TextView)findViewById(R.id.txtTotalPreReg);
        txtTotalRegister = (TextView)findViewById(R.id.txtTotalRegister);

        lyTotalOutlet = (LinearLayout)findViewById(R.id.layout_total);
        lyTotalPreReg = (LinearLayout)findViewById(R.id.layout_total_prereg);
        lyTotalRegister = (LinearLayout)findViewById(R.id.layout_total_register);

        vwTotal = (View) findViewById(R.id.vwTotal);
        vwProspek = (View) findViewById(R.id.vwProspek);
        vwRegister = (View) findViewById(R.id.vwRegister);

        vwProspek.setVisibility(View.GONE);
        vwRegister.setVisibility(View.GONE);

        lyAdd = (RelativeLayout)findViewById(R.id.layout_add);
        lyBack = (RelativeLayout)findViewById(R.id.layout_back);

        lySearch = (LinearLayout)findViewById(R.id.layout_search);
        lySearchDetail = (LinearLayout)findViewById(R.id.layout_search_detail);
        lyKelurahan = (LinearLayout)findViewById(R.id.layout_kelurahan);
        lyBatal = (LinearLayout)findViewById(R.id.layout_batal);
        lyCari = (LinearLayout)findViewById(R.id.layout_cari);
        layout_sync = (RelativeLayout)findViewById(R.id.layout_sync);

        edtKeyWord = (EditText)findViewById(R.id.edt_keyword);
        edtSearch = (EditText)findViewById(R.id.edtSearch);
        txtKelurahan = (TextView)findViewById(R.id.txtKelurahan);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        lySearch.setVisibility(View.VISIBLE);
        lySearchDetail.setVisibility(View.GONE);

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

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    String KeyWord = edtSearch.getText().toString().trim();
                    if (KeyWord.equals("")) KeyWord = "0";
                    FillGrid(KeyWord,"", true);
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
                Intent mIntent = new Intent(MasterOutletActivity.this, ListKelurahan.class);
                startActivityForResult(mIntent, REQ_CODE_KELURAHAN);
            }
        });

        lyTotalOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("0","", false);
                vwTotal.setVisibility(View.VISIBLE);
                vwProspek.setVisibility(View.GONE);
                vwRegister.setVisibility(View.GONE);
            }
        });

        lyTotalPreReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("1","", false);
                vwTotal.setVisibility(View.GONE);
                vwProspek.setVisibility(View.VISIBLE);
                vwRegister.setVisibility(View.GONE);
            }
        });

        lyTotalRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("2","", false);
                vwTotal.setVisibility(View.GONE);
                vwProspek.setVisibility(View.GONE);
                vwRegister.setVisibility(View.VISIBLE);
            }
        });

        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VisitValidation()){
                    GPSTracker gps;
                    // create class object
                    gps = new GPSTracker(MasterOutletActivity.this);

                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled

                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        Intent mIntent = new Intent(MasterOutletActivity.this, AddOutletPreRegActivity.class);
                        mIntent.putExtra("FLAGOUT", "0");
                        startActivity(mIntent);
                    }else{
                        gps.showSettingsAlert();
                    }
                }else{
                    Intent mIntent = new Intent(MasterOutletActivity.this, TimeVisitActivity.class);
                    startActivity(mIntent);
                }


            }
        });

        layout_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidation()){
                    AppController.getInstance().CustomeDialog(MasterOutletActivity.this,"Tidak bisa sync data, karena sudah melakukan absen pergi");
                }else {
                    if (AppController.getInstance().isOnline()){
                        CustomeDialogSyncOutlet();
                    }else{
                        AppController.getInstance().CustomeDialog(MasterOutletActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }

            }
        });
    }



    public void FillGrid(String KEYWORD, String KELURAHAN, boolean bSearch){
        progress = ProgressDialog.show(MasterOutletActivity.this, "Informasi",
                "Sinkronisasi data", true);

        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();

        txtTotalOutlet.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOut()));
        txtTotalPreReg.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutPreReg()));
        txtTotalRegister.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutRegister()));

        objEcustmst = new Obj_CUSTMST();
        KELURAHAN = "SEMUA";
        if (bSearch){
            objEcustmsts = objEcustmst.getDataListNoo(KEYWORD,KELURAHAN);
        }else{
            objEcustmsts = objEcustmst.getDataListReg(KEYWORD);
        }


        mAdapter = new AdapterMasterOutletFrag(this, objEcustmsts, new AdapterMasterOutletFrag.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String FLAGOUT) {
                Intent mIntent = new Intent(MasterOutletActivity.this, OutletActivity.class);
                if (FLAGOUT != null){
                    mIntent.putExtra("CUSTNO", CUSTNO);
                    mIntent.putExtra("CUSTNAME", CUSTNAME);
                    mIntent.putExtra("ADDRESS", ADDRESS);
                    mIntent.putExtra("FLAGOUT", FLAGOUT);
                    mIntent.putExtra("STATUS", false);
                    startActivity(mIntent);
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);


        AppConstant.bRefresh = false;
        progress.dismiss();
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

                            FillGrid("0","",false);

                            AppController.getInstance().CustomeDialog(MasterOutletActivity.this, getResources().getString(R.string.setting_sync_complete));
                        }else{
                            progress.dismiss();
                            AppController.getInstance().CustomeDialog(MasterOutletActivity.this,
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

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        FillGrid("0","", false);
    }
}
