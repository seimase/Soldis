package com.soldis.yourthaitea.master.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_PRLIN;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.master.ListCategory;
import com.soldis.yourthaitea.master.ListGroupClassification;
import com.soldis.yourthaitea.master.adapter.AdapterMasterClassification;
import com.soldis.yourthaitea.master.adapter.AdapterMasterProduct;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.transaction.adapter.AdapterListTypeOut;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class frag_classification extends Fragment {
    static int REQ_CATEGORY = 100;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    ArrayList<Obj_MASTER> objEmasters;
    Obj_MASTER objEmaster;

    ArrayList<Obj_TYPEOUT> objTypeouts;
    Obj_TYPEOUT objTypeout;
    Obj_PRLIN objPrlin;

    Tbl_Master tblMaster;
    ProgressDialog progress;
    TextView txtCategory;

    LinearLayout lyCategory;

    String CATEGORY_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_master_classification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    private void InitControl(final View v) {
        txtCategory = (TextView)v.findViewById(R.id.txtCategory);
        lyCategory = (LinearLayout) v.findViewById(R.id.ly_search_category);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mGridlayoutManager = new GridLayoutManager(getActivity(),1);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mGridlayoutManager);


        lyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), ListGroupClassification.class);
                startActivityForResult(mIntent, REQ_CATEGORY);
            }
        });

        FillGrid();
    }

    void FillGrid(){
        objTypeouts = new ArrayList<>();
        objTypeout = new Obj_TYPEOUT();

        objTypeouts = objTypeout.getDataList();

        Log.w("ListTypeout " , " GetDatalist");

        mAdapter = new AdapterMasterClassification(getActivity(), objTypeouts, new AdapterMasterClassification.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String TYPEOUT, String TYPENAME) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void FillGridSortBy(String GROUP_CODE){
        objTypeouts = new ArrayList<>();
        objTypeout = new Obj_TYPEOUT();

        objTypeouts = objTypeout.getDataList(GROUP_CODE);

        mAdapter = new AdapterMasterClassification(getActivity(), objTypeouts, new AdapterMasterClassification.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String TYPEOUT, String TYPENAME) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CATEGORY){
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                CATEGORY_ID = res.getString("GROUP_CODE");
                String CATEGORY_NAME = res.getString("GROUP_NAME");
                txtCategory.setText(CATEGORY_NAME);
                FillGridSortBy(CATEGORY_ID);
            }
        }
    }
}
