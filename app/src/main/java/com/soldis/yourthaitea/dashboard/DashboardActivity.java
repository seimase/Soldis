package com.soldis.yourthaitea.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.fragment.Frag_Dashboard_Penjualan;
import com.soldis.yourthaitea.dashboard.fragment.Frag_Dashboard_Stock;

/**
 * Created by User on 8/16/2017.
 */

public class DashboardActivity extends AppCompatActivity {
    static String STOCK = "Stok";
    static String PENJUALAN = "Penjualan";

    static final int POSITION_STOCK = 0;
    static final int POSITION_PENJUALAN = 1;
    int CURRENT_POSITION = 1;

    static int REQ_CODE_LIST= 100;
    TextView  txtLabel;
    private long[] yValues ;

    private String[] xValues;
    // colors for different sections in pieChart
    public  int[] MY_COLORS ;
    int iEC;

    FrameLayout frameLayout;
    Fragment fragment;

    RelativeLayout layout_back;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_dashboard);

        InitControl();

        frameLayout = (FrameLayout)findViewById(R.id.frame_content);

        DisplayView(POSITION_PENJUALAN);
    }

    void InitControl(){
        MY_COLORS = new int[]{
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.red_deep)
        };
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText(getResources().getString(R.string.dash_sales_label) + " "
                + AppController.getInstance().getDateYYYYMMDDtoMMYYYY(AppConstant.strTglTrx));

        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void DisplayView(int Position){
        CURRENT_POSITION = Position;
        fragment = null;
        switch (Position){
            case POSITION_STOCK:
                txtLabel.setText(getResources().getString(R.string.dash_stock_today));
                fragment = new Frag_Dashboard_Stock();
                break;
            case POSITION_PENJUALAN:
                txtLabel.setText(getResources().getString(R.string.dash_sales_label) + " "
                        + AppController.getInstance().getDateYYYYMMDDtoMMYYYY(AppConstant.strTglTrx));
                fragment = new Frag_Dashboard_Penjualan();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }

        /*if (CURRENT_POSITION == POSITION_STOCK){
            lyFilter.setVisibility(View.INVISIBLE);
        }else{
            lyFilter.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (CURRENT_POSITION == POSITION_STOCK){
                    DisplayView(POSITION_PENJUALAN);
                }else{
                    finish();

                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQ_CODE_LIST){
            if (resultCode == RESULT_OK) {
                try{
                    Bundle res = data.getExtras();
                    String sResult = res.getString("KELURAHAN");
                    txtLabel.setText(sResult);

                    if (sResult.equals(STOCK)){
                        txtLabel.setText(getResources().getString(R.string.dash_stock_today));
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                DisplayView(POSITION_STOCK);
                            }
                        }, 500);

                    }else{
                        txtLabel.setText(getResources().getString(R.string.dash_sales_label) + " "
                                + AppController.getInstance().getDateYYYYMMDDtoMMYYYY(AppConstant.strTglTrx));
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                DisplayView(POSITION_PENJUALAN);
                            }
                        }, 500);

                    }



                }catch (Exception e){
                    String error = e.getMessage();
                }

            }
        }

    }

}
