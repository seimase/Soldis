package com.soldis.yourthaitea.transaction.taking_order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.transaction.taking_order.ListOrderActivity;
import com.soldis.yourthaitea.transaction.taking_order.ViewOrderActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterListOrder;

public class frag_listorder_byorder extends Fragment {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Obj_TRX_H objTrxH;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listorder, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);

    }

    private void InitControl(final View v) {
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillGrid(){
        objTrxH = new Obj_TRX_H();

        mAdapter = new AdapterListOrder(getActivity(), objTrxH.getDataList(), new AdapterListOrder.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CUSTNO, String ORDERNO) {
                Intent mIntent = new Intent(getActivity(), ViewOrderActivity.class);
                mIntent.putExtra("CUSTNO", CUSTNO);
                mIntent.putExtra("ORDERNO", ORDERNO);
                mIntent.putExtra("CUSTNAME", AppConstant.strCustName);
                mIntent.putExtra("ADDRESS", AppConstant.strCustAddress);
                mIntent.putExtra("STATUS", true);
                AppConstant.strOrderNo = ORDERNO;
                startActivity(mIntent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FillGrid();
    }
}
