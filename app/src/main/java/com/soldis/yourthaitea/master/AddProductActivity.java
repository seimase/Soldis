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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.util.NumberTextWatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import me.echodev.resizer.Resizer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    Toolbar toolbar;

    EditText edtIdMakanan,
            edtNama,
            edtHarga;

    RelativeLayout lySave;
    TextView textLabel;
    Obj_MASTER objMaster;
    static int REQ_PHOTO = 100;
    private static final int REQUEST_PERMISSIONS = 1000;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    String sPhotoName;
    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private ArrayList<File> photos = new ArrayList<>();

    ImageView imgOutlet;
    boolean bPermission;
    long lHarga;

    ProgressDialog progress;
    Tbl_Master tblMaster;
    Spinner spnStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue2));
        }
        setContentView(R.layout.activity_add_product);
        sPhotoName = "";
        InitControl();
        bPermission = true;

        objMaster = new Obj_MASTER();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void InitControl(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        lySave = (RelativeLayout) findViewById(R.id.layout_save);
        textLabel = (TextView)findViewById(R.id.textLabel);
        textLabel.setText("INPUT ITEM");

        spnStatus = (Spinner)findViewById(R.id.spnStatus);
        edtIdMakanan = (EditText)findViewById(R.id.edtIdMakanan);
        edtNama = (EditText)findViewById(R.id.edtNama);
        edtHarga = (EditText)findViewById(R.id.edtHarga);

        imgOutlet = (ImageView)findViewById(R.id.imgOutlet);


        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Makanan");
        spinnerArray.add("Minuman");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adapter);


        lySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidasiInput())
                CustomeDialog();
            }
        });

        edtHarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("") && !charSequence.toString().equals("0")){
                    lHarga = Long.parseLong(charSequence.toString().trim().replace(",",""));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextWatcher tw = new NumberTextWatcher(edtHarga, Locale.US, 0);
        edtHarga.addTextChangedListener(tw);

        imgOutlet.setImageDrawable(null);
        imgOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();

                if (bPermission) {
                    try{
                        sPhotoName = "SB_" + AppConstant.strSlsNo + "_" + AppController.getInstance().getDateTime() + ".jpg";
                        EasyImage.openChooserWithDocuments(AddProductActivity.this,"Pilih Sumber Gambar", REQ_PHOTO);
                    }catch (Exception e){
                        Log.w("Error Photo", e.getMessage());
                    }
                }
            }
        });
    }

    boolean ValidasiInput(){
        String ID_MAKANAN = edtIdMakanan.getText().toString().trim();

        objMaster = new Obj_MASTER();
        objMaster = objMaster.getData(ID_MAKANAN);

        if (objMaster.getPCodeName() == null || objMaster.getPCodeName().equals("")){
            if (edtIdMakanan.getText().toString().equals("")){
                AppController.getInstance().CustomeDialog(AddProductActivity.this, "ID Makanan belum diisi");
                return false;
            }

            if (edtNama.getText().toString().equals("")){
                AppController.getInstance().CustomeDialog(AddProductActivity.this, "Nama belum diisi");
                return false;
            }

            if (lHarga == 0){
                AppController.getInstance().CustomeDialog(AddProductActivity.this, "Harga belum diisi");
                return false;
            }
        }else{
            AppController.getInstance().CustomeDialog(AddProductActivity.this, "ID sudah di pakai");
            return false;
        }



        return true;
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.stock_saved));
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

                SendDataToServer();

            }
        });

        dialog.show();
    }

    void CustomeDialogFinish(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtYes = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_isi);

        txtDialog.setText(getResources().getString(R.string.stock_hasben_saved));

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();

            }
        });

        dialog.show();
    }


    void SaveData(){
        objMaster = new Obj_MASTER();

        objMaster.setPCode(edtIdMakanan.getText().toString());
        objMaster.setPCodeName(edtNama.getText().toString());
        objMaster.setUnit1("PORSI");
        objMaster.setUnit2("PORSI");
        objMaster.setUnit3("PORSI");
        objMaster.setConvUnit2(1);
        objMaster.setConvUnit3(1);
        objMaster.setPrLin("");

        objMaster.setPHOTO_NAME(sPhotoName);
        objMaster.setSellPrice1(lHarga);
        objMaster.setSellPrice2(lHarga);
        objMaster.setSellPrice3(lHarga);
        objMaster.CreateMaster(objMaster);
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
                            ActivityCompat.requestPermissions(AddProductActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
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

    @Override
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
                if (type == REQ_PHOTO){
                    onPhotosReturned(imagesFiles);
                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(AddProductActivity.this);
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
                    imgOutlet.setImageBitmap(bmp);
                }
            } catch (IOException e) {
                AppController.getInstance().CustomeDialog(AddProductActivity.this,
                        "Error " + e.getMessage());
            }
        }

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

    void SendDataToServer(){
        boolean bDoneCek = false;

        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_data_saved), true);

        Log.w("Cek Database"," File Raw");


        try{
            RequestBody requestFile ;
            MultipartBody.Part body = null;
            if (sPhotoName != ""){
                File filePhoto = new File(AppConstant.PATH_PHOTO + "/" + sPhotoName);
                String sFile_SizeRaw = "0";
                if (filePhoto.exists()){
                    sFile_SizeRaw = Long.toString(filePhoto.length());
                    bDoneCek = true;

                    requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), filePhoto);
                    body = MultipartBody.Part.createFormData("files", filePhoto.getName(), requestFile);
                }else{
                    bDoneCek = false;
                }
            }


            RequestBody kodecabang = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strMitraID);
            RequestBody tgltrx = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strTglTrx);
            RequestBody slsno = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strSlsNo);
            RequestBody idmakanan = RequestBody.create(MediaType.parse("multipart/form-data"), edtIdMakanan.getText().toString().trim());
            RequestBody namamakanan = RequestBody.create(MediaType.parse("multipart/form-data"), edtNama.getText().toString().trim());
            RequestBody harga = RequestBody.create(MediaType.parse("multipart/form-data"), edtHarga.getText().toString().trim().replace(",",""));

            String sCategoryID = "";
            if (spnStatus.getSelectedItem().toString().trim().equals("Makanan")){
                sCategoryID = "10";
            }else{
                sCategoryID = "11";
            }

            RequestBody categoryID = RequestBody.create(MediaType.parse("multipart/form-data"), sCategoryID);

            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), sPhotoName);

            Call<Tbl_Master> call;

            if (bDoneCek){
                call = NetworkManager.getNetworkService().postAddProductPhoto(kodecabang,
                        tgltrx,
                        idmakanan,
                        namamakanan,
                        categoryID,
                        harga,
                        slsno,
                        photo,
                        body);
            }else{
                call = NetworkManager.getNetworkService().postAddProduct(kodecabang,
                        tgltrx,
                        idmakanan,
                        namamakanan,
                        categoryID,
                        harga,
                        slsno);
            }


            call.enqueue(new Callback<Tbl_Master>() {
                @Override
                public void onResponse(Call<Tbl_Master> call, Response<Tbl_Master> response) {
                    int code = response.code();
                    progress.dismiss();
                    if (code == 200){
                        tblMaster = response.body();
                        if (!tblMaster.error){
                            SaveData();
                            CustomeDialogFinish();
                        }

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Tbl_Master> call, Throwable t) {
                    progress.dismiss();
                    AppController.getInstance().CustomeDialog(AddProductActivity.this, t.getMessage());
                }
            });
        }catch (Exception e){
            AppController.getInstance().CustomeDialog(AddProductActivity.this, e.getMessage());
            progress.dismiss();
        }
    }


}
