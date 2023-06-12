package com.soldis.yourthaitea.dashboard;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashboardPresenceSummary;
import com.soldis.yourthaitea.model.Tbl_Presence;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by snugrah4 on 11/1/2017.
 */

public class DashboardPresenceSummaryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView
            txtLabel,
            txtTgl,
            txtName
    ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    Toolbar toolbar;
    ProgressDialog progress;
    String DATE = "";
    Tbl_Presence tblPresence;

    RelativeLayout lyFilter, layout_back;
    String KODECABANG = "";
    String NAMACABANG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_presence_summary);
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

        tblPresence = AppController.getInstance().getSessionManager().getListPresence();

        if (tblPresence == null ){
            DATE = AppController.getInstance().getDateYYYYMMDD();
            SyncData(DATE);
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(DATE));
        }else{
            FillGrid();
        }
    }

    void InitControl(){
        txtName = (TextView)findViewById(R.id.txtName);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText(NAMACABANG);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        txtName.setText(NAMACABANG);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    //fabXls.hide();
                } else{
                    //fabXls.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        lyFilter = (RelativeLayout)findViewById(R.id.layout_tgl);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        lyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
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

    void SyncData(String sDATE) {
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_Presence> call;
            call = NetworkManager.getNetworkService().ListPresence(KODECABANG,
                    sDATE);

            call.enqueue(new Callback<Tbl_Presence>() {
                @Override
                public void onResponse(Call<Tbl_Presence> call, Response<Tbl_Presence> response) {
                    int code  = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblPresence = response.body();
                        if (!tblPresence.error){
                            FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Presence> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                DashboardPresenceSummaryActivity.this,
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
        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(strDate));

        SyncData(DATE);
    }

    void FillGrid(){
        //txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(tblPresence.tgl_start) + " s/d "+
                //AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(tblPresence.tgl_end));

        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(tblPresence.tgl));
        AppController.getInstance().getSessionManager().setListPresence(tblPresence);

        mAdapter = new AdapterDashboardPresenceSummary(this, tblPresence, new AdapterDashboardPresenceSummary.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }


}
