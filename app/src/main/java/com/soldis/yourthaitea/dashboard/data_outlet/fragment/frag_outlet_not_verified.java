package com.soldis.yourthaitea.dashboard.data_outlet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.data_outlet.DashboardDataOutletActivity;
import com.soldis.yourthaitea.dashboard.data_outlet.adapter.AdapterMasterOutletNotVerified;
import com.soldis.yourthaitea.master.OutletActivity;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_Dashboard_DataOutlet;

public class frag_outlet_not_verified extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ProgressDialog progress;
    Tbl_Custmst tblCustmst;
    String CUSTNO;

    Tbl_Dashboard_DataOutlet dashboardDataOutlet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard_master_outlet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    private void InitControl(final View v) {
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    //fabAdd.hide();
                } else{
                    //fabAdd.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }



    public void FillGrid(){
        dashboardDataOutlet = ((DashboardDataOutletActivity)this.getActivity()).dashboardDataOutlet;

        if (dashboardDataOutlet.outlet_not_verified.size() > 0){
            mAdapter = new AdapterMasterOutletNotVerified(getActivity(), dashboardDataOutlet , new AdapterMasterOutletNotVerified.OnDownloadClicked() {
                @Override
                public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String FLAGOUT) {
                    Intent mIntent = new Intent(getActivity(), OutletActivity.class);
                    if (FLAGOUT != null){
                        mIntent.putExtra("CUSTNO", CUSTNO);
                        mIntent.putExtra("CUSTNAME", CUSTNAME);
                        mIntent.putExtra("ADDRESS", ADDRESS);
                        mIntent.putExtra("FLAGOUT", FLAGOUT);
                        mIntent.putExtra("STATUS", false);
                        startActivity(mIntent);
                    }

                }
            });

            mRecyclerView.setAdapter(mAdapter);
        }

    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        FillGrid();
    }
}
