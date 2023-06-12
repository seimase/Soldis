package com.soldis.yourthaitea;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.admin.ringkasan.RingkasanActivity;
import com.soldis.yourthaitea.admin.absensi.Admin_AbsensiActivity;
import com.soldis.yourthaitea.admin.stock.AdminListStockActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_DISPENSER;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Promo_Banner;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.service.AlarmReceiver;
import com.soldis.yourthaitea.service.AlarmReceiverAutoLogout;
import com.soldis.yourthaitea.service.AlarmReceiverGPS;
import com.soldis.yourthaitea.service.ServiceAutoLogOut;
import com.soldis.yourthaitea.service.ServiceAutoSend;
import com.soldis.yourthaitea.stock.ListStockActivity;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import me.echodev.resizer.Resizer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.ImageBannerAdapter;
import ui.widget.CircleIndicator;

public class MainAdminActivity extends AppCompatActivity {
    static int REQ_PHOTO = 100;
    private static final int REQUEST_PERMISSIONS = 1000;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    LinearLayout lyVisit,
            lyOutlet,
            lyTransaction,
            lyRingkasan
                    ;
    RelativeLayout lyLogout;

    Intent mIntent;
    Tbl_Karyawan tblMotoris;

    TextView txtTgl, txtName;
    TextView
            txtEmail,
            txtPhone;
    String EMAIL_ID, PHONE_NUMBER;

    Obj_MOTORIS objMotoris;
    ProgressDialog progress;
    boolean bPermission;
    String sPhotoName;
    ImageView imgAvatar;

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();

    int REQ_CODE = 2000;
    private CircleIndicator viewPagerIndicator;
    private ViewPager viewPagerHeader;
    private ImageView imageViewBanner;
    private Handler handler;
    ImageBannerAdapter adapter;

    private Runnable runnable;
    int position = 1;
    List<String> resourcesImg;
    LinearLayout lySync, lyLoading;
    RelativeLayout lyAdapter;

    Tbl_Promo_Banner tblPromoBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_main);
        InitControl();
        bPermission = true;


        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        AppConstant.strSlsNo = "";
        AppConstant.strSlsName  = "";
        AppConstant.strMitraID = "";

        if (tblMotoris != null && tblMotoris.data.size() > 0){
            for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                AppConstant.strSlsNo = dat.SLSNO;
                AppConstant.strSlsName = dat.SLSNAME;
                AppConstant.strSlsPass = dat.PASSWORD;
                AppConstant.strMitraID = dat.MITRA_ID;
                AppConstant.strLevel = dat.LEVEL_ID;
                EMAIL_ID = "";
                PHONE_NUMBER = "";

                if (dat.PHOTO != null && !dat.PHOTO.equals("")){
                    AppController.getInstance().displayImage(MainAdminActivity.this , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, imgAvatar);
                }else{
                    imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                }
            }
        }

        txtEmail.setText(EMAIL_ID);
        txtEmail.setPaintFlags(txtEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtPhone.setText(PHONE_NUMBER);
        txtPhone.setPaintFlags(txtPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtName.setText("Hi, " + AppConstant.strSlsName);

        StartService();

        handler = new Handler();
        SyncPromoBanner();
    }

    void InitControl(){
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtPhone = (TextView)findViewById(R.id.txtPhone);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        txtName = (TextView)findViewById(R.id.txtName);
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        lyVisit = (LinearLayout)findViewById(R.id.layout_visit);
        lyOutlet = (LinearLayout)findViewById(R.id.layout_outlet);
        lyTransaction = (LinearLayout)findViewById(R.id.layout_transaction);
        lyRingkasan = (LinearLayout)findViewById(R.id.layout_ringkasan);
        lySync = (LinearLayout)findViewById(R.id.layout_sync);
        lyLoading = (LinearLayout)findViewById(R.id.layout_loading);
        lyAdapter = (RelativeLayout)findViewById(R.id.layout_adapter);

        viewPagerHeader = (ViewPager) findViewById(R.id.main_header_viewpager);
        viewPagerIndicator = (CircleIndicator) findViewById(R.id.indicator);

        //lyLogout = (RelativeLayout) findViewById(R.id.layout_logout);


        lyVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getBaseContext(), Admin_AbsensiActivity.class);
                startActivity(mIntent);
            }
        });

        lyOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getBaseContext(), AdminListStockActivity.class);
                startActivity(mIntent);
            }
        });

        lyTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidationStart()){
                    mIntent = new Intent(getBaseContext(), InputProductActivity.class);
                    objMotoris = new Obj_MOTORIS();
                    objMotoris = objMotoris.getData(AppConstant.strSlsNo);
                    String sTrxNo = "" ;
                    long dTrxNo = 0;
                    if (objMotoris.getSLSNO() != null){
                        dTrxNo = (long) objMotoris.getTRXNO() + 1;
                        sTrxNo = Long.toString((long)dTrxNo);
                    }
                    mIntent.putExtra("CUSTNO", "IB");
                    mIntent.putExtra("ORDERNO", sTrxNo);
                    mIntent.putExtra("CUSTNAME", "");
                    mIntent.putExtra("ADDRESS", "");
                    mIntent.putExtra("STATUS", false);
                    AppConstant.bClose = false;
                    startActivity(mIntent);
                }else{
                    mIntent = new Intent(getBaseContext(), TimeVisitActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        lyRingkasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getBaseContext(), RingkasanActivity.class);
                AppConstant.bClose = false;
                startActivity(mIntent);
            }
        });

        /*lyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialog();
            }
        });*/

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainAdminActivity.this, ProfileActivity.class);
                startActivityForResult(mIntent, REQ_CODE);
                /*checkPermissions();

                if (bPermission) {
                    try {
                        sPhotoName = AppConstant.strSlsNo + "_" + AppController.getInstance().getDateTime() + ".jpg";

                        *//*File file = new File(AppConstant.PATH_PHOTO_CAMERA + "/" + sPhotoName);
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent, REQ_PHOTO);*//*

                        EasyImage.openCamera(MainActivity.this, REQ_PHOTO);
                    } catch (Exception e) {
                        Log.w("Error Photo", e.getMessage());
                    }

                }*/
            }
        });
    }

    void StartService(){
        //ServiceAutoSend==================================================
        Intent myIntent = new Intent(getBaseContext(),
                AlarmReceiver.class);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(getBaseContext(),
                222223, myIntent, 0);

        AlarmManager alarmManager
                = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        Log.w("Start Service"," Start");
        long interval = (15) * 1000; //
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), interval, pendingIntent);


        //ServiceAutoLogout==================================================
        Intent myIntentLogOut = new Intent(getBaseContext(),
                AlarmReceiverAutoLogout.class);

        PendingIntent pendingIntentLogOut
                = PendingIntent.getBroadcast(getBaseContext(),
                222224, myIntentLogOut, 0);

        AlarmManager alarmManagerLogOut
                = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendarLogOut  = Calendar.getInstance();
        calendarLogOut.setTimeInMillis(System.currentTimeMillis());
        calendarLogOut.add(Calendar.SECOND, 10);
        long intervalLogOut = (1 * 60) * 1000; //
        alarmManagerLogOut.setRepeating(AlarmManager.RTC_WAKEUP,
                calendarLogOut.getTimeInMillis(), intervalLogOut, pendingIntentLogOut);

        //ServiceGPSTracker==================================================
        Intent myIntentGPS = new Intent(getBaseContext(),
                AlarmReceiverGPS.class);

        PendingIntent pendingIntentGPS
                = PendingIntent.getBroadcast(getBaseContext(),
                222225, myIntentGPS, 0);

        AlarmManager alarmManagerGPS
                = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendarGPS  = Calendar.getInstance();
        calendarGPS.setTimeInMillis(System.currentTimeMillis());
        calendarGPS.add(Calendar.SECOND, 10);
        long intervalGPS = (10 * 60) * 1000; //
        alarmManagerGPS.setRepeating(AlarmManager.RTC_WAKEUP,
                calendarGPS.getTimeInMillis(), intervalGPS, pendingIntentGPS);
    }

    void SyncPromoBanner(){
        lyLoading.setVisibility(View.VISIBLE);
        lyAdapter.setVisibility(View.GONE);
        lySync.setVisibility(View.GONE);
        try{
            Call<Tbl_Promo_Banner> call = NetworkManager.getNetworkService().PromoBanner(AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Promo_Banner>() {
                @Override
                public void onResponse(Call<Tbl_Promo_Banner> call, Response<Tbl_Promo_Banner> response) {
                    int code = response.code();
                    if (code == 200){
                        tblPromoBanner = response.body();
                        if (!tblPromoBanner.error){
                            resourcesImg = new ArrayList<>();
                            for(Tbl_Promo_Banner.Datum dat : tblPromoBanner.data){
                                resourcesImg.add(AppConstant.PHOTO_PROMO_URL + dat.IMG_URL);
                            }

                            for (Tbl_Promo_Banner.Team dat : tblPromoBanner.team){
                                txtEmail.setText(dat.EMAIL_ID);
                                txtEmail.setPaintFlags(txtEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                txtPhone.setText(dat.PHONE_NUMBER);
                                txtPhone.setPaintFlags(txtPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                            }

                            setupViewPagerHeader(viewPagerHeader, resourcesImg);
                            lyLoading.setVisibility(View.GONE);
                            lyAdapter.setVisibility(View.VISIBLE);
                            lySync.setVisibility(View.VISIBLE);
                        }
                    }else{
                        lyLoading.setVisibility(View.GONE);
                        lyAdapter.setVisibility(View.GONE);
                        lySync.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Promo_Banner> call, Throwable t) {
                    lyLoading.setVisibility(View.GONE);
                    lyAdapter.setVisibility(View.GONE);
                    lySync.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            lyLoading.setVisibility(View.GONE);
            lyAdapter.setVisibility(View.GONE);
            lySync.setVisibility(View.VISIBLE);
        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(ServiceAutoSend.FILEPATH);
                int resultCode = bundle.getInt(ServiceAutoSend.RESULT);
                if (resultCode == RESULT_OK) {
                    //CustomeDialog();
                }
            }
        }
    };

    private BroadcastReceiver receiverAutoLogOut = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(ServiceAutoLogOut.FILEPATH);
                int resultCode = bundle.getInt(ServiceAutoLogOut.RESULT);
                if (resultCode == RESULT_OK) {
                    AppController.getInstance().getSessionManager().setUserAccount(null);
                    CustomeDialog();
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try{
            handler.postDelayed(runnable, 5000);
            registerReceiver(receiverAutoLogOut, new IntentFilter(ServiceAutoLogOut.NOTIFICATION));
        }catch (Exception e){

        }

        Obj_MOTORIS objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);
        if (objMotoris.getSLSNO() != null) AppConstant.strTglTrx = objMotoris.getTRX_DATE();

        txtTgl.setText( AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));

        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() != null && !objVisit.getTMGO().equals("")){
            //txtTime.setText(getResources().getString(R.string.main_finish));
            //txtStock.setText(getResources().getString(R.string.main_view));

            if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") ){
                //txtTime.setText(getResources().getString(R.string.main_start));
                //txtStock.setText(getResources().getString(R.string.main_input));
            }else{
                //txtStock.setText(getResources().getString(R.string.main_view));
            }
        }else{
            //txtTime.setText(getResources().getString(R.string.main_start));
            //txtStock.setText(getResources().getString(R.string.main_input));
        }
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.setting_logout_app));
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
                if (VisitValidation()){
                    if (AppController.getInstance().isOnline()){
                        LogOut();
                    }else{
                        AppController.getInstance().CustomeDialog(MainAdminActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    CustomeDialog(getResources().getString(R.string.setting_transaction_not_complete));
                }

            }
        });

        dialog.show();
    }

    void LogOut(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);
        try{
            Call<Tbl_Karyawan> call = NetworkManager.getNetworkService().logOutUser(AppConstant.strSlsNo, AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Karyawan>() {
                @Override
                public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMotoris = response.body();
                        if (!tblMotoris.error){
                            Obj_CUSTMST obj_custmst = new Obj_CUSTMST();
                            obj_custmst.DeleteData();

                            Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                            objCustcard.DeleteData();

                            Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
                            objCustcard1.DeleteData();

                            Obj_DISPENSER objDispenser = new Obj_DISPENSER();
                            objDispenser.DeleteData();

                            Obj_TRX_D objTrxD = new Obj_TRX_D();
                            objTrxD.DeleteData();

                            Obj_TRX_H objTrxH = new Obj_TRX_H();
                            objTrxH.DeleteData();

                            Obj_VISIT objVisit = new Obj_VISIT();
                            objVisit.DeleteData();

                            Obj_MASTER objMaster = new Obj_MASTER();
                            objMaster.DeleteMaster();

                            Obj_MOTORIS objMotoris = new Obj_MOTORIS();
                            objMotoris.DeleteData();

                            AppController.getInstance().getSessionManager().setUserAccount(null);
                            AppController.getInstance().getSessionManager().putStringData(AppConstant.DATE_SYNC_OUTLET, null);
                            AppController.getInstance().getSessionManager().putStringData(AppConstant.DATE_SYNC_PRODUCT, null);
                            AppController.getInstance().getSessionManager().putStringData(AppConstant.START_ON, null);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Karyawan> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") ){
            return true;
        }else{
            if (objVisit.getTMGO() != null){
                if (objVisit.getTMBCK() == null || objVisit.getTMBCK().equals("")){
                    return false;
                }
            }
        }

        return true;
    }

    boolean VisitValidationStart(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") )
            return false;

        if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") )
            return false;

        return true;
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

    @Override
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQ_CODE){
            if (resultCode == RESULT_OK) {
                finish();
                AppController.getInstance().getSessionManager().setUserAccount(null);

                Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                objCustmst.DeleteData();

                Obj_VISIT objVisit = new Obj_VISIT();
                objVisit.DeleteData();

                Obj_TRX_H objTrxH = new Obj_TRX_H();
                objTrxH.DeleteData();

                Obj_TRX_D objTrxD = new Obj_TRX_D();
                objTrxD.DeleteData();

                Obj_MOTORIS objMotoris= new Obj_MOTORIS();
                objMotoris.DeleteData();

                Intent mIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(mIntent);
            }
        }

    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        photos.addAll(returnedPhotos);
        for (File file : returnedPhotos){

            File file1 = new File(AppConstant.PATH_PHOTO_CAMERA + "/" + sPhotoName);
            try {
                AppController.getInstance().copyFile(file, file1);

                File resizedImage = new Resizer(this)
                        .setTargetLength(480)
                        .setQuality(80)
                        .setOutputFormat("JPEG")
                        .setOutputDirPath(AppConstant.PATH_PHOTO)
                        .setSourceImage(file1)
                        .getResizedFile();

                file1 = new File(AppConstant.PATH_PHOTO + "/" + sPhotoName);
                if (file1.exists()){
                    Bitmap bmp = BitmapFactory.decodeFile(AppConstant.PATH_PHOTO + "/" + sPhotoName);
                    imgAvatar.setImageBitmap(bmp);
                    SendDataToServer();
                }
            } catch (IOException e) {
                AppController.getInstance().CustomeDialog(MainAdminActivity.this,
                        "Error " + e.getMessage());
            }
        }

        /*photos.addAll(returnedPhotos);
        imagesAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() - 1);*/
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }

    void SendDataToServer(){
        boolean bDoneCek = true;

        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_data_saved), true);

        Log.w("Cek Database"," File Raw");
        File filePhoto = new File(AppConstant.PATH_PHOTO + "/" + sPhotoName);
        String sFile_SizeRaw = "0";
        if (filePhoto.exists()){
            sFile_SizeRaw = Long.toString(filePhoto.length());
            bDoneCek = true;
        }else{
            bDoneCek = false;
        }

        try{

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), filePhoto);
            MultipartBody.Part body = MultipartBody.Part.createFormData("files", filePhoto.getName(), requestFile);

            RequestBody kodecabang = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strMitraID);
            RequestBody slsno = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strSlsNo);
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), sPhotoName);

            Call<Tbl_Karyawan> call;
            call = NetworkManager.getNetworkService().postUpdateMotoris(kodecabang,
                    slsno,
                    photo,
                    body);

            call.enqueue(new Callback<Tbl_Karyawan>() {
                @Override
                public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMotoris = response.body();
                        if (!tblMotoris.error){
                            if (tblMotoris.data.size() > 0){
                                AppController.getInstance().getSessionManager().setUserAccount(null);
                                AppController.getInstance().getSessionManager().setUserAccount(tblMotoris);
                                AppController.getInstance().getSessionManager().getUserProfile();

                                AppController.getInstance().CustomeDialog(MainAdminActivity.this,
                                        tblMotoris.message);
                            }
                        }

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Tbl_Karyawan> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    private boolean checkPermission(String permissions[]) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void checkPermissions() {
        boolean permissionsGranted = checkPermission(PERMISSIONS_REQUIRED);
        if (permissionsGranted) {
            //Toast.makeText(this, "You've granted all required permissions!", Toast.LENGTH_SHORT).show();
        } else {
            boolean showRationale = true;
            for (String permission: PERMISSIONS_REQUIRED) {
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    bPermission = false;
                    break;
                }
            }

            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

            new AlertDialog.Builder(this)
                    .setTitle("Permissions Required")
                    .setMessage(dialogMsg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainAdminActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
                        }
                    }).create().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("MainLeaderActivity", "requestCode: " + requestCode);
        Log.d("MainLeaderActivity", "Permissions:" + Arrays.toString(permissions));
        Log.d("MainLeaderActivity", "grantResults: " + Arrays.toString(grantResults));

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean hasGrantedPermissions = true;
            for (int i=0; i<grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasGrantedPermissions = false;
                    break;
                }
            }

            if (!hasGrantedPermissions) {
                finish();
            }

        } else {
            finish();
        }
    }



    private void setupViewPagerHeader(ViewPager viewPager, List<String> resources) {

        adapter = new ImageBannerAdapter(getApplicationContext(), resources, R.layout.cover_image);
        viewPager.setAdapter(adapter);
        viewPagerIndicator.setViewPager(viewPagerHeader, adapter.getCount());
        viewPagerHeader.setCurrentItem(0);
        for (int i = 0; i < adapter.getCount() - 1; i++) {
            viewPagerHeader.setCurrentItem(i, true);
            runnable = new Runnable() {
                public void run() {
                    if (position >= adapter.getCount()) {
                        position = 0;
                    } else {
                        position = position + 1;
                    }
                    viewPagerHeader.setCurrentItem(position, true);
                    handler.postDelayed(runnable, 5000);
                }
            };
        }
    }

}
