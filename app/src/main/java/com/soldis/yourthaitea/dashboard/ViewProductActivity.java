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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.adapter.AdapterViewProduct;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.transaction.CustcardActivity;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;
import com.soldis.yourthaitea.transaction.taking_order.OrderConfirmationActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProduct;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductTmp;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class ViewProductActivity extends AppCompatActivity {
    TextView
            txtNamaToko,
            txtAlamat,
            txtTgl,
            txtOrderNo,
            txtTotalSKU
                    ;
    TextView txtRemark;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    RelativeLayout layout_back;

    String sCustNo, sCustName, sAddress, sTgl, sOrderNo;

    boolean bStatus;
    String sPhotoName = "";

    public static ArrayList<Tmp_MASTER> tmpMasters;

    Obj_TRX_D trxD = new Obj_TRX_D();

    ImageView imgAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_view_product);

        InitControl();

        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            sCustName = getIntent().getExtras().getString("CUSTNAME");
            sAddress = getIntent().getExtras().getString("ADDRESS");
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);
        }catch (Exception e){
            txtNamaToko.setText("");
            txtAlamat.setText("");
        }

        try{
            sOrderNo = getIntent().getExtras().getString("ORDERNO");
        }catch (Exception e){
            sOrderNo = "";
        }


        txtOrderNo.setText(sOrderNo);

        Obj_CUSTMST objCustmst = new Obj_CUSTMST();
        objCustmst = objCustmst.getData(sCustNo);
        sPhotoName = objCustmst.getPHOTO_NAME();

        if (sPhotoName != null && !sPhotoName.equals("")){
            AppController.getInstance().displayImage(ViewProductActivity.this , AppConstant.PHOTO_OUTLET_URL + sPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        FillGrid();
    }

    void InitControl(){
        txtTgl = (TextView)findViewById(R.id.text_tgl);
        txtNamaToko = (TextView)findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtTotalSKU = (TextView)findViewById(R.id.txtTotalSKU);
        txtRemark = (TextView) findViewById(R.id.txtRemark);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);;

        imgAvatar = (ImageView)findViewById(R.id.img_avatar);

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
        for (Obj_TRX_D dat : trxD.getDataList(sOrderNo)){
            tmpMaster = new Tmp_MASTER();
            tmpMaster.PRODUCT = dat.getPCODE();
            tmpMasters.add(tmpMaster);
        }

        Obj_TRX_H objTrxH = new Obj_TRX_H();
        objTrxH = objTrxH.getData(sOrderNo);
        txtRemark.setText((objTrxH.getREMARK() == null ? "" : objTrxH.getREMARK()));
        sTgl = (objTrxH.getORDERDATE() == null ? "" : objTrxH.getORDERDATE());

        if (!sTgl.equals("")){
            txtTgl.setText("Tanggal transaksi : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(sTgl));
        }else{
            txtTgl.setText("Tanggal transaksi : ");
        }


        if (tmpMasters.size() > 0) txtTotalSKU.setText(tmpMasters.size() + " Items");

        mAdapter = new AdapterInputProductTmp(this, tmpMasters, new AdapterInputProductTmp.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(int Position){

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
                //ValidasiTransaksi();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_CALL) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

            return true;
        }else if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }

        return false;
    }
}
