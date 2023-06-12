package com.soldis.yourthaitea.dashboard.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashboardPenjualan;
import com.soldis.yourthaitea.dashboard.fragment.adapter.TargetAdapter;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_DASHBOARD;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.model.Tbl_TargetTmp;

import java.util.ArrayList;

import ui.widget.CircleIndicator;

/**
 * Created by ftctest on 11/09/2017.
 */

public class Frag_Dashboard_Penjualan extends Fragment {
    private int DATA_SALES = 1;
    private int DATA_COMPLAINT = 2;
    private int DATA_MAINTAIN = 3;
    int iEC, iEC_MTD;
    Toolbar toolbar;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    double dTargetEC = 0;
    double dTargetEC_MTD = 0;
    double dTarget = 0;
    double dSales = 0;
    double dSales_MTD = 0;
    double dCALL_MTD = 0;
    double dTargetCALL = 0;
    double dTargetCALL_MTD = 0;

    Obj_CUSTMST objCustmst;
    ArrayList<Obj_CUSTMST> objCustmsts;

    Obj_DASHBOARD objDashboard;
    ArrayList<Obj_DASHBOARD>objDashboards;

    TextView txtTgl;
    TextView txtECTarget, txtECPencapaian;
    TextView txtECTargetMTD, txtECPencapaianMTD;
    TextView txtSalesTarget, txtSalesPencapaian;
    TextView txtSalesTargetMTD, txtSalesPencapaianMTD;
    TextView txtTargetCALL, txtCALLPencapaian;
    TextView txtCALL_MTD, txtCALLPencapaianMTD;


   // TextView txtCustSales, txtCustComplaint, txtCustMaintain;

    //LinearLayout lySales, lyComplaint, lyMaintain;
    ImageView imgMedalEC,
            imgMedalECPencapaianMTD,
            imgMedalSalesPencapaian,
            imgMedalSalesPencapaianMTD
            ;

    Tbl_TargetTmp tblTargetTmp;
    ArrayList<Tbl_TargetTmp> tblTargetTmps = new ArrayList<>();

    ViewPager pager;
    TargetAdapter targetAdapter;
    CircleIndicator viewPagerIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard_penjualan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }


    private void InitControl(final View v) {
        pager = (ViewPager)v.findViewById(R.id.pager);
        viewPagerIndicator = (CircleIndicator) v.findViewById(R.id.indicator);
        imgMedalEC = (ImageView)v.findViewById(R.id.imgMedalEC);
        imgMedalECPencapaianMTD = (ImageView)v.findViewById(R.id.imgMedalECPencapaianMTD);
        imgMedalSalesPencapaian = (ImageView)v.findViewById(R.id.imgMedalSalesPencapaian);
        imgMedalSalesPencapaianMTD = (ImageView)v.findViewById(R.id.imgMedalSalesPencapaianMTD);

        txtTgl = (TextView)v.findViewById(R.id.txtTgl);
        txtECTarget = (TextView)v.findViewById(R.id.txtECTarget);
        txtECTargetMTD = (TextView)v.findViewById(R.id.txtECTargetMTD);
        txtECPencapaian = (TextView)v.findViewById(R.id.txtECPencapaian);
        txtECPencapaianMTD = (TextView)v.findViewById(R.id.txtECPencapaianMTD);
        txtSalesTarget = (TextView)v.findViewById(R.id.txtSalesTarget);
        txtSalesTargetMTD = (TextView)v.findViewById(R.id.txtSalesTargetMTD);
        txtSalesPencapaian = (TextView)v.findViewById(R.id.txtSalesPencapaian);
        txtSalesPencapaianMTD = (TextView)v.findViewById(R.id.txtSalesPencapaianMTD);
        txtTargetCALL = (TextView)v.findViewById(R.id.txtCallTarget);
        txtCALLPencapaian = (TextView)v.findViewById(R.id.txtCallPencapaian);
        txtCALL_MTD = (TextView)v.findViewById(R.id.txtCallTargetMTD);
        txtCALLPencapaianMTD = (TextView)v.findViewById(R.id.txtCallPencapaianMTD);

        //txtCustSales = (TextView)v.findViewById(R.id.txtCustSales);
        //txtCustComplaint = (TextView)v.findViewById(R.id.txtCustComplaint);
        //txtCustMaintain = (TextView)v.findViewById(R.id.txtCustMaintain);

        //lySales = (LinearLayout)v.findViewById(R.id.layout_sales);
        //lyComplaint = (LinearLayout)v.findViewById(R.id.layout_complaint);
        //lyMaintain = (LinearLayout)v.findViewById(R.id.layout_maintain);

        toolbar = (Toolbar) v.findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);


        Obj_MOTORIS objMotoris = new Obj_MOTORIS();
        objMotoris = objMotoris.getData(AppConstant.strSlsNo);

        Obj_TRX_H objTrxH = new Obj_TRX_H();
        iEC = 0;
        dSales = 0;

        /*for (Obj_TRX_H dat : objTrxH.getDataListDashboard()){
            dSales += dat.getINVAMOUNT();
        }*/

        dSales = objTrxH.TotalQty();

        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        iEC = objCustcard1.getDataListEC().size();

        iEC_MTD = 0;
        dSales_MTD = 0;
        dTargetEC = 0;
        dTargetEC_MTD = 0;
        if(objMotoris.getSLSNO() != null){
            txtECTarget.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_EC()));
            txtECTargetMTD.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_EC_MTD()));
            txtSalesTarget.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_SALES()));
            txtSalesTargetMTD.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_SALES_MTD()));

            dTargetEC_MTD = objMotoris.getTARGET_EC_MTD();
            dTargetEC = objMotoris.getTARGET_EC();
            dTarget = objMotoris.getTARGET_SALES();
            iEC_MTD = objMotoris.getTOTAL_EC();
            dSales_MTD = objMotoris.getTOTAL_SALES();
            dCALL_MTD = objMotoris.getTOTAL_CALL();

            if (dSales >= dTarget) imgMedalSalesPencapaian.setVisibility(View.VISIBLE);
            if ((dSales + dSales_MTD) >= objMotoris.getTARGET_SALES_MTD()) imgMedalSalesPencapaianMTD.setVisibility(View.VISIBLE);


            txtTargetCALL.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_CALL()));
            txtCALL_MTD.setText(AppController.getInstance().toCurrency(objMotoris.getTARGET_CALL_MTD()));

            dTargetCALL = objMotoris.getTARGET_CALL();
            dTargetCALL_MTD = objMotoris.getTARGET_CALL_MTD();

        }



        if (iEC >= dTargetEC) imgMedalEC.setVisibility(View.VISIBLE);
        if ((iEC + iEC_MTD) >= dTargetEC_MTD) imgMedalECPencapaianMTD.setVisibility(View.VISIBLE);


        txtECPencapaian.setText(AppController.getInstance().toCurrency(iEC));
        txtECPencapaianMTD.setText(AppController.getInstance().toCurrency(iEC + iEC_MTD));
        txtSalesPencapaian.setText(AppController.getInstance().toCurrency(dSales));
        txtSalesPencapaianMTD.setText(AppController.getInstance().toCurrency(dSales + dSales_MTD));

        txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));

        objCustcard1 = new Obj_CUSTCARD1();

        int iCALL = objCustcard1.getDataList().size();

        txtCALLPencapaianMTD.setText(AppController.getInstance().toCurrency(iCALL + dCALL_MTD));
        txtCALLPencapaian.setText(AppController.getInstance().toCurrency(iCALL));


        /*
        lySales.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               FillGrid(DATA_SALES);
            }
        });

        lyComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid(DATA_COMPLAINT);
            }
        });

        lyMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid(DATA_MAINTAIN);
            }
        });
         */

        objCustmsts = new ArrayList<>();
        objCustmst = new Obj_CUSTMST();

        //txtCustSales.setText(AppController.getInstance().toCurrency(objCustmst.getDataListDashboard().size()));
        //txtCustComplaint.setText(AppController.getInstance().toCurrency(objCustmst.getDataListDashboardComplaint().size()));
        //txtCustMaintain.setText(AppController.getInstance().toCurrency(objCustmst.getDataListDashboardMaintenance().size()));
        FillGrid(DATA_SALES);

        tblTargetTmp = new Tbl_TargetTmp();
        tblTargetTmp.TARGET_TYPE = "EC Target";
        tblTargetTmp.TARGET_DAY = dTargetEC;
        tblTargetTmp.TARGET_MONTH = dTargetEC_MTD;
        tblTargetTmp.ACH_MONTH = iEC;
        tblTargetTmp.ACH_DAY = iEC + iEC_MTD;
        tblTargetTmps.add(tblTargetTmp);


        tblTargetTmp = new Tbl_TargetTmp();
        tblTargetTmp.TARGET_TYPE = "Call Target";
        tblTargetTmp.TARGET_DAY = dTargetCALL;
        tblTargetTmp.TARGET_MONTH = dTargetCALL_MTD;
        tblTargetTmp.ACH_MONTH = dCALL_MTD;
        tblTargetTmp.ACH_DAY = iCALL;
        tblTargetTmps.add(tblTargetTmp);

        tblTargetTmp = new Tbl_TargetTmp();
        tblTargetTmp.TARGET_TYPE = "Penjualan";
        tblTargetTmp.TARGET_DAY = objMotoris.getTARGET_SALES();
        tblTargetTmp.TARGET_MONTH = objMotoris.getTARGET_SALES_MTD();
        tblTargetTmp.ACH_MONTH = dSales_MTD;
        tblTargetTmp.ACH_DAY = dSales;
        tblTargetTmps.add(tblTargetTmp);

        targetAdapter = new TargetAdapter(getActivity());
        targetAdapter.setData(tblTargetTmps);
        pager.setAdapter(targetAdapter);

        viewPagerIndicator.setViewPager(pager, tblTargetTmps.size());
    }


    void FillGrid(int STATUS){
        objCustmsts = new ArrayList<>();
        objCustmst = new Obj_CUSTMST();


        switch (STATUS){
            case 1:
                objCustmsts = objCustmst.getDataListDashboard();
                break;
            case 2:
                objCustmsts = objCustmst.getDataListDashboardComplaint();
                break;
            case 3:
                objCustmsts = objCustmst.getDataListDashboardMaintenance();
                break;
            default:
                objCustmsts = objCustmst.getDataListDashboard();
                break;
        }

        mAdapter = new AdapterDashboardPenjualan(getActivity(), STATUS, objCustmsts, new AdapterDashboardPenjualan.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
