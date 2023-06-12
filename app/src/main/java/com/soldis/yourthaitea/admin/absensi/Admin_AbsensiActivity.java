package com.soldis.yourthaitea.admin.absensi;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.admin.absensi.adapter.AdapterAdminAbsensi;
import com.soldis.yourthaitea.dashboard.DashboardLeaderSummaryXLSActivity;
import com.soldis.yourthaitea.model.Tbl_Presence;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_AbsensiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    RelativeLayout layout_back;
    ProgressDialog progress;
    Tbl_Presence tblPresence;

    TextView txtTgl;

    RelativeLayout layout_tgl;
    String sDATE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_admin_absensi);

        InitControl();

        sDATE = AppController.getInstance().getDateYYYYMMDD();
        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(sDATE));

        SyncData(sDATE);
    }

    void InitControl(){
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layout_tgl = (RelativeLayout)findViewById(R.id.layout_tgl);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });
    }

    void SyncData(String sDATE) {
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_Presence> call = NetworkManager.getNetworkService().ListPresence(AppConstant.strSlsNo,
                    sDATE);
            call.enqueue(new Callback<Tbl_Presence>() {
                @Override
                public void onResponse(Call<Tbl_Presence> call, Response<Tbl_Presence> response) {
                    progress.dismiss();
                    int code = response.code();
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
                    AppController.getInstance().CustomeDialog(Admin_AbsensiActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){
            AppController.getInstance().CustomeDialog(Admin_AbsensiActivity.this, e.getMessage());
            progress.dismiss();
        }

    }

    void FillGrid(){
         AppController.getInstance().getSessionManager().setListPresence(tblPresence);

        mAdapter = new AdapterAdminAbsensi(this, tblPresence, new AdapterAdminAbsensi.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                Admin_AbsensiActivity.this,
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
        sDATE = year + "-" + sMonth + "-" + sDay;
        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(sDATE));

        SyncData(sDATE);
    }
}
