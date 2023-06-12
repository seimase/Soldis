package com.soldis.yourthaitea.transaction.taking_order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.printer.DeviceListActivity;
import com.soldis.yourthaitea.printer.PrintPic;
import com.soldis.yourthaitea.printer.PrinterCommands;
import com.soldis.yourthaitea.printer.UnicodeFormatter;
import com.soldis.yourthaitea.printer.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class OrderPaymentSuccessActivity extends AppCompatActivity implements Runnable{
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;


    TextView txtAmount,
            txtKembali,
            txtSave,
            txtOrderNo,
            txtPrint
                    ;
    EditText edtPay;
    RelativeLayout layout_back;
    public static ArrayList<Tmp_MASTER> tmpMasters;
    double dResidual = 0;
    double dPay = 0;

    boolean bDone;
    byte FONT_TYPE;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    Bitmap bitmap;

    InputStream inputStream;
    Thread thread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    boolean bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_order_payment_success);

        InitControl();

        try{
            dResidual = getIntent().getExtras().getDouble("RESIDUAL");
            dPay = getIntent().getExtras().getDouble("PAY");
        }catch (Exception e){
            dResidual = 0;
            dPay = 0;
        }

        txtKembali.setText(AppController.getInstance().toCurrencyRp(dResidual));
    }

    void InitControl(){
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        txtKembali = (TextView)findViewById(R.id.txtKembali);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtPrint = (TextView)findViewById(R.id.txtPrint);

        txtOrderNo.setText(AppConstant.strOrderNo);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.bClose = true;
                SaveData();
                finish();
            }
        });

        txtPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoPrint();
                }
            }
        );
    }

    void GoPrint(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(OrderPaymentSuccessActivity.this, "Message1", Toast.LENGTH_SHORT).show();

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                if (AppConstant.mBluetoothDevice != null){
                    try {
                        SaveData();

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

                        printText("Meja No  : " + AppConstant.strMejaNo);
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
                        printText( AppController.getInstance().toCurrencyRp(dResidual));
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
                    Intent connectIntent = new Intent(OrderPaymentSuccessActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent,
                            REQUEST_CONNECT_DEVICE);
                }

            }
        }
    }

    public boolean writeWithFormat(byte[] buffer, final byte[] pFormat, final byte[] pAlignment) {
        try {
            // Notify printer it should be printed with given alignment:
            outputStream.write(pAlignment);
            // Notify printer it should be printed in the given format:
            outputStream.write(pFormat);
            // Write the actual data:
            outputStream.write(buffer, 0, buffer.length);

            // Share the sent message back to the UI Activity
     //       App.getInstance().getHandler().obtainMessage(MESSAGE_WRITE, buffer.length, -1, buffer).sendToTarget();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
            return false;
        }
    }

    void SaveData(){
        AppController.getInstance().OpenDatabase(OrderPaymentSuccessActivity.this);

        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        Obj_TRX_D objTrxD = new Obj_TRX_D();

        bDone = false;

        int iSKU = 0;
        double dInvAmount = 0 ;

        for(Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getINP_UOM1().equals("")) dat.setINP_UOM1("0");
            if (dat.getINP_UOM2().equals("")) dat.setINP_UOM2("0");
            if (dat.getINP_UOM3().equals("")) dat.setINP_UOM3("0");

            int iQty_B = Integer.parseInt(dat.getINP_UOM1());
            int iQty_T = Integer.parseInt(dat.getINP_UOM2());
            int iQty_K =  (int)dat.getQTY_TRX();

            if (iQty_K > 0){
                iSKU += 1;
                dInvAmount += (dat.getQTY_TRX() * dat.getSellPrice1());


                long iQtyTotal = (iQty_B * dat.getConvUnit2() * dat.getConvUnit3()) +
                        (iQty_T * dat.getConvUnit2()) +
                        (iQty_K)
                        ;

                //Insert Deatil------------------------------------------------
                objTrxD = new Obj_TRX_D();
                objTrxD.setORDERNO(AppConstant.strOrderNo);
                objTrxD.setPCODE(dat.getPCode());
                objTrxD.setQTY_B(iQty_B);
                objTrxD.setQTY_T(iQty_T);
                objTrxD.setQTY_K(iQty_K);
                objTrxD.setSELLPRICE1(dat.getSellPrice1());
                objTrxD.setSELLPRICE2(dat.getSellPrice2());
                objTrxD.setSELLPRICE2(dat.getSellPrice3());
                objTrxD.setQTY_PCS((int)iQtyTotal);
                objTrxD.setAMOUNT(dat.getQTY_TRX() * dat.getSellPrice1());
                objTrxD.CreateData(objTrxD);

                bDone = true;
            }

        }

        if (bDone){
            //Insert CustCard-------------------------------------------
            objCustcard.setCUSTNO(AppConstant.strCustNo);
            objCustcard.setDOCNO(AppConstant.strOrderNo);
            objCustcard.setFLAG_KIRIM("N");
            objCustcard.setLATITUDE("");
            objCustcard.setLONGITUDE("");
            objCustcard.setTGL_TRX(AppConstant.strTglTrx);
            objCustcard.setTIMEIN(AppController.getInstance().getCurrentTime());

            objCustcard.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard.CreateData(objCustcard);

            //Insert Header------------------------------------------------
            objTrxH.setORDERNO(AppConstant.strOrderNo);
            objTrxH.setCUSTNO(AppConstant.strCustNo);
            objTrxH.setORDERDATE(AppConstant.strTglTrx);
            objTrxH.setSKU(iSKU);
            objTrxH.setINVAMOUNT(dInvAmount);
            objTrxH.setPAYAMOUNT(dPay);
            objTrxH.setREMARK(AppConstant.strMejaNo);
            objTrxH.CreateData(objTrxH);

            //Update TRXNO-------------------------------------------------
            Obj_MOTORIS objMotoris = new Obj_MOTORIS();
            objMotoris.setTRXNO(Double.parseDouble(AppConstant.strOrderNo));
            objMotoris.UpdateTrxNo(objMotoris);

        }
    }

      //print new line
    private void printNewLine() {
        try {
            outputStream = AppConstant.mBluetoothSocket.getOutputStream();
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
            outputStream = AppConstant.mBluetoothSocket.getOutputStream();
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream = AppConstant.mBluetoothSocket.getOutputStream();
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print photo
    public void printPhoto(Bitmap bmp) {
        try {

            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream = AppConstant.mBluetoothSocket.getOutputStream();
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

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
                    Intent connectIntent = new Intent(OrderPaymentSuccessActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(OrderPaymentSuccessActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
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
            Toast.makeText(OrderPaymentSuccessActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
            GoPrint();
        }
    };


    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
}
