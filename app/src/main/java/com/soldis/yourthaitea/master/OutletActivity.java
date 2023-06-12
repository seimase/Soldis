package com.soldis.yourthaitea.master;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.TimeVisitActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.entity.Obj_VISIT;

import java.io.File;

public class OutletActivity extends AppCompatActivity {
    ImageView img_avatar;
    TextView txtRegister;
    TextView txtCompanyName,
            txtAddress,
            txtPhoneOutlet,
            txtKelurahan,
            txtBranch,
            txtTypeOut,
            text_tgl,
            txtKTPNo,
            txtKTPName,
            txtKTPAddress,
            txtNPWPNo,
            txtNPWPName,
            txtNPWPAddress,
            txtPhone,
            txtPaymentType,
            txtOrderPeriod,
            txtStartDate,
            txtEndDate,
            text_nama_toko
                    ;

    CheckBox chkContract;

    Obj_CUSTMST objCustmst;
    String CUSTNO, FLAGOUT;


    LinearLayout lyDetailOutlet;
    RelativeLayout layout_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }

        setContentView(R.layout.activity_master_dataoutlet);
        InitControl();


        try {
            CUSTNO = getIntent().getExtras().getString("CUSTNO");
            FLAGOUT = getIntent().getExtras().getString("FLAGOUT");
        }catch (Exception e){
            CUSTNO = "";
            FLAGOUT = "1";
        }

        if (FLAGOUT.equals("0") || FLAGOUT.equals("2")){
            txtRegister.setVisibility(View.GONE);
            lyDetailOutlet.setVisibility(View.VISIBLE);
        }else if (FLAGOUT.equals("1")){
            txtRegister.setVisibility(View.VISIBLE);
            lyDetailOutlet.setVisibility(View.GONE);
        }

        FillForm();
    }

    void InitControl(){
        img_avatar = (ImageView)findViewById(R.id.img_avatar);
        text_nama_toko = (TextView)findViewById(R.id.text_nama_toko);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        txtCompanyName = (TextView)findViewById(R.id.txtCompanyName) ;
        txtAddress = (TextView)findViewById(R.id.txtAddress) ;
        txtPhoneOutlet = (TextView)findViewById(R.id.txtPhoneOutlet) ;
        txtKelurahan = (TextView)findViewById(R.id.txtKelurahan) ;
        txtBranch = (TextView)findViewById(R.id.txtBranch) ;
        text_tgl = (TextView)findViewById(R.id.text_tgl) ;
        txtTypeOut = (TextView)findViewById(R.id.txtTypeOut) ;
        txtKTPNo = (TextView)findViewById(R.id.txtKTPNo) ;
        txtKTPName = (TextView)findViewById(R.id.txtKTPName) ;
        txtKTPAddress = (TextView)findViewById(R.id.txtKTPAddress) ;
        txtNPWPNo = (TextView)findViewById(R.id.txtNPWPNo) ;
        txtNPWPName = (TextView)findViewById(R.id.txtNPWPName) ;
        txtNPWPAddress = (TextView)findViewById(R.id.txtNPWPAddress) ;
        txtPhone = (TextView)findViewById(R.id.txtPhone) ;
        txtPaymentType = (TextView)findViewById(R.id.txtPaymentType) ;
        txtOrderPeriod = (TextView)findViewById(R.id.txtOrderPeriod) ;
        txtStartDate = (TextView)findViewById(R.id.txtStartDate) ;
        txtEndDate = (TextView)findViewById(R.id.txtEndDate) ;

        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

        chkContract = (CheckBox) findViewById(R.id.chkContract) ;

        lyDetailOutlet = (LinearLayout)findViewById(R.id.layout_detail_outlet);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidation()){
                    Intent mIntent = new Intent(getBaseContext(), AddOutletRegActivity.class);
                    mIntent.putExtra("CUSTNO", CUSTNO);
                    startActivity(mIntent);
                    finish();
                }else{
                    Intent mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void FillForm(){
        objCustmst = new Obj_CUSTMST();

        Obj_TYPEOUT objTypeout;
        String TYPEOUT = "";

        if (CUSTNO != null){
            objCustmst = objCustmst.getData(CUSTNO);
            text_nama_toko.setText((objCustmst.getCUSTNAME() == null ? "" : objCustmst.getCUSTNAME()));
            txtCompanyName.setText((objCustmst.getCOMPANY_NAME() == null ? "" : objCustmst.getCOMPANY_NAME()));
            txtAddress.setText((objCustmst.getCUSTADD1() == null ? "" : objCustmst.getCUSTADD1()));
            txtPhoneOutlet.setText((objCustmst.getCPHONE1() == null ? "" : objCustmst.getCPHONE1()));
            txtKelurahan.setText((objCustmst.getKELURAHAN() == null ? "" : objCustmst.getKELURAHAN()));

            TYPEOUT = (objCustmst.getTYPEOUT() == null ? "" : objCustmst.getTYPEOUT());
            objTypeout = new Obj_TYPEOUT();
            objTypeout = objTypeout.getData(TYPEOUT);
            txtTypeOut.setText((objTypeout.getTYPENAME() == null ? "" : objTypeout.getTYPENAME().replace("AFH ", "")));

            txtBranch.setText(Integer.toString(objCustmst.getNUMBER_OF_BRANCH()));
            text_tgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY((objCustmst.getDATE_NEXT_VISIT() == null ? "" : objCustmst.getDATE_NEXT_VISIT())));
            txtKTPNo.setText((objCustmst.getKTP_ID() == null ? "" : objCustmst.getKTP_ID()));
            txtKTPName.setText((objCustmst.getKTP_NAME() == null ? "" : objCustmst.getKTP_NAME()));
            txtKTPAddress.setText((objCustmst.getKTP_ADDRESS() == null ? "" : objCustmst.getKTP_ADDRESS()));
            txtNPWPNo.setText((objCustmst.getNPWP_ID() == null ? "" : objCustmst.getNPWP_ID()));
            txtNPWPName.setText((objCustmst.getNPWP_NAME() == null ? "" : objCustmst.getNPWP_NAME()));
            txtNPWPAddress.setText((objCustmst.getNPWP_ADDRESS() == null ? "" : objCustmst.getNPWP_ADDRESS()));
            txtOrderPeriod.setText((objCustmst.getPERIODE_ORDER() == null ? "" : objCustmst.getPERIODE_ORDER()));
            txtPaymentType.setText((objCustmst.getPAYMENT_TYPE() == null ? "" : objCustmst.getPAYMENT_TYPE()));
            txtStartDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY((objCustmst.getSTARTDATE_CONTRACT() == null ? "" : objCustmst.getSTARTDATE_CONTRACT())));
            txtEndDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY((objCustmst.getENDDATE_CONTRACT() == null ? "" : objCustmst.getENDDATE_CONTRACT())));

            String sPhotoName = "";
            if (objCustmst.getPHOTO_NAME() != null) sPhotoName = AppConstant.PATH_PHOTO + "/" + objCustmst.getPHOTO_NAME();
            File file = new File(sPhotoName);
            if (file.exists()){
                AppController.getInstance().displayImage(OutletActivity.this, sPhotoName, img_avatar);
            }else{
                AppController.getInstance().displayImage(OutletActivity.this, AppConstant.PHOTO_OUTLET_URL + "/" + objCustmst.getPHOTO_NAME(),img_avatar);
            }
        }
    }

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

    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") )
            return false;

        if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") )
            return false;

        return true;
    }
}
