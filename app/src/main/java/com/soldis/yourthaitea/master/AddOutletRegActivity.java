package com.soldis.yourthaitea.master;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.util.GPSTracker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddOutletRegActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView txtPaymentType,
            txtOrderPeriod,
            txtStartDate,
            txtEndDate
    ;

    EditText edtKTPNo,
            edtKTPName,
            edtKTPAddress,
            edtNPWPNo,
            edtNPWPName,
            edtNPWPAddress,
            edtCompanyName,
            edtBranch,
            edtJRT,
            edtHRT,
            edtMultifold,
            edtROLL

            ;

    CheckBox chkContract, chkDispenser;

    static int REQ_PAYMENTYPE = 110;
    static int REQ_PERIODE = 120;
    String PAYMENT_TYPE, STARTDATE, ENDDATE, STATUS_CONTRACT;
    boolean bSTARTDATE;

    LinearLayout lyContract, lyPeriode, lyReg, lyContractHeader, lyDispenser;
    RelativeLayout layout_back;
    TextView lySave;

    Obj_CUSTMST objCustmst;
    String sCustNo;
    String STATUS_OUTLET;

    String sLat, sLng, sLatLng;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_add_customer_register);

        STARTDATE = "";
        ENDDATE = "";
        STATUS_CONTRACT = "N";

        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
        }catch (Exception e){
            sCustNo = "";
        }

        InitControl();

        sLat = "0.0";
        sLng= "0.0";
        sLatLng = sLat + "," + sLng;

        gps = new GPSTracker(getBaseContext());

    }

    private void InitControl() {

        txtPaymentType = (TextView) findViewById(R.id.txtPaymentType);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) findViewById(R.id.txtEndDate);
        txtOrderPeriod = (TextView) findViewById(R.id.txtOrderPeriod);
        chkContract = (CheckBox)findViewById(R.id.chkContract);
        chkDispenser = (CheckBox)findViewById(R.id.chkDispenser);

        edtKTPNo = (EditText) findViewById(R.id.edtKTPNo);
        edtKTPName = (EditText) findViewById(R.id.edtKTPName);
        edtKTPAddress = (EditText) findViewById(R.id.edtKTPAddress);
        edtNPWPNo = (EditText) findViewById(R.id.edtNPWPNo);
        edtNPWPName = (EditText) findViewById(R.id.edtNPWPName);
        edtNPWPAddress = (EditText) findViewById(R.id.edtNPWPAddress);
        edtBranch = (EditText)findViewById(R.id.edtBranch);
        edtCompanyName = (EditText)findViewById(R.id.edtCompanyName);

        edtJRT = (EditText)findViewById(R.id.edtJRT);
        edtHRT = (EditText)findViewById(R.id.edtHRT);
        edtMultifold = (EditText)findViewById(R.id.edtMultiFold);
        edtROLL = (EditText)findViewById(R.id.edtROLL);

        lyContract = (LinearLayout)findViewById(R.id.layout_contract);
        lyContractHeader = (LinearLayout)findViewById(R.id.layout_contract_header);
        lyPeriode = (LinearLayout)findViewById(R.id.layout_periode);
        lyReg = (LinearLayout)findViewById(R.id.layout_reg);
        lySave = (TextView) findViewById(R.id.txtSave);
        lyDispenser = (LinearLayout)findViewById(R.id.layout_dispenser);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

        /*lyContract.setVisibility(View.GONE);
        lyPeriode.setVisibility(View.GONE);*/

        lyReg.setVisibility(View.GONE);
        lyDispenser.setVisibility(View.GONE);
        layoutPeriodeEnable(false);
        layoutContractEnable(false);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lyPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(AddOutletRegActivity.this, DialogPerioderOrder.class);
                startActivityForResult(mIntent, REQ_PERIODE);
            }
        });

        txtPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(AddOutletRegActivity.this, DialogPaymentType.class);
                startActivityForResult(mIntent, REQ_PAYMENTYPE);
            }
        });

        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bSTARTDATE = true;
                showDialodDatePick();
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bSTARTDATE = false;
                showDialodDatePick();
            }
        });

        chkContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkContract.isChecked()){
                    lyContract.setVisibility(View.VISIBLE);
                }else{
                    STARTDATE = "";
                    ENDDATE = "";
                    //lyContract.setVisibility(View.GONE);
                }
            }
        });

        chkDispenser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkDispenser.isChecked()){
                    lyDispenser.setVisibility(View.VISIBLE);
                }else{
                    lyDispenser.setVisibility(View.GONE);
                    //lyContract.setVisibility(View.GONE);
                }
            }
        });

        lySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiInput()){
                    if (STATUS_OUTLET.equals("1")){
                        String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if(!providerGps.contains("gps")){ //if gps is disabled
                            DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                    getResources().getString(R.string.text_gps_is_off));
                            return;
                        }

                        // GPSTracker class
                        gps = new GPSTracker(AddOutletRegActivity.this);

                        sLng = "0.0";
                        // check if GPS enabled
                        if(gps.canGetLocation()){

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            sLat = Double.toString(latitude);
                            sLng = Double.toString(longitude);
                            // \n is for new line
                            if (sLng != null && !sLng.equals("0.0")){
                                sLatLng = sLat + "," + sLng;
                                //SyncGoogleMaps();
                                SaveOutlet();
                            }else{
                                AppController.getInstance().CustomeDialog(AddOutletRegActivity.this,
                                        getResources().getString(R.string.text_gps_not_locked));
                            }
                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                    }else{
                        SaveOutlet();
                    }

                }
            }
        });

        objCustmst = new Obj_CUSTMST();
        objCustmst = objCustmst.getData(sCustNo);
        STATUS_OUTLET = (objCustmst.getFLAGCUST() == null ? "" : objCustmst.getFLAGCUST());

        if (STATUS_OUTLET.equals("1")) lyReg.setVisibility(View.VISIBLE);

    }

    void SaveOutlet(){
        objCustmst = new Obj_CUSTMST();
        objCustmst.setKTP_ID(edtKTPNo.getText().toString().trim());
        objCustmst.setKTP_NAME(edtKTPName.getText().toString().trim());
        objCustmst.setKTP_ADDRESS(edtKTPAddress.getText().toString().trim());
        objCustmst.setNPWP_ID(edtNPWPNo.getText().toString().trim());
        objCustmst.setNPWP_NAME(edtNPWPName.getText().toString().trim());
        objCustmst.setNPWP_ADDRESS(edtNPWPAddress.getText().toString().trim());
        objCustmst.setPAYMENT_TYPE(txtPaymentType.getText().toString().trim());
        objCustmst.setPERIODE_ORDER(txtOrderPeriod.getText().toString().trim());

        String STATUS = "";
        String DISPENSER = "";
        if (chkContract.isChecked()){
            STATUS = "Y";
        }else{
            STATUS = "N";
        }

        String JRT, HRT, MULTIFOLD, ROLL;
        if (chkDispenser.isChecked()){
            DISPENSER = "Y";
            JRT = edtJRT.getText().toString();
            MULTIFOLD = edtHRT.getText().toString();
            ROLL = edtMultifold.getText().toString();
            HRT = edtROLL.getText().toString();

            if (JRT.equals("")) JRT = "0";
            if (MULTIFOLD.equals("")) MULTIFOLD = "0";
            if (ROLL.equals("")) ROLL = "0";
            if (HRT.equals("")) HRT = "0";
        }else{
            DISPENSER = "N";
            JRT = "0";
            MULTIFOLD = "0";
            ROLL = "0";
            HRT = "0";
        }

        Log.w("STATUS_OUTLET", "DISPENSER " + DISPENSER);
        Log.w("STATUS_OUTLET", "JRT " + JRT);
        Log.w("STATUS_OUTLET", "MULTIFOLD " + MULTIFOLD);
        Log.w("STATUS_OUTLET", "ROLL " + ROLL);
        Log.w("STATUS_OUTLET", "HRT " + HRT);
        objCustmst.setSTATUS_DISPENSER(DISPENSER);
        objCustmst.setDISPENSER_JRT(Integer.parseInt(JRT));
        objCustmst.setDISPENSER_HRT(Integer.parseInt(HRT));
        objCustmst.setDISPENSER_MULTIFOLD(Integer.parseInt(MULTIFOLD));
        objCustmst.setDISPENSER_ROLL(Integer.parseInt(ROLL));
        objCustmst.setSTATUS_CONTRACT(STATUS);
        objCustmst.setSTARTDATE_CONTRACT(STARTDATE);
        objCustmst.setENDDATE_CONTRACT(ENDDATE);
        objCustmst.setCUSTNO(sCustNo);

        Log.w("STATUS_OUTLET", STATUS_OUTLET);
        if (STATUS_OUTLET.equals("1")){
            objCustmst.UpdateDataReg(objCustmst);
        }else{
            //objCustmst.setCOMPANY_NAME(edtCompanyName.getText().toString());
            //objCustmst.setNUMBER_OF_BRANCH(Integer.parseInt(edtBranch.getText().toString()));
            objCustmst.UpdateDataReg(objCustmst);
        }


        CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
    }

    boolean ValidasiInput(){
        boolean bDone = true;

        String KTP_NO,
                KTP_NAME,
                KTP_ADDRESS,
                NPWP_NO,
                NPWP_NAME,
                NPWP_ADDRESS,
                PAYMENTTYPE,
                PERIODE_ORDER,
                STARTDATE,
                ENDDATE,
                sCompanyName,
                sBranch
        ;

        sCompanyName = edtCompanyName.getText().toString().trim();
        sBranch = edtBranch.getText().toString().trim();
        KTP_NO = edtKTPNo.getText().toString().trim();
        KTP_NAME = edtKTPName.getText().toString().trim();
        KTP_ADDRESS = edtKTPAddress.getText().toString().trim();
        NPWP_NO = edtNPWPNo.getText().toString().trim();
        NPWP_NAME = edtNPWPName.getText().toString().trim();
        NPWP_ADDRESS = edtNPWPAddress.getText().toString().trim();
        PAYMENTTYPE = txtPaymentType.getText().toString().trim();
        PERIODE_ORDER = txtOrderPeriod.getText().toString().trim();
        STARTDATE = txtStartDate.getText().toString().trim();
        ENDDATE = txtEndDate.getText().toString().trim();

        if (KTP_NO.equals("") && NPWP_NO.equals("")){
            AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                    getResources().getString(R.string.addoutlet_KTPNPWP_must_be_filled));
            return false;
        }

        if (!KTP_NO.equals("")){
            if (KTP_NAME.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_KTP_Name_must_be_filled));
                return false;
            }

            if (KTP_ADDRESS.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_KTP_Address_must_be_filled));
                return false;
            }
        }

        if (!NPWP_NO.equals("")){
            if (NPWP_NAME.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_NPWP_Name_must_be_filled));
                return false;
            }

            if (NPWP_ADDRESS.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_NPWP_Address_must_be_filled));
                return false;
            }
        }

        if (PAYMENTTYPE.equals("")){
            AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                    getResources().getString(R.string.addoutlet_paymenttype_must_be_filled));
            return false;
        }

        if (PAYMENTTYPE.equals("RUTIN")){
            if (PERIODE_ORDER.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_periodeorder_must_be_filled));
                return false;
            }
        }

        if (chkContract.isChecked()){
            if (STARTDATE.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_start_contract_must_be_filled));
                return false;
            }

            if (ENDDATE.equals("")){
                AppController.getInstance().CustomeDialog (AddOutletRegActivity.this,
                        getResources().getString(R.string.addoutlet_end_contract_must_be_filled));
                return false;
            }

        }

        if (STATUS_OUTLET.equals("1")){
            if (sCompanyName.equals("")){
                AppController.getInstance().CustomeDialog( AddOutletRegActivity.this, getResources().getString(R.string.addoutlet_outlet_name_must_be_filled));
                return false;
            }

            if (sBranch.equals("") || sBranch.equals("0")){
                AppController.getInstance().CustomeDialog( AddOutletRegActivity.this,getResources().getString(R.string.addoutlet_branch_must_be_filled));
                return false;
            }
        }

        return bDone;
    }

    void layoutPeriodeEnable(boolean STATUS){
        if (STATUS){
            lyPeriode.setEnabled(STATUS);
            lyPeriode.setBackground(getResources().getDrawable(R.color.transparant_pure));
        }else{
            lyPeriode.setEnabled(STATUS);
            lyPeriode.setBackground(getResources().getDrawable(R.color.grey_sss));
        }
    }

    void layoutContractEnable(boolean STATUS){
        if (STATUS){
            lyContractHeader.setEnabled(STATUS);
            lyContractHeader.setBackground(getResources().getDrawable(R.color.transparant_pure));
        }else{
            lyContractHeader.setEnabled(STATUS);
            lyContractHeader.setBackground(getResources().getDrawable(R.color.grey_sss));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PAYMENTYPE){
            try {
                Bundle res = data.getExtras();
                PAYMENT_TYPE = res.getString("PAYTYPE");
                String TYPENAME = res.getString("PAYTYPE");
                txtPaymentType.setText(TYPENAME);
                lyPeriode.setVisibility(View.VISIBLE);
                txtEndDate.setEnabled(true);
                txtStartDate.setEnabled(true);

                if (TYPENAME.equals("RUTIN")){
                    chkContract.setChecked(false);
                    chkContract.setEnabled(true);
                    layoutPeriodeEnable(true);
                    layoutContractEnable(true);
                }else {
                    layoutPeriodeEnable(false);
                    if (TYPENAME.equals("TENDER")){
                        chkContract.setChecked(true);
                        chkContract.setEnabled(false);
                        layoutContractEnable(true);
                    }else {
                        layoutContractEnable(false);
                        chkContract.setChecked(false);
                        chkContract.setEnabled(false);
                        txtEndDate.setEnabled(false);
                        txtStartDate.setEnabled(false);
                    }

                }
            }catch (Exception e){

            }
        }else if (requestCode == REQ_PERIODE){
            try{
                Bundle res = data.getExtras();
                txtOrderPeriod.setText(res.getString("PERIODE"));
            }catch (Exception e){

            }

        }
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                AddOutletRegActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String sMonth = String.valueOf((monthOfYear+1));
        if (sMonth.length() < 2) sMonth = "0" + sMonth;

        String sDay = String.valueOf(dayOfMonth);
        if (sDay.length() < 2) sDay = "0" + sDay;

        String strDate = sDay + "/" + sMonth + "/" + year;
        if (bSTARTDATE){
            STARTDATE = year + "-" + sMonth + "-" + sDay;
            txtStartDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(STARTDATE));
        }else{
            ENDDATE = year + "-" + sMonth + "-" + sDay;
            txtEndDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(ENDDATE));
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

    private void DisplayDialogGPS(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddOutletRegActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(android.R.string.ok),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }

}
