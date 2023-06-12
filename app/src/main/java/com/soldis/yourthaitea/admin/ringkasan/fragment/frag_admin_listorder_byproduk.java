package com.soldis.yourthaitea.admin.ringkasan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.admin.ringkasan.adapter.AdapterAdminRingkasanListorderByProduk;
import com.soldis.yourthaitea.entity.Obj_MASTER;

public class frag_admin_listorder_byproduk extends Fragment {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Obj_MASTER objMaster;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listorder, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid();
    }

    private void InitControl(final View v) {
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillGrid(){
        objMaster = new Obj_MASTER();

        mAdapter = new AdapterAdminRingkasanListorderByProduk(getActivity(), AppConstant.tblRingkasan, new AdapterAdminRingkasanListorderByProduk.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked() {

            }

        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
