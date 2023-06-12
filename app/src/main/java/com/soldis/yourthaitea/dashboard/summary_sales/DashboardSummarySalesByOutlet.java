package com.soldis.yourthaitea.dashboard.summary_sales;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.fragment.adapter.TargetAdapter;
import com.soldis.yourthaitea.dashboard.summary_sales.adapter.AdapterDashboardSummarySalesByOutlet;
import com.soldis.yourthaitea.dashboard.summary_sales.adapter.TargetAdapterSummaryByOutlet;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.Tbl_TargetTmp;

import java.util.ArrayList;

/**
 * Created by ftctest on 11/09/2017.
 */

public class DashboardSummarySalesByOutlet extends AppCompatActivity {

    int iEC, iEC_MTD;
    Toolbar toolbar;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    double dTarget = 0;
    double dSales = 0;
    double dSales_MTD = 0;

    TextView txtTgl;

    //TextView txtName, txtId, txtLabel, txtLabelDate;
    String SLSNO, KODECABANG;

    public static Tbl_List_Motoris.Datum tblListMotoris;
    public static Tbl_List_Motoris listTrx;
    public static boolean bDays;

    RelativeLayout layout_back;
    String sPhotoName;
    ImageView imgAvatar;

    ViewPager pager;
    TargetAdapterSummaryByOutlet targetAdapter;
    Tbl_TargetTmp tblTargetTmp;
    ArrayList<Tbl_TargetTmp> tblTargetTmps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard_summary_sales_byoutlet);
        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SLSNO = "";
        KODECABANG = "";
        try{
            //txtId.setText(tblListMotoris.SLSNO);
            //txtName.setText(tblListMotoris.SLSNAME);

            SLSNO = tblListMotoris.SLSNO;
            if (tblListMotoris.PHOTO != null && !tblListMotoris.PHOTO.equals("")){
                AppController.getInstance().displayImage(DashboardSummarySalesByOutlet.this , AppConstant.PHOTO_MOTORIS_URL + tblListMotoris.PHOTO, imgAvatar);
            }else{
                imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
            }

        }catch (Exception e){
            //txtId.setText("");
            //txtName.setText("");
        }

        FillGrid();
    }

    private void InitControl() {
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);

        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

        txtTgl = (TextView)findViewById(R.id.txtTgl);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        pager = (ViewPager)findViewById(R.id.pager);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void FillGrid(){
        iEC_MTD = 0;
        dSales_MTD = 0;

        /*txtECTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_EC_MTD));
        txtECTargetMTD.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_EC_MTD));
        txtSalesTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_SALES_MTD));
        txtSalesTargetMTD.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_SALES_MTD));
*/
        iEC = 0;
        dSales = 0;
        String TGLTRX = "";
        String sCUSTNO ="";
        for(Tbl_List_Motoris.Trx dat : listTrx.trx){
            if (dat.SLSNO.equals(SLSNO)){
                if (dat.ORDERDATE != null) TGLTRX = dat.ORDERDATE;
                if (!sCUSTNO.equals(dat.CUSTNO)){
                    iEC += 1;
                }

                sCUSTNO = dat.CUSTNO;
                dSales += dat.INVAMOUNT;
            }
        }
        /*txtECPencapaian.setText(AppController.getInstance().toCurrency((tblListMotoris.TOTAL_EC == 0 ? iEC : tblListMotoris.TOTAL_EC)));
        txtECPencapaianMTD.setText(AppController.getInstance().toCurrency(tblListMotoris.TOTAL_EC));
        txtSalesPencapaian.setText(AppController.getInstance().toCurrency(tblListMotoris.TOTAL_SALES));
        txtCallPencapaian.setText(AppController.getInstance().toCurrency(tblListMotoris.TOTAL_CALL));
        txtSalesPencapaianMTD.setText(AppController.getInstance().toCurrency(dSales));*/

        tblTargetTmps = new ArrayList<>();
        if (bDays){
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(TGLTRX));
            //txtLabelDate.setText(getResources().getString(R.string.dash_days));
            /*txtECTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_EC));
            txtSalesTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_SALES));
            txtCallTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_CALL));*/

            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "EC Target";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_EC;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_EC;
            tblTargetTmps.add(tblTargetTmp);


            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "Call Target";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_CALL;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_CALL;
            tblTargetTmps.add(tblTargetTmp);

            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "Penjualan";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_SALES;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_SALES;
            tblTargetTmps.add(tblTargetTmp);


        }else{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoMMYYYY(TGLTRX));
            //txtLabelDate.setText(getResources().getString(R.string.dash_mtd));
            /*txtECTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_EC_MTD));
            txtSalesTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_SALES_MTD));
            txtCallTarget.setText(AppController.getInstance().toCurrency(tblListMotoris.TARGET_CALL_MTD));*/

            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "EC Target";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_EC_MTD;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_EC;
            tblTargetTmps.add(tblTargetTmp);


            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "Call Target";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_CALL_MTD;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_CALL;
            tblTargetTmps.add(tblTargetTmp);

            tblTargetTmp = new Tbl_TargetTmp();
            tblTargetTmp.TARGET_TYPE = "Penjualan";
            tblTargetTmp.TARGET_DAY = tblListMotoris.TARGET_SALES_MTD;
            tblTargetTmp.TARGET_MONTH = 0;
            tblTargetTmp.ACH_MONTH = 0;
            tblTargetTmp.ACH_DAY = tblListMotoris.TOTAL_SALES;
            tblTargetTmps.add(tblTargetTmp);
        }

        targetAdapter = new TargetAdapterSummaryByOutlet(DashboardSummarySalesByOutlet.this);
        targetAdapter.setData(tblTargetTmps);
        pager.setAdapter(targetAdapter);

        mAdapter = new AdapterDashboardSummarySalesByOutlet(this, listTrx, new AdapterDashboardSummarySalesByOutlet.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
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
}
