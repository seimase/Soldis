package com.soldis.yourthaitea.dashboard.data_outlet;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.data_outlet.adapter.AdapterDashboardASMListDepoOutlet;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ftctest on 29/09/2017.
 */

public class Dashboard_ASM_OutletActivity extends AppCompatActivity {
    TextView txtName, txtId, txtLabel,
            txtSync, txtTgl,
            txtLabelDate
    ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ProgressDialog progress;

    Tbl_ListDepo listDepo;
    LinearLayout lySync;
    String ZONEID = "";

    RelativeLayout layout_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_asm_depo);
        try{
            ZONEID = getIntent().getExtras().getString("ZONEID");
        }catch (Exception e){
            ZONEID = "";
        }

        InitControl();
        SyncData(AppController.getInstance().getDateYYYYMMDD());

    }

    void InitControl(){
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        txtLabelDate = (TextView)findViewById(R.id.txtLabelDate);
        txtSync = (TextView)findViewById(R.id.txtSync);
        txtTgl = (TextView)findViewById(R.id.text_tgl);

        txtName = (TextView)findViewById(R.id.text_name);
        txtId = (TextView)findViewById(R.id.text_id);
        txtLabel = (TextView)findViewById(R.id.txtLabel);
        txtName.setText(AppConstant.strSlsName);
        txtId.setText(AppConstant.strSlsNo);

        txtLabel.setText(getResources().getString(R.string.master_data_outlet));
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
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

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lySync = (LinearLayout)findViewById(R.id.layout_sync);

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
            Call<Tbl_ListDepo> call;
            if (ZONEID.equals("")){
                call = NetworkManager.getNetworkService().ListDepoOnly(AppConstant.strMitraID,
                        AppConstant.strSlsNo,
                        sDATE);
            }else{
                call = NetworkManager.getNetworkService().ListDepoOnlyBot(AppConstant.strMitraID,
                        AppConstant.strSlsNo,
                        ZONEID,
                        sDATE);
            }



            call.enqueue(new Callback<Tbl_ListDepo>() {
                @Override
                public void onResponse(Call<Tbl_ListDepo> call, Response<Tbl_ListDepo> response) {
                    progress.dismiss();
                    int code = response.code();
                    if (code == 200){
                        listDepo = response.body();
                        if (!listDepo.error){
                            FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_ListDepo> call, Throwable t) {
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
        mAdapter = new AdapterDashboardASMListDepoOutlet(this, listDepo, new AdapterDashboardASMListDepoOutlet.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

}
