package com.soldis.yourthaitea;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.dbhelper.DBHelper;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_VERSION;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Version;
import com.soldis.yourthaitea.model.net.NetworkManager;

import java.io.File;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    TextView btnLogin, txtVersion, txtUpdate;
    EditText edtUserName, edtPassword, edtBranch;

    DBHelper myDBHelper;
    ProgressDialog progress;

    Obj_MOTORIS objMotoris;
    Tbl_Karyawan tblMotoris;
    Tbl_Version tblVersion;

    RelativeLayout layout_setting;

    boolean bPermission;
    boolean bIntialFolder;
    String DEVICE_ID = "";
    String SLSNO = "";
    String LEVEL_ID = "";

    String sCurrentLanguage;
    String sLanguagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_login);
        InitControl();
        bPermission = true;

        DEVICE_ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID); ;

        AppController.getInstance().getSessionManager().putStringData(AppConstant.DEVICE_ID, DEVICE_ID);

        String DOMAIN = AppController.getInstance().getSessionManager().getStringData(SessionManager.DOMAIN_URL);

        if (DOMAIN.equals("")){
            AppController.getInstance().getSessionManager().putStringData(SessionManager.DOMAIN_URL, "http://www.bumbupekalongan.com/pos");
            //CustomeDialogSettingDomain();
        }

        checkPermissions();

        if (bPermission) {

            InitFolder();
            InitDatabase();

        }

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String brand = Build.BRAND;

        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        edtUserName.setEnabled(true);
        if (tblMotoris != null){
            if (tblMotoris.data.size() > 0){
                for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                    SLSNO = dat.SLSNO;
                    LEVEL_ID = dat.LEVEL_ID;
                    edtUserName.setText(dat.SLSNO + " : " + dat.SLSNAME);
                    edtUserName.setEnabled(false);
                    edtBranch.setText(dat.MITRA_ID);
                    edtBranch.setEnabled(false);

                    AppConstant.strSlsNo = dat.SLSNO;
                    AppConstant.strMitraID = dat.MITRA_ID;
                }
            }
        }


        objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(SLSNO);

        Log.w("SLSNO", objMotoris.getSLSNAME());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bPermission){
                    bPermission = true;
                    checkPermissions();
                    if (bPermission){
                        InitFolder();
                        InitDatabase();
                    }
                }else{
                    Login();
                }
            }
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    if (!bPermission){
                        bPermission = true;
                        checkPermissions();
                        if (bPermission){
                            InitFolder();
                            InitDatabase();

                        }
                    }else{
                        Login();
                    }
                }
                return false;
            }
        });

        sLanguagePref =  AppController.getInstance().getSessionManager().getStringData(AppConstant.LANGUAGE);

        sCurrentLanguage = getResources().getConfiguration().locale.getLanguage();
        sCurrentLanguage = (sCurrentLanguage.equals("en") || sCurrentLanguage.equals("EN")) ? "en" : "id";

        if (sLanguagePref != null && sCurrentLanguage != null){
            if (!sLanguagePref.equals(sCurrentLanguage)){
                Resources res = getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                if (sLanguagePref.trim().equals(""))sLanguagePref = AppConstant.LANGUAGE_DEFAULT;

                AppController.getInstance().getSessionManager().setLocale(sLanguagePref);
                AppController.getInstance().getSessionManager().setLanguage(sLanguagePref);

                finish();
                startActivity(getIntent());
            }
        }else{
            AppController.getInstance().getSessionManager().setLocale(AppConstant.LANGUAGE_DEFAULT);
            AppController.getInstance().getSessionManager().setLanguage(AppConstant.LANGUAGE_DEFAULT);

            finish();
            startActivity(getIntent());
        }

        txtUpdate.setPaintFlags(txtUpdate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    void InitControl(){
        sCurrentLanguage = getResources().getConfiguration().locale.getLanguage();
        sCurrentLanguage = (sCurrentLanguage.equals("en") || sCurrentLanguage.equals("EN")) ? "en" : "id";

        layout_setting = (RelativeLayout) findViewById(R.id.layout_setting);

        btnLogin = (TextView)findViewById(R.id.btn_login);
        edtUserName = (EditText)findViewById(R.id.edt_username);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        edtBranch = (EditText)findViewById(R.id.edt_branch);
        txtVersion = (TextView)findViewById(R.id.text_version);
        txtUpdate = (TextView)findViewById(R.id.txtUpdate);

        txtVersion.setText( getResources().getString(R.string.main_version) + " " + BuildConfig.VERSION_NAME + AppConstant.REVISION);

        AppController.getInstance().getSessionManager().setLocale(sCurrentLanguage);
        AppController.getInstance().getSessionManager().setLanguage(sCurrentLanguage);

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialogSettingDomain();
            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VisitValidation()){
                    if (AppController.getInstance().isOnline()){
                        SyncVersion();
                    }else{
                        AppController.getInstance().CustomeDialog(LoginActivity.this,getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    AppController.getInstance().CustomeDialog(LoginActivity.this, getResources().getString(R.string.setting_transaction_not_complete));
                }
            }
        });

    }

    void Login(){
        String sUserName = "";
        if (SLSNO.equals("")){
            sUserName = edtUserName.getText().toString();
        }else{
            sUserName = SLSNO;
        }

        String sPassword = edtPassword.getText().toString();
        String sBranch = edtBranch.getText().toString();

        if (sUserName.trim().equals("") || sPassword.trim().trim().equals("") || sBranch.trim().trim().equals("")){
            CustomeDialog();
        }else{
            objMotoris = new Obj_MOTORIS();
            objMotoris = objMotoris.getData(sUserName.toUpperCase().trim(), sPassword);
            if (objMotoris.getSLSNO() == null || objMotoris.getSLSNO().equals("")){
                if (AppController.getInstance().isOnline()){
                    ValidasiLogin(sUserName, sPassword, sBranch);
                }else{
                    CustomeDialog(getResources().getString(R.string.text_no_connection));
                }

            }else{
                tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
                if (tblMotoris == null){
                    if (AppController.getInstance().isOnline()){
                        ValidasiLogin(sUserName, sPassword, sBranch);
                    }else{
                        CustomeDialog(getResources().getString(R.string.text_no_connection));
                    }
                }else{
                    if (LEVEL_ID != null){
                        if (LEVEL_ID.equals("1")){
                            Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(mIntent);
                        }else{
                            Intent mIntent = new Intent(getBaseContext(), MainAdminActivity.class);
                            startActivity(mIntent);
                        }
                    }

                    finish();
                }

            }
        }
    }

    void InitFolder(){
        AppController.getInstance().DeleteFile(AppConstant.PATH_PHOTO_CAMERA);

        File folder;
        try{
            String sFolder = Environment.getExternalStorageDirectory().toString();

            folder = new File(AppConstant.FOLDER_MAIN);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.FOLDER_PROJECT);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_DB);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.FOLDER_DOWNLOAD);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_PHOTO);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_PHOTO_CAMERA);
            if (!folder.exists()) folder.mkdirs();

            folder = new File(AppConstant.PATH_FOLDER_LOG);
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
            /*myDBHelper = new DBHelper(getBaseContext());
            myDBHelper.createDataBase();
            AppConstant.myDb = myDBHelper.openDataBase();*/
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
            AppController.getInstance().CustomeDialog(LoginActivity.this, e.getMessage());
            File file = getDatabasePath(AppConstant.PATH_DB);
            if (file.exists())file.delete();
        }
    }

    void ValidasiLogin(final String USERNAME, final String PASSWORD, final String KODECABANG){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);

        try{
            Call<Tbl_Karyawan> call = NetworkManager.getNetworkService().loginUser(USERNAME, PASSWORD, KODECABANG);
            call.enqueue(new Callback<Tbl_Karyawan>() {
                @Override
                public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMotoris = response.body();
                        if (!tblMotoris.error){

                            boolean bDoneValidasi = true;
                            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                                if (dat.DEVICE_ID != null){
                                    if (!dat.DEVICE_ID.equals(DEVICE_ID)){
                                        bDoneValidasi = false;
                                        CustomeDialog(getResources().getString(R.string.login_used_motoris));
                                    }
                                }

                                if (bDoneValidasi){
                                    if (dat.DEVICE_ID == null){
                                        UpdateDeviceID(USERNAME, PASSWORD, KODECABANG);
                                    }else{
                                        AppController.getInstance().getSessionManager().setUserAccount(null);
                                        AppController.getInstance().getSessionManager().setUserAccount(tblMotoris);
                                        AppController.getInstance().getSessionManager().getUserProfile();

                                        objMotoris = new Obj_MOTORIS();
                                        objMotoris.setSLSNO(dat.SLSNO);
                                        objMotoris.setSLSNAME(dat.SLSNAME);
                                        objMotoris.setPASSWORD(dat.PASSWORD);
                                        objMotoris.setADDRESS(dat.ADDRESS);
                                        objMotoris.setCITY("");
                                        objMotoris.setPHONE("");
                                        objMotoris.setGOOGLE_API_KEY("");
                                        objMotoris.setSEQNO(0);
                                        objMotoris.setPREFIX_ID(dat.PREFIX_ID);
                                        objMotoris.CreateData(objMotoris);

                                        AppController.getInstance().getSessionManager().putStringData(AppConstant.DEVICE_ID, DEVICE_ID);
                                        if (dat.LEVEL_ID != null){
                                            if (dat.LEVEL_ID.equals("1")){
                                                Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
                                                startActivity(mIntent);
                                            }else{
                                                Intent mIntent = new Intent(getBaseContext(), MainAdminActivity.class);
                                                startActivity(mIntent);
                                            }
                                        }

                                    }

                                }
                            }

                        }else{
                            CustomeDialog(tblMotoris.message);
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

    void UpdateDeviceID(String USERNAME, String PASSWORD, String KODECABANG){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.login_update_data), true);
        try{
            Call<Tbl_Karyawan> call = NetworkManager.getNetworkService().UpdateDevice(USERNAME, PASSWORD, DEVICE_ID, KODECABANG);
            call.enqueue(new Callback<Tbl_Karyawan>() {
                @Override
                public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMotoris = response.body();
                        if (!tblMotoris.error){
                            for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                                AppController.getInstance().getSessionManager().setUserAccount(null);
                                AppController.getInstance().getSessionManager().setUserAccount(tblMotoris);
                                AppController.getInstance().getSessionManager().getUserProfile();

                                objMotoris = new Obj_MOTORIS();
                                objMotoris.setSLSNO(dat.SLSNO);
                                objMotoris.setSLSNAME(dat.SLSNAME);
                                objMotoris.setPASSWORD(dat.PASSWORD);
                                objMotoris.setADDRESS(dat.ADDRESS);
                                objMotoris.setCITY("");
                                objMotoris.setPHONE("");
                                objMotoris.setGOOGLE_API_KEY("");
                                objMotoris.setSEQNO(0);
                                objMotoris.CreateData(objMotoris);

                                if (dat.LEVEL_ID != null){
                                    if (dat.LEVEL_ID.equals("1")){
                                        Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(mIntent);
                                    }else{
                                        Intent mIntent = new Intent(getBaseContext(), MainAdminActivity.class);
                                        startActivity(mIntent);
                                    }
                                }

                                finish();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<Tbl_Karyawan> call, Throwable t) {

                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
                            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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


    void SyncVersion(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_check_for_update), true);
        try{
            Call<Tbl_Version> call = NetworkManager.getNetworkService().CheckVersion(AppConstant.strMitraID,
                    AppConstant.strSlsNo);
            call.enqueue(new Callback<Tbl_Version>() {
                @Override
                public void onResponse(Call<Tbl_Version> call, Response<Tbl_Version> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblVersion = response.body();
                        if (!tblVersion.error){
                            for(Tbl_Version.Datum dat : tblVersion.data){
                                String sMainVersion = BuildConfig.VERSION_NAME.replace(".","");
                                String sNewVersion = dat.VERSION_ID.replace(".","");

                                if (Long.parseLong(sNewVersion) > Long.parseLong(sMainVersion)){
                                    CustomeDialogSyncVersion(dat.VERSION_ID, dat.APK_NAME);
                                }else{
                                    AppController.getInstance().CustomeDialog(LoginActivity.this, "You apps version is Up to date");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Version> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void CustomeDialogSyncVersion(final String VERSION, final String APK_NAME){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText("Download new version : " + VERSION + " ?");
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
                Intent mIntent = new Intent(LoginActivity.this, DownloadActivity.class);
                mIntent.putExtra("VERSION", VERSION);
                mIntent.putExtra("APK_NAME", APK_NAME);
                startActivity(mIntent);
            }
        });

        dialog.show();
    }
}
