package com.soldis.yourthaitea;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.soldis.yourthaitea.model.Tbl_Karyawan;

import org.w3c.dom.Text;

public class SplashScreenActivity extends AppCompatActivity{
    private final int SPLASH_DISPLAY_LENGHT = 1500;
    TextView txtVersion;
    Tbl_Karyawan tblMotoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        txtVersion = (TextView)findViewById(R.id.text_version);

        txtVersion.setText(BuildConfig.VERSION_NAME + AppConstant.REVISION);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mIntent;
                if (tblMotoris != null && tblMotoris.data.size() > 0){
                    mIntent  = new Intent(getBaseContext(), PinViewActivity.class);
                    startActivity(mIntent);
                }else{
                    mIntent  = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(mIntent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);

    }
}
