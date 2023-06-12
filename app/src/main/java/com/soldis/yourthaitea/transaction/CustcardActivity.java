package com.soldis.yourthaitea.transaction;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.model.Tbl_Message;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.adapter.ViewPagerAdapterMenuCustcard;
import com.soldis.yourthaitea.util.GPSTracker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.echodev.resizer.Resizer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustcardActivity extends AppCompatActivity {
    static int REQ_PHOTO = 100;
    private static final int REQUEST_PERMISSIONS = 1000;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    CharSequence Titles[];
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterMenuCustcard adapter;
    TabLayout tabs;

    public String sCustNo, sCustName, sAddress, sTgl, sOrderNo, sTypeName, sPhotoName;
    boolean bStatus;

    TextView txtNamaToko,
            txtAlamat,
            txtLabel,
            txtCheckin,
            txtCheckout,
            txtTgl
    ;

    public String sFLAGOUT;
    Toolbar toolbar;

    Obj_CUSTCARD1 objCustcard1;
    String TIMEIN = "";
    String sLat, sLng;

    LinearLayout layout_checkout;
    RelativeLayout layout_back;

    ImageView imgAvatar;
    ProgressDialog progress;
    boolean bPermission;

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();

    Tbl_Message tblMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_custcard);
        bPermission = true;

        Titles = new CharSequence[]{
                "Data Outlet",
                "Menu Trx"
        };

        InitControl();
        sLat = "0.0";
        sLng = "0.0";
        TIMEIN = AppController.getInstance().getCurrentTime();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            sCustName = getIntent().getExtras().getString("CUSTNAME");
            sAddress = getIntent().getExtras().getString("ADDRESS");
            bStatus = getIntent().getExtras().getBoolean("STATUS");
            sTypeName = getIntent().getExtras().getString("TYPENAME");
            sPhotoName = getIntent().getExtras().getString("PHOTONAME");
            sTypeName = (sTypeName == null ? "" :  sTypeName);

            Log.w("Custcard" , sTypeName);
            txtNamaToko.setText(sCustName);
            txtAlamat.setText(sAddress);
            //txtTypeName.setText(sTypeName.replace("AFH ", ""));
            AppConstant.strCustNo = sCustNo;
            AppConstant.strCustName = sCustName;
            AppConstant.strCustAddress = sAddress;
            AppConstant.strCustTypeName = sTypeName.replace("AFH ", "");
            AppConstant.strCustPhotoName = sPhotoName;
        }catch (Exception e){
            Log.w("Custcard" , e.getMessage());
            AppConstant.strCustNo = "";
            AppConstant.strCustName = "";
            AppConstant.strCustAddress = "";
            AppConstant.strCustTypeName = "";
            AppConstant.strCustPhotoName = "";
            sPhotoName = "";
            txtNamaToko.setText("");
            txtAlamat.setText("");
            sFLAGOUT = "1";
        }

        if (sPhotoName != null && !sPhotoName.equals("")){
            AppController.getInstance().displayImage(CustcardActivity.this , AppConstant.PHOTO_OUTLET_URL + sPhotoName, imgAvatar);
        }else{
            imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
        }
        try{
            sFLAGOUT = getIntent().getExtras().getString("FLAGOUT");
        }catch (Exception e){
            sFLAGOUT = "1";
        }
        objCustcard1 = new Obj_CUSTCARD1();
        objCustcard1 = objCustcard1.getData(sCustNo);

        if(objCustcard1.getCUSTNO() != null){
            Log.w("CUSCTARD", sCustNo);
            TIMEIN = (objCustcard1.getTIMEIN() == null ? "-" : objCustcard1.getTIMEIN());
        }else{
            TIMEIN = AppController.getInstance().getCurrentTime();
        }

        if (TIMEIN.equals("")) TIMEIN = AppController.getInstance().getTime();

        if (objCustcard1.getTIMEOUT() != null){
            if (!objCustcard1.getTIMEOUT().equals("")){
                layout_checkout.setVisibility(View.GONE);
            }else{
                layout_checkout.setVisibility(View.VISIBLE);
            }
        }
        txtCheckin.setText(TIMEIN);
        txtCheckout.setText((objCustcard1.getTIMEOUT() == null ? "-" : objCustcard1.getTIMEOUT()));

        CreateCustCard1("N","R1");
    }

    void InitControl(){
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        //txtTypeName = (TextView)findViewById(R.id.txtTypeName);
        txtNamaToko = (TextView)findViewById(R.id.text_name);
        txtAlamat = (TextView)findViewById(R.id.text_address);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtCheckin = (TextView)findViewById(R.id.txtCheckin);
        txtCheckout = (TextView)findViewById(R.id.txtCheckout);
        layout_checkout = (LinearLayout)findViewById(R.id.layout_checkout);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        txtTgl = (TextView)findViewById(R.id.text_tgl);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterMenuCustcard(getSupportFragmentManager(),Titles,Numboftabs);
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

        txtLabel.setText(getResources().getString(R.string.daily_sales_input));

        layout_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiGPS()){
                    ValidasiTransaksi();
                }
            }
        });

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try{
            txtTgl.setText("Tanggal transaksi : "  + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("Tanggal transaksi : ");
        }

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();

                if (bPermission) {
                    try {
                        sPhotoName = AppConstant.strMitraID + "_" + AppConstant.strCustNo + "_" + AppController.getInstance().getDateTime() + ".jpg";

                        //EasyImage.openCamera(CustcardActivity.this, REQ_PHOTO);
                        EasyImage.openChooserWithDocuments(CustcardActivity.this, "Pilih Sumber Gambar", REQ_PHOTO);
                    } catch (Exception e) {
                        Log.w("Error Photo", e.getMessage());
                    }

                }
            }
        });
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
                SaveCustCard1("N", "R1");
                AppConstant.bRefresh = true;
                finish();
            }
        });

        dialog.show();
    }

    void SaveCustCard1(String FLAG_EC, String REASON){
        //InsertCustcard1----------------------------------------------
        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        objCustcard1 = objCustcard1.getData(sCustNo);

        if (objCustcard1.getCUSTNO() != null && !objCustcard1.getCUSTNO().equals("")){
            Log.w("SaveCustCard1", "Update");
            //Update
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.UpdateFlagEC(objCustcard1);
        }else{
            Log.w("SaveCustCard1", "Insert");
            //Insert
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(TIMEIN);
            objCustcard1.setTIMEOUT(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.CreateData(objCustcard1);
        }
    }

    boolean ValidasiTransaksi(){
        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
        objCustcard = objCustcard.getData(sCustNo);

        if (objCustcard.getCUSTNO() != null && !objCustcard.getCUSTNO().equals("")){
            AppConstant.bRefresh = true;
            Obj_TRX_H objTrxH = new Obj_TRX_H();
            objTrxH = objTrxH.getDataCustNo(sCustNo);

            if (objTrxH.getCUSTNO() != null) {
                CustomeDialog();
            }
            return true;
        }else {

            if (ValidasiGPS()){
                CustomeDialogTidakBeli();
            }
            return false;
        }
    }

    void CreateCustCard1(String FLAG_EC, String REASON){
        //InsertCustcard1----------------------------------------------
        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        objCustcard1 = objCustcard1.getData(sCustNo);

        if (objCustcard1.getCUSTNO() != null && !objCustcard1.getCUSTNO().equals("")){
            Log.w("CUSCTARD", "UPDATE");
        }else{
            Log.w("CUSCTARD", "INSERT");
            //Insert
            objCustcard1 = new Obj_CUSTCARD1();
            objCustcard1.setCUSTNO(sCustNo);
            objCustcard1.setFLAG_EC(FLAG_EC);
            objCustcard1.setTIMEIN(AppController.getInstance().getTime());
            objCustcard1.setREASON(REASON);
            objCustcard1.setLATITUDE(sLat);
            objCustcard1.setLONGITUDE(sLng);
            objCustcard1.CreateData(objCustcard1);
        }
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
                AppController.getInstance().CustomeDialog (CustcardActivity.this, getResources().getString(R.string.text_gps_not_locked));
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

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_complete_visit));
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

                SaveCustCard1("Y", "");
                finish();
            }
        });

        dialog.show();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                onPhotosReturned(imagesFiles);

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CustcardActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
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
                AppController.getInstance().CustomeDialog(CustcardActivity.this,
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
        AppConstant.strCustPhotoName = sPhotoName;
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
            RequestBody custno = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strCustNo);
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), sPhotoName);

            Call<Tbl_Message> call;
            call = NetworkManager.getNetworkService().postUpdatePhotoOutlet(kodecabang,
                    custno,
                    photo,
                    body);

            call.enqueue(new Callback<Tbl_Message>() {
                @Override
                public void onResponse(Call<Tbl_Message> call, Response<Tbl_Message> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMessage = response.body();
                        if (!tblMessage.error){
                            Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                            objCustmst.UpdatePhoto(AppConstant.strCustNo, sPhotoName);
                            AppController.getInstance().CustomeDialog(CustcardActivity.this,
                                    tblMessage.message);
                        }

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Tbl_Message> call, Throwable t) {
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
                            ActivityCompat.requestPermissions(CustcardActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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

}
