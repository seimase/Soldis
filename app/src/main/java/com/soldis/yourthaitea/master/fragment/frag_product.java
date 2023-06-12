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
import com.soldis.yourthaitea.dashboard.ListDashboardProductActivity;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_PRLIN;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.master.ListCategory;
import com.soldis.yourthaitea.master.adapter.AdapterMasterProduct;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.Tbl_Master;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class frag_product extends Fragment {
    static int REQ_CATEGORY = 100;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    ArrayList<Obj_MASTER> objEmasters;
    Obj_MASTER objEmaster;
    Obj_PRLIN objPrlin;

    Tbl_Master tblMaster;
    ProgressDialog progress;
    TextView txtCategory;

    LinearLayout lyCategory;

    String CATEGORY_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_master_product, container, false);
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
        mGridlayoutManager = new GridLayoutManager(getActivity(),2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mGridlayoutManager);

        mRecyclerView.setNestedScrollingEnabled(false);

        lyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), ListCategory.class);
                startActivityForResult(mIntent, REQ_CATEGORY);
            }
        });

        FillGrid();
    }

    void FillGrid(){
        objEmasters = new ArrayList<>();
        objEmaster = new Obj_MASTER();
        if (VisitValidation()){
            objEmasters = objEmaster.getDataListAwal();
        }else{
            objEmasters = objEmaster.getDataList();
        }
        Log.w("SyncProduct", "PCODE : " + objEmasters.size());
        mAdapter = new AdapterMasterProduct(getActivity(), objEmasters, new AdapterMasterProduct.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void FillGridSortBy(String CATEGORY_ID){
        objEmasters = new ArrayList<>();
        objEmaster = new Obj_MASTER();

        objEmasters = objEmaster.getDataListSortBy(CATEGORY_ID);
        mAdapter = new AdapterMasterProduct(getActivity(), objEmasters, new AdapterMasterProduct.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") ){
            return true;
        }else{
            if (objVisit.getTMGO() != null){
                if (objVisit.getTMBCK() == null || objVisit.getTMBCK().equals("")){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CATEGORY){
            if (resultCode == RESULT_OK) {
                Bundle res = data.getExtras();
                CATEGORY_ID = res.getString("CATEGORY_ID");
                String CATEGORY_NAME = res.getString("CATEGORY_NAME");
                txtCategory.setText(CATEGORY_NAME);
                FillGridSortBy(CATEGORY_ID);
            }
        }
    }
}
