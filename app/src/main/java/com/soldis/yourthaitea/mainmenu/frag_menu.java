package com.soldis.yourthaitea.mainmenu;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.TimeVisitActivity;
import com.soldis.yourthaitea.dashboard.DashboardActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_DISPENSER;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.master.MasterOutletActivity;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.transaction.DailySalesmanActivity;

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
 * Created by User on 8/10/2017.
 */

public class frag_menu extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
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
                EMAIL_ID = "support@soldis-sfa.com";
                PHONE_NUMBER = "";

                if (dat.PHOTO != null && !dat.PHOTO.equals("")){
                    AppController.getInstance().displayImage(getActivity() , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, imgAvatar);
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
        
    }



    void InitControl(View v){
        txtEmail = (TextView)v.findViewById(R.id.txtEmail);
        txtPhone = (TextView)v.findViewById(R.id.txtPhone);
        txtTgl = (TextView)v.findViewById(R.id.txtTgl);
        txtName = (TextView)v.findViewById(R.id.txtName);
        imgAvatar = (ImageView)v.findViewById(R.id.img_avatar);
        lyVisit = (LinearLayout)v.findViewById(R.id.layout_visit);
        lyOutlet = (LinearLayout)v.findViewById(R.id.layout_outlet);
        lyTransaction = (LinearLayout)v.findViewById(R.id.layout_transaction);
        lyRingkasan = (LinearLayout)v.findViewById(R.id.layout_ringkasan);
        //lyLogout = (RelativeLayout) v.findViewById(R.id.layout_logout);


        lyVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getActivity(), TimeVisitActivity.class);
                startActivity(mIntent);
            }
        });

        lyOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getActivity(), MasterOutletActivity.class);
                startActivity(mIntent);
            }
        });

        lyTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getActivity(), DailySalesmanActivity.class);
                startActivity(mIntent);
            }
        });

        lyRingkasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getActivity(), DashboardActivity.class);
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
                checkPermissions();

                if (bPermission) {
                    try {
                        sPhotoName = AppConstant.strSlsNo + "_" + AppController.getInstance().getDateTime() + ".jpg";

                        /*File file = new File(AppConstant.PATH_PHOTO_CAMERA + "/" + sPhotoName);
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent, REQ_PHOTO);*/

                        EasyImage.openCamera(getActivity(), REQ_PHOTO);
                    } catch (Exception e) {
                        Log.w("Error Photo", e.getMessage());
                    }

                }
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();

        Obj_MOTORIS objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);
        if (objMotoris.getSLSNO() != null) AppConstant.strTglTrx = objMotoris.getTRX_DATE();

        txtTgl.setText("Tanggal transaksi : " + AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));

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
        final Dialog dialog = new Dialog(getActivity());
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
                        AppController.getInstance().CustomeDialog(getActivity(),getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    CustomeDialog(getResources().getString(R.string.setting_transaction_not_complete));
                }

            }
        });

        dialog.show();
    }

    void LogOut(){
        progress = ProgressDialog.show(getActivity(), getResources().getString(R.string.main_Information),
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
        final Dialog dialog = new Dialog(getActivity());
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
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
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
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

                File resizedImage = new Resizer(getActivity())
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
                AppController.getInstance().CustomeDialog(getActivity(),
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
        EasyImage.clearConfiguration(getActivity());
        super.onDestroy();
    }

    void SendDataToServer(){
        boolean bDoneCek = true;

        progress = ProgressDialog.show(getActivity(), getResources().getString(R.string.main_Information),
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

                                AppController.getInstance().CustomeDialog(getContext(),
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
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
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
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
                if (!showRationale) {
                    bPermission = false;
                    break;
                }
            }

            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

            new AlertDialog.Builder(getActivity())
                    .setTitle("Permissions Required")
                    .setMessage(dialogMsg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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
                getActivity().finish();
            }

        } else {
            getActivity().finish();

        }
    }

}
