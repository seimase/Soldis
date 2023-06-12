package com.soldis.yourthaitea.transaction.maintenance;

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
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_DISPENSER;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_STOCK_TMP;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.stock.InputStockActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterOrderConfimation;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class DispenserConfirmationActivity extends AppCompatActivity {
    TextView txtLabel,
            txtNamaToko,
            txtAlamat,
            txtSave,
            txtAddMoreItems,
            txtTypeName,
            txtTotalSKU
    ;
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;


    String sCustNo, sCustName, sAddress, sTgl, sOrderNo;

    double dSubtotal = 0;
    long lQty = 0;
    int iTotalSKU = 0;
    boolean bStatus;

    Obj_DISPENSER objDispenser;
    boolean bDone = false;

    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dispenser_confirmation);

        InitControl();
        try {
            sCustNo = AppConstant.strCustNo;
            sCustName = AppConstant.strCustName;
            sAddress = AppConstant.strCustAddress;
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);
        }catch (Exception e){
            bStatus = false;
            txtNamaToko.setText("");
            txtAlamat.setText("");
        }

        try{
            sOrderNo = getIntent().getExtras().getString("ORDERNO");
        }catch (Exception e){
            sOrderNo = "";
        }

        txtLabel.setText("ORDER CONFIRMATION");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FillGrid();

        AppController.getInstance().hideKeyboardFrom(DispenserConfirmationActivity.this);
    }

    void InitControl(){
        txtAddMoreItems = (TextView)findViewById(R.id.txtAddMoreItems);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtNamaToko = (TextView)findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);

        txtTypeName = (TextView)findViewById(R.id.txtTypeName);
        txtTotalSKU = (TextView)findViewById(R.id.txtTotalSKU);
        txtTypeName.setText(AppConstant.strCustTypeName);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialog();
            }
        });

        txtAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    void FillGrid(){
       //AppController.getInstance().CustomeDialog(InputProductActivity.this, Status);

        iTotalSKU = 0;
        for (Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getQTY_TRX() > 0) {
                iTotalSKU += 1;
            }
        }
        txtTotalSKU.setText(AppController.getInstance().toCurrency(iTotalSKU));
        mAdapter = new AdapterOrderConfimation(this, AppConstant.objMasters, false, new AdapterOrderConfimation.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(){
                dSubtotal = 0;
                lQty = 0;
                iTotalSKU = 0;
                for (Obj_MASTER dat : AppConstant.objMasters){
                    dSubtotal += dat.getTOTAL();
                    lQty += dat.getQTY_TRX();
                    if (dat.getQTY_TRX() > 0) {
                        iTotalSKU += 1;
                    }
                }

                txtTotalSKU.setText(AppController.getInstance().toCurrency(iTotalSKU));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.stock_saved));
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SaveData();
                if (bDone){
                    //lyAdd.setVisibility(View.VISIBLE);
                    //lySave.setVisibility(View.GONE);
                    //AppController.getInstance().CustomeDialog(InputProductActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                    AppController.getInstance().BackUpDB(DispenserConfirmationActivity.this);
                    setResult(RESULT_OK);
                    CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
                }else{
                    AppController.getInstance().CustomeDialog(DispenserConfirmationActivity.this, getResources().getString(R.string.daily_product_not_selected));
                }

            }
        });

        dialog.show();
    }

    void CustomeDialogProses(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_data_in_process));
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                objCustcard.UpdateFlagProses(sCustNo);
                AppController.getInstance().CustomeDialog(DispenserConfirmationActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                AppController.getInstance().BackUpDB(DispenserConfirmationActivity.this);
            }
        });

        dialog.show();
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


    void CustomeDialog(String ISI){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void CustomeDialogSave(String ISI){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    void SaveData(){
        objDispenser = new Obj_DISPENSER();
        objDispenser.DeleteData(AppConstant.strCustNo);
        bDone = false;
        for(Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getINP_UOM1().equals("")) dat.setINP_UOM1("0");
            if (dat.getINP_UOM2().equals("")) dat.setINP_UOM2("0");
            if (dat.getINP_UOM3().equals("")) dat.setINP_UOM3("0");

            int iQty_B = Integer.parseInt(dat.getINP_UOM1());
            int iQty_T = Integer.parseInt(dat.getINP_UOM2());
            int iQty_K =  (int)dat.getQTY_TRX();

            if (iQty_K > 0){
                long iQtyTotal = (iQty_B * dat.getConvUnit2() * dat.getConvUnit3()) +
                        (iQty_T * dat.getConvUnit2()) +
                        (iQty_K)
                        ;

                objDispenser = new Obj_DISPENSER();
                objDispenser.setCUSTNO(AppConstant.strCustNo);
                objDispenser.setPCODE(dat.getPCode());
                objDispenser.setQTY((int)iQtyTotal);
                objDispenser.CreateDate(objDispenser);
                bDone = true;
            }
        }
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
