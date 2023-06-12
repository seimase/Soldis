package com.soldis.yourthaitea.master;

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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.model.ApiGoogleMaps;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.transaction.ListTypeout;
import com.soldis.yourthaitea.util.GPSTracker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import me.echodev.resizer.Resizer;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class AddOutletPreRegActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    static int REQ_PHOTO = 100;
    static int REQ_TYPEOUT = 90;
    static int REQ_PHOTO_OPTIONAL = 200;
    private static final int REQUEST_PERMISSIONS = 1000;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    String strDummyCode = "";
    String DATE = "";
    LinearLayout lyTgl    ;
    TextView lySave;

    ImageView imgOutlet, imgOutletOptional;
    String sPhotoName, sPhotoNameSamping;

    EditText edtCustName,
            edtAddress,
            edtPhoneArea,
            edtPhone,
            edtKelurahan,
            edtBranch,
            edtCompanyName
                    ;

    TextView txtTgl, txtTypeOut, txtRegister;

    String GOOGLE_PROVINSI,
            GOOGLE_KABUPATEN,
            GOOGLE_KECAMATAN,
            GOOGLE_KELURAHAN,
            GOOGLE_KODEPOS,
            GOOGLE_ALAMAT,
            TYPEOUT
                    ;

    ArrayList<String> adm1  = new ArrayList<String>();
    ArrayList<String> adm2  = new ArrayList<String>();
    ArrayList<String> adm3  = new ArrayList<String>();
    ArrayList<String> adm4  = new ArrayList<String>();
    ArrayList<String> postalCode  = new ArrayList<String>();


    boolean PhotoFlag;
    String sLat, sLng, sLatLng;

    ApiGoogleMaps apiGoogleMaps;

    ProgressDialog progress;
    Tbl_Custmst tblCustmst;
    Obj_MOTORIS objMotoris;
    Obj_CUSTMST objCustmst;
    Tbl_Karyawan tblMotoris;
    String SLSNO = "";
    String KODECABANG = "";
    boolean bPermission;

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();
    String FLAGOUT, sCustNo;

    GPSTracker gps;
    Obj_TYPEOUT objTypeout;
    RelativeLayout layout_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_add_customer_pre_reg);

        try {
            sCustNo = getIntent().getExtras().getString("CUSTNO");
            FLAGOUT = getIntent().getExtras().getString("FLAGOUT");
        }catch (Exception e){
            FLAGOUT = "";
            sCustNo = "";
        }
        sLat = "0.0";
        sLng= "0.0";
        sLatLng = sLat + "," + sLng;

        gps = new GPSTracker(getBaseContext());

        InitControl();


        if (FLAGOUT.equals("0") || FLAGOUT.equals("2")){
            txtRegister.setVisibility(View.GONE);
        }else if (FLAGOUT.equals("1")){
            txtRegister.setVisibility(View.VISIBLE);
        }

        if (FLAGOUT.equals("1") || FLAGOUT.equals("2")){
            lySave.setVisibility(View.INVISIBLE);
        }

        FillForm();

    }

    private void InitControl() {
        PhotoFlag = true;
        bPermission = true;

        TYPEOUT = "";
        SLSNO = "";
        KODECABANG = "";
        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null && tblMotoris.data.size() > 0){
            for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                SLSNO = dat.SLSNO;
                KODECABANG = dat.MITRA_ID;
            }
        }

        objCustmst = new Obj_CUSTMST();

        adm1.add("administrative_area_level_1");
        adm1.add("political");

        adm2.add("administrative_area_level_2");
        adm2.add("political");

        adm3.add("administrative_area_level_3");
        adm3.add("political");

        adm4.add("administrative_area_level_4");
        adm4.add("political");
        postalCode.add("postal_code");


        sPhotoName = "";
        sPhotoNameSamping = "";
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        edtCustName = (EditText)findViewById(R.id.edtNameToko);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        edtPhoneArea = (EditText)findViewById(R.id.edtPhoneArea);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtKelurahan  = (EditText)findViewById(R.id.edtKelurahan);
        edtBranch = (EditText)findViewById(R.id.edtBranch);
        edtCompanyName = (EditText)findViewById(R.id.edtCompanyName);

        txtTgl = (TextView)findViewById(R.id.text_tgl);
        txtTypeOut = (TextView)findViewById(R.id.txtTypeOut);

        lyTgl = (LinearLayout)findViewById(R.id.layout_tgl);
        lySave = (TextView) findViewById(R.id.txtSave);
        imgOutlet = (ImageView)findViewById(R.id.imgOutlet);
        imgOutletOptional = (ImageView)findViewById(R.id.imgOutletOptional);

        layout_back = (RelativeLayout)findViewById(R.id.layout_back);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,REQ_PHOTO);*/
                checkPermissions();

                if (bPermission) {
                    try{
                        sPhotoName = "D_" + KODECABANG + "_" + SLSNO + "_" + AppController.getInstance().getDateTime() + ".jpg";
                        //EasyImage.openCamera(AddOutletPreRegActivity.this, REQ_PHOTO);
                        EasyImage.openChooserWithDocuments(AddOutletPreRegActivity.this, "Pilih Sumber Gambar", REQ_PHOTO);
                    }catch (Exception e){
                        Log.w("Error Photo", e.getMessage());
                    }
                }
            }
        });

        imgOutletOptional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();

                if (bPermission) {
                    try{
                        sPhotoNameSamping = "S_" + KODECABANG + "_" + SLSNO + "_" + AppController.getInstance().getDateTime() + ".jpg";

                        //EasyImage.openCamera(AddOutletPreRegActivity.this, REQ_PHOTO_OPTIONAL);
                        EasyImage.openChooserWithDocuments(AddOutletPreRegActivity.this, "Pilih Sumber Gambar", REQ_PHOTO_OPTIONAL);
                    }catch (Exception e){
                        Log.w("Error Photo", e.getMessage());
                    }
                }
            }
        });

        edtPhoneArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>2){
                    edtPhone.setFocusableInTouchMode(true);
                    edtPhone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lyTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });

        txtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialodDatePick();
            }
        });

        txtTypeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), ListTypeout.class);
                startActivityForResult(mIntent, REQ_TYPEOUT);
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), AddOutletRegActivity.class);
                mIntent.putExtra("CUSTNO", sCustNo);
                startActivity(mIntent);
                finish();
            }
        });

        lySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiInput()){
                    String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(!providerGps.contains("gps")){ //if gps is disabled
                        DisplayDialogGPS(getResources().getString(R.string.text_confirmation),
                                getResources().getString(R.string.text_gps_is_off));
                        return;
                    }

                    // GPSTracker class
                    gps = new GPSTracker(AddOutletPreRegActivity.this);

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
                            AddOutletManual();
                        }else{
                            AppController.getInstance().CustomeDialog(AddOutletPreRegActivity.this,
                                    getResources().getString(R.string.text_gps_not_locked));
                        }
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }

                }
            }
        });

    }

    void AddOutletManual(){
        double iDummyCode = 0;
        AppController.getInstance().OpenDatabase(AddOutletPreRegActivity.this);
        objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(SLSNO);
        if (objMotoris.getSLSNO() != null){
            iDummyCode = objMotoris.getSEQNO()+1;
            strDummyCode = "D" + String.valueOf((long)iDummyCode);

            objCustmst = new Obj_CUSTMST();
            objCustmst.setCUSTNO(SLSNO + "_"+ strDummyCode);
            objCustmst.setCUSTNAME(edtCustName.getText().toString());
            objCustmst.setCOMPANY_NAME(edtCompanyName.getText().toString());
            objCustmst.setTYPEOUT(TYPEOUT);
            String sBranch = edtBranch.getText().toString();
            if (sBranch.equals("") || sBranch.equals("0")) sBranch = "1";
            objCustmst.setNUMBER_OF_BRANCH(Integer.parseInt(sBranch));

            objCustmst.setDATE_NEXT_VISIT(DATE);
            //objCustmst.setCCONTACT(edtOwnerName.getText().toString());
            objCustmst.setCUSTADD1(edtAddress.getText().toString());
            objCustmst.setCPHONE1(edtPhoneArea.getText().toString() + edtPhone.getText().toString());
            objCustmst.setPROVINSI("");
            objCustmst.setKABUPATEN("");
            objCustmst.setKECAMATAN("");
            objCustmst.setKELURAHAN("");
            objCustmst.setKODEPOS("");
            objCustmst.setALAMAT("");
            objCustmst.setFLAG_KIRIM("N");
            objCustmst.setLATITUDE(sLat);
            objCustmst.setLONGITUDE(sLng);
            objCustmst.setPHOTO_NAME(sPhotoName);
            objCustmst.setPHOTO_NAME_SAMPING(sPhotoNameSamping);
            objCustmst.setTOTAL_KUNJUNGAN(0);
            objCustmst.setLAST_TRANSACTION("");
            objCustmst.setFLAGOUT("1");
            objCustmst.CreateData(objCustmst);

            objMotoris = new Obj_MOTORIS();
            objMotoris.setSEQNO(iDummyCode);
            objMotoris.UpdateSeqNo(objMotoris);

            CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
        }

    }

    void FillForm(){
        objCustmst = new Obj_CUSTMST();
        if (!FLAGOUT.equals("0") || !FLAGOUT.equals("1")){
            objCustmst = objCustmst.getData(sCustNo);
            edtCompanyName.setText((objCustmst.getCOMPANY_NAME() == null ? "" : objCustmst.getCOMPANY_NAME()));
            edtAddress.setText((objCustmst.getCUSTADD1() == null ? "" : objCustmst.getCUSTADD1()));
            edtPhone.setText((objCustmst.getCPHONE1() == null ? "" : objCustmst.getCPHONE1()));
            edtKelurahan.setText((objCustmst.getKELURAHAN() == null ? "" : objCustmst.getKELURAHAN()));

            TYPEOUT = (objCustmst.getTYPEOUT() == null ? "" : objCustmst.getTYPEOUT());
            objTypeout = new Obj_TYPEOUT();
            objTypeout = objTypeout.getData(TYPEOUT);
            txtTypeOut.setText((objTypeout.getTYPENAME() == null ? "" : objTypeout.getTYPENAME()));

            edtBranch.setText(Integer.toString(objCustmst.getNUMBER_OF_BRANCH()));
            txtTgl.setText((objCustmst.getDATE_NEXT_VISIT() == null ? "" : objCustmst.getDATE_NEXT_VISIT()));

        }
    }

    boolean ValidasiInput(){
        boolean bDone = true;

        String sCustName,
                sCompanyName,
                sTypeOut,
                sOwnerName,
                sAddress,
                sPhoneArea,
                sPhone,
                sBranch,
                sKelurahan;

        sCustName = edtCustName.getText().toString().trim();
        sCompanyName = edtCompanyName.getText().toString().trim();
        sAddress = edtAddress.getText().toString().trim();
        sPhone = edtPhone.getText().toString().trim();
        sPhoneArea = edtPhoneArea.getText().toString().trim();
        sKelurahan = edtKelurahan.getText().toString().trim();
        sTypeOut = txtTypeOut.getText().toString().trim();
        sBranch = edtBranch.getText().toString().trim();

        if (sCustName.equals("")){
            CustomeDialog(getResources().getString(R.string.addoutlet_outlet_name_must_be_filled));
            return false;
        }

        if (sCompanyName.equals("")){
            CustomeDialog(getResources().getString(R.string.addoutlet_outlet_name_must_be_filled));
            return false;
        }

        if (sTypeOut.equals("")){
            CustomeDialog(getResources().getString(R.string.addoutlet_classification_must_be_filled));
            return false;
        }

        if (sBranch.equals("") || sBranch.equals("0")){
            CustomeDialog(getResources().getString(R.string.addoutlet_branch_must_be_filled));
            return false;
        }

        if (DATE.equals("")){
            CustomeDialog(getResources().getString(R.string.addoutlet_next_visit_must_be_filled));
            return false;
        }


        if (!PhotoFlag){
            CustomeDialog(getResources().getString(R.string.addoutlet_take_photo));
            return false;
        }

        return bDone;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_TYPEOUT){
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                TYPEOUT = res.getString("TYPEOUT");
                String TYPENAME = res.getString("TYPENAME");
                txtTypeOut.setText(TYPENAME);

            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                if (type == REQ_PHOTO){
                    onPhotosReturned(imagesFiles);
                }else{
                    onPhotosReturnedSide(imagesFiles);
                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(AddOutletPreRegActivity.this);
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

                File resizedImage = new Resizer(AddOutletPreRegActivity.this)
                        .setTargetLength(480)
                        .setQuality(80)
                        .setOutputFormat("JPEG")
                        .setOutputDirPath(AppConstant.PATH_PHOTO)
                        .setSourceImage(file1)
                        .getResizedFile();

                file1 = new File(AppConstant.PATH_PHOTO + "/" + sPhotoName);
                if (file1.exists()){
                    Bitmap bmp = BitmapFactory.decodeFile(AppConstant.PATH_PHOTO + "/" + sPhotoName);
                    imgOutlet.setImageBitmap(bmp);
                }
            } catch (IOException e) {
                AppController.getInstance().CustomeDialog(AddOutletPreRegActivity.this,
                        "Error " + e.getMessage());
            }
        }

        /*photos.addAll(returnedPhotos);
        imagesAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() - 1);*/
    }

    private void onPhotosReturnedSide(List<File> returnedPhotos) {
        photos.addAll(returnedPhotos);
        for (File file : returnedPhotos){

            File file1 = new File(AppConstant.PATH_PHOTO_CAMERA + "/" + sPhotoNameSamping);
            try {
                AppController.getInstance().copyFile(file, file1);

                File resizedImage = new Resizer(AddOutletPreRegActivity.this)
                        .setTargetLength(480)
                        .setQuality(80)
                        .setOutputFormat("JPEG")
                        .setOutputDirPath(AppConstant.PATH_PHOTO)
                        .setSourceImage(file1)
                        .getResizedFile();

                file1 = new File(AppConstant.PATH_PHOTO + "/" + sPhotoNameSamping);
                if (file1.exists()){
                    Bitmap bmp = BitmapFactory.decodeFile(AppConstant.PATH_PHOTO + "/" + sPhotoNameSamping);
                    imgOutletOptional.setImageBitmap(bmp);
                }
            } catch (IOException e) {
                AppController.getInstance().CustomeDialog(AddOutletPreRegActivity.this,
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
    public void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(AddOutletPreRegActivity.this);
        super.onDestroy();
    }


    void CustomeDialog(String ISI){
        final Dialog dialog = new Dialog(AddOutletPreRegActivity.this);
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

    private void DisplayDialogGPS(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddOutletPreRegActivity.this);
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

    private boolean checkPermission(String permissions[]) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(AddOutletPreRegActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
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
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(AddOutletPreRegActivity.this, permission);
                if (!showRationale) {
                    bPermission = false;
                    break;
                }
            }

            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

            new AlertDialog.Builder(AddOutletPreRegActivity.this)
                    .setTitle("Permissions Required")
                    .setMessage(dialogMsg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AddOutletPreRegActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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
                //finish();
            }

        } else {
            //finish();
        }
    }

    void showDialodDatePick(){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                AddOutletPreRegActivity.this,
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
    }


}
