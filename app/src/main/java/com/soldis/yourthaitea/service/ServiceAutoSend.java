package com.soldis.yourthaitea.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.dbhelper.DBHelper;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.ApiGoogleMaps;
import com.soldis.yourthaitea.model.Obj_SendData;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.net.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceAutoSend extends IntentService {

    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.bpbatam.enterprise.MainMenuActivity";
    public ServiceAutoSend() {
        super("ServiceAutoLogOut");
    }
    Obj_SendData objSendData;

    ApiGoogleMaps apiGoogleMaps;
    String GOOGLE_PROVINSI,
            GOOGLE_KABUPATEN,
            GOOGLE_KECAMATAN,
            GOOGLE_KELURAHAN,
            GOOGLE_KODEPOS,
            GOOGLE_ALAMAT
                    ;

    ArrayList<String> adm1  = new ArrayList<String>();
    ArrayList<String> adm2  = new ArrayList<String>();
    ArrayList<String> adm3  = new ArrayList<String>();
    ArrayList<String> adm4  = new ArrayList<String>();
    ArrayList<String> postalCode  = new ArrayList<String>();


    Tbl_Custmst tblCustmst;
    @Override
    protected void onHandleIntent(Intent intent){
        String USER = "";
        Log.w("Start Service"," Cek");

        String sCekDB = AppConstant.PATH_DB;
        adm1.add("administrative_area_level_1");
        adm1.add("political");

        adm2.add("administrative_area_level_2");
        adm2.add("political");

        adm3.add("administrative_area_level_3");
        adm3.add("political");

        adm4.add("administrative_area_level_4");
        adm4.add("political");
        postalCode.add("postal_code");

        AppConstant.API_KEY_GOOGLE_MAPS = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                AppConstant.API_KEY_GOOGLE_MAPS = "";
            }
        }

        if (AppConstant.API_KEY_GOOGLE_MAPS == null) AppConstant.API_KEY_GOOGLE_MAPS = "";

        Log.w("GOOGLE API ", AppConstant.API_KEY_GOOGLE_MAPS);

        File fileDb = new File(sCekDB);
        if (fileDb.exists()){
            DBHelper myDBHelper;
            try{
/*
                myDBHelper = new DBHelper(getBaseContext());
                AppConstant.myDb = myDBHelper.openDataBase();*/

                AppController.getInstance().OpenDatabaseSqlite(getBaseContext());

                Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                ArrayList<Obj_CUSTCARD> objCustcards = new ArrayList<>();
                objCustcards = objCustcard.getDataList(false);
                Log.w("Total Order ", Integer.toString(objCustcards.size()));

                Obj_VISIT objVisit = new Obj_VISIT();
                objVisit = objVisit.getData();
                if (objVisit.getTMGO() != null && !objVisit.getTMGO().equals("")){
                    if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") ){
                        //do nothing
                    }else{

                        Obj_CUSTMST objCustmst = new Obj_CUSTMST();

                        for (Obj_CUSTMST dat : objCustmst.getDataListFlagKirim("N")){
                            SyncGoogleMaps(dat);
                        }

                        for(Obj_CUSTCARD dat : objCustcards){
                            if (dat.getDOCNO() != null) SendData(dat.getDOCNO());
                        }
                    }
                }


            }catch (Exception e){

            }
        }
    }

    void SyncGoogleMaps(final Obj_CUSTMST objCustmst){
        String sLatLng = objCustmst.getLATITUDE() + "," +objCustmst.getLONGITUDE();
        try{
            Call<ApiGoogleMaps> call = NetworkManager.getNetworkServiceGoogle(this).getMapsAPI(sLatLng
                    , AppConstant.API_KEY_GOOGLE_MAPS
            );
            call.enqueue(new Callback<ApiGoogleMaps>() {
                @Override
                public void onResponse(Call<ApiGoogleMaps> call, Response<ApiGoogleMaps> response) {
                    int code = response.code();

                    int iResult = 0;
                    if (code == 200){
                        apiGoogleMaps = response.body();
                        if (apiGoogleMaps.status.equals("OK")){
                            for(ApiGoogleMaps.Result dat: apiGoogleMaps.results){
                                if (iResult == 0){
                                    for(ApiGoogleMaps.AddressComponent addressComponent : dat.address_components){
                                        try{
                                            if (addressComponent.types.equals(adm1)){
                                                GOOGLE_PROVINSI = addressComponent.short_name;
                                            }
                                        }catch (Exception e){
                                            GOOGLE_PROVINSI = "";
                                            Log.w("GenGPS", " GOOGLE_PROVINSI " );
                                        }


                                        try{
                                            if (addressComponent.types.equals(adm2)){
                                                GOOGLE_KABUPATEN = addressComponent.short_name;
                                            }
                                        }catch (Exception e){
                                            GOOGLE_KABUPATEN = "";
                                            Log.w("GenGPS", " GOOGLE_KABUPATEN " );
                                        }


                                        try{
                                            if (addressComponent.types.equals(adm3)){
                                                GOOGLE_KECAMATAN = addressComponent.short_name;
                                            }
                                        }catch (Exception e){
                                            GOOGLE_KECAMATAN = "";
                                            Log.w("GenGPS", " GOOGLE_KECAMATAN " );
                                        }


                                        try{
                                            if (addressComponent.types.equals(adm4)){
                                                GOOGLE_KELURAHAN = addressComponent.short_name;
                                            }
                                        }catch (Exception e){
                                            GOOGLE_KELURAHAN = "";
                                            Log.w("GenGPS", " GOOGLE_KELURAHAN " );
                                        }


                                        try{
                                            if (addressComponent.types.equals(postalCode)){
                                                GOOGLE_KODEPOS = addressComponent.short_name;
                                            }
                                        }catch (Exception e){
                                            GOOGLE_KODEPOS = "";
                                            Log.w("GenGPS", " GOOGLE_KODEPOS " );
                                        }
                                    }

                                    try{
                                        GOOGLE_ALAMAT = dat.formatted_address;
                                    }catch (Exception e){
                                        GOOGLE_ALAMAT = "";
                                    }
                                    SendDataToServer(objCustmst);
                                }
                                iResult = 1;

                            }
                        }

                    }else{
                        //AppController.getInstance().CustomeDialog(AddOutletActivity.this, apiGoogleMaps.status);
                    }
                }

                @Override
                public void onFailure(Call<ApiGoogleMaps> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void SendDataToServer(final Obj_CUSTMST objCustmst){
        boolean bDoneCek = true;

        Log.w("Cek Database"," File Raw");
        File filePhoto = null;
        String sPhoto = "";
        if (objCustmst.getPHOTO_NAME() != null ){
            if (!objCustmst.getPHOTO_NAME().equals("")){
                sPhoto = objCustmst.getPHOTO_NAME();
                filePhoto = new File(AppConstant.PATH_PHOTO + "/" + objCustmst.getPHOTO_NAME());
                String sFile_SizeRaw = "0";
                if (filePhoto.exists()){
                    sFile_SizeRaw = Long.toString(filePhoto.length());
                    bDoneCek = true;
                }else{
                    bDoneCek = false;
                }

                Log.w("NewOutlet"," PHOTO_NAME " + objCustmst.getPHOTO_NAME()+ " " + bDoneCek);
            }

        }
        String sPhotoNameSamping = "";
        File filePhotoSamping = null;
        if (objCustmst.getPHOTO_NAME_SAMPING() != null ){
            if (!objCustmst.getPHOTO_NAME_SAMPING().equals("")){
                sPhotoNameSamping = objCustmst.getPHOTO_NAME_SAMPING();
                filePhotoSamping = new File(AppConstant.PATH_PHOTO + "/" + objCustmst.getPHOTO_NAME_SAMPING());
                String sFile_SizeRaw_Samping = "0";
                if (filePhotoSamping.exists()){
                    bDoneCek = true;
                }else{
                    bDoneCek = false;
                }
            }

            Log.w("NewOutlet"," PHOTO_NAME " + objCustmst.getPHOTO_NAME_SAMPING() + " " + bDoneCek);
        }


        int iDummyCode = 0;


        try{
            MultipartBody.Part body = null;
            if(!sPhoto.equals("")){
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), filePhoto);
                body = MultipartBody.Part.createFormData("files", filePhoto.getName(), requestFile);

            }

            MultipartBody.Part body_samping = null;
            if(!sPhotoNameSamping.equals("")){
                RequestBody requestFileSamping =
                        RequestBody.create(MediaType.parse("multipart/form-data"), filePhotoSamping);
                body_samping = MultipartBody.Part.createFormData("files_samping", filePhotoSamping.getName(), requestFileSamping);
            }
            RequestBody kodecabang = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strMitraID);
            RequestBody slsno = RequestBody.create(MediaType.parse("multipart/form-data"), AppConstant.strSlsNo);
            RequestBody custno = RequestBody.create(MediaType.parse("multipart/form-data"), objCustmst.getCUSTNO());
            final RequestBody custname = RequestBody.create(MediaType.parse("multipart/form-data"), objCustmst.getCUSTNAME());
            RequestBody contact = RequestBody.create(MediaType.parse("multipart/form-data"), objCustmst.getCCONTACT());
            RequestBody telp = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getCPHONE1()== null ? "" : objCustmst.getCPHONE1())  );
            RequestBody kelurahan = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getKELURAHAN()== null ? "" : objCustmst.getKELURAHAN()));
            RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getCUSTADD1()== null ? "" : objCustmst.getCUSTADD1()));
            RequestBody typeout = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getTYPEOUT()== null ? "" : objCustmst.getTYPEOUT()));
            RequestBody company_name = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getCOMPANY_NAME()== null ? "" : objCustmst.getCOMPANY_NAME()));
            RequestBody date_next_visit = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getDATE_NEXT_VISIT()== null ? "" : objCustmst.getDATE_NEXT_VISIT()));
            RequestBody number_of_branch = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(objCustmst.getNUMBER_OF_BRANCH()));
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"),sPhoto);
            RequestBody photo_samping = RequestBody.create(MediaType.parse("multipart/form-data"), sPhotoNameSamping);
            RequestBody google_provinsi = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_PROVINSI == null ? "" : GOOGLE_PROVINSI));
            Log.w("NewOutlet"," 11");
            RequestBody google_kabupaten = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_KABUPATEN == null ? "" : GOOGLE_KABUPATEN));
            Log.w("NewOutlet"," 12");
            RequestBody google_kecamatan = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_KECAMATAN == null ? "" : GOOGLE_KECAMATAN));
            Log.w("NewOutlet"," 13");
            RequestBody google_kelurahan = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_KELURAHAN == null ? "" : GOOGLE_KELURAHAN));
            Log.w("NewOutlet"," 14");
            RequestBody google_alamat = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_ALAMAT == null ? "" : GOOGLE_ALAMAT));
            Log.w("NewOutlet"," 15");
            final RequestBody google_kodepos = RequestBody.create(MediaType.parse("multipart/form-data"), (GOOGLE_KODEPOS == null ? "" : GOOGLE_KODEPOS));
            RequestBody flagout = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getFLAGOUT() == null ? "" : objCustmst.getFLAGOUT()));
            RequestBody ktp_id = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getKTP_ID() == null ? "" : objCustmst.getKTP_ID()));
            RequestBody ktp_name = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getKTP_NAME() == null ? "" : objCustmst.getKTP_NAME()));
            RequestBody ktp_address = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getKTP_ADDRESS() == null ? "" : objCustmst.getKTP_ADDRESS()));
            RequestBody npwp_id = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getNPWP_ID() == null ? "" : objCustmst.getNPWP_ID()));
            RequestBody npwp_name = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getNPWP_NAME() == null ? "" : objCustmst.getNPWP_NAME()));
            RequestBody npwp_address = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getNPWP_ADDRESS() == null ? "" : objCustmst.getNPWP_ADDRESS()));
            RequestBody payment_type = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getPAYMENT_TYPE() == null ? "" : objCustmst.getPAYMENT_TYPE()));
            RequestBody periode_order = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getPERIODE_ORDER() == null ? "" : objCustmst.getPERIODE_ORDER()));
            RequestBody status_contract = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getSTATUS_CONTRACT() == null ? "" : objCustmst.getSTATUS_CONTRACT()));
            RequestBody startdate_contract = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getSTARTDATE_CONTRACT() == null ? "" : objCustmst.getSTARTDATE_CONTRACT()));
            RequestBody enddate_contract = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getENDDATE_CONTRACT() == null ? "" : objCustmst.getENDDATE_CONTRACT()));
            RequestBody status_dispenser = RequestBody.create(MediaType.parse("multipart/form-data"), (objCustmst.getSTATUS_DISPENSER() == null ? "" : objCustmst.getSTATUS_DISPENSER()));
            RequestBody latitude = RequestBody.create(MediaType.parse("multipart/form-data"), objCustmst.getLATITUDE());
            RequestBody longitude = RequestBody.create(MediaType.parse("multipart/form-data"), objCustmst.getLONGITUDE());
            RequestBody dispenser_jrt = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(objCustmst.getDISPENSER_JRT()));
            RequestBody dispenser_hrt = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(objCustmst.getDISPENSER_HRT()));
            RequestBody dispenser_multifold = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(objCustmst.getDISPENSER_MULTIFOLD()));
            RequestBody dispenser_roll = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(objCustmst.getDISPENSER_ROLL()));

            Log.w("NewOutlet"," custno " + objCustmst.getCUSTNO());
            Log.w("NewOutlet"," dispenser_jrt " + String.valueOf(objCustmst.getDISPENSER_JRT()));
            Log.w("NewOutlet"," dispenser_hrt " + String.valueOf(objCustmst.getDISPENSER_MULTIFOLD()));
            Log.w("NewOutlet"," dispenser_multifold " + String.valueOf(objCustmst.getDISPENSER_HRT()));
            Log.w("NewOutlet"," Start");
            Call<Tbl_Custmst> call;
            if(!sPhotoNameSamping.equals("")){
                call = NetworkManager.getNetworkService().postNewOutlet2Photo(kodecabang,
                        slsno,
                        custno,
                        custname,
                        contact,
                        address,
                        telp,
                        kelurahan,
                        typeout,
                        company_name,
                        date_next_visit,
                        number_of_branch,
                        photo,
                        photo_samping,
                        google_provinsi,
                        google_kabupaten,
                        google_kecamatan,
                        google_kelurahan,
                        google_alamat,
                        google_kodepos,
                        latitude,
                        longitude,
                        flagout,
                        ktp_id,
                        ktp_name,
                        ktp_address,
                        npwp_id,
                        npwp_name,
                        npwp_address,
                        payment_type,
                        periode_order,
                        status_contract,
                        startdate_contract,
                        enddate_contract,
                        status_dispenser,
                        dispenser_jrt,
                        dispenser_hrt,
                        dispenser_multifold,
                        dispenser_roll,
                        body,
                        body_samping);
            }else{
                call = NetworkManager.getNetworkService().postNewOutlet(kodecabang,
                        slsno,
                        custno,
                        custname,
                        contact,
                        address,
                        telp,
                        kelurahan,
                        typeout,
                        company_name,
                        date_next_visit,
                        number_of_branch,
                        photo,
                        google_provinsi,
                        google_kabupaten,
                        google_kecamatan,
                        google_kelurahan,
                        google_alamat,
                        google_kodepos,
                        latitude,
                        longitude,
                        flagout,
                        ktp_id,
                        ktp_name,
                        ktp_address,
                        npwp_id,
                        npwp_name,
                        npwp_address,
                        payment_type,
                        periode_order,
                        status_contract,
                        startdate_contract,
                        enddate_contract,
                        status_dispenser,
                        dispenser_jrt,
                        dispenser_hrt,
                        dispenser_multifold,
                        dispenser_roll,
                        body);
            }
            call.enqueue(new Callback<Tbl_Custmst>() {
                @Override
                public void onResponse(Call<Tbl_Custmst> call, Response<Tbl_Custmst> response) {
                    int code = response.code();
                    Log.w("NewOutlet","Result : " + code);
                    if (code == 200){
                        tblCustmst = response.body();
                        objCustmst.UpdateDataFlagKirim(objCustmst.getCUSTNO());
                    }else{
                        //CustomeDialog(response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Custmst> call, Throwable t) {
                    Log.w("NewOutlet","onFailure : " + t.getMessage());
                }
            });
        }catch (Exception e){
            Log.w("NewOutlet","Exception : " + e.getMessage());
        }
    }

    void SendData(String ORDERNO){
        objSendData = new Obj_SendData();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject = objSendData.sendData(ORDERNO);
            String sJson = jsonObject.toString();

            Call<Obj_SendData>call = NetworkManager.getNetworkService().postTransacation(jsonObject.toString());
            call.enqueue(new Callback<Obj_SendData>() {
                @Override
                public void onResponse(Call<Obj_SendData> call, Response<Obj_SendData> response) {
                    int code = response.code();
                    if (code == 200){
                        objSendData = response.body();
                        if (!objSendData.error && !objSendData.docno.equals("")){
                            Obj_CUSTCARD custcard = new Obj_CUSTCARD();
                            custcard.UpdateFlagKirim(objSendData.docno);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Obj_SendData> call, Throwable t) {
                    //writeLog("Respond API : " + t.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }




}
