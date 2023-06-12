package com.soldis.yourthaitea.admin.ringkasan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.admin.ringkasan.adapter.ViewPagerAdapterAdminListOrder;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.model.Tbl_Ringkasan;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.printer.DeviceListActivity;
import com.soldis.yourthaitea.printer.PrintPic;
import com.soldis.yourthaitea.printer.PrinterCommands;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RingkasanActivity extends AppCompatActivity implements Runnable, DatePickerDialog.OnDateSetListener {
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;

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


    TextView txtNamaToko,
            txtAlamat,
            txtTgl,
            txtTotalPrice,
            txtTotalPenjualan,
            txtTotalKas,
            txtTotalUM,
            txtPrint
    ;

    CharSequence Titles[];
    int Numboftabs =3;
    ViewPager pager;
    ViewPagerAdapterAdminListOrder adapterMenu;
    TabLayout tabs;

    RelativeLayout  lyBack;
    ProgressDialog progress;
    LinearLayout lyAdd, lyTgl;

    Obj_TRX_H objTrxH;

    Tbl_Ringkasan tblRingkasan;

    String DATE = "";

    boolean bDone = false;
    int iTotalSKU = 0;
    double dTotalPrice = 0;
    double dResidual = 0;
    double dPay = 0;

    double dTotalPenjualan = 0,
            dTotalKas = 0,
            dTotalUM = 0,
            dTotalAll = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_listorder);

        InitControl();

        DATE = AppController.getInstance().getDateYYYYMMDD();
        try{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(DATE));
        }catch (Exception e){
            txtTgl.setText("");
        }

        SyncData(DATE);


    }

    void InitControl(){
        lyAdd = (LinearLayout)findViewById(R.id.layout_add);
        lyBack = (RelativeLayout)findViewById(R.id.layout_back);
        lyTgl= (LinearLayout)findViewById(R.id.layout_tgl);

        txtNamaToko = (TextView)findViewById(R.id.text_nama);
        txtAlamat = (TextView)findViewById(R.id.text_address);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtTotalPenjualan = (TextView)findViewById(R.id.txtTotalPenjualan);
        txtTotalKas = (TextView)findViewById(R.id.txtTotalKas);
        txtTotalUM = (TextView)findViewById(R.id.txtTotalUM);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
        txtPrint = (TextView)findViewById(R.id.txtPrint);
        txtNamaToko.setText(AppConstant.strCustName);
        txtAlamat.setText(AppConstant.strCustAddress);
        tabs = (TabLayout)findViewById(R.id.tabs);


        pager = (ViewPager)findViewById(R.id.pager);
        Titles = new CharSequence[]{
                "Produk",
                "Kas",
                "Stok"
        };

        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tblRingkasan.data_sales.size() > 0){
                    GoPrint();
                }else{
                    AppController.getInstance().CustomeDialog(RingkasanActivity.this,
                            getResources().getString(R.string.text_data_not_found));
                }
            }
        });

        lyTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });

        lyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(RingkasanActivity.this, InputProductActivity.class);
                InputProductActivity.tmpMasters = new ArrayList<>();
                mIntent.putExtra("CUSTNO", AppConstant.strCustNo);
                mIntent.putExtra("ORDERNO", "");
                mIntent.putExtra("CUSTNAME", AppConstant.strCustName);
                mIntent.putExtra("ADDRESS", AppConstant.strCustAddress);
                mIntent.putExtra("STATUS", false);
                startActivity(mIntent);
            }
        });

        adapterMenu =  new ViewPagerAdapterAdminListOrder(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter

    }


    void SyncData(String sDATE) {
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_Ringkasan> call = NetworkManager.getNetworkService().ListSales(
                    AppConstant.strMitraID,
                    AppConstant.strSlsNo,
                    sDATE);
            call.enqueue(new Callback<Tbl_Ringkasan>() {
                @Override
                public void onResponse(Call<Tbl_Ringkasan> call, Response<Tbl_Ringkasan> response) {
                    progress.dismiss();
                    int code = response.code();
                    if (code == 200){
                        tblRingkasan = response.body();
                        AppConstant.tblRingkasan = response.body();
                        if (!tblRingkasan.error){
                            FillGrid();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Ringkasan> call, Throwable t) {
                    progress.dismiss();
                    AppController.getInstance().CustomeDialog(RingkasanActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){
            AppController.getInstance().CustomeDialog(RingkasanActivity.this, e.getMessage());
            progress.dismiss();
        }

    }

    void FillGrid(){
        dTotalPenjualan = 0;
        dTotalKas = 0;
        dTotalUM = 0;
        dTotalAll = 0;
        if (tblRingkasan != null){
            for(Tbl_Ringkasan.DataKa dat : tblRingkasan.data_kas){
                if (dat.TYPE_KAS.equals("Y")){
                    dTotalKas += dat.AMOUNT;
                }else{
                    dTotalKas -= dat.AMOUNT;
                }
            }

            for (Tbl_Ringkasan.DataSale dat : tblRingkasan.data_sales){
                dTotalPenjualan += dat.AMOUNT;
            }

            dTotalUM = 0;

            for (Tbl_Ringkasan.DataUang dat : tblRingkasan.data_uangmakan){
                dTotalUM += dat.UANG_MAKAN;
            }

            txtTotalPenjualan.setText(AppController.getInstance().toCurrency(dTotalPenjualan));
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

            pager.setAdapter(adapterMenu);

        }else{
            pager.setAdapter(null);
            txtTotalPenjualan.setText(AppController.getInstance().toCurrency(0));
            txtTotalKas.setText(AppController.getInstance().toCurrency(0));
            txtTotalUM.setText(AppController.getInstance().toCurrency(0));
            txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(0));
        }
        ;
        tabs.setupWithViewPager(pager);
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                RingkasanActivity.this,
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
        DATE = year + "-" + sMonth + "-" + sDay;
        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(DATE));

        SyncData(DATE);
    }

    void GoPrint(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(RingkasanActivity.this, "Message1", Toast.LENGTH_SHORT).show();

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
                        printText("Date     : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(DATE)
                                + " " + AppController.getInstance().getTime());
                        printNewLine();
                        printText("Staff    : " + AppConstant.strSlsName);
                        printNewLine();


                        printText("================================");
                        printNewLine();
                        //outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                        outputStream.write(PrinterCommands.CENTER);
                        outputStream.write(PrinterCommands.TEXT_BIG_SIZE);
                        printText("RINGKASAN");
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        printNewLine();
                        printText("================================");

/*
                                    System.out.printf("|%s|%s|%s|%n",
                                            StringUtils.left("Name", 22),
                                            StringUtils.right("Birth Date", 16),
                                            StringUtils.center("Age", 6));
*/
                        dTotalPenjualan = 0;
                        for (Tbl_Ringkasan.DataSale dat : tblRingkasan.data_sales){
                            dTotalPenjualan += dat.AMOUNT;
                            printNewLine();
                            outputStream.write(PrinterCommands.LEFT);
                            printText(dat.PCODENAME);
                            printNewLine();

                            //printText(dat.getQTY_TRX() + " x " + AppController.getInstance().toCurrency(dat.getSellPrice1()) + "\t"+ " " +"\t" + AppController.getInstance().toCurrency(dat.getQTY_TRX() * dat.getSellPrice1()) );
                            outputStream.write(PrinterCommands.LEFT);
                            printText(dat.QTY + " x " + AppController.getInstance().toCurrency(dat.SELLPRICE1) + "\t"+ " " +"\t"  );
                            outputStream.write(PrinterCommands.HT);
                            outputStream.write(PrinterCommands.RIGHT);
                            printText(AppController.getInstance().toCurrency(Integer.parseInt(dat.QTY) * dat.SELLPRICE1));

                        }

                        printNewLine();
                        //printText("-------------------------------");
                        printText("================================");
                        printNewLine();
                        outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        outputStream.write(PrinterCommands.LEFT);
                        printText("Total"  );
                        outputStream.write(PrinterCommands.HT);
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        outputStream.write(PrinterCommands.RIGHT);
                        printText(AppController.getInstance().toCurrencyRp(dTotalPenjualan));
                        outputStream.write(PrinterCommands.TEXT_NORMAL_SIZE);
                        printNewLine();
                        printNewLine();
                        printNewLine();

                        outputStream.write(PrinterCommands.CENTER);
                        printText("**Thank You**");
                        printNewLine();
                        printText("Follow IG rossacatering.id");
                        printNewLine();
                        printNewLine();
                        printNewLine();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ListPairedDevices();
                    Intent connectIntent = new Intent(RingkasanActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent,
                            REQUEST_CONNECT_DEVICE);
                }

            }
        }
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
                    Intent connectIntent = new Intent(RingkasanActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(RingkasanActivity.this, "Message", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(RingkasanActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
            GoPrint();
        }
    };
}
