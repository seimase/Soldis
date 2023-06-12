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

public class DialogPaymentType extends AppCompatActivity {
    TextView txtLabel,
            txtSemua,
            txtTransaction,
            txtNoTransaction
    ;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_paymenttype);

        InitControl();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtSemua = (TextView)findViewById(R.id.text_semua);
        txtTransaction = (TextView)findViewById(R.id.text_transaction);
        txtNoTransaction = (TextView)findViewById(R.id.text_notransaction);

        lyKembali = (RelativeLayout)findViewById(R.id.layout_kembali);

        lyKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PAYTYPE", "RUTIN");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txtTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PAYTYPE", "TENDER");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txtNoTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("PAYTYPE", "CASH");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
    }



}
