package com.soldis.yourthaitea.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;

import java.util.ArrayList;

/**
 * Created by User on 8/22/2017.
 */

public class DialogPerioderOrder extends AppCompatActivity {
    TextView txtLabel,
            txt1Month,
            txt2Month,
            txt3Month
    ;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_periode_order);

        InitControl();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txt1Month = (TextView)findViewById(R.id.txt1Month);
        txt2Month = (TextView)findViewById(R.id.txt2Month);
        txt3Month = (TextView)findViewById(R.id.txt3Month);

        lyKembali = (RelativeLayout)findViewById(R.id.layout_kembali);

        lyKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt1Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PERIODE", "1 Bulan");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txt2Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PERIODE", "2 Bulan");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txt3Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PERIODE", "3 Bulan");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
    }



}
