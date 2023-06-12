package com.soldis.yourthaitea.transaction.taking_order;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.printer.DeviceListActivity;
import com.soldis.yourthaitea.printer.PrintPic;
import com.soldis.yourthaitea.printer.PrinterCommands;
import com.soldis.yourthaitea.printer.Utils;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterViewOrder;
import com.soldis.yourthaitea.util.GPSTracker;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by User on 8/13/2017.
 */

public class ViewOrderActivity extends AppCompatActivity implements Runnable {
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;

    TextView txtLabel,
            txtNamaToko,
            txtAlamat,
            txtVoid,
            txtPrint,
            txtAddMoreItems,
            //txtTypeName,
            txtOrderNo,
            txtTotalPrice,
                    txtDate
    ;
    EditText txtRemark;

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    RelativeLayout layout_back;
    String sCustNo, sCustName, sAddress, sTgl, sOrderNo;

    double dSubtotal = 0;
    long lQty = 0;
    boolean bStatus;
    String TIMEIN = "";
    String sLat, sLng;
    String sSpeed;

    boolean bDone = false;
    int iTotalSKU = 0;
    double dTotalPrice = 0;
    double dResidual = 0;
    double dPay = 0;

    Obj_MASTER objEmaster = new Obj_MASTER();
    public static ArrayList<Tmp_MASTER> tmpMasters;
    ImageView imgAvatar;

    Obj_TRX_D trxD = new Obj_TRX_D();

    byte FONT_TYPE;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    Bitmap bitmap;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;

    InputStream inputStream;
    Thread thread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_view_order);

        InitControl();

        TIMEIN = AppController.getInstance().getTime();
        sLat = "0.0";
        sLng = "0.0";
        sSpeed = "0.0";
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
            AppConstant.strOrderNo = sOrderNo;
        }catch (Exception e){
            sOrderNo = "";
        }


        txtLabel.setText("ORDER CONFIRMATION");
        txtOrderNo.setText(sOrderNo);

        if (AppConstant.strCustPhotoName != null && !AppConstant.strCustPhotoName.equals("")){
            AppController.getInstance().displayImage(ViewOrderActivity.this , AppConstant.PHOTO_OUTLET_URL + AppConstant.strCustPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }


        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        objCustcard = objCustcard.getData(sCustNo, sOrderNo);

        objEmaster = new Obj_MASTER();
        AppConstant.objMasters = objEmaster.getDataListStock(sCustNo, sOrderNo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FillGrid();

        AppController.getInstance().hideKeyboardFrom(ViewOrderActivity.this);
    }

    void InitControl(){
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        txtAddMoreItems = (TextView)findViewById(R.id.txtAddMoreItems);
        txtVoid = (TextView)findViewById(R.id.txtVoid);
        txtPrint = (TextView)findViewById(R.id.txtPrint);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtNamaToko = (TextView)findViewById(R.id.text_nama_toko);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);

        //txtTypeName = (TextView)findViewById(R.id.txtTypeName);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
        txtRemark = (EditText)findViewById(R.id.txtRemark);
        txtDate = (TextView)findViewById(R.id.text_tgl);
        //txtTypeName.setText(AppConstant.strCustTypeName);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        RelativeLayout layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtDate.setText(getResources().getString(R.string.profile_transaction_date) + " " +
                AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx) );


        txtAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bStatus){
                    Intent mIntent = new Intent(ViewOrderActivity.this, InputProductActivity.class);

                    InputProductActivity.tmpMasters = tmpMasters;
                    mIntent.putExtra("CUSTNO", AppConstant.strCustNo);
                    mIntent.putExtra("ORDERNO", sOrderNo);
                    mIntent.putExtra("CUSTNAME", AppConstant.strCustName);
                    mIntent.putExtra("ADDRESS", AppConstant.strCustAddress);
                    mIntent.putExtra("STATUS", true);
                    startActivity(mIntent);
                }
                finish();

            }
        });

        txtPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(bluetoothAdapter.STATE_CONNECTED == 2){
                    //lblPrinterName.setText("No Bluetooth Adapter found");
                    Intent mIntent = new Intent(ViewOrderActivity.this, DeviceListActivity.class);
                    startActivityForResult(mIntent, 200);
                }*/
                GoPrint();

            }
        });

        txtVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialogVoid();
            }
        });
    }

    void GoPrint(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(ViewOrderActivity.this, "Message1", Toast.LENGTH_SHORT).show();

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                if (AppConstant.mBluetoothDevice != null){
                    try {
                        outputStream = AppConstant.mBluetoothSocket.getOutputStream();
                        outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                        resetPrint();

                        outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                        printPhoto(getResources().getDrawable(R.drawable.logo_rc));
                        printText("Rossa's Catering Pekalongan");
                        printNewLine();
                        printText("0856-65556555");
                        printNewLine();
                        printNewLine();

                        outputStream.write(PrinterCommands.LEFT);
                        outputStream.write(PrinterCommands.SELECT_CYRILLIC_CHARACTER_CODE_TABLE);
                        printText("Orderno  : ");
                        printText(AppConstant.strOrderNo);
                        printNewLine();

                        outputStream.write(PrinterCommands.LEFT);
                        printText("Date     : " + AppController.getInstance().getDateDDMMYYYY()
                                + " " + AppController.getInstance().getTime());
                        printNewLine();
                        printText("Staff    : " + AppConstant.strSlsName);
                        printNewLine();

                        Obj_TRX_H objTrxH = new Obj_TRX_H();
                        objTrxH =  objTrxH.getData(AppConstant.strOrderNo);
                        dPay = objTrxH.getPAYAMOUNT();
                        printText("Meja No  : " + objTrxH.getREMARK());
                        printNewLine();

                        printText("================================");
                        printNewLine();
                        //outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                        outputStream.write(PrinterCommands.CENTER);
                        outputStream.write(PrinterCommands.TEXT_BIG_SIZE);
                        printText(AppConstant.strPrefixID + AppConstant.strOrderNo.substring(6,9));
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        printNewLine();
                        printText("================================");

/*
                                    System.out.printf("|%s|%s|%s|%n",
                                            StringUtils.left("Name", 22),
                                            StringUtils.right("Birth Date", 16),
                                            StringUtils.center("Age", 6));
*/

                        double dSubTotal = 0;
                        for(Obj_MASTER dat : AppConstant.objMasters){
                            if (dat.getQTY_TRX() > 0){
                                dSubTotal += (dat.getQTY_TRX() * dat.getSellPrice1());
                                printNewLine();
                                outputStream.write(PrinterCommands.LEFT);
                                printText(dat.getPCodeName());
                                printNewLine();

                                //printText(dat.getQTY_TRX() + " x " + AppController.getInstance().toCurrency(dat.getSellPrice1()) + "\t"+ " " +"\t" + AppController.getInstance().toCurrency(dat.getQTY_TRX() * dat.getSellPrice1()) );
                                outputStream.write(PrinterCommands.LEFT);
                                printText(dat.getQTY_TRX() + " x " + AppController.getInstance().toCurrency(dat.getSellPrice1()) + "\t"+ " " +"\t"  );
                                outputStream.write(PrinterCommands.HT);
                                outputStream.write(PrinterCommands.RIGHT);
                                printText(AppController.getInstance().toCurrency(dat.getQTY_TRX() * dat.getSellPrice1()));
                            }
                        }
                        printNewLine();
                        //printText("-------------------------------");
                        printText("================================");
                        printNewLine();
                        outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                        outputStream.write(PrinterCommands.TEXT_BIG_HEIGHT);
                        printText("Total"  );
                        outputStream.write(PrinterCommands.HT);
                        printText(AppController.getInstance().toCurrencyRp(dSubTotal));
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        printNewLine();

                        outputStream.write(PrinterCommands.LEFT);
                        printText("Pay"   );
                        outputStream.write(PrinterCommands.HT);
                        printText(AppController.getInstance().toCurrencyRp(dPay));
                        printNewLine();
                        outputStream.write(PrinterCommands.LEFT);
                        printText("Change" );
                        outputStream.write(PrinterCommands.HT);
                        dResidual =  dPay - dSubTotal;
                        printText( AppController.getInstance().toCurrencyRp(dResidual));
                        printNewLine();
                        printNewLine();
                        printNewLine();

                        outputStream.write(PrinterCommands.CENTER);
                        printText("**Thank You**");
                        printNewLine();
                        printText("Follow IG rossacatering.id");
/*
                        String result = String.format("|%s|%s|%s|%n",
                                StringUtils.left("Name", 50),
                                StringUtils.right("Birth Date", 16),
                                StringUtils.center("Age", 6));*/

                        printNewLine();

                        printNewLine();
                        printNewLine();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ListPairedDevices();
                    Intent connectIntent = new Intent(ViewOrderActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent,
                            REQUEST_CONNECT_DEVICE);
                }

            }
        }
    }

    void FillGrid(){
        dTotalPrice = 0;
        for (Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getQTY_TRX() > 0) {
                iTotalSKU += 1;
                dTotalPrice += (dat.getQTY_TRX() * dat.getSellPrice1()) ;
            }
        }

        txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dTotalPrice));

        mAdapter = new AdapterViewOrder(this, AppConstant.objMasters, false, new AdapterViewOrder.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked() {
                dSubtotal = 0;
                lQty = 0;
                iTotalSKU = 0;
                dTotalPrice = 0;
                for (Obj_MASTER dat : AppConstant.objMasters){
                    dSubtotal += dat.getTOTAL();
                    lQty += dat.getQTY_TRX();
                    if (dat.getQTY_TRX() > 0) {
                        iTotalSKU += 1;
                        dTotalPrice += (dat.getQTY_TRX() * dat.getSellPrice1()) ;
                    }
                }
                txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dTotalPrice));
            }

        });

        mRecyclerView.setAdapter(mAdapter);

    }

    void CustomeDialogVoid(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        TextView txtLabel = (TextView)dialog.findViewById(R.id.textLabel);

        txtDialog.setText("Transaksi number : " + sOrderNo + " di batalkan ?");
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Obj_TRX_H objTrxH = new Obj_TRX_H();
                objTrxH.UpdateFlagVoid(sOrderNo);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    void CustomeDialogTidakBeli(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_not_so_transaction));
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
                //SaveCustCard1("N", "R1");
                AppConstant.bRefresh = true;
                setResult(RESULT_OK);
                finish();
            }
        });

        dialog.show();
    }


    void SaveData(){
        Log.w("SaveOrder", String.valueOf(bStatus));
        AppController.getInstance().OpenDatabase(ViewOrderActivity.this);

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        Obj_TRX_D objTrxD = new Obj_TRX_D();

        bDone = false;

        int iSKU = 0;
        double dInvAmount = 0 ;

        for(Tmp_MASTER dat : tmpMasters){
            objTrxD = new Obj_TRX_D();
            objTrxD.setORDERNO(sOrderNo);
            objTrxD.setPCODE(dat.PRODUCT);
            objTrxD.setQTY_B(0);
            objTrxD.setQTY_T(0);
            objTrxD.setQTY_K(1);
            objTrxD.setSELLPRICE1(0);
            objTrxD.setSELLPRICE2(0);
            objTrxD.setSELLPRICE2(0);
            objTrxD.setQTY_PCS(1);
            objTrxD.setAMOUNT(0);
            objTrxD.CreateData(objTrxD);

            bDone = true;
        }

        if (bDone){
            iSKU = tmpMasters.size();
            if (!bStatus){
                //Insert CustCard-------------------------------------------
                objCustcard.setCUSTNO(sCustNo);
                objCustcard.setDOCNO(sOrderNo);
                objCustcard.setFLAG_KIRIM("N");
                objCustcard.setLATITUDE(sLat);
                objCustcard.setLONGITUDE(sLng);
                objCustcard.setTGL_TRX(AppConstant.strTglTrx);
                objCustcard.setTIMEIN(TIMEIN);

                objCustcard.setTIMEOUT(AppController.getInstance().getTime());
                objCustcard.CreateData(objCustcard);

                //Insert Header------------------------------------------------
                objTrxH.setORDERNO(sOrderNo);
                objTrxH.setCUSTNO(sCustNo);
                objTrxH.setORDERDATE(AppConstant.strTglTrx);
                objTrxH.setSKU(iSKU);
                objTrxH.setINVAMOUNT(dInvAmount);
                objTrxH.setREMARK(txtRemark.getText().toString().trim());
                objTrxH.CreateData(objTrxH);

                //Update TRXNO-------------------------------------------------
                Obj_MOTORIS objMotoris = new Obj_MOTORIS();
                objMotoris.setTRXNO(Double.parseDouble(sOrderNo));
                objMotoris.UpdateTrxNo(objMotoris);

                AppController.getInstance().AddGPSLog(sCustName, sLat, sLng, sSpeed, AppController.getInstance().BatteryLevel());

            }else{
                //Insert Header------------------------------------------------
                objTrxH.setORDERNO(sOrderNo);
                objTrxH.setCUSTNO(sCustNo);
                objTrxH.setORDERDATE(AppConstant.strTglTrx);
                objTrxH.setSKU(iSKU);
                objTrxH.setINVAMOUNT(dInvAmount);
                objTrxH.setREMARK(txtRemark.getText().toString().trim());
                objTrxH.CreateData(objTrxH);
            }
        }
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


    boolean ValidasiGPS(){
        GPSTracker gps;
        // create class object
        gps = new GPSTracker(getBaseContext());

        String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!providerGps.contains("gps")){ //if gps is disabled
            DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                    getResources().getString(R.string.text_gps_is_off));
            return false;
        }
        sLat = "0.0";
        sLng= "0.0";

        // create class object
        gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            double speed = gps.getSpeed();
            sLat = Double.toString(latitude);
            sLng = Double.toString(longitude);
            //sSpeed = Double.toString(speed);

            if (sLat.equals("0.0")){
                CustomeDialog(getResources().getString(R.string.text_gps_not_locked));
                return false;
            }
            // \n is for new line
        }else{
            gps.showSettingsAlert();
        }
        return true;
    }

    private void DisplayDialogGPS(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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

    boolean ValidasiTransaksi(){

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        objCustcard = objCustcard.getData(sCustNo);

        if (objCustcard.getCUSTNO() != null && !objCustcard.getCUSTNO().equals("")){
            AppConstant.bRefresh = true;
            finish();
            return true;
        }else {
            if (ValidasiGPS()){
                CustomeDialogTidakBeli();
            }
            return false;
        }
    }

    void SaveCustCard1(String FLAG_EC, String REASON){
        //InsertCustcard1----------------------------------------------
        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        objCustcard1 = objCustcard1.getData(sCustNo);

        if (objCustcard1.getCUSTNO() != null && !objCustcard1.getCUSTNO().equals("")){
            //Update
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            objCustcard1.setTIMEOUT(AppController.getInstance().getCurrentTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.UpdateFlagEC(objCustcard1);
        }else{
            //Insert
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            //objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.CreateData(objCustcard1);
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

    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print photo
    public void printPhoto(Drawable drawable) {
        try {

            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            PrintPic printPic = PrintPic.getInstance();
            printPic.init(bitmap);
            byte[] bitmapdata = printPic.printDraw();
            if(bitmap!=null){

                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(bitmapdata);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mDataIntent) {
        super.onActivityResult(requestCode, resultCode, mDataIntent);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    AppConstant.mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", AppConstant.mBluetoothDevice.getName() + " : "
                                    + AppConstant.mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                }
                break;

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(ViewOrderActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(ViewOrderActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    void openBluetoothPrinter() throws IOException{
        try{

            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream=bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();

            beginListenData();

        }catch (Exception ex){

        }
    }

    void beginListenData(){
        try{

            final Handler handler =new Handler();
            final byte delimiter=10;
            stopWorker =false;
            readBufferPosition=0;
            readBuffer = new byte[1024];

            thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if(byteAvailable>0){
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for(int i=0; i<byteAvailable; i++){
                                    byte b = packetByte[i];
                                    if(b==delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte,"US-ASCII");
                                        readBufferPosition=0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                //lblPrinterName.setText(data);
                                            }
                                        });
                                    }else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }
                            }
                        }catch(Exception ex){
                            stopWorker=true;
                        }
                    }

                }
            });

            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    void printData() throws  IOException{
        try{
            String msg = "test";
            msg+="\n";
            outputStream.write(msg.getBytes());
            //lblPrinterName.setText("Printing Text...");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Disconnect Printer //
    void disconnectBT() throws IOException{
        try {
            stopWorker=true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            //lblPrinterName.setText("Printer Disconnected.");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(AppConstant.bClose) finish();
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            AppConstant.mBluetoothSocket = AppConstant.mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            AppConstant.mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(AppConstant.mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(ViewOrderActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
            GoPrint();
        }
    };
}
