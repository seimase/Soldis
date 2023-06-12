package com.soldis.yourthaitea.transaction.maintenance;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.MainActivity;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_COMPLAINT;
import com.soldis.yourthaitea.entity.Obj_MAINTENANCE;
import com.soldis.yourthaitea.transaction.adapter.ViewPagerAdapterMenuCustcard;
import com.soldis.yourthaitea.transaction.complaint.ComplainActivity;
import com.soldis.yourthaitea.transaction.maintenance.adapter.ViewPagerAdapterMaintenance;

public class MaintenanceActivity extends AppCompatActivity {
    TextView txtLabel,
            text_nama_toko,
            text_alamat,
            txtTgl
                    ;
    Toolbar toolbar;

    Obj_MAINTENANCE objMaintenance;

    CharSequence Titles[];
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterMaintenance adapter;
    TabLayout tabs;

    RelativeLayout layout_back;
    ImageView imgAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_maintain);

        Titles = new CharSequence[]{
                "Maintenance",
                "Dispenser"
        };

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void InitControl() {
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        txtTgl = (TextView)findViewById(R.id.text_tgl);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        text_nama_toko = (TextView)findViewById(R.id.text_nama_toko);
        text_alamat = (TextView)findViewById(R.id.text_alamat);
        txtLabel.setText("Maintenance");
        text_nama_toko.setText(AppConstant.strCustName);
        text_alamat.setText(AppConstant.strCustAddress);

        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterMaintenance(getSupportFragmentManager(),Titles,Numboftabs);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        try{
            txtTgl.setText("Tanggal transaksi : "  + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("Tanggal transaksi : ");
        }

        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(MaintenanceActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
}
