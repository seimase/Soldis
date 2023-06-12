package com.soldis.yourthaitea;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.dashboard.asm.Dashboard_ASM_Activity;
import com.soldis.yourthaitea.dashboard.asm.Dashboard_ASM_SummaryActivity;
import com.soldis.yourthaitea.dashboard.asm.Dashboard_ASM_SummaryPresenceActivity;
import com.soldis.yourthaitea.dashboard.bot.Dashboard_Zone_Activity;
import com.soldis.yourthaitea.dashboard.bot.Dashboard_Zone_SummaryPresence_Activity;
import com.soldis.yourthaitea.dashboard.bot.Dashboard_Zone_Summary_Activity;
import com.soldis.yourthaitea.dashboard.data_outlet.Dashboard_ASM_OutletActivity;
import com.soldis.yourthaitea.dashboard.data_outlet.Dashboard_Zone_Outlet_Activity;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Promo_Banner;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.service.AlarmReceiverAutoLogout;
import com.soldis.yourthaitea.service.ServiceAutoLogOut;
import com.soldis.yourthaitea.service.ServiceAutoSend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.ImageBannerAdapter;
import ui.widget.CircleIndicator;

/**
 * Created by User on 8/10/2017.
 */

public class MainLeaderActivity extends AppCompatActivity {
    int REQ_CODE = 2000;
    private CircleIndicator viewPagerIndicator;
    private ViewPager viewPagerHeader;
    private ImageView imageViewBanner;
    private Handler handler;
    ImageBannerAdapter adapter;

    private Runnable runnable;
    int position = 1;
    List<String> resourcesImg;

    LinearLayout lySync, lyLoading;
    RelativeLayout lyAdapter;
    TextView txtSync,
            txtEmail,
            txtPhone;

    RelativeLayout lySetting, lyProfile;
    Tbl_Karyawan tblMotoris;

    Tbl_Promo_Banner tblPromoBanner;
    LinearLayout lyRingkasanMotoris, lyRingkasanPenjualan, lyRingkasanKehadiran, lyOutlet;

    String EMAIL_ID, PHONE_NUMBER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_main_leader);

        InitControl();
        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        AppConstant.strSlsNo = "";
        AppConstant.strSlsName  = "";
        AppConstant.strMitraID = "";
        EMAIL_ID = "";
        PHONE_NUMBER = "";
        if (tblMotoris != null && tblMotoris.data.size() > 0){
            for (Tbl_Karyawan.Datum dat : tblMotoris.data){
                AppConstant.strSlsNo = dat.SLSNO;
                AppConstant.strSlsName = dat.SLSNAME;
                AppConstant.strSlsPass = dat.PASSWORD;
                AppConstant.strMitraID = dat.MITRA_ID;
                AppConstant.strLevel = dat.LEVEL_ID;
                EMAIL_ID = "";
                PHONE_NUMBER = "";
            }
        }

        txtEmail.setText(EMAIL_ID);
        txtEmail.setPaintFlags(txtEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtPhone.setText(PHONE_NUMBER);
        txtPhone.setPaintFlags(txtPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("smsto:"+ PHONE_NUMBER);
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);

                    sendIntent.setPackage("com.whatsapp");
                    if (sendIntent!= null) {
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        startActivity(sendIntent);
                    }else{
                        AppController.getInstance().CustomeDialog(MainLeaderActivity.this, getResources().getString(R.string.main_wa_not_installed));

                    }

                }catch(Exception ex) {
                }
            }
        });

        handler = new Handler();
        SyncPromoBanner();

        StartService();
    }

    private void InitControl() {
        txtSync = (TextView)findViewById(R.id.txtSync);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtPhone = (TextView)findViewById(R.id.txtPhone);

        lySetting = (RelativeLayout)findViewById(R.id.layout_setting);
        lyProfile = (RelativeLayout)findViewById(R.id.layout_profile);
        lySync = (LinearLayout)findViewById(R.id.layout_sync);
        lyLoading = (LinearLayout)findViewById(R.id.layout_loading);
        lyRingkasanMotoris = (LinearLayout)findViewById(R.id.layout_main_ringkasan_motoris);
        lyRingkasanPenjualan = (LinearLayout)findViewById(R.id.layout_main_ringkasan_penjualan);
        lyRingkasanKehadiran = (LinearLayout)findViewById(R.id.layout_main_ringkasan_absensi);
        lyOutlet = (LinearLayout)findViewById(R.id.layout_main_outlet);
        lyAdapter = (RelativeLayout)findViewById(R.id.layout_adapter);
        viewPagerHeader = (ViewPager) findViewById(R.id.main_header_viewpager);
        viewPagerIndicator = (CircleIndicator) findViewById(R.id.indicator);

        lyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivityForResult(mIntent, REQ_CODE);
            }
        });

        lyRingkasanMotoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstant.strLevel.equals("6") || AppConstant.strLevel.equals("7")) {
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_Zone_Activity.class);
                    startActivity(mIntent);
                }else{
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_ASM_Activity.class);
                    startActivity(mIntent);
                }



            }
        });

        lyRingkasanPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent mIntent = new Intent(getBaseContext(), DashboardLeaderSummaryXLSActivity.class);
                startActivity(mIntent);*/

                if (AppConstant.strLevel.equals("6") || AppConstant.strLevel.equals("7")) {
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_Zone_Summary_Activity.class);
                    startActivity(mIntent);
                }else{
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_ASM_SummaryActivity.class);
                    startActivity(mIntent);
                }

            }
        });

        lyOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstant.strLevel.equals("6") || AppConstant.strLevel.equals("7")) {
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_Zone_Outlet_Activity.class);
                    startActivity(mIntent);
                }else{
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_ASM_OutletActivity.class);
                    startActivity(mIntent);
                }
            }
        });

        lyRingkasanKehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstant.strLevel.equals("6") || AppConstant.strLevel.equals("7")) {
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_Zone_SummaryPresence_Activity.class);
                    startActivity(mIntent);
                }else{
                    Intent mIntent = new Intent(getBaseContext(), Dashboard_ASM_SummaryPresenceActivity.class);
                    startActivity(mIntent);
                }
            }
        });

        lyLoading.setVisibility(View.VISIBLE);
        lySync.setVisibility(View.GONE);
        lyAdapter.setVisibility(View.GONE);

        lySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(mIntent);
            }
        });
    }


    private void setupViewPagerHeader(ViewPager viewPager, List<String> resources) {

        adapter = new ImageBannerAdapter(getApplicationContext(), resources, R.layout.cover_image);
        viewPager.setAdapter(adapter);
        viewPagerIndicator.setViewPager(viewPagerHeader, adapter.getCount());
        for (int i = 0; i < adapter.getCount() - 1; i++)
            viewPagerHeader.setCurrentItem(i, true);
        runnable = new Runnable() {
            public void run() {
                if (position >= adapter.getCount()) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                viewPagerHeader.setCurrentItem(position, true);
                handler.postDelayed(runnable, 5000);
            }
        };

    }

    void StartService(){
        //ServiceAutoLogout==================================================
        Intent myIntentLogOut = new Intent(getBaseContext(),
                AlarmReceiverAutoLogout.class);

        PendingIntent pendingIntentLogOut
                = PendingIntent.getBroadcast(getBaseContext(),
                222224, myIntentLogOut, 0);

        AlarmManager alarmManagerLogOut
                = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendarLogOut  = Calendar.getInstance();
        calendarLogOut.setTimeInMillis(System.currentTimeMillis());
        calendarLogOut.add(Calendar.SECOND, 10);
        long intervalLogOut = (1 * 60) * 1000; //
        alarmManagerLogOut.setRepeating(AlarmManager.RTC_WAKEUP,
                calendarLogOut.getTimeInMillis(), intervalLogOut, pendingIntentLogOut);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(ServiceAutoSend.FILEPATH);
                int resultCode = bundle.getInt(ServiceAutoSend.RESULT);
                if (resultCode == RESULT_OK) {
                    //CustomeDialog();
                }
            }
        }
    };

    private BroadcastReceiver receiverAutoLogOut = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(ServiceAutoLogOut.FILEPATH);
                int resultCode = bundle.getInt(ServiceAutoLogOut.RESULT);
                if (resultCode == RESULT_OK) {
                    AppController.getInstance().getSessionManager().setUserAccount(null);
                    CustomeDialog();
                }
            }
        }
    };

    void SyncPromoBanner(){
        lyLoading.setVisibility(View.VISIBLE);
        lyAdapter.setVisibility(View.GONE);
        lySync.setVisibility(View.GONE);
        try{
            Call<Tbl_Promo_Banner> call = NetworkManager.getNetworkService().PromoBanner(AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Promo_Banner>() {
                @Override
                public void onResponse(Call<Tbl_Promo_Banner> call, Response<Tbl_Promo_Banner> response) {
                    int code = response.code();
                    if (code == 200){
                        tblPromoBanner = response.body();
                        if (!tblPromoBanner.error){
                            resourcesImg = new ArrayList<>();
                            for(Tbl_Promo_Banner.Datum dat : tblPromoBanner.data){
                                resourcesImg.add(AppConstant.PHOTO_PROMO_URL + dat.IMG_URL);
                            }

                            for (Tbl_Promo_Banner.Team dat : tblPromoBanner.team){
                                txtEmail.setText(dat.EMAIL_ID);
                                txtEmail.setPaintFlags(txtEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                txtPhone.setText(dat.PHONE_NUMBER);
                                txtPhone.setPaintFlags(txtPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                            }

                            setupViewPagerHeader(viewPagerHeader, resourcesImg);
                            lyLoading.setVisibility(View.GONE);
                            lyAdapter.setVisibility(View.VISIBLE);
                            lySync.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Promo_Banner> call, Throwable t) {
                    lyLoading.setVisibility(View.GONE);
                    lyAdapter.setVisibility(View.GONE);
                    lySync.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            lyLoading.setVisibility(View.GONE);
            lyAdapter.setVisibility(View.GONE);
            lySync.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQ_CODE){
            if (resultCode == RESULT_OK) {
                finish();
                AppController.getInstance().getSessionManager().setUserAccount(null);

                Obj_CUSTMST objCustmst = new Obj_CUSTMST();
                objCustmst.DeleteData();

                Obj_VISIT objVisit = new Obj_VISIT();
                objVisit.DeleteData();

                Obj_TRX_H objTrxH = new Obj_TRX_H();
                objTrxH.DeleteData();

                Obj_TRX_D objTrxD = new Obj_TRX_D();
                objTrxD.DeleteData();

                Obj_MOTORIS objMotoris= new Obj_MOTORIS();
                objMotoris.DeleteData();

                Intent mIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(mIntent);
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_isi);
        txtDismis.setText("OK");
        txtDialog.setText(getResources().getString(R.string.main_logout));
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregisterReceiver(receiverAutoLogOut);
                dialog.dismiss();

                finish();
                Intent mIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(mIntent);
            }
        });



        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    unregisterReceiver(receiverAutoLogOut);
                    dialog.dismiss();

                    finish();
                    Intent mIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(mIntent);
                }
                return true;
            }
        });



        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
        registerReceiver(receiverAutoLogOut, new IntentFilter(ServiceAutoLogOut.NOTIFICATION));

        Obj_MOTORIS objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);
        if (objMotoris.getSLSNO() != null) AppConstant.strTglTrx = objMotoris.getTRX_DATE();
    }

}
