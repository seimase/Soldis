package com.soldis.yourthaitea;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.soldis.yourthaitea.model.net.NetworkManager;

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

/**
 * Created by User on 8/15/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    static int REQ_PHOTO = 100;
    private static final int REQUEST_PERMISSIONS = 1000;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    String sPhotoName;

    RelativeLayout layout_back;

    TextView txtId,
            txtName,
            txtAlamat,
            txtTelp,
            txtKota,
            txtTrxDate,
            txtLevel,
            txtType,
            txtLogout,
            txtSetting
    ;

    Obj_MOTORIS objMotoris;
    Tbl_Karyawan tblMotoris;

    ProgressDialog progress;
    boolean bPermission;

    ImageView imgAvatar;

    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();

    String LEVEL_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_profile);
        bPermission = true;
        InitControl();

        FillForm();
    }

    private void InitControl() {
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        txtId = (TextView)findViewById(R.id.text_id);
        txtLevel = (TextView)findViewById(R.id.text_level);
        txtType = (TextView)findViewById(R.id.text_type);
        txtName = (TextView)findViewById(R.id.text_name);
        txtAlamat = (TextView)findViewById(R.id.text_alamat);
        txtTelp = (TextView)findViewById(R.id.text_telp);
        txtKota = (TextView)findViewById(R.id.text_city);
        txtTrxDate = (TextView)findViewById(R.id.text_trxdate);
        txtLogout = (TextView)findViewById(R.id.txtLogout);
        txtSetting = (TextView)findViewById(R.id.txtSetting);

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialog();
            }
        });

        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,REQ_PHOTO);*/
                checkPermissions();

                if (bPermission) {
                    try {
                        sPhotoName = AppConstant.strSlsNo + "_" + AppController.getInstance().getDateTime() + ".jpg";
                        //EasyImage.openCamera(ProfileActivity.this, REQ_PHOTO);
                        EasyImage.openChooserWithDocuments(ProfileActivity.this, "Pilih Sumber Gambar", REQ_PHOTO);
                    } catch (Exception e) {
                        Log.w("Error Photo", e.getMessage());
                    }

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
        objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);

        if (objMotoris.getSLSNO() != null) txtId.setText(objMotoris.getSLSNO());
        if (objMotoris.getSLSNAME() != null) txtName.setText(objMotoris.getSLSNAME());
        if (objMotoris.getADDRESS() != null) txtAlamat.setText(objMotoris.getADDRESS());
        if (objMotoris.getPHONE() != null) txtTelp.setText(objMotoris.getPHONE());
        if (objMotoris.getCITY() != null) txtKota.setText(objMotoris.getCITY());
        if (objMotoris.getTRX_DATE() != null) txtTrxDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(objMotoris.getTRX_DATE()));

        txtLevel.setText(AppConstant.strLevelDescription);
        txtType.setText(AppConstant.strPrefixID);

        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null && tblMotoris.data.size() > 0) {
            for (Tbl_Karyawan.Datum dat : tblMotoris.data) {
                LEVEL_ID = dat.LEVEL_ID == null ? "" : dat.LEVEL_ID;
                if (dat.PHOTO != null && !dat.PHOTO.equals("")){
                    AppController.getInstance().displayImage(ProfileActivity.this , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, imgAvatar);
                }else{
                    imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                }
            }
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
                        AppController.getInstance().CustomeDialog(ProfileActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    if (!LEVEL_ID.equals("1")){
                        if (AppController.getInstance().isOnline()){
                            LogOut();
                        }else{
                            AppController.getInstance().CustomeDialog(ProfileActivity.this,getResources().getString(R.string.text_no_connection));
                        }
                    }else
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
                            Obj_CUSTMST  obj_custmst = new Obj_CUSTMST();
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

                            Obj_MASTER objMaster = new Obj_MASTER();
                            objMaster.DeleteMaster();

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
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(ProfileActivity.this);
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
                AppController.getInstance().CustomeDialog(ProfileActivity.this,
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

                                AppController.getInstance().CustomeDialog(ProfileActivity.this,
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
                            ActivityCompat.requestPermissions(ProfileActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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
