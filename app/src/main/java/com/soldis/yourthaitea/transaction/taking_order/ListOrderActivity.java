package com.soldis.yourthaitea.transaction.taking_order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.master.adapter.ViewPagerAdapterMenuMaster;
import com.soldis.yourthaitea.transaction.CustcardActivity;
import com.soldis.yourthaitea.transaction.adapter.ViewPagerAdapterMenuCustcard;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterListOrder;
import com.soldis.yourthaitea.transaction.taking_order.adapter.ViewPagerAdapterListOrder;

import java.util.ArrayList;

public class ListOrderActivity extends AppCompatActivity {
    TextView txtNamaToko,
            txtAlamat,
            txtTgl,
            txtTotalPrice,
            txtTotalPenjualan,
            txtTotalKas,
            txtTotalUM
    ;

    CharSequence Titles[];
    int Numboftabs =3;
    ViewPager pager;
    ViewPagerAdapterListOrder adapterMenu;
    TabLayout tabs;

    RelativeLayout  lyBack;
    LinearLayout lyAdd;

    Obj_TRX_H objTrxH;
    ImageView imgAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_listorder);

        InitControl();
        objTrxH = new Obj_TRX_H();
        double dTotalPenjualan = 0,
                dTotalKas = 0,
                dTotalUM = 0,
                dTotalAll = 0;
        ;
        Obj_KAS objKas = new Obj_KAS();
        for (Obj_KAS dat : objKas.getDataList()){
            if (dat.getTYPE_KAS().equals("Y")){
                dTotalKas += dat.getAMOUNT();
            }else{
                dTotalKas -= dat.getAMOUNT();
            }
        }

        dTotalPenjualan = objTrxH.TotalInvAmount();
        dTotalUM = AppConstant.dblUangMakan;

        Obj_TRX_H objTrxH = new Obj_TRX_H();
        txtTotalPenjualan.setText(AppController.getInstance().toCurrency(objTrxH.TotalQtySales()) + " / " +
                AppController.getInstance().toCurrency(dTotalPenjualan));
        txtTotalKas.setText(AppController.getInstance().toCurrency(dTotalKas));
        if(dTotalKas < 0 ){
            txtTotalKas.setText(AppController.getInstance().toCurrency(dTotalKas * -1));
            txtTotalKas.setTextColor(getResources().getColor(R.color.red));
        }

        txtTotalUM.setText(AppController.getInstance().toCurrency(dTotalUM));
        txtTotalUM.setTextColor(getResources().getColor(R.color.red));

        dTotalAll = (dTotalPenjualan + dTotalKas) - dTotalUM;
        txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dTotalAll));

        if(dTotalAll < 0 ){
            txtTotalPrice.setTextColor(getResources().getColor(R.color.red));
        }
    }

    void InitControl(){
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        lyAdd = (LinearLayout)findViewById(R.id.layout_add);
        lyBack = (RelativeLayout)findViewById(R.id.layout_back);

        txtNamaToko = (TextView)findViewById(R.id.text_nama);
        txtAlamat = (TextView)findViewById(R.id.text_address);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtTotalPenjualan = (TextView)findViewById(R.id.txtTotalPenjualan);
        txtTotalKas = (TextView)findViewById(R.id.txtTotalKas);
        txtTotalUM = (TextView)findViewById(R.id.txtTotalUM);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
        txtNamaToko.setText(AppConstant.strCustName);
        txtAlamat.setText(AppConstant.strCustAddress);
        tabs = (TabLayout)findViewById(R.id.tabs);

        try{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("");
        }


        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(ListOrderActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        pager = (ViewPager)findViewById(R.id.pager);
        Titles = new CharSequence[]{
                "Order",
                "Produk",
                "Kas"
        };

        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ListOrderActivity.this, InputProductActivity.class);
                InputProductActivity.tmpMasters = new ArrayList<>();
                mIntent.putExtra("CUSTNO", AppConstant.strCustNo);
                mIntent.putExtra("ORDERNO", "");
                mIntent.putExtra("CUSTNAME", AppConstant.strCustName);
                mIntent.putExtra("ADDRESS", AppConstant.strCustAddress);
                mIntent.putExtra("STATUS", false);
                startActivity(mIntent);
            }
        });

        adapterMenu =  new ViewPagerAdapterListOrder(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapterMenu);

        tabs.setupWithViewPager(pager);
    }


}
