package com.soldis.yourthaitea.stock;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_STOCK_MAPPING;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.service.ServiceAutoLogOut;
import com.soldis.yourthaitea.stock.adapter.AdapterInputStock;
import com.soldis.yourthaitea.stock.adapter.AdapterListStock;
import com.soldis.yourthaitea.transaction.pembelian.PembelianActivity;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;
import com.soldis.yourthaitea.transaction.taking_order.ListOrderActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStockActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    RelativeLayout layout_back;
    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    Obj_STOCK objStock;
    TextView txtPembelian,
            txtTgl;
    Obj_VISIT objVisit ;
    boolean bDone = false;
    double dSubtotal = 0;

    LinearLayout LySyncProduct;
    ProgressDialog progress;

    Tbl_Master tblMaster;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_list_stock);
        InitControl();
    }

    void InitControl(){
        txtPembelian = (TextView)findViewById(R.id.txtPembelian);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        LySyncProduct = (LinearLayout)findViewById(R.id.layout_sync_product);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        try{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("");
        }

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ListStockActivity.this, PembelianActivity.class);
                startActivity(mIntent);
            }
        });

        LySyncProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncProduct();
            }
        });
    }

    void FillGrid(){
        objEmasters = new ArrayList<>();
        objEmaster = new Obj_MASTER();
        boolean bStock = false;
        objEmasters = objEmaster.getDataListStock();
        if (objEmasters.size() > 0){
            bStock = true;
        }

        mAdapter = new AdapterListStock(this, objEmasters, bStock,  new AdapterListStock.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String PCODE, String PCODENAME) {
                //CustomeDialogInputStock(PCODE, PCODENAME);
                Intent mIntent = new Intent(getBaseContext(), InputStockActivity.class);
                mIntent.putExtra("PCODE", PCODE);
                mIntent.putExtra("PCODENAME", PCODENAME);
                startActivity(mIntent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }


    void SyncProduct() {
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.tv_sync_start), true);

        try{
            Call<Tbl_Master> call = NetworkManager.getNetworkService().MasterProduct(AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Master>() {
                @Override
                public void onResponse(Call<Tbl_Master> call, Response<Tbl_Master> response) {
                    int code = response.code();

                    if (code ==200){
                        tblMaster = response.body();

                        if (!tblMaster.error){
                            if (tblMaster.master.size() > 0){
                                Obj_MASTER objMaster = new Obj_MASTER();
                                objMaster.DeleteMaster();
                                for(Tbl_Master.Master dat : tblMaster.master){
                                    objMaster = new Obj_MASTER();
                                    objMaster.setPCode(dat.PCODE);
                                    objMaster.setPCodeName(dat.PCODENAME);
                                    objMaster.setPHOTO_NAME(dat.PHOTO_NAME);
                                    objMaster.setFLAG_SALES(dat.FLAG_SALES);
                                    objMaster.setConvUnit2(dat.CONVUNIT2);
                                    objMaster.setConvUnit3(dat.CONVUNIT3);
                                    objMaster.setUnit1(dat.UNIT1);
                                    objMaster.setUnit2(dat.UNIT2);
                                    objMaster.setUnit3(dat.UNIT3);
                                    objMaster.setSellPrice1(dat.SELLPRICE);
                                    objMaster.setSellPrice2(dat.SELLPRICE);
                                    objMaster.setSellPrice3(dat.SELLPRICE);
                                    objMaster.setPrLin(dat.CATEGORY_ID);
                                    objMaster.CreateMaster(objMaster);
                                }
                            }

                            if (tblMaster.stock_mapping.size() > 0){
                                Obj_STOCK_MAPPING objStockMapping = new Obj_STOCK_MAPPING();
                                objStockMapping.DeleteData();

                                for (Tbl_Master.StockMapping dat : tblMaster.stock_mapping ){
                                    objStockMapping = new Obj_STOCK_MAPPING();
                                    objStockMapping.setPCODE_SALES(dat.PCODE_SALES);
                                    objStockMapping.setPCODE(dat.PCODE);
                                    objStockMapping.setQTY(dat.QTY);
                                    objStockMapping.CreateDate(objStockMapping);
                                }
                            }
                            FillGrid();
                            progress.dismiss();
                        }

                    }
                    progress.dismiss();

                    AppController.getInstance().CustomeDialog(ListStockActivity.this, tblMaster.message);
                }

                @Override
                public void onFailure(Call<Tbl_Master> call, Throwable t) {
                    progress.dismiss();
                    AppController.getInstance().CustomeDialog(ListStockActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){

            progress.dismiss();
            AppController.getInstance().CustomeDialog(ListStockActivity.this, e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FillGrid();
    }
}
