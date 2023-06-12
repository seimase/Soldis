package com.soldis.yourthaitea.admin.stock;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Visit;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.stock.adapter.AdapterListStock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListStockActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    RelativeLayout layout_back;
    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    Obj_STOCK objStock;

    Obj_VISIT objVisit ;
    boolean bDone = false;
    double dSubtotal = 0;

    Tbl_Master tblMaster;
    ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_list_stock);
        InitControl();

        SyncData();

    }

    void InitControl(){
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void SyncData(){
        progress = ProgressDialog.show(this, getResources().getString(R.string.main_Information),
                getResources().getString(R.string.setting_sync_data), true);

        try{
            Call<Tbl_Master> call = NetworkManager.getNetworkService().MasterProduct(AppConstant.strMitraID);
            call.enqueue(new Callback<Tbl_Master>() {
                @Override
                public void onResponse(Call<Tbl_Master> call, Response<Tbl_Master> response) {
                    int code = response.code();

                    if (code == 200){
                        tblMaster = response.body();
                        if (!tblMaster.error){
                            objEmaster = new Obj_MASTER();

                            if (tblMaster.master.size() > 0){
                                objEmaster.DeleteMaster();

                                for(Tbl_Master.Master dat : tblMaster.master){
                                    objEmaster = new Obj_MASTER();
                                    objEmaster.setPCode(dat.PCODE);
                                    objEmaster.setPCodeName(dat.PCODENAME);
                                    objEmaster.setPHOTO_NAME(dat.PHOTO_NAME);
                                    objEmaster.setFLAG_SALES(dat.FLAG_SALES);
                                    objEmaster.setConvUnit2(dat.CONVUNIT2);
                                    objEmaster.setConvUnit3(dat.CONVUNIT3);
                                    objEmaster.setUnit1(dat.UNIT1);
                                    objEmaster.setUnit2(dat.UNIT2);
                                    objEmaster.setUnit3(dat.UNIT3);
                                    objEmaster.setSellPrice1(dat.SELLPRICE);
                                    objEmaster.setSellPrice2(dat.SELLPRICE);
                                    objEmaster.setSellPrice3(dat.SELLPRICE);
                                    objEmaster.setPrLin(dat.CATEGORY_ID);
                                    objEmaster.CreateMaster(objEmaster);
                                }
                            }

                            objStock = new Obj_STOCK();
                            if (tblMaster.stock.size() >0 ){
                                objStock.DeleteData();
                                for (Tbl_Master.Stock dat : tblMaster.stock){
                                    objStock = new Obj_STOCK();
                                    objStock.setPCODE(dat.PCODE);
                                    objStock.setDOCNO(dat.DOCNO);
                                    objStock.setSTOCK(dat.QTY);
                                    objStock.setFLAG_IN(dat.FLAG_IN);
                                    objStock.setTIMEIN(AppController.getInstance().getCurrentTime());
                                    objStock.setTGL_TRX(dat.TGL_TRX);
                                    objStock.CreateDate(objStock);
                                }
                            }
                            FillGrid();
                            progress.dismiss();
                        }
                    }else{
                        progress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Tbl_Master> call, Throwable t) {
                    progress.dismiss();
                }
            });
        }catch (Exception e){
            progress.dismiss();
        }
    }

    void FillGrid(){
        objEmasters = new ArrayList<>();
        objEmaster = new Obj_MASTER();
        boolean bStock = false;
        objEmasters = objEmaster.getDataListStock();
        if (objEmasters.size() > 0){
            bStock = true;
        }

        mAdapter = new AdapterListStock(this, objEmasters, bStock,  new AdapterListStock.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String PCODE, String PCODENAME) {
                CustomeDialogInputStock(PCODE, PCODENAME);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    void CustomeDialogInputStock(final String PCODE, String PCODENAME) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialoginputstock_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView) dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView) dialog.findViewById(R.id.text_yes);
        TextView txtPCodeName = (TextView) dialog.findViewById(R.id.txtPCodeName);
        final EditText edtStock = (EditText)dialog.findViewById(R.id.edtStock);

        txtPCodeName.setText(PCODENAME);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Masuk");
        spinnerArray.add("Keluar");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatus);
        spnStatus.setAdapter(adapter);

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sStock = edtStock.getText().toString().trim();
                if (sStock.equals("") || sStock.equals("0")){
                    AppController.getInstance().CustomeDialog(AdminListStockActivity.this, "Stock belum diisi!");
                }else{
                    String sFlagIn;
                    if (spnStatus.getSelectedItem().toString().trim().equals("Masuk")){
                        sFlagIn = "Y";
                    }else{
                        sFlagIn = "N";
                    }
                    objStock = new Obj_STOCK();
                    objStock.setPCODE(PCODE);
                    objStock.setSTOCK(Integer.parseInt(sStock));
                    objStock.setFLAG_IN(sFlagIn);
                    objStock.setTIMEIN(AppController.getInstance().getCurrentTime());
                    objStock.setTGL_TRX(AppConstant.strTglTrx);
                    objStock.CreateDate(objStock);
                    dialog.dismiss();
                    FillGrid();
                }

            }
        });

        dialog.show();
    }
}
