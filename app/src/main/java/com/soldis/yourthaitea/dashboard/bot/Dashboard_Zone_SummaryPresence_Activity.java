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
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.bot.adapter.AdapterDashboardListZonePresenceSummary;
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

public class Dashboard_Zone_SummaryPresence_Activity extends AppCompatActivity  {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_zone_summary);

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

        txtLabel.setText(getResources().getString(R.string.main_presence));
        txtLabelList.setText("LIST ZONE");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        // use a LinearLayoutManager
        mRecyclerView.setLayoutManager(mLayoutManager);

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
            call = NetworkManager.getNetworkService().ListZoneOnly(AppConstant.strSlsNo);

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
        mAdapter = new AdapterDashboardListZonePresenceSummary(this, listZone, new AdapterDashboardListZonePresenceSummary.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

}
