package com.soldis.yourthaitea.dashboard.bot;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.bot.adapter.AdapterDashboardListZone;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_ListZone;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 29/09/2017.
 */

public class Dashboard_Zone_Activity extends AppCompatActivity  {
    TextView txtName, txtId, txtLabel, txtLabelList
            ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;
    Toolbar toolbar;

    ProgressDialog progress;

    Tbl_ListDepo listDepo;
    Tbl_List_Motoris listMotoris;
    Tbl_ListZone listZone;

    String DATE = "";

    LinearLayout layout_row, layout_zone;
    ImageView imgArrow;
    boolean bRow ;


    TextView txtTotalEC,
            txtECTarget,
            txtCallTarget,
            txtTotalCall,
            txtSalesTarget,
            txtTotalSales,
            txtTotalECAFH,
            txtECTargetAFH,
            txtCallTargetAFH,
            txtTotalCallAFH,
            txtSalesTargetAFH,
            txtTotalSalesAFH,
            txtTotalMotoris,
            txtTotalMotorisAFH,
            txtHadir,
            txtHadirAFH,
            txtTotalRO

                    ;

    ProgressBar progressEC,
            progressCall,
            progressSales,
            progressECAFH,
            progressCallAFH,
            progressSalesAFH,
            progressHadir,
            progressHadirAFH
                    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_zone);

        bRow = false;
        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SyncData();

    }

    void InitControl(){
        txtLabelList = (TextView)findViewById(R.id.text_pilih);

        txtName = (TextView)findViewById(R.id.text_name);
        txtId = (TextView)findViewById(R.id.text_id);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtName.setText(AppConstant.strSlsName);
        txtId.setText(AppConstant.strSlsNo);

        txtLabel.setText(getResources().getString(R.string.main_motorist));
        txtLabelList.setText("SUMMARY ZONE");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        // use a LinearLayoutManager
        mRecyclerView.setLayoutManager(mLayoutManager);

        layout_row = (LinearLayout)findViewById(R.id.layout_row);
        layout_zone = (LinearLayout)findViewById(R.id.layout_zone);
        layout_row.setVisibility(View.GONE);

        imgArrow = (ImageView)findViewById(R.id.img_arrow);
        txtTotalEC = (TextView)findViewById(R.id.txtTotalEC);
        txtECTarget = (TextView)findViewById(R.id.txtECTarget);
        txtCallTarget = (TextView)findViewById(R.id.txtCallTarget);
        txtSalesTarget = (TextView)findViewById(R.id.txtSalesTarget);
        txtTotalECAFH = (TextView)findViewById(R.id.txtTotalECAFH);
        txtECTargetAFH = (TextView)findViewById(R.id.txtECTargetAFH);
        txtCallTargetAFH = (TextView)findViewById(R.id.txtCallTargetAFH);
        txtSalesTargetAFH = (TextView)findViewById(R.id.txtSalesTargetAFH);

        txtTotalCall = (TextView)findViewById(R.id.txtTotalCall);
        txtTotalSales = (TextView)findViewById(R.id.txtTotalSales);
        txtTotalCallAFH = (TextView)findViewById(R.id.txtTotalCallAFH);
        txtTotalSalesAFH = (TextView)findViewById(R.id.txtTotalSalesAFH);

        txtTotalMotoris = (TextView)findViewById(R.id.txtTotalMotoris);
        txtTotalMotorisAFH = (TextView)findViewById(R.id.txtTotalMotorisAFH);
        txtHadir = (TextView)findViewById(R.id.txtHadir);
        txtHadirAFH = (TextView)findViewById(R.id.txtHadirAFH);
        txtTotalRO = (TextView)findViewById(R.id.txtTotalRo);

        progressEC = (ProgressBar)findViewById(R.id.progressEC);
        progressCall = (ProgressBar)findViewById(R.id.progressCall);
        progressSales = (ProgressBar)findViewById(R.id.progressSales);
        progressECAFH = (ProgressBar)findViewById(R.id.progressECAFH);
        progressCallAFH = (ProgressBar)findViewById(R.id.progressCallAFH);
        progressSalesAFH = (ProgressBar)findViewById(R.id.progressSalesAFH);
        progressHadir = (ProgressBar)findViewById(R.id.progressHadir);
        progressHadirAFH = (ProgressBar)findViewById(R.id.progressHadirAFH);


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

        layout_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bRow){
                    layout_row.setVisibility(View.GONE);
                    imgArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
                    bRow = false;
                }else {
                    layout_row.setVisibility(View.VISIBLE);
                    imgArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
                    bRow = true;
                }
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

    void SyncData(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);
        try{
            Call<Tbl_ListZone> call;
            call = NetworkManager.getNetworkService().ListZone(AppConstant.strSlsNo);

            call.enqueue(new Callback<Tbl_ListZone>() {
                @Override
                public void onResponse(Call<Tbl_ListZone> call, Response<Tbl_ListZone> response) {
                    progress.dismiss();
                    int code = response.code();
                    if (code == 200){
                        listZone = response.body();
                        if (!listZone.error){
                            FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_ListZone> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void FillGrid(){
        double TOTAL_EC_REGULAR = 0;
        double TOTAL_EC_AFH = 0;
        double TOTAL_CALL_REGULAR = 0;
        double TOTAL_CALL_AFH = 0;
        double TOTAL_SALES_REGULAR = 0;
        double TOTAL_SALES_AFH = 0;
        double TOTAL_MOTORIS_REGULAR = 0;
        double TOTAL_MOTORIS_AFH = 0;
        double KEHADIRAN_REGULAR = 0;
        double KEHADIRAN_AFH = 0;
        double TOTAL_RO = 0;

        double TARGET_EC = 0;
        double TARGET_SALES = 0;
        double TARGET_EC_MTD = 0;
        double TARGET_SALES_MTD = 0;
        double TARGET_CALL = 0;
        double TARGET_CALL_MTD = 0;

        for (Tbl_ListZone.Target dat : listZone.target){
            TARGET_EC = dat.TARGET_EC;
            TARGET_SALES = dat.TARGET_SALES;
            TARGET_EC_MTD = dat.TARGET_EC_MTD;
            TARGET_SALES_MTD = dat.TARGET_SALES_MTD;
            TARGET_CALL = dat.TARGET_CALL;
            TARGET_CALL_MTD = dat.TARGET_CALL_MTD;
        }

        for (Tbl_ListZone.Datum dat : listZone.data){
            TOTAL_EC_REGULAR += dat.TOTAL_EC_REGULAR;
            TOTAL_EC_AFH += dat.TOTAL_EC_AFH;
            TOTAL_CALL_REGULAR += dat.TOTAL_CALL_REGULAR;
            TOTAL_CALL_AFH += dat.TOTAL_CALL_AFH;
            TOTAL_SALES_REGULAR += dat.TOTAL_SALES_REGULAR;
            TOTAL_SALES_AFH += dat.TOTAL_SALES_AFH;
            TOTAL_MOTORIS_REGULAR += dat.TOTAL_MOTORIS_REGULAR;
            TOTAL_MOTORIS_AFH += dat.TOTAL_MOTORIS_AFH;
            KEHADIRAN_REGULAR += dat.KEHADIRAN_REGULAR;
            KEHADIRAN_AFH += dat.KEHADIRAN_AFH;
            TOTAL_RO += dat.TOTAL_RO;
        }

        txtTotalRO.setText(AppController.getInstance().toCurrency(TOTAL_RO));
        txtTotalCall.setText(AppController.getInstance().toCurrency(TOTAL_CALL_REGULAR));
        txtTotalCallAFH.setText(AppController.getInstance().toCurrency(TOTAL_CALL_AFH));
        txtTotalEC.setText(AppController.getInstance().toCurrency(TOTAL_EC_REGULAR));
        txtTotalECAFH.setText(AppController.getInstance().toCurrency(TOTAL_EC_AFH));
        txtTotalSales.setText(AppController.getInstance().toCurrency(TOTAL_SALES_REGULAR));
        txtTotalSalesAFH.setText(AppController.getInstance().toCurrency(TOTAL_SALES_AFH));
        txtTotalMotoris.setText(AppController.getInstance().toCurrency(TOTAL_MOTORIS_REGULAR));
        txtTotalMotorisAFH.setText(AppController.getInstance().toCurrency(TOTAL_MOTORIS_AFH));
        txtHadir.setText(AppController.getInstance().toCurrency(KEHADIRAN_REGULAR));
        txtHadirAFH.setText(AppController.getInstance().toCurrency(KEHADIRAN_AFH));

        txtECTarget.setText(AppController.getInstance().toCurrency(TARGET_EC_MTD * TOTAL_MOTORIS_REGULAR));
        txtECTargetAFH.setText(AppController.getInstance().toCurrency(TARGET_EC_MTD * TOTAL_MOTORIS_AFH));
        txtCallTarget.setText(AppController.getInstance().toCurrency(TARGET_CALL_MTD * TOTAL_MOTORIS_REGULAR));
        txtCallTargetAFH.setText(AppController.getInstance().toCurrency(TARGET_CALL_MTD * TOTAL_MOTORIS_AFH));
        txtSalesTarget.setText(AppController.getInstance().toCurrency(TARGET_SALES_MTD * TOTAL_MOTORIS_REGULAR));
        txtSalesTargetAFH.setText(AppController.getInstance().toCurrency(TARGET_SALES_MTD * TOTAL_MOTORIS_AFH));

        double lStatus = 0;
        try{
            if (TOTAL_MOTORIS_REGULAR > 0){
                lStatus = (KEHADIRAN_REGULAR / TOTAL_MOTORIS_REGULAR) * 100;
                progressHadir.setProgress((int)lStatus);
            }

            if (TOTAL_MOTORIS_AFH > 0){
                lStatus = (KEHADIRAN_AFH / TOTAL_MOTORIS_AFH) * 100;
                progressHadirAFH.setProgress((int)lStatus);
            }

        }catch (Exception e){
            progressHadir.setProgress(0);
            progressHadirAFH.setProgress(0);
        }

        //Call
        try{
            if ((TARGET_CALL_MTD * TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (TOTAL_CALL_REGULAR / (TARGET_CALL_MTD * TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    progressCall.setProgress(100);
                } else{
                    progressCall.setProgress((int)lStatus);
                }
            }else{
                progressCall.setProgress(0);

            }

        }catch (Exception e){
            progressCall.setProgress(0);

        }

        if ((TARGET_CALL_MTD * TOTAL_MOTORIS_AFH) > 0){
            lStatus = 0;
            lStatus = (TOTAL_CALL_AFH / (TARGET_CALL_MTD * TOTAL_MOTORIS_AFH)) * 100;
            if (lStatus > 100){
                progressCallAFH.setProgress(100);
            } else{
                progressCallAFH.setProgress((int)lStatus);
            }
        }else{
            progressCallAFH.setProgress(0);
        }

        try{
            if ((TARGET_EC_MTD * TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (TOTAL_EC_REGULAR / (TARGET_EC_MTD * TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    progressEC.setProgress(100);
                } else{
                    progressEC.setProgress((int)lStatus);
                }

            }else{
                progressEC.setProgress(0);

            }

        }catch (Exception e){
            progressEC.setProgress(0);
        }

        if ((TARGET_EC_MTD * TOTAL_MOTORIS_AFH) > 0){
            lStatus = 0;
            lStatus = (TOTAL_EC_AFH / (TARGET_EC_MTD * TOTAL_MOTORIS_AFH)) * 100;
            if (lStatus > 100){
                progressECAFH.setProgress(100);
            } else{
                progressECAFH.setProgress((int)lStatus);
            }
        }else{
            progressECAFH.setProgress(0);
        }

        try{
            if ((TARGET_SALES_MTD * TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (TOTAL_SALES_REGULAR / (TARGET_SALES_MTD * TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    progressSales.setProgress(100);
                } else{
                    progressSales.setProgress((int)lStatus);
                }


            }else{
                progressSales.setProgress(0);
            }


            if ((TARGET_SALES_MTD * TOTAL_MOTORIS_AFH) > 0){
                lStatus = (TOTAL_SALES_AFH / (TARGET_SALES_MTD * TOTAL_MOTORIS_AFH)) * 100;
                if (lStatus > 100){
                    progressSalesAFH.setProgress(100);
                } else{
                    progressSalesAFH.setProgress((int)lStatus);
                }
            }else{
                progressSalesAFH.setProgress(0);
            }
        }catch (Exception e){
            progressSales.setProgress(0);
            progressSalesAFH.setProgress(0);
        }


        mAdapter = new AdapterDashboardListZone(this, listZone, new AdapterDashboardListZone.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

}
