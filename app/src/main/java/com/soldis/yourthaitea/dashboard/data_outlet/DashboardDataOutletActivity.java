package com.soldis.yourthaitea.dashboard.data_outlet;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.DashboardLeaderSummaryXLSActivity;
import com.soldis.yourthaitea.dashboard.data_outlet.adapter.ViewPagerAdapterMenu;
import com.soldis.yourthaitea.dashboard.fragment.Frag_Dashboard_Stock;
import com.soldis.yourthaitea.model.Tbl_Dashboard_DataOutlet;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.adapter.ViewPagerAdapterMenuCustcard;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

public class DashboardDataOutletActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    PieChart mChart;
    private long[] yValues ;

    private String[] xValues;
    // colors for different sections in pieChart
    public  int[] MY_COLORS ;
    double dVerified = 0;
    double dNotVerified = 0;

    CharSequence Titles[];
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterMenu adapter;
    TabLayout tabs;


    TextView txtLabel;
    Toolbar toolbar;

    ProgressDialog progress;
    String KODECABANG,
            NAMACABANG;

    public Tbl_Dashboard_DataOutlet dashboardDataOutlet;

    RelativeLayout lyFilter;
    FloatingActionButton fabXls;
    boolean bPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_data_outlet_verified);
        bPermission = true;
        Titles = new CharSequence[]{
                "NOT VERIFIED",
                "PROSPECT"
        };

        try{
            KODECABANG = getIntent().getExtras().getString("KODECABANG");
            NAMACABANG = getIntent().getExtras().getString("NAMACABANG");

        }catch (Exception e){
            KODECABANG = "";
            NAMACABANG = "";
        }

        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yValues = new long[]{0, 0};
        SyncData();
    }

    void InitControl(){
        MY_COLORS = new int[]{
                getResources().getColor(R.color.red_deep),
                getResources().getColor(R.color.green)

        };

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mChart = (PieChart)findViewById(R.id.chart1);

        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText(NAMACABANG);

        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        fabXls = (FloatingActionButton)findViewById(R.id.fab_xls);
        xValues = new String[]{
                "Not Verified",
                "Verified"
        };

        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;
                if (xValues[e.getXIndex()].equals("Verified")){
                    View rootView = getWindow().getDecorView().getRootView();
                    Snackbar.make(rootView,  "Outlet Verified "
                            + AppController.getInstance().toCurrency(dVerified) , Snackbar.LENGTH_LONG)
                            .setAction("Close", null).show();
                }else{
                    View rootView = getWindow().getDecorView().getRootView();
                    Snackbar.make(rootView,  "Outlet Not Verified "
                            + AppController.getInstance().toCurrency(dNotVerified) , Snackbar.LENGTH_LONG)
                            .setAction("Close", null).show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        lyFilter = (RelativeLayout)findViewById(R.id.layout_filter);
        lyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });
        lyFilter.setVisibility(View.INVISIBLE);
        fabXls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
                if(bPermission)
                    CustomeDialog();
            }
        });

    }

    void SyncData(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_Dashboard_DataOutlet> call = NetworkManager.getNetworkService().DashboardDataOutlet(KODECABANG);
            call.enqueue(new Callback<Tbl_Dashboard_DataOutlet>() {
                @Override
                public void onResponse(Call<Tbl_Dashboard_DataOutlet> call, Response<Tbl_Dashboard_DataOutlet> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        dashboardDataOutlet = response.body();
                        if (!dashboardDataOutlet.error){
                            for (Tbl_Dashboard_DataOutlet.Datum dat : dashboardDataOutlet.data){
                                dVerified = dat.TOTAL_VERIFIED;
                                dNotVerified = dat.TOTAL_NOT_VERIFIED;
                            }
                            yValues = new long[]{(long) dNotVerified, (long) dVerified};
                            FillForm();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Dashboard_DataOutlet> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void FillForm(){
        adapter =  new ViewPagerAdapterMenu(getSupportFragmentManager(),Titles,Numboftabs);
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);
        setDataForPieChart();
    }

    public void setDataForPieChart() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
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

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                DashboardDataOutletActivity.this,
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
        //DATE = year + "-" + sMonth + "-" + sDay;
        //txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(strDate));

        //SyncData(DATE);
    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
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
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
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
                            ActivityCompat.requestPermissions(DashboardDataOutletActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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
                            " " + KODECABANG + " - " + NAMACABANG );
                    shareIntent.setType("application/vnd.ms-excel");
                    startActivity(Intent.createChooser(shareIntent, "Send to"));
                }catch (Exception e){
                    AppController.getInstance().CustomeDialog(DashboardDataOutletActivity.this, "Share : " + e.getMessage());
                }

            }
        });

        dialog.show();
    }

    void CreateExcel(){
        File directory = new File(AppConstant.PATH_FOLDER_XLS);

        if ( dashboardDataOutlet !=null && dashboardDataOutlet.data.size() > 0){
            try {
                //file path
                String csvFile = "Data Outlet" + AppController.getInstance().getDateYYYYMMDD()  +".xls";
                File file = new File(directory, csvFile);
                if (file.exists()) file.delete();

                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("OUTLET", 0);
                int iIndex = 0;

                //Not EC------------------------------------------------------------------------
                sheet.addCell(new Label(0, 2 + iIndex, "NOT VERIFIED"));

                iIndex += 1;
                //DetailOtlet------------------------------------------------------------------------
                sheet.addCell(new Label(0, 2 + iIndex, "KODE OULET"));
                sheet.addCell(new Label(1, 2 + iIndex, "NAMA OUTLET"));
                sheet.addCell(new Label(2, 2 + iIndex, "CONTACT"));
                sheet.addCell(new Label(3, 2 + iIndex, "ADDRESS"));
                sheet.addCell(new Label(4, 2 + iIndex, "PHONE"));


                for (Tbl_Dashboard_DataOutlet.OutletNotVerified dat : dashboardDataOutlet.outlet_not_verified){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 2 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(1, 2 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(2, 2 + iIndex, dat.CCONTACT));
                    sheet.addCell(new Label(3, 2 + iIndex, dat.CUSTADD1));
                    sheet.addCell(new Label(4, 2 + iIndex, dat.CPHONE1));
                }

                iIndex += 2;
                //Not EC------------------------------------------------------------------------
                sheet.addCell(new Label(0, 2 + iIndex, "PROSPECT"));
                iIndex += 1;
                sheet.addCell(new Label(0, 2 + iIndex, "TANGGAL"));
                sheet.addCell(new Label(1, 2 + iIndex, "SALES ID"));
                sheet.addCell(new Label(2, 2 + iIndex, "NAMA SALES"));
                sheet.addCell(new Label(3, 2 + iIndex, "KODE OULET"));
                sheet.addCell(new Label(4, 2 + iIndex, "NAMA OUTLET"));
                sheet.addCell(new Label(5, 2 + iIndex, "PEMILIK OUTLET"));
                sheet.addCell(new Label(6, 2 + iIndex, "ALAMAT"));
                sheet.addCell(new Label(7, 2 + iIndex, "KELURAHAN"));
                sheet.addCell(new Label(8, 2 + iIndex, "KLASIFIKASI OUTLET"));
                sheet.addCell(new Label(9, 2 + iIndex, "NAMA PERUSAHAAN"));
                sheet.addCell(new Label(10, 2 + iIndex, "JUMLAH CABANG"));
                sheet.addCell(new Label(11, 2 + iIndex, "TGL KUNJUNGAN BERIKUTNYA"));
                sheet.addCell(new Label(12, 2 + iIndex, "KTP ID"));
                sheet.addCell(new Label(13, 2 + iIndex, "NAMA KTP"));
                sheet.addCell(new Label(14, 2 + iIndex, "ALAMAT KTP"));
                sheet.addCell(new Label(15, 2 + iIndex, "NPWP ID"));
                sheet.addCell(new Label(16, 2 + iIndex, "NAMA NPWP"));
                sheet.addCell(new Label(17, 2 + iIndex, "ALAMAT NPWP"));
                sheet.addCell(new Label(18, 2 + iIndex, "TIPE PEMBELIAN"));
                sheet.addCell(new Label(19, 2 + iIndex, "STATUS KONTRAK"));
                sheet.addCell(new Label(20, 2 + iIndex, "TGL MULAI KONTRAK"));
                sheet.addCell(new Label(21, 2 + iIndex, "TGL AKHIR KONTRAK"));
                sheet.addCell(new Label(22, 2 + iIndex, "GOOGLE_KODEPOS"));
                sheet.addCell(new Label(23, 2 + iIndex, "GOOGLE ALAMAT"));
                sheet.addCell(new Label(24, 2 + iIndex, "GOOGLE KELURAHAN"));
                sheet.addCell(new Label(25, 2 + iIndex, "GOOGLE KECAMATAN"));
                sheet.addCell(new Label(26, 2 + iIndex, "GOOGLE KABUPATEN"));
                sheet.addCell(new Label(27, 2 + iIndex, "LATITUDE"));
                sheet.addCell(new Label(28, 2 + iIndex, "LONGITUDE"));

                for (Tbl_Dashboard_DataOutlet.Prospect dat : dashboardDataOutlet.prospect){
                    iIndex += 1;
                    sheet.addCell(new Label(0, 2 + iIndex, dat.TGL));
                    sheet.addCell(new Label(1, 2 + iIndex, dat.SLSNO));
                    sheet.addCell(new Label(2, 2 + iIndex, dat.SLSNAME));
                    sheet.addCell(new Label(3, 2 + iIndex, dat.CUSTNO));
                    sheet.addCell(new Label(4, 2 + iIndex, dat.CUSTNAME));
                    sheet.addCell(new Label(5, 2 + iIndex, dat.CCONTACT));
                    sheet.addCell(new Label(6, 2 + iIndex, dat.CUSTADD1));
                    sheet.addCell(new Label(7, 2 + iIndex, dat.KELURAHAN));
                    sheet.addCell(new Label(8, 2 + iIndex, dat.TYPENAME));
                    sheet.addCell(new Label(9, 2 + iIndex, dat.COMPANY_NAME));
                    sheet.addCell(new Label(10, 2 + iIndex, dat.NUMBER_OF_BRANCH));
                    sheet.addCell(new Label(11, 2 + iIndex, dat.DATE_NEXT_VISIT));
                    sheet.addCell(new Label(12, 2 + iIndex, dat.KTP_ID));
                    sheet.addCell(new Label(13, 2 + iIndex, dat.KTP_NAME));
                    sheet.addCell(new Label(14, 2 + iIndex, dat.KTP_ADDRESS));
                    sheet.addCell(new Label(15, 2 + iIndex, dat.NPWP_ID));
                    sheet.addCell(new Label(16, 2 + iIndex, dat.NPWP_NAME));
                    sheet.addCell(new Label(17, 2 + iIndex, dat.NPWP_ADDRESS));
                    sheet.addCell(new Label(18, 2 + iIndex, dat.PAYMENT_TYPE));
                    sheet.addCell(new Label(19, 2 + iIndex, dat.STATUS_CONTRACT));
                    sheet.addCell(new Label(20, 2 + iIndex, dat.STARTDATE_CONTRACT));
                    sheet.addCell(new Label(21, 2 + iIndex, dat.ENDDATE_CONTRACT));
                    sheet.addCell(new Label(22, 2 + iIndex, dat.GOOGLE_KODEPOS));
                    sheet.addCell(new Label(23, 2 + iIndex, dat.GOOGLE_ALAMAT));
                    sheet.addCell(new Label(24, 2 + iIndex, dat.GOOGLE_KELURAHAN));
                    sheet.addCell(new Label(25, 2 + iIndex, dat.GOOGLE_KECAMATAN));
                    sheet.addCell(new Label(26, 2 + iIndex, dat.GOOGLE_KABUPATEN));
                    sheet.addCell(new Label(27, 2 + iIndex, dat.LATITUDE));
                    sheet.addCell(new Label(28, 2 + iIndex, dat.LONGITUDE));
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

}
