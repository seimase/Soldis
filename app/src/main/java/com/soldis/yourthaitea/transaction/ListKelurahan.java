package com.soldis.yourthaitea.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.transaction.adapter.AdapterListKelurahan;

import java.util.ArrayList;

/**
 * Created by User on 8/22/2017.
 */

public class ListKelurahan extends AppCompatActivity {
    TextView txtLabel;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_kelurahan);

        InitControl();
        FillGrid();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);

        lyKembali = (RelativeLayout)findViewById(R.id.layout_kembali);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        lyKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void FillGrid(){
        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();
        objEcustmsts = objEcustmst.getDataListKelurahan();

        objEcustmst = new Obj_CUSTMST();
        objEcustmst.setKELURAHAN(getResources().getString(R.string.daily_all)) ;
        objEcustmsts.add(objEcustmst);

        mAdapter = new AdapterListKelurahan(this, objEcustmsts, new AdapterListKelurahan.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String KELURAHAN) {
                Intent mIntent = new Intent();
                mIntent.putExtra("KELURAHAN", KELURAHAN);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

}
