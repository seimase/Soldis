package com.soldis.yourthaitea.dashboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashbordListProduct;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.transaction.CustcardActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductTmp;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by User on 8/13/2017.
 */

public class ListDashboardProductActivity extends AppCompatActivity {
    TextView txtLabel,
            txtNamaToko,
            txtAlamat,
            txtTgl,
            txtOrderNo,
            txtRemark
    ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;


    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    String sCustNo, sCustName, sAddress, sOrderNo, sTanggal;

    double dSubtotal = 0;
    boolean bStatus;
    String TIMEIN = "";
    String sLat, sLng, sPhotoName = "", REMARK;

    public static Tbl_List_Motoris listTrx;
    Tbl_List_Motoris listMotoris;

    public static ArrayList<Tmp_MASTER> tmpMasters;
    RelativeLayout layout_back;

    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_input_product_vew);

        InitControl();
        TIMEIN = AppController.getInstance().getTime();
        sLat = "0.0";
        sLng = "0.0";

        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            sCustName = getIntent().getExtras().getString("CUSTNAME");
            sAddress = getIntent().getExtras().getString("ADDRESS");
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            sOrderNo = getIntent().getExtras().getString("ORDERNO");
            sPhotoName = getIntent().getExtras().getString("PHOTO");
            REMARK = getIntent().getExtras().getString("REMARK");
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);

            sTanggal = getIntent().getExtras().getString("TANGGAL");
            txtRemark.setText(REMARK);
            txtTgl.setText(sTanggal);
        }catch (Exception e){
            sPhotoName = "";
            txtNamaToko.setText("");
            txtAlamat.setText("");
            txtRemark.setText("");
        }

        txtLabel.setText("Data Penjualan");

        txtOrderNo.setText(sOrderNo);

        FillGrid();

        if (sPhotoName != null && !sPhotoName.equals("")){
            AppController.getInstance().displayImage(ListDashboardProductActivity.this , AppConstant.PHOTO_OUTLET_URL + sPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        AppController.getInstance().hideKeyboardFrom(ListDashboardProductActivity.this);
    }

    void InitControl(){
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtRemark = (TextView) findViewById(R.id.txtRemark);

        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtNamaToko = (TextView)findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        txtTgl = (TextView)findViewById(R.id.text_tgl);
        txtTgl.setText(AppConstant.strTglTrx);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

     void FillGrid(){
        Tmp_MASTER tmpMaster;
        tmpMasters = new ArrayList<>();

        listMotoris = new Tbl_List_Motoris();
        listMotoris.trx_d = new ArrayList<>();
        double dTotal = 0;
        for (Tbl_List_Motoris.TrxD trx : listTrx.trx_d){
            tmpMaster = new Tmp_MASTER();
            tmpMaster.PRODUCT = trx.PCODE;
            tmpMasters.add(tmpMaster);

            if (trx.ORDERDATE!= null)
            txtTgl.setText("Tanggal transaksi : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(trx.ORDERDATE));
        }

        //txtSubtotal.setText(AppController.getInstance().toCurrencyRp(dTotal));
        mAdapter = new AdapterInputProductTmp(this, tmpMasters, new AdapterInputProductTmp.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(int Position){

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

}
