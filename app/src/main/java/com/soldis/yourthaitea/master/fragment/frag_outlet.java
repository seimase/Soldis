package com.soldis.yourthaitea.master.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.master.OutletActivity;
import com.soldis.yourthaitea.master.adapter.AdapterMasterOutletFrag;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.transaction.ListKelurahan;

import java.util.ArrayList;

public class frag_outlet extends Fragment {
    static int REQ_CODE_KELURAHAN = 100;
    static int REQ_CODE_SORT = 200;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_CUSTMST objEcustmst;
    ArrayList<Obj_CUSTMST> objEcustmsts;

    LinearLayout lySearch, lySearchDetail, lyKelurahan,
            lyBatal, lyCari,
            lyTotalOutlet,
            lyTotalPreReg,
            lyTotalRegister
            ;
    TextView txtKelurahan,
            txtTotalOutlet,
            txtTotalPreReg,
            txtTotalRegister
    ;
    EditText edtKeyWord;

    ProgressDialog progress;
    Tbl_Custmst tblCustmst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_master_outlet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        objEcustmst = new Obj_CUSTMST();
        txtTotalOutlet.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOut()));
        txtTotalPreReg.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutPreReg()));
        txtTotalRegister.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutRegister()));

        AppConstant.bRefresh = false;
        FillGrid("","", true);

    }

    private void InitControl(final View v) {
        txtTotalOutlet = (TextView)v.findViewById(R.id.txtTotalOutlet);
        txtTotalPreReg = (TextView)v.findViewById(R.id.txtTotalPreReg);
        txtTotalRegister = (TextView)v.findViewById(R.id.txtTotalRegister);

        lyTotalOutlet = (LinearLayout)v.findViewById(R.id.layout_total);
        lyTotalPreReg = (LinearLayout)v.findViewById(R.id.layout_total_prereg);
        lyTotalRegister = (LinearLayout)v.findViewById(R.id.layout_total_register);

        lySearch = (LinearLayout)v.findViewById(R.id.layout_search);
        lySearchDetail = (LinearLayout)v.findViewById(R.id.layout_search_detail);
        lyKelurahan = (LinearLayout)v.findViewById(R.id.layout_kelurahan);
        lyBatal = (LinearLayout)v.findViewById(R.id.layout_batal);
        lyCari = (LinearLayout)v.findViewById(R.id.layout_cari);

        edtKeyWord = (EditText)v.findViewById(R.id.edt_keyword);
        txtKelurahan = (TextView)v.findViewById(R.id.txtKelurahan);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mGridlayoutManager = new GridLayoutManager(getActivity(),2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        lySearch.setVisibility(View.VISIBLE);
        lySearchDetail.setVisibility(View.GONE);

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


        lyCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid(edtKeyWord.getText().toString().trim(),txtKelurahan.getText().toString(), true);
                lySearch.setVisibility(View.VISIBLE);
                lySearchDetail.setVisibility(View.GONE);
            }
        });

        lyBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtKeyWord.setText("");
                lySearch.setVisibility(View.VISIBLE);
                lySearchDetail.setVisibility(View.GONE);
            }
        });

        lySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lySearch.setVisibility(View.GONE);
                lySearchDetail.setVisibility(View.VISIBLE);
            }
        });

        lyKelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), ListKelurahan.class);
                startActivityForResult(mIntent, REQ_CODE_KELURAHAN);
            }
        });

        lyTotalOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("0","", false);
            }
        });

        lyTotalPreReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("1","", false);
            }
        });

        lyTotalRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillGrid("2","", false);
            }
        });

    }



    public void FillGrid(String KEYWORD, String KELURAHAN, boolean bSearch){
        progress = ProgressDialog.show(getActivity(), "Informasi",
                "Sinkronisasi data", true);

        objEcustmsts = new ArrayList<>();
        objEcustmst = new Obj_CUSTMST();

        txtTotalOutlet.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOut()));
        txtTotalPreReg.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutPreReg()));
        txtTotalRegister.setText(AppController.getInstance().toCurrency(objEcustmst.TotalOutRegister()));

        objEcustmst = new Obj_CUSTMST();
        if (bSearch){
            if (KELURAHAN.equals(getResources().getString(R.string.daily_all))) KELURAHAN = "SEMUA";
            objEcustmsts = objEcustmst.getDataListNoo(KEYWORD,KELURAHAN);
        }else{
            objEcustmsts = objEcustmst.getDataListReg(KEYWORD);
        }


        mAdapter = new AdapterMasterOutletFrag(getActivity(), objEcustmsts, new AdapterMasterOutletFrag.OnDownloadClicked() {
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


        AppConstant.bRefresh = false;
        progress.dismiss();
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        FillGrid("","", true);
    }
}
