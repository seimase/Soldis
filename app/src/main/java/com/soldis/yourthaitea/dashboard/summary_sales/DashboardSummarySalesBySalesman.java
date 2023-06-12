package com.soldis.yourthaitea.dashboard.summary_sales;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.summary_sales.adapter.AdapterDashboardSummarySalesBySalesman;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 29/09/2017.
 */

public class DashboardSummarySalesBySalesman extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    TextView txtName, txtId, txtLabel,
            txtSync, txtTgl,
            txtFab, txtLabelDate
            ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    Toolbar toolbar;

    ProgressDialog progress;
    Tbl_List_Sales_Motoris tblListSalesMotoris;
    public static Tbl_List_Motoris listMotoris;
    LinearLayout lySync;

    RelativeLayout layout_back, layout_xls;
    String DATE = "";
    String CABANG = "";
    String KODECABANG = "";
    FloatingActionButton fabDate;

    boolean bDays = false;
    boolean bPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_teamleader);

        bDays = true;
        bPermission = true;
        try{
            bDays = AppController.getInstance().getSessionManager().getBooleanData(AppConstant.DATA_SALES_MOTORIS);
            CABANG = getIntent().getExtras().getString("NAMACABANG");
            KODECABANG = getIntent().getExtras().getString("KODECABANG");
        }catch (Exception e){
            CABANG = "";
            KODECABANG = "";
        }

        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listMotoris = AppController.getInstance().getSessionManager().getListMotoris();
        if (listMotoris != null && listMotoris.data.size() > 0){
            if (bDays){
                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(listMotoris.tgl));
                SyncXls(listMotoris.tgl);
            }else{
                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(listMotoris.tgl));
            }

            DATE = listMotoris.tgl;

            FillGrid();
        }else{
            lySync.setVisibility(View.VISIBLE);
        }

    }

    void InitControl(){
        txtLabelDate = (TextView)findViewById(R.id.txtLabelDate);
        txtFab = (TextView)findViewById(R.id.txt_fab);
        txtSync = (TextView)findViewById(R.id.txtSync);
        txtTgl = (TextView)findViewById(R.id.text_tgl);

        txtName = (TextView)findViewById(R.id.text_name);
        txtId = (TextView)findViewById(R.id.text_id);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtName.setText(CABANG);
        txtId.setText(KODECABANG);

        txtLabel.setText(getResources().getString(R.string.main_motorist));
        fabDate = (FloatingActionButton)findViewById(R.id.fab_date);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layout_xls = (RelativeLayout)findViewById(R.id.layout_xls);
        //lyFilter = (RelativeLayout)findViewById(R.id.layout_filter);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        txtFab.setText(getResources().getString(R.string.dash_days));


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    //fabDate.hide();
                } else{
                    //fabDate.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        lySync = (LinearLayout)findViewById(R.id.layout_sync);

        txtSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppController.getInstance().isOnline()){
                    SyncData(DATE);
                }else{
                    AppController.getInstance().CustomeDialog(DashboardSummarySalesBySalesman.this, getResources().getString(R.string.text_no_connection));
                }

            }
        });

        /*lyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });*/

        if (bDays){
            txtFab.setText(getResources().getString(R.string.dash_days));
            txtLabelDate.setText(getResources().getString(R.string.dash_date_label));
        }else{
            txtFab.setText(getResources().getString(R.string.dash_mtd));
            txtLabelDate.setText(getResources().getString(R.string.dash_month_label));
        }

        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bDays){
                    bDays = false;
                    txtFab.setText(getResources().getString(R.string.dash_mtd));
                    txtLabelDate.setText(getResources().getString(R.string.dash_month_label));
                }else{
                    bDays = true;
                    txtFab.setText(getResources().getString(R.string.dash_days));
                    txtLabelDate.setText(getResources().getString(R.string.dash_date_label));
                }

                SyncData(DATE);
            }
        });

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout_xls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
                if(bPermission)
                    CustomeDialog();
            }
        });
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

    void SyncData(String sDATE){
        lySync.setVisibility(View.GONE);
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);
        try{
            Call<Tbl_List_Motoris> call;
            if (bDays){
                call = NetworkManager.getNetworkService().ListAssMotoris(AppConstant.strMitraID,
                        AppConstant.strSlsNo,
                        sDATE);
            }else{
                call = NetworkManager.getNetworkService().ListAssMotorisMTD(AppConstant.strMitraID,
                        AppConstant.strSlsNo,
                        sDATE);
            }

            call.enqueue(new Callback<Tbl_List_Motoris>() {
                @Override
                public void onResponse(Call<Tbl_List_Motoris> call, Response<Tbl_List_Motoris> response) {
                    progress.dismiss();
                    int code = response.code();
                    if (code == 200){
                        listMotoris = response.body();
                        if (!listMotoris.error){
                            AppController.getInstance().getSessionManager().setListMotoris(null);
                            AppController.getInstance().getSessionManager().setListMotoris(listMotoris);
                            DATE = listMotoris.tgl;
                            if (bDays){
                                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(listMotoris.tgl));
                            }else{
                                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(listMotoris.tgl));
                            }

                            AppController.getInstance().getSessionManager().putBooleanData(AppConstant.DATA_SALES_MOTORIS, bDays);
                            FillGrid();

                            SyncXls(DATE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_List_Motoris> call, Throwable t) {
                    progress.dismiss();
                    lySync.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void FillGrid(){
        lySync.setVisibility(View.GONE);
        mAdapter = new AdapterDashboardSummarySalesBySalesman(this, listMotoris, bDays, new AdapterDashboardSummarySalesBySalesman.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                DashboardSummarySalesBySalesman.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String sMonth = String.valueOf((monthOfYear+1));
        if (sMonth.length() < 2) sMonth = "0" + sMonth;

        String sDay = String.valueOf(dayOfMonth);
        if (sDay.length() < 2) sDay = "0" + sDay;

        String strDate = sDay + "/" + sMonth + "/" + year;
        DATE = year + "-" + sMonth + "-" + sDay;

        if (bDays){
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(strDate));
        }else{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(strDate));
        }
        SyncData(DATE);
    }

    void SyncXls(String sDATE) {
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_List_Sales_Motoris> call;

            call = NetworkManager.getNetworkService().ListSalesDepoMotoris(KODECABANG,
                    AppConstant.strSlsNo,
                    sDATE);

            call.enqueue(new Callback<Tbl_List_Sales_Motoris>() {
                @Override
                public void onResponse(Call<Tbl_List_Sales_Motoris> call, Response<Tbl_List_Sales_Motoris> response) {
                    int code  = response.code();
                    progress.dismiss();
                    Log.w("SyncXLS", String.valueOf(code));
                    if (code == 200){

                        tblListSalesMotoris = response.body();
                        if (!tblListSalesMotoris.error){
                            AppController.getInstance().getSessionManager().setListSalesMotoris(tblListSalesMotoris);
                            //FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_List_Sales_Motoris> call, Throwable t) {
                    Log.w("SyncXLS", t.getMessage());
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            Log.w("SyncXLS", e.getMessage());
            progress.dismiss();
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

        txtDialog.setText(getResources().getString(R.string.dash_export_to_excel));
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
                CreateExcel();
            }
        });

        dialog.show();
    }



    void CustomeDialogProcess(final File file){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(getResources().getString(R.string.daily_data_hasbeen_process));
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try{
                    Uri path = Uri.fromFile(file);
                    if(Build.VERSION.SDK_INT >= 24){
                        try{
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    Intent shareIntent = new Intent();
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.main_dashboard) + " " + file.getName());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.main_dashboard) +
                            " " + KODECABANG + " - " + CABANG );
                    shareIntent.setType("application/vnd.ms-excel");
                    startActivity(Intent.createChooser(shareIntent, "Send to"));
                }catch (Exception e){
                    AppController.getInstance().CustomeDialog(DashboardSummarySalesBySalesman.this, "Share : " + e.getMessage());
                }

            }
        });

        dialog.show();
    }

    void CreateExcel(){
        Tbl_List_Sales_Motoris tblListSalesMotoris;
        tblListSalesMotoris = AppController.getInstance().getSessionManager().getListSalesMotoris();

        File directory = new File(AppConstant.PATH_FOLDER_XLS);
        Log.w("SyncXLS", "Start");
        Log.w("SyncXLS", "Data : " + tblListSalesMotoris.data.size());
        if (tblListSalesMotoris !=null && tblListSalesMotoris.data.size() > 0){

            try {
                //file path
                String csvFile = tblListSalesMotoris.tgl  +".xls";
                File file = new File(directory, csvFile);
                if (file.exists()) file.delete();

                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("PENJUALAN", 0);
                // column and row

                sheet.addCell(new Label(0, 5, "KETEGORI"));
                sheet.addCell(new Label(1, 5, "HARIAN"));
                sheet.addCell(new Label(2, 5, "BULANAN"));


                sheet.addCell(new Label(4, 5, "HARIAN"));
                sheet.addCell(new Label(5, 5, "BULANAN"));
                //Header---------------------------------------------------------------------------------
                for (Tbl_List_Sales_Motoris.Datum dat : tblListSalesMotoris.data){
                    sheet.addCell(new Label(0, 0, "Tanggal"));//Tanggal
                    sheet.addCell(new Label(1, 0, tblListSalesMotoris.tgl));

                    sheet.addCell(new Label(0, 1, ""));
                    sheet.addCell(new Label(0, 2, "Jumlah Salesman"));
                    sheet.addCell(new Label(1, 2, (dat.TOTAL_MOTORIS == null ? "0" : dat.TOTAL_MOTORIS)));
                    sheet.addCell(new Label(0, 3, "Masuk"));
                    sheet.addCell(new Label(1, 3, (dat.KEHADIRAN == null ? "0" : dat.KEHADIRAN)));

                    //EC
                    sheet.addCell(new Label(0, 6, "EC"));
                    sheet.addCell(new Label(1, 6, (dat.EC == null ? "0" : dat.EC))); //HARIAN
                    sheet.addCell(new Label(4, 6, (dat.EC_AFH == null ? "0" : dat.EC_AFH))); //HARIAN
                    sheet.addCell(new Label(2, 6, (dat.EC_MTD == null ? "0" : dat.EC_MTD))); //BULANAN
                    sheet.addCell(new Label(5, 6, (dat.EC_AFH_MTD == null ? "0" : dat.EC_AFH_MTD))); //BULANAN

                    //CALL
                    sheet.addCell(new Label(0, 7, "CALL"));
                    sheet.addCell(new Label(1, 7, (dat.TOTAL_CALL == null ? "0" : dat.TOTAL_CALL))); //HARIAN
                    sheet.addCell(new Label(4, 7, (dat.CALL_AFH == null ? "0" : dat.CALL_AFH))); //HARIAN
                    sheet.addCell(new Label(2, 7, (dat.CALL_MTD == null ? "0" : dat.CALL_MTD))); //BULANAN
                    sheet.addCell(new Label(5, 7, (dat.CALL_AFH_MTD == null ? "0" : dat.CALL_AFH_MTD))); //BULANAN

                    //NOO
                    sheet.addCell(new Label(0, 8, "NOO"));
                    sheet.addCell(new Label(1, 8, (dat.NOO == null ? "0" : dat.NOO))); //HARIAN
                    sheet.addCell(new Label(4, 8, (dat.NOO_AFH == null ? "0" : dat.NOO_AFH))); //HARIAN
                    sheet.addCell(new Label(2, 8, (dat.NOO_MTD == null ? "0" : dat.NOO_MTD))); //BULANAN
                    sheet.addCell(new Label(5, 8, (dat.NOO_AFH_MTD == null ? "0" : dat.NOO_AFH_MTD))); //BULANAN

                    //PENJUALAN
                    sheet.addCell(new Label(0, 9, "PENJUALAN"));
                    sheet.addCell(new Label(1, 9, Double.toString(dat.SALES))); //HARIAN
                    sheet.addCell(new Label(4, 9, Double.toString(dat.SALES_AFH))); //HARIAN
                    sheet.addCell(new Label(2, 9, Double.toString(dat.SALES_MTD))); //BULANAN
                    sheet.addCell(new Label(5, 9, Double.toString(dat.SALES_AFH_MTD))); //HARIAN
                }

                int iIndex = 0;
                //Detail----------------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "SALES ID"));
                sheet.addCell(new Label(1, 11 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(2, 11 + iIndex, "SALES TYPE"));
                sheet.addCell(new Label(3, 11 + iIndex, "TIME GO"));
                sheet.addCell(new Label(4, 11 + iIndex, "DATE GO"));
                sheet.addCell(new Label(5, 11 + iIndex, "TIME BACK"));
                sheet.addCell(new Label(6, 11 + iIndex, "DATE BACK"));
                sheet.addCell(new Label(7, 11 + iIndex, "TOTAL HADIR"));
                sheet.addCell(new Label(8, 11 + iIndex, "TOTAL EC"));
                sheet.addCell(new Label(9, 11 + iIndex, "TOTAL CALL"));
                sheet.addCell(new Label(10, 11 + iIndex, "TOTAL NOO"));
                sheet.addCell(new Label(11, 11 + iIndex, "TOTAL AMOUNT"));
                for (Tbl_List_Sales_Motoris.TmVisit dat : tblListSalesMotoris.tm_visit){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(2, 11 + iIndex, dat.SALES_TYPE));
                    sheet.addCell(new Label(3, 11 + iIndex, dat.TMGO));
                    sheet.addCell(new Label(4, 11 + iIndex, dat.TGLVISIT));
                    sheet.addCell(new Label(5, 11 + iIndex, dat.TMBCK));
                    sheet.addCell(new Label(6, 11 + iIndex, dat.TGLBACK));
                    sheet.addCell(new Label(7, 11 + iIndex, Long.toString(dat.TOTAL_HADIR)));
                    sheet.addCell(new Label(8, 11 + iIndex, Long.toString(dat.TOTAL_EC)));
                    sheet.addCell(new Label(9, 11 + iIndex, Long.toString(dat.TOTAL_CALL)));
                    sheet.addCell(new Label(10, 11 + iIndex, Long.toString(dat.NOO)));
                    sheet.addCell(new Label(11, 11 + iIndex, Long.toString(dat.INVAMOUNT)));
                }

                iIndex += 2;
                //Detail----------------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "PCODE"));
                sheet.addCell(new Label(1, 11 + iIndex, "PRODUK"));
                sheet.addCell(new Label(2, 11 + iIndex, "TOTAL TOKO HARI INI"));
                sheet.addCell(new Label(3, 11 + iIndex, "TOTAL TOKO BULANAN"));
                for (Tbl_List_Sales_Motoris.Trx dat : tblListSalesMotoris.trx){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.PCODE));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.PCODENAME));
                    sheet.addCell(new Label(2, 11 + iIndex, Integer.toString(dat.TOTAL_TOKO)));
                    sheet.addCell(new Label(3, 11 + iIndex, Integer.toString(dat.TOTAL_TOKO_MTD)));
                }

                iIndex += 2;
                //DetailOtlet------------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "TANGGAL"));
                sheet.addCell(new Label(1, 11 + iIndex, "SALES ID"));
                sheet.addCell(new Label(2, 11 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(3, 11 + iIndex, "KODE"));
                sheet.addCell(new Label(4, 11 + iIndex, "NAMA TOKO"));
                sheet.addCell(new Label(5, 11 + iIndex, "PEMILIK TOKO"));
                sheet.addCell(new Label(6, 11 + iIndex, "ALAMAT"));
                sheet.addCell(new Label(7, 11 + iIndex, "KELURAHAN"));
                sheet.addCell(new Label(8, 11 + iIndex, "GOOGLE_KODEPOS"));
                sheet.addCell(new Label(9, 11 + iIndex, "GOOGLE ALAMAT"));
                sheet.addCell(new Label(10, 11 + iIndex, "GOOGLE KELURAHAN"));
                sheet.addCell(new Label(11, 11 + iIndex, "GOOGLE KECAMATAN"));
                sheet.addCell(new Label(12, 11 + iIndex, "GOOGLE KABUPATEN"));
                sheet.addCell(new Label(13, 11 + iIndex, "PRODUK"));
                sheet.addCell(new Label(14, 11 + iIndex, "QTY"));
                sheet.addCell(new Label(15, 11 + iIndex, "AMOUNT"));
                sheet.addCell(new Label(16, 11 + iIndex, "LATITUDE"));
                sheet.addCell(new Label(17, 11 + iIndex, "LONGITUDE"));

                for (Tbl_List_Sales_Motoris.TrxD dat : tblListSalesMotoris.trx_d){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.ORDERDATE));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(2, 11 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(3, 11 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(4, 11 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(5, 11 + iIndex, dat.CCONTACT));
                    sheet.addCell(new Label(6, 11 + iIndex, dat.CUSTADD1));
                    sheet.addCell(new Label(7, 11 + iIndex, dat.KELURAHAN));
                    sheet.addCell(new Label(8, 11 + iIndex, dat.GOOGLE_KODEPOS));
                    sheet.addCell(new Label(9, 11 + iIndex, dat.GOOGLE_ALAMAT));
                    sheet.addCell(new Label(10, 11 + iIndex, dat.GOOGLE_KELURAHAN));
                    sheet.addCell(new Label(11, 11 + iIndex, dat.GOOGLE_KECAMATAN));
                    sheet.addCell(new Label(12, 11 + iIndex, dat.GOOGLE_KABUPATEN));
                    sheet.addCell(new Label(13, 11 + iIndex, dat.PCODENAME));
                    sheet.addCell(new Label(14, 11 + iIndex, Integer.toString(dat.QTY)));
                    sheet.addCell(new Label(15, 11 + iIndex, Long.toString(dat.AMOUNT)));
                    sheet.addCell(new Label(16, 11 + iIndex, dat.LATITUDE));
                    sheet.addCell(new Label(17, 11 + iIndex, dat.LONGITUDE));
                }

                iIndex += 2;
                //Not EC------------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "NOT EC"));
                iIndex += 1;
                sheet.addCell(new Label(0, 11 + iIndex, "TANGGAL"));
                sheet.addCell(new Label(1, 11 + iIndex, "SALES ID"));
                sheet.addCell(new Label(2, 11 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(3, 11 + iIndex, "KODE"));
                sheet.addCell(new Label(4, 11 + iIndex, "NAMA TOKO"));
                sheet.addCell(new Label(5, 11 + iIndex, "PEMILIK TOKO"));
                sheet.addCell(new Label(6, 11 + iIndex, "ALAMAT"));
                sheet.addCell(new Label(7, 11 + iIndex, "KELURAHAN"));
                sheet.addCell(new Label(8, 11 + iIndex, "GOOGLE_KODEPOS"));
                sheet.addCell(new Label(9, 11 + iIndex, "GOOGLE ALAMAT"));
                sheet.addCell(new Label(10, 11 + iIndex, "GOOGLE KELURAHAN"));
                sheet.addCell(new Label(11, 11 + iIndex, "GOOGLE KECAMATAN"));
                sheet.addCell(new Label(12, 11 + iIndex, "GOOGLE KABUPATEN"));
                sheet.addCell(new Label(13, 11 + iIndex, "LATITUDE"));
                sheet.addCell(new Label(14, 11 + iIndex, "LONGITUDE"));

                for (Tbl_List_Sales_Motoris.TrxNotEc dat : tblListSalesMotoris.trx_not_ec){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.TGL_TRX));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(2, 11 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(3, 11 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(4, 11 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(5, 11 + iIndex, dat.CCONTACT));
                    sheet.addCell(new Label(6, 11 + iIndex, dat.CUSTADD1));
                    sheet.addCell(new Label(7, 11 + iIndex, dat.KELURAHAN));
                    sheet.addCell(new Label(8, 11 + iIndex, dat.GOOGLE_KODEPOS));
                    sheet.addCell(new Label(9, 11 + iIndex, dat.GOOGLE_ALAMAT));
                    sheet.addCell(new Label(10, 11 + iIndex, dat.GOOGLE_KELURAHAN));
                    sheet.addCell(new Label(11, 11 + iIndex, dat.GOOGLE_KECAMATAN));
                    sheet.addCell(new Label(12, 11 + iIndex, dat.GOOGLE_KABUPATEN));
                    sheet.addCell(new Label(13, 11 + iIndex, dat.LATITUDE));
                    sheet.addCell(new Label(14, 11 + iIndex, dat.LONGITUDE));
                }


                iIndex += 2;
                //NOO------------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "NOO"));
                iIndex += 1;
                sheet.addCell(new Label(0, 11 + iIndex, "TANGGAL"));
                sheet.addCell(new Label(1, 11 + iIndex, "SALES ID"));
                sheet.addCell(new Label(2, 11 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(3, 11 + iIndex, "KODE"));
                sheet.addCell(new Label(4, 11 + iIndex, "NAMA TOKO"));
                sheet.addCell(new Label(5, 11 + iIndex, "PEMILIK TOKO"));
                sheet.addCell(new Label(6, 11 + iIndex, "ALAMAT"));
                sheet.addCell(new Label(7, 11 + iIndex, "KELURAHAN"));
                sheet.addCell(new Label(8, 11 + iIndex, "GOOGLE_KODEPOS"));
                sheet.addCell(new Label(9, 11 + iIndex, "GOOGLE ALAMAT"));
                sheet.addCell(new Label(10, 11 + iIndex, "GOOGLE KELURAHAN"));
                sheet.addCell(new Label(11, 11 + iIndex, "GOOGLE KECAMATAN"));
                sheet.addCell(new Label(12, 11 + iIndex, "GOOGLE KABUPATEN"));
                sheet.addCell(new Label(13, 11 + iIndex, "LATITUDE"));
                sheet.addCell(new Label(14, 11 + iIndex, "LONGITUDE"));

                for (Tbl_List_Sales_Motoris.DataNoo dat : tblListSalesMotoris.data_noo){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.TGL));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(2, 11 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(3, 11 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(4, 11 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(5, 11 + iIndex, dat.CCONTACT));
                    sheet.addCell(new Label(6, 11 + iIndex, dat.CUSTADD1));
                    sheet.addCell(new Label(7, 11 + iIndex, dat.KELURAHAN));
                    sheet.addCell(new Label(8, 11 + iIndex, dat.GOOGLE_KODEPOS));
                    sheet.addCell(new Label(9, 11 + iIndex, dat.GOOGLE_ALAMAT));
                    sheet.addCell(new Label(10, 11 + iIndex, dat.GOOGLE_KELURAHAN));
                    sheet.addCell(new Label(11, 11 + iIndex, dat.GOOGLE_KECAMATAN));
                    sheet.addCell(new Label(12, 11 + iIndex, dat.GOOGLE_KABUPATEN));
                    sheet.addCell(new Label(13, 11 + iIndex, dat.LATITUDE));
                    sheet.addCell(new Label(14, 11 + iIndex, dat.LONGITUDE));
                }

                iIndex += 2;
                //DISPENSER-------------------------------------------------------------------
                sheet.addCell(new Label(0, 11 + iIndex, "DISPENSER"));
                iIndex += 1;
                sheet.addCell(new Label(0, 11 + iIndex, "TANGGAL"));
                sheet.addCell(new Label(1, 11 + iIndex, "SALES ID"));
                sheet.addCell(new Label(2, 11 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(3, 11 + iIndex, "KODE"));
                sheet.addCell(new Label(4, 11 + iIndex, "NAMA TOKO"));
                sheet.addCell(new Label(5, 11 + iIndex, "DISPENSER ID"));
                sheet.addCell(new Label(6, 11 + iIndex, "DISPENSER"));
                sheet.addCell(new Label(7, 11 + iIndex, "QTY"));
                for (Tbl_List_Sales_Motoris.DataDispenser dat : tblListSalesMotoris.data_dispenser){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 11 + iIndex, dat.TGL_TRX));
                    sheet.addCell(new Label(1, 11 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(2, 11 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(3, 11 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(4, 11 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(5, 11 + iIndex, dat.PCODE));
                    sheet.addCell(new Label(6, 11 + iIndex, dat.PCODEUCINAME));
                    sheet.addCell(new Label(7, 11 + iIndex, Integer.toString(dat.QTY)));
                }

                workbook.write();
                workbook.close();

                CustomeDialogProcess(file);
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean checkPermission(String permissions[]) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void checkPermissions() {
        boolean permissionsGranted = checkPermission(PERMISSIONS_REQUIRED);
        if (permissionsGranted) {
            //Toast.makeText(this, "You've granted all required permissions!", Toast.LENGTH_SHORT).show();
        } else {
            boolean showRationale = true;
            for (String permission: PERMISSIONS_REQUIRED) {
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    bPermission = false;
                    break;
                }
            }

            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

            new AlertDialog.Builder(this)
                    .setTitle("Permissions Required")
                    .setMessage(dialogMsg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DashboardSummarySalesBySalesman.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
                        }
                    }).create().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("MainLeaderActivity", "requestCode: " + requestCode);
        Log.d("MainLeaderActivity", "Permissions:" + Arrays.toString(permissions));
        Log.d("MainLeaderActivity", "grantResults: " + Arrays.toString(grantResults));

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean hasGrantedPermissions = true;
            for (int i=0; i<grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasGrantedPermissions = false;
                    break;
                }
            }

            if (!hasGrantedPermissions) {
                finish();
            }

        } else {
            finish();
        }
    }


}
