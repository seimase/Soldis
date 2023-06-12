package com.soldis.yourthaitea.dashboard.asm;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.asm.adapter.AdapterDashboardListDepo;
import com.soldis.yourthaitea.dashboard.asm.adapter.AdapterDashboardListDepoMTD;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 29/09/2017.
 */

public class Dashboard_ASM_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView txtName, txtId,txtLabel,
            txtTgl,
            txtFab, txtLabelDate
    ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ProgressDialog progress;

    Tbl_ListDepo listDepo;
    Tbl_List_Motoris listMotoris;

    RelativeLayout layout_tgl, layout_back;
    String DATE = "";
    String ZONEID = "";

    FloatingActionButton fabDate;

    boolean bDays = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_asm);

        bDays = true;

        try{
            ZONEID = getIntent().getExtras().getString("ZONEID");
            bDays = AppController.getInstance().getSessionManager().getBooleanData(AppConstant.DATA_SALES_MOTORIS);
        }catch (Exception e){
            ZONEID = "";
        }

        InitControl();


/*        listMotoris = AppController.getInstance().getSessionManager().getListMotoris();
        if (listMotoris != null && listMotoris.data.size() > 0){
            if (bDays){
                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(listMotoris.tgl));
            }else{
                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(listMotoris.tgl));
            }

            DATE = listMotoris.tgl;
            FillGrid();
        }else{
            lySync.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }*/

        SyncData(AppController.getInstance().getDateYYYYMMDD());

    }

    void InitControl(){
        txtLabelDate = (TextView)findViewById(R.id.txtLabelDate);
        txtFab = (TextView)findViewById(R.id.txt_fab);
        txtTgl = (TextView)findViewById(R.id.text_tgl);

        txtName = (TextView)findViewById(R.id.text_name);
        txtId = (TextView)findViewById(R.id.text_id);
        txtLabel = (TextView)findViewById(R.id.txtLabel);
        txtName.setText(AppConstant.strSlsName);
        txtId.setText(AppConstant.strSlsNo);
        fabDate = (FloatingActionButton)findViewById(R.id.fab_date);

        layout_tgl = (RelativeLayout)findViewById(R.id.layout_tgl);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

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

        layout_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });

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
        Log.w("DS", sDATE);
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);
        try{
            Call<Tbl_ListDepo> call;
            if (ZONEID.equals("")){
                if (bDays){
                    call = NetworkManager.getNetworkService().ListDepo(AppConstant.strMitraID,
                            AppConstant.strSlsNo,
                            sDATE);
                }else{
                    call = NetworkManager.getNetworkService().ListDepoMTD(AppConstant.strMitraID,
                            AppConstant.strSlsNo,
                            sDATE);
                }
            }else{
                if (bDays){
                    call = NetworkManager.getNetworkService().ListDepoBot(AppConstant.strMitraID,
                            AppConstant.strSlsNo,
                            ZONEID,
                            sDATE);
                }else{
                    call = NetworkManager.getNetworkService().ListDepoBotMTD(AppConstant.strMitraID,
                            AppConstant.strSlsNo,
                            ZONEID,
                            sDATE);
                }
            }

            call.enqueue(new Callback<Tbl_ListDepo>() {
                @Override
                public void onResponse(Call<Tbl_ListDepo> call, Response<Tbl_ListDepo> response) {
                    progress.dismiss();
                    int code = response.code();
                    if (code == 200){
                        listDepo = response.body();
                        if (!listDepo.error){
                            DATE = listDepo.tgl;

                            if (bDays){
                                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(listDepo.tgl));
                            }else{
                                txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(listDepo.tgl));
                            }

                            AppController.getInstance().getSessionManager().putBooleanData(AppConstant.DATA_SALES_MOTORIS, bDays);
                            FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_ListDepo> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void FillGrid(){
        if (bDays){
            mAdapter = new AdapterDashboardListDepo(this, listDepo, bDays, DATE, new AdapterDashboardListDepo.OnDownloadClicked() {
                @Override
                public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

                }
            });

            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter = new AdapterDashboardListDepoMTD(this, listDepo, bDays, DATE, new AdapterDashboardListDepoMTD.OnDownloadClicked() {
                @Override
                public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

                }
            });

            mRecyclerView.setAdapter(mAdapter);
        }

    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                Dashboard_ASM_Activity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

}
