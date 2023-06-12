package com.soldis.yourthaitea.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 3/2/2017.
 */

public class ServiceAutoLogOut extends IntentService {

    Tbl_Karyawan tblMotoris;
    String DeviceID = "";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.bpbatam.enterprise.MainMenuActivity";
    public ServiceAutoLogOut() {
        super("ServiceAutoLogOut");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        String USER = "";
        String PASSWORD = "";
        String BRANCH = "";
        Log.w("Start Service Logout"," Cek");
        Tbl_Karyawan authUser = AppController.getInstance().getSessionManager().getUserProfile();
        DeviceID = AppController.getInstance().getSessionManager().getStringData(AppConstant.DEVICE_ID);

        if (authUser != null){
            if (authUser.data !=null){
                if (authUser.data.size() > 0){
                    for (Tbl_Karyawan.Datum dat : authUser.data){
                        USER = dat.SLSNO;
                        PASSWORD = dat.PASSWORD;
                        BRANCH = dat.MITRA_ID;
                    }
                    Log.w("USER",USER);

                    try{
                        Call<Tbl_Karyawan> call = NetworkManager.getNetworkService().loginUser(USER, PASSWORD, BRANCH);
                        call.enqueue(new Callback<Tbl_Karyawan>() {
                            @Override
                            public void onResponse(Call<Tbl_Karyawan> call, Response<Tbl_Karyawan> response) {
                                int code = response.code();
                                if (code == 200){
                                    tblMotoris = response.body();
                                    if (!tblMotoris.error){
                                        for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                                            if (dat.DEVICE_ID !=null && !dat.DEVICE_ID.equals(DeviceID) ){
                                                Log.w("Status"," Berbeda");
                                                publishResults("", Activity.RESULT_OK);
                                            }else{
                                                Log.w("Status"," Sama");
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Tbl_Karyawan> call, Throwable t) {
                                Log.w("Err Logout ", t.getMessage());
                            }
                        });
                    }catch (Exception e){
                        Log.w("Err Logout ", e.getMessage());
                    }

                }
            }

        }


    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }




}
