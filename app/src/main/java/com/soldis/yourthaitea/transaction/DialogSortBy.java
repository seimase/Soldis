package com.soldis.yourthaitea.transaction;

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

public class DialogSortBy extends AppCompatActivity {
    TextView txtLabel,
            txtSemua,
            txtTransaction,
            txtNoTransaction,
            txtNotComplete
    ;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sortby);

        InitControl();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtSemua = (TextView)findViewById(R.id.text_semua);
        txtTransaction = (TextView)findViewById(R.id.text_transaction);
        txtNoTransaction = (TextView)findViewById(R.id.text_notransaction);
        txtNotComplete = (TextView)findViewById(R.id.text_notcomplete);
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
                mIntent.putExtra("SORTBY", "ALL");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txtTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("SORTBY", "TRANSACTION");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txtNoTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("SORTBY", "NO_TRANSACTION");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        txtNotComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra("SORTBY", "NOT_COMPLETE");
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
    }



}
