package com.soldis.yourthaitea.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.model.Tbl_GPS_Logs;
import com.soldis.yourthaitea.util.GPSTracker;

/**
 * Created by ftctest on 20/10/2017.
 */

public class ServiceGPSTracker extends IntentService {

    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.bpbatam.enterprise.MainMenuActivity";

    public ServiceGPSTracker() {
        super("ServiceGPSTracker");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w("Service GPSTracker"," Start");
        String START_ON = "";
        try{
            START_ON = AppController.getInstance().getSessionManager().getStringData(AppConstant.START_ON);
        }catch (Exception e){
            START_ON = "";
        }

        if (START_ON.equals("YES")){
            String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(providerGps.contains("gps")) { //if gps is disabled
                Tbl_GPS_Logs tblGpsLogs = new Tbl_GPS_Logs();
                tblGpsLogs = AppController.getInstance().getSessionManager().getListJourney();
                if (tblGpsLogs == null) tblGpsLogs = new Tbl_GPS_Logs();
                GPSTracker gps;
                gps = new GPSTracker(this);
                String sLat = "0.0";
                String sLng = "0.0";
                String sSpeed = "0.0";
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    double speed = gps.getSpeed();
                    sLat = Double.toString(latitude);
                    sLng = Double.toString(longitude);
                    //sSpeed = Double.toString(speed);
                    // \n is for new line
                    if (sLng != null ){
                        if (!sLng.equals("0.0") && !sLng.equals("0")){
                            tblGpsLogs.AddData(tblGpsLogs.new TblGPSLogs( "","", sLat, sLng, sSpeed, AppController.getInstance().BatteryLevel()));
                            AppController.getInstance().getSessionManager().setListJourney(tblGpsLogs);
                        }

                    }
                }

            }else{
                Log.w("Service GPSTracker"," GPS is off");
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
