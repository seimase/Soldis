package com.soldis.yourthaitea.dashboard;

import android.os.Build;
import android.os.Bundle;
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
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashboardSummaryOutlet;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TRX_H;

import java.util.ArrayList;

/**
 * Created by ftctest on 18/10/2017.
 */

public class SummaryOutletActivity extends AppCompatActivity {
    TextView txtLabel,
            txtNamaToko,
            txtAlamat,
            txtTgl,
            txtSubtotal,
            txtTotalInvoice
                    ;
    String sCustNo, sCustName, sAddress, sTgl;
    boolean bStatus;

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    Obj_TRX_H objTrxH;
    Obj_CUSTMST objCustmst;
    ArrayList<Obj_CUSTMST> objCustmsts;
    ArrayList<Obj_TRX_H> objTrxHs;

    ImageView imgAvatar;
    RelativeLayout layout_back;
    String sPhotoName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_summary_outlet);

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            sCustName = getIntent().getExtras().getString("CUSTNAME");
            sAddress = getIntent().getExtras().getString("ADDRESS");
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);
            txtSubtotal.setText("");
        }catch (Exception e){
            txtNamaToko.setText("");
            txtAlamat.setText("");
            txtSubtotal.setText("");
        }


        Obj_CUSTMST objCustmst = new Obj_CUSTMST();
        objCustmst = objCustmst.getData(sCustNo);
        sPhotoName = objCustmst.getPHOTO_NAME();

        if (sPhotoName != null && !sPhotoName.equals("")){
            AppController.getInstance().displayImage(SummaryOutletActivity.this , AppConstant.PHOTO_OUTLET_URL + sPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        FillGrid();
    }

    void InitControl() {
        txtLabel = (TextView) findViewById(R.id.textLabel);
        txtNamaToko = (TextView) findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView) findViewById(R.id.text_alamat);
        txtTgl = (TextView)findViewById(R.id.text_tgl);
        txtTotalInvoice = (TextView)findViewById(R.id.text_total_inv);
        txtSubtotal = (TextView)findViewById(R.id.text_subtotal);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);;

        imgAvatar = (ImageView)findViewById(R.id.img_avatar);

        txtTgl.setText("Tanggal transaksi : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));

        txtLabel.setText(getResources().getString(R.string.dash_sales_data_label));
        toolbar = (Toolbar)findViewById(R.id.tool_bar);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
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

    void FillGrid(){
        objTrxH = new Obj_TRX_H();
        objTrxHs = new ArrayList<>();
        objTrxHs = objTrxH.getDataListCustNo(sCustNo);

        int iSKU = 0;
        int iTotalInv = 0;
        double dInvAmount = 0;
        for (Obj_TRX_H dat : objTrxHs){
            iSKU += dat.getSKU();
            dInvAmount += dat.getINVAMOUNT();
        }
        iTotalInv = objTrxHs.size();

        txtSubtotal.setText(AppController.getInstance().toCurrencyRp(dInvAmount));
        txtTotalInvoice.setText(Integer.toString(iTotalInv) + " Order");

        objCustmsts = new ArrayList<>();
        objCustmst = new Obj_CUSTMST();
        objCustmsts = objCustmst.getDataListDashboardByCustno(sCustNo);

        mAdapter = new AdapterDashboardSummaryOutlet(this, objCustmsts, new AdapterDashboardSummaryOutlet.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
