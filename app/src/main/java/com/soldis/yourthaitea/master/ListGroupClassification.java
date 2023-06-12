package com.soldis.yourthaitea.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.master.adapter.AdapterMasterClassification;
import com.soldis.yourthaitea.master.adapter.AdapterMasterGroupClassification;
import com.soldis.yourthaitea.transaction.adapter.AdapterListTypeOut;

import java.util.ArrayList;

/**
 * Created by User on 8/22/2017.
 */

public class ListGroupClassification extends AppCompatActivity {
    TextView txtLabel;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_TYPEOUT objTypeout;
    ArrayList<Obj_TYPEOUT> objTypeouts;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_typeout);

        InitControl();
        FillGrid();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);

        lyKembali = (RelativeLayout)findViewById(R.id.layout_kembali);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,1);
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
        objTypeout = new Obj_TYPEOUT();
        objTypeouts = new ArrayList<>();

        objTypeouts = objTypeout.getDataListGroup();

        Log.w("ListTypeout " , " GetGroup" + objTypeouts.size());
        mAdapter = new AdapterMasterGroupClassification(this, objTypeouts, new AdapterMasterGroupClassification.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String TYPEOUT, String TYPENAME) {
                Intent mIntent = new Intent();
                mIntent.putExtra("GROUP_CODE", TYPEOUT);
                mIntent.putExtra("GROUP_NAME", TYPENAME);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

}
