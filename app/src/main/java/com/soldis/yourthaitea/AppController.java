package com.soldis.yourthaitea;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.soldis.yourthaitea.model.Obj_SendData;
import com.soldis.yourthaitea.model.Tbl_GPS_Logs;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.net.NetworkManager;

import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/15/2016.
 */
public class AppController extends Application {
    private static AppController mInstance;
    private SessionManager sessionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        sessionManager = new SessionManager(getApplicationContext());

        String DOMAIN_URL = AppController.getInstance().getSessionManager().getStringData(SessionManager.DOMAIN_URL);

        if (AppConstant.DOMAIN_URL.equals("https://dtbumotoris.app.co.id/rest_server")){
            AppConstant.APK_FILENAME = "tbumotoris.apk";
            AppConstant.REVISION = " Staging" ;
        }else{
            AppConstant.APK_FILENAME = "tbumotorislive.apk";
            AppConstant.REVISION = " " ;
        }


        /*TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        AppConstant.IMEI =  mngr.getDeviceId();*/

        AppController.getInstance().getSessionManager().putStringData(AppConstant.TOKEN, FirebaseInstanceId.getInstance().getToken());



    }

    public static Context getAppContext() {
        return mInstance;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getStringData(Context ctx, String sData){
        String sResult = "";
        sResult = AppConstant.pref.getString(sData, AppConstant.EMPTY_STRING);
        return  sResult;
    }

    public void displayImage(Context context,String uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .error(R.drawable.no_image)
                .into(imageView);

    }
    public void displayImageDrawable(Context context, Drawable drawable, ImageView imageView) {
        Glide.with(context)
                .load(drawable)
                .error(R.drawable.no_image)
                .into(imageView);
    }

    public void displayImageFull(Context context,String uri, ImageView imageView) {
        Glide   .with(context)
                .load(uri)
                .fitCenter()
                .error(R.drawable.no_image)
                .into(imageView);
        ;
    }

    public void displayImagePicasso(Context context, String uri, ImageView imageView) {
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(uri)
                .into(imageView);
    }

    public void displayImagePicassoDrawable(Context context, Drawable drawable, ImageView imageView) {
        Picasso.with(context).load(String.valueOf(drawable)).into(imageView);
    }

    public String toCurrency(double dValue) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        String sCur = nf.format(dValue);
        sCur = sCur.replace("$", "");
        sCur = sCur.replace(".00", "");
        return sCur;
    }

    public String toCurrencyRp(double dValue) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        String sCur = nf.format(dValue);
        sCur = sCur.replace("$", "Rp ");

        sCur = sCur.replace(".00", "");
        return sCur;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                //hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDateYYYYMMDD() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDateYYYYMMDDtoDDMMYYYY(String sDate) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = null;
            try {
                dt1 = format1.parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("dd-MMM-yyyy");

            return format2.format(dt1);
        }catch (Exception e){
            return "";
        }
    }

    public String getDateYYYYMMDDtoMMYYYY(String sDate) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = null;
            try {
                dt1 = format1.parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("MMM-yyyy");

            return format2.format(dt1);
        }catch (Exception e){
            return "";
        }
    }

    public String getDateDDMMYYYY() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDateTime() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        AppConstant.REQID = dateFormat.format(date) + getTimeWithoutDot();
        return AppConstant.REQID;
    }

    public String getDateAndTime() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        AppConstant.REQID = dateFormat.format(date) + getTime();
        return AppConstant.REQID;
    }

    public String getTime() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String sHour = String.valueOf(hour);
        String sMinute= String.valueOf(minute);
        String sSecond = String.valueOf(second);

        if (sHour.length() < 2) sHour = "0" + sHour;
        if (sMinute.length() < 2) sMinute = "0" + sMinute;
        if (sSecond.length() < 2) sSecond = "0" + sSecond;

        return sHour + ":" + sMinute + ":" + sSecond;
    }

    public String getTimeWithoutDot() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String sHour = String.valueOf(hour);
        String sMinute= String.valueOf(minute);
        String sSecond = String.valueOf(second);

        if (sHour.length() < 2) sHour = "0" + sHour;
        if (sMinute.length() < 2) sMinute = "0" + sMinute;
        if (sSecond.length() < 2) sSecond = "0" + sSecond;

        return sHour + "" + sMinute + "" + sSecond;
    }

    public String getHasPassword(String Password) throws NoSuchAlgorithmException {
        return getSHA1(Password+"S.1bc29400");
    }

    public String getSHA1(String input)throws NoSuchAlgorithmException{

        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String getHashId(String sUserId, String sPassword)throws NoSuchAlgorithmException{
        AppConstant.REQID = getDateTime();

        String sResult = getSHA1(getDateTime() + sUserId +  AppConstant.KEY_USER  + sPassword);

        return sResult;
    }

    public String getHashId(String sUserId)throws NoSuchAlgorithmException{
        AppConstant.REQID = getDateTime();

        String sResult = getSHA1(getDateTime() + sUserId +  AppConstant.KEY_USER);

        return sResult;
    }

    public void SendData(String ORDERNO){
        Obj_SendData objSendData = new Obj_SendData();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject = objSendData.sendData(ORDERNO);
            String sJson = jsonObject.toString();
            //writeLog(sJson);
            Call<Obj_SendData>call = NetworkManager.getNetworkService().postTransacation(jsonObject.toString());
            call.enqueue(new Callback<Obj_SendData>() {
                @Override
                public void onResponse(Call<Obj_SendData> call, Response<Obj_SendData> response) {
                    int code = response.code();
                    if (code == 200){

                    }
                    //writeLog("Respond API : " + code);

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

    public void SendDataAll(){
        Obj_SendData objSendData = new Obj_SendData();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject = objSendData.sendDataAll(AppController.getInstance().getCurrentTime());
            String sJson = jsonObject.toString();
            //writeLog(sJson);
            Call<Obj_SendData>call = NetworkManager.getNetworkService().postTransacationAll(jsonObject.toString());
            call.enqueue(new Callback<Obj_SendData>() {
                @Override
                public void onResponse(Call<Obj_SendData> call, Response<Obj_SendData> response) {
                    int code = response.code();
                    if (code == 200){

                    }
                    //writeLog("Respond API : " + code);

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

    public void writeLog(String sLog){
        FileWriter fstream;
        String sFileName = "";
        sFileName = "LogService_" + getDateYYYYMMDD() + ".txt";
        sFileName = AppConstant.STORAGE_CARD + "/Univenus/Motoris/Log/" + sFileName;
        File file = new File(sFileName);
        try {

            if (sLog != null && !sLog.equals("")){
                if (!file.exists()){
                    file.createNewFile();
                }

                fstream = new FileWriter(sFileName,true);
                BufferedWriter out = new BufferedWriter(fstream);
                String sGabung = getDateTime() + "	" + sLog;
//				sGabung = GblFunction.GenerateCode(sGabung);
                out.write(sGabung + "\n");
                out.close();
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void writeLogTrx(String sLog){
        FileWriter fstream;
        String sFileName = "";
        sFileName = "Trx_" + getDateYYYYMMDD() + ".txt";
        sFileName = AppConstant.PATH_FOLDER_TRX + "/" + sFileName;
        File file = new File(sFileName);
        try {

            if (sLog != null && !sLog.equals("")){
                if (!file.exists()){
                    file.createNewFile();
                }

                fstream = new FileWriter(sFileName,true);
                BufferedWriter out = new BufferedWriter(fstream);
                String sGabung = getDateTime() + "	" + sLog;
//				sGabung = GblFunction.GenerateCode(sGabung);
                out.write(sGabung + "\n");
                out.close();
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public static String convertQtyToString(int inlK31, int inlK21, long lolQty) {
        long iXQty3 = 0;
        long iXQty2 = 0;
        long iXQty1 = 0;
        //Dim iXQty3 As Integer, iXQty2 As Integer, iXQty1 As Integer
        String sXQty = "";
        iXQty3 = (lolQty / (inlK31*inlK21));
        iXQty2 = ((lolQty % (inlK31*inlK21)) / inlK21);
        iXQty1 = lolQty - (iXQty3 * (inlK31*inlK21)) - (iXQty2 * inlK21);
        sXQty = (iXQty3) + "/"
                + (iXQty2) + "/"
                + (iXQty1);

        return sXQty;
    }

    public String getCurrentTime() {
        Date dt = new Date();
        String sJam, sMenit, sDetik;
        sJam = Integer.toString(dt.getHours());
        sMenit = Integer.toString(dt.getMinutes());
        sDetik = Integer.toString(dt.getSeconds());

        if (sJam.length() < 2) sJam = "0" + sJam;
        if (sMenit.length() < 2) sMenit = "0" + sMenit;
        if (sDetik.length() < 2) sDetik = "0" + sDetik;
        //return dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
        return sJam + ":" + sMenit + ":" + sDetik;
    }

    public String getCurrentTimeAdd() {
        Date dt = new Date();
        String sJam, sMenit, sDetik;
        sJam = Integer.toString(dt.getHours());
        sMenit = Integer.toString(dt.getMinutes());
        sDetik = Integer.toString(dt.getSeconds());

        return sJam + sMenit  + sDetik;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void CustomeDialog(Context context, String ISI){
        final Dialog dialog = new Dialog(context);
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

    public boolean FileDelete(String strFileName) {
        boolean bSucceed = false;
        File file = new File(strFileName);

        if (file.exists()) {
            file.delete();
        }
        bSucceed = true;

        return bSucceed;
    }

    public void hideKeyboardFrom(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void AddGPSLog(String sCustNo, String sLat, String sLng, String sSpeed, int battery_level){
        Tbl_GPS_Logs tblGpsLogs = new Tbl_GPS_Logs();
        tblGpsLogs = getSessionManager().getListJourney();
        if (tblGpsLogs == null) tblGpsLogs = new Tbl_GPS_Logs();
        tblGpsLogs.AddData(tblGpsLogs.new TblGPSLogs( getDateAndTime() ,sCustNo, sLat, sLng, sSpeed, battery_level));
        getSessionManager().setListJourney(tblGpsLogs);
    }

    public int BatteryLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        return level;
    }

    public void DeleteFile(String PATH){
        File file = new File(PATH);
        if (file.exists()){
            File[] yfiles = file.listFiles();
            for (File doc : yfiles){
                AppController.getInstance().FileDelete(doc.toString());
            }
        }
    }

    public void OpenDatabase(Context context){
        try {
            Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
            if (tblMotoris != null && tblMotoris.data.size() > 0){
                for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                    AppConstant.strSlsNo = dat.SLSNO;
                    AppConstant.strSlsName = dat.SLSNAME;
                    AppConstant.strMitraID = dat.MITRA_ID;
                    AppConstant.strLevel = dat.LEVEL_ID;
                }
            }

            if (AppConstant.myDb == null){
                /*DBHelper myDBHelper = new DBHelper(context);
                AppConstant.myDb = myDBHelper.openDataBase();*/

                OpenDatabaseSqlite(context);
            }
        }catch (Exception e){

        }
    }

    public void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static String GenerateCode(String svalue) {

        String sn = "";
        int i = 0;
        float j = 0;
        String temp = "";
        try {


            sn = new StringBuffer(svalue).reverse().toString();
            temp = "";
            for (i = 1; i <= sn.length(); i++) {

                j = sn.charAt(i) + 17 + i;

                do {
                    j = (int) Math.rint((((j + 13) * 17) / 21) - 10);
                    if (j < 48) {
                        j = (int) Math.rint((j * 3) / 2);
                    }
                    if (j > 90) {
                        j = (int) Math.rint((j * 2) / 3);
                    }
                }
                while (!(((j >= 48) && (j <= 57)) || ((j >= 65) && (j <= 90))));

                temp = temp + String.valueOf((char) (j));
            }

        } catch (Exception e) {

        }

        return temp;

    }


    public void BackUpDB(Context context){
        File src, dst;
        src = new File(AppConstant.PATH_FOLDER_DB +  "/SOLDIS_POS.db");
        dst = new File(AppConstant.PATH_FOLDER_DB_BACKUP +"/SOLDIS_POS.db");
        try{
            copyFile(src, dst);

        }catch(Exception e){
            CustomeDialog(context, e.getMessage());

        }
    }

    public void RestoreDB(Context context){
        File src, dst;
        src = new File(AppConstant.PATH_FOLDER_DB_BACKUP + "/SOLDIS_POS.db");
        dst = new File(AppConstant.PATH_FOLDER_DB + "/SOLDIS_POS.db");
        try{
            if (src.exists())
                copyFile(src, dst);
        }catch(Exception e){
        }

    }

    public void OpenDatabaseSqlite(Context context){
        SQLiteDatabase.loadLibs(context);
        File databaseFile = getDatabasePath(AppConstant.PATH_DB);
        if (!databaseFile.exists()){
            try {
                copyDataBaseAsset(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            databaseFile = getDatabasePath(AppConstant.PATH_DB);
        }

        try{
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, AppConstant.KEY_DATABASE, null);
            AppConstant.myDb = database;
        }catch (Exception e){
            CustomeDialog(context, e.getMessage());
        }

    }

    private void copyDataBaseAsset(Context context) throws IOException{
        // TODO Auto-generated method stub
        InputStream myInput = context.getAssets().open(AppConstant.DATABASE_NAME_ASSET);
        File outFile = context.getDatabasePath(AppConstant.PATH_DB);

        outFile.delete();

        OutputStream myOutput = new FileOutputStream(outFile);

        byte [] buffer = new byte[1204];
        int length ;
        while ((length =  myInput.read(buffer)) > 0){
            myOutput.write(buffer,0,length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

}
