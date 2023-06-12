package com.soldis.yourthaitea.master;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.soldis.yourthaitea.TimeVisitActivity;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_PRODUCT_CATEGORY;
import com.soldis.yourthaitea.entity.Obj_STOCK_MAPPING;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.master.adapter.ViewPagerAdapterMenuMaster;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Visit;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.stock.ListStockActivity;
import com.soldis.yourthaitea.util.GPSTracker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDataActivity extends AppCompatActivity {
    int CURRENT_POSITION = 1;
    static final int PRODUCT = 1;
    static final int OUTLET = 2;

    CharSequence Titles[];
    int Numboftabs =2;
    ViewPager pager;
    ViewPagerAdapterMenuMaster adapterMenu;

    LinearLayout layout_master_product,
            layout_master_outlet;

    ImageView img_menu_product,
            img_menu_outlet;

    Toolbar toolbar;

    public int position_product = 0,
            position_outlet = 1;

    TextView txt_menu_product,
            txt_menu_outlet,
            txtLabel
                    ;

    RelativeLayout lyAdd, lyBack, lySync;

    Tbl_Master tblMaster;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_master_data);
        InitControl();
    }

    private void InitControl(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        lyAdd = (RelativeLayout)findViewById(R.id.layout_add);
        lyBack = (RelativeLayout)findViewById(R.id.layout_back);
        lySync  = (RelativeLayout)findViewById(R.id.layout_sync);

        layout_master_product = (LinearLayout)findViewById(R.id.layout_master_product);
        layout_master_outlet = (LinearLayout)findViewById(R.id.layout_master_outlet);
        img_menu_product = (ImageView)findViewById(R.id.img_menu_product);
        img_menu_outlet = (ImageView)findViewById(R.id.img_menu_outlet);
        txt_menu_product = (TextView)findViewById(R.id.text_menu_product);
        txt_menu_outlet = (TextView)findViewById(R.id.text_menu_outlet);
        txtLabel = (TextView)findViewById(R.id.textLabel);

        img_menu_product.setColorFilter(getResources().getColor(R.color.colorGreen));
        txt_menu_product.setTextColor(getResources().getColor(R.color.colorGreen));

        img_menu_outlet.setColorFilter(getResources().getColor(R.color.grey));
        txt_menu_outlet.setTextColor(getResources().getColor(R.color.grey));

        txtLabel.setText(getResources().getString(R.string.master_data_product));

        pager = (ViewPager)findViewById(R.id.pager);
        Titles = new CharSequence[]{
                getResources().getString(R.string.master_product),
                getResources().getString(R.string.master_outlet)
        };

        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        txtLabel.setText(getResources().getString(R.string.master_data_product));

                        img_menu_product.setColorFilter(getResources().getColor(R.color.colorGreen));
                        txt_menu_product.setTextColor(getResources().getColor(R.color.colorGreen));

                        img_menu_outlet.setColorFilter(getResources().getColor(R.color.grey));
                        txt_menu_outlet.setTextColor(getResources().getColor(R.color.grey));


                        break;
                    case 1:

                        txtLabel.setText(getResources().getString(R.string.daily_classification));
                        img_menu_outlet.setColorFilter(getResources().getColor(R.color.colorGreen));
                        txt_menu_outlet.setTextColor(getResources().getColor(R.color.colorGreen));

                        img_menu_product.setColorFilter(getResources().getColor(R.color.grey));
                        txt_menu_product.setTextColor(getResources().getColor(R.color.grey));

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layout_master_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(position_product);
            }
        });

        layout_master_outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(position_outlet);
            }
        });



        adapterMenu =  new ViewPagerAdapterMenuMaster(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapterMenu);

        lySync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncProduct();
            }
        });

        lyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MasterDataActivity.this, AddProductActivity.class);
                startActivity(mIntent);
            }
        });

    }



    public void PagerCurrentItem(int position){
        pager.setCurrentItem(position);
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

                            Obj_PRODUCT_CATEGORY productCategory = new Obj_PRODUCT_CATEGORY();
                            if(tblMaster.category.size() > 0){
                                productCategory.DeleteData();
                                for (Tbl_Master.Category dat : tblMaster.category ){
                                    productCategory = new Obj_PRODUCT_CATEGORY();
                                    productCategory.setCATEGORY_ID(dat.CATEGORY_ID);
                                    productCategory.setCATEGORY_NAME(dat.CATEGORY_NAME);
                                    productCategory.CreateDate(productCategory);
                                }
                            }

                            FillGrid();

                        }

                    }
                    progress.dismiss();

                    AppController.getInstance().CustomeDialog(MasterDataActivity.this, tblMaster.message);
                }

                @Override
                public void onFailure(Call<Tbl_Master> call, Throwable t) {
                    progress.dismiss();
                    AppController.getInstance().CustomeDialog(MasterDataActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){

            progress.dismiss();
            AppController.getInstance().CustomeDialog(MasterDataActivity.this, e.getMessage());
        }
    }

    void FillGrid(){
        adapterMenu =  new ViewPagerAdapterMenuMaster(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapterMenu);
        progress.dismiss();
    }

}
