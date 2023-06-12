package com.soldis.yourthaitea;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_VERSION;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.kevalpatel.passcodeview.PinView;
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator;
import com.kevalpatel.passcodeview.indicators.CircleIndicator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder;
import com.kevalpatel.passcodeview.keys.RoundKey;

import java.io.File;
import java.util.Arrays;

public class PinViewActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String ARG_CURRENT_PIN = "current_pin";

    private PinView mPinView;
    ProgressDialog progress;
    Tbl_Karyawan tblMotoris;

    RelativeLayout layout_setting;

    boolean bPermission;
    String sPhotoName;
    ImageView imgAvatar;
    TextView txtName, txtAddress, txtSlsno;

    Obj_MOTORIS objMotoris;
    String SLSNO = "", LEVEL_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_login_passcode);
        InitControl();
        bPermission = true;

        if (bPermission) {
            InitFolder();
            InitDatabase();

        }

        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            if (tblMotoris.data.size() > 0){
                for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                    SLSNO = dat.SLSNO;

                    LEVEL_ID = dat.LEVEL_ID;
                    AppConstant.strSlsNo = dat.SLSNO;
                    AppConstant.strMitraID = dat.MITRA_ID;
                    txtSlsno.setText(dat.SLSNO == null ? "" : dat.SLSNO);
                    txtName.setText(dat.SLSNAME == null ? "" : "Hi, " + dat.SLSNAME);
                    txtAddress.setText(dat.MITRA_ID == null ? "" :  dat.MITRA_ID + " - " + (dat.ADDRESS == null ? "" :  dat.ADDRESS) );
                    if (dat.PHOTO != null && !dat.PHOTO.equals("")){
                        AppController.getInstance().displayImage(PinViewActivity.this , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, imgAvatar);
                    }else{
                        imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
                    }
                }
            }
        }

        objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(SLSNO);

        if (objMotoris.getSLSNO() != null && !objMotoris.getSLSNO() .equals("")){
            String PASSWORD = objMotoris.getPASSWORD();
            int[] correctPattern = new int[PASSWORD.length()];
            for ( int i = 0; i < PASSWORD.length(); i++){
                correctPattern[i] = Integer.parseInt(String.valueOf(PASSWORD.charAt(i)));
            }

            mPinView.setPinAuthenticator(new PasscodeViewPinAuthenticator(correctPattern));
        }else{
            Intent mIntent = new Intent(PinViewActivity.this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }


    }

    void InitControl(){
        layout_setting = (RelativeLayout)findViewById(R.id.layout_setting);
        txtName = (TextView)findViewById(R.id.txtName);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtSlsno = (TextView)findViewById(R.id.txtSlsno);
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        mPinView = (PinView) findViewById(R.id.pattern_view);

        //Build the desired key shape and pass the theme parameters.
        //REQUIRED
        mPinView.setKey(new RoundKey.Builder(mPinView)
                .setKeyPadding(R.dimen.space_5)
                .setKeyStrokeColorResource(R.color.colorBar)
                .setKeyStrokeWidth(R.dimen.space_1)
                .setKeyTextColorResource(R.color.colorBar)
                .setKeyTextSize(R.dimen.space_18));

        //Build the desired indicator shape and pass the theme attributes.
        //REQUIRED
        mPinView.setIndicator(new CircleIndicator.Builder(mPinView)
                .setIndicatorRadius(R.dimen.space_10)
                .setIndicatorFilledColorResource(R.color.white)
                .setIndicatorStrokeColorResource(R.color.colorBar)
                .setIndicatorStrokeWidth(R.dimen.space_5));

        mPinView.setPinLength(PinView.DYNAMIC_PIN_LENGTH);

        //Set the name of the keys based on your locale.
        //OPTIONAL. If not passed key names will be displayed based on english locale.
        mPinView.setKeyNames(new KeyNamesBuilder()
                .setKeyOne(this, R.string.key_1)
                .setKeyTwo(this, R.string.key_2)
                .setKeyThree(this, R.string.key_3)
                .setKeyFour(this, R.string.key_4)
                .setKeyFive(this, R.string.key_5)
                .setKeySix(this, R.string.key_6)
                .setKeySeven(this, R.string.key_7)
                .setKeyEight(this, R.string.key_8)
                .setKeyNine(this, R.string.key_9)
                .setKeyZero(this, R.string.key_0));

        mPinView.setTitle("Enter the PIN");

        mPinView.setAuthenticationListener(new AuthenticationListener() {
            @Override
            public void onAuthenticationSuccessful() {
                //User authenticated successfully.
                //Navigate to secure screens.
                if (LEVEL_ID.equals("1")){
                    Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(mIntent);
                }else{
                    Intent mIntent = new Intent(getBaseContext(), MainAdminActivity.class);
                    startActivity(mIntent);
                }
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                //Calls whenever authentication is failed or user is unauthorized.
                //Do something
            }
        });

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialogSettingDomain();
            }
        });
    }

    void InitFolder(){
        AppController.getInstance().DeleteFile(AppConstant.PATH_PHOTO_CAMERA);

        File folder;
        try{
            String sFolder = Environment.getExternalStorageDirectory().toString();

            folder = new File(sFolder + "/Univenus");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Univenus/AFH");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Univenus/AFH/db");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Univenus/AFH/Download");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Univenus/AFH/Photo");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_PHOTO_CAMERA);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Univenus/AFH/Log");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_XLS);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_APK);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_DB_BACKUP);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_TRX);
            if (!folder.exists()) folder.mkdirs();
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    void InitDatabase(){
        try{
            AppController.getInstance().OpenDatabaseSqlite(this);

            //Check Version DB--------------------------------------------------------------
            Obj_VERSION objVersion = new Obj_VERSION();
            objVersion = objVersion.getData();
            if (objVersion.getVERSION_DB() != null){
                if (!objVersion.getVERSION_DB().equals(AppConstant.VERSION_DB)){
                    AppConstant.myDb.close();
                    File file = new File(AppConstant.PATH_FOLDER_DB);
                    if (file.exists()){
                        File[] yfiles = file.listFiles();
                        for (File doc : yfiles){
                            AppController.getInstance().FileDelete(doc.toString());
                        }

                        //Copy New File---------------------------------------------------------
                        AppController.getInstance().OpenDatabaseSqlite(this);
                    }
                }
            }
        }catch (Exception e){
            AppController.getInstance().CustomeDialog(PinViewActivity.this, e.getMessage());
            File file = getDatabasePath(AppConstant.PATH_DB);
            if (file.exists())file.delete();
        }
    }

    void CustomeDialogSettingDomain(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_input_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        final EditText edtIsi = (EditText) dialog.findViewById(R.id.edt_isi);
        txtDialog.setText("Isi Domain/IP");
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        try{
            edtIsi.setText(AppController.getInstance().getSessionManager().getStringData(SessionManager.DOMAIN_URL
            ));
        }catch (Exception e){
            edtIsi.setText("");
        }

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AppController.getInstance().getSessionManager().putStringData(SessionManager.DOMAIN_URL,
                        edtIsi.getText().toString().trim());
            }
        });

        dialog.show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray(ARG_CURRENT_PIN, mPinView.getCurrentTypedPin());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPinView.setCurrentTypedPin(savedInstanceState.getIntArray(ARG_CURRENT_PIN));
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
                            ActivityCompat.requestPermissions(PinViewActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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