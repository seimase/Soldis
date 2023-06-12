package com.soldis.yourthaitea.stock;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_STOCK_MAPPING;
import com.soldis.yourthaitea.entity.Obj_STOCK_TMP;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Visit;
import com.soldis.yourthaitea.model.net.NetworkManager;
import com.soldis.yourthaitea.stock.adapter.AdapterHistStock;
import com.soldis.yourthaitea.stock.adapter.AdapterInputStock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/13/2017.
 */

public class InputStockActivity extends AppCompatActivity {
    TextView txtProduct, txtSave, txtStock, txtStockIn, txtStockOut, txtStockSales;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    RelativeLayout layout_back;
    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    Obj_STOCK objStock;
    ArrayList<Obj_STOCK> objStocks;

    Obj_VISIT objVisit ;
    boolean bDone = false;
    double dSubtotal = 0;
    String PCODE, PCODENAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_input_stock);

        try{
            PCODE = getIntent().getExtras().getString("PCODE");
            PCODENAME = getIntent().getExtras().getString("PCODENAME");
        }catch (Exception e){
            PCODE = "";
            PCODENAME = "";
        }

        InitControl();
        FillGrid(PCODE);
        AppController.getInstance().hideKeyboardFrom(InputStockActivity.this);

    }

    void InitControl(){
        txtProduct = (TextView)findViewById(R.id.txtProduct);
        txtSave = (TextView)findViewById(R.id.txtSave);
        txtStock = (TextView)findViewById(R.id.txtStock);
        txtStockIn = (TextView)findViewById(R.id.txtStockIn);
        txtStockOut = (TextView)findViewById(R.id.txtStockOut);
        txtStockSales = (TextView)findViewById(R.id.txtStockSales);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        txtProduct.setText(PCODENAME);

        objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialogInputStock(PCODE, PCODENAME);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (ValidasiTransaksi()){
                    finish();
                }else{
                    CustomeDialogCancel();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    void FillGrid(String PCODE){
        Obj_STOCK_MAPPING objStockMapping = new Obj_STOCK_MAPPING();
        objStock = new Obj_STOCK();
        //objEmasters = objEmaster.getDataList();
        int iSTOCK = 0;
        int iSTOCK_IN = 0;
        int iSTOCK_OUT = 0;
        int iSTOCK_SALES = 0;

        iSTOCK_SALES = objStockMapping.TotalQty(PCODE);
        for (Obj_STOCK dat : objStock.getDataListPCode(PCODE)){
            if (dat.getFLAG_IN().equals("A") || dat.getFLAG_IN().equals("Y")){
                //iSTOCK = iSTOCK + dat.getSTOCK();
                iSTOCK_IN += dat.getSTOCK();
            }else{
                //iSTOCK = iSTOCK - dat.getSTOCK();
                iSTOCK_OUT += dat.getSTOCK();
            }
        }

        iSTOCK = iSTOCK_IN - (iSTOCK_OUT + iSTOCK_SALES);
        txtStockIn.setText(AppController.getInstance().toCurrency(iSTOCK_IN));
        txtStockOut.setText(AppController.getInstance().toCurrency(iSTOCK_OUT));
        txtStockSales.setText(AppController.getInstance().toCurrency(iSTOCK_SALES));
        txtStock.setText(AppController.getInstance().toCurrency(iSTOCK));

        mAdapter = new AdapterHistStock(InputStockActivity.this, objStock.getDataListPCode(PCODE), new AdapterHistStock.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FLAG_IN, String TGL_TXR, String TIMEIN) {
                if (!FLAG_IN.equals("A")){
                    CustomeDialogDelete("Hapus data : " +TGL_TXR + " " + TIMEIN + " ?", TGL_TXR, TIMEIN );
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    void CustomeDialogDelete(String ISI, final String TGL_TRX, final String TIMEIN ){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(ISI);
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                objStock = new Obj_STOCK();
                objStock.DeleteData(TGL_TRX, TIMEIN);
                FillGrid(PCODE);
            }
        });

        dialog.show();
    }
    boolean VisitValidation(){
        Obj_VISIT objVisit = new Obj_VISIT();
        objVisit = objVisit.getData();

        if (objVisit.getTMGO() == null || objVisit.getTMGO().equals("") )
            return false;

        if (objVisit.getTMBCK() != null && !objVisit.getTMBCK().equals("") )
            return false;

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_CALL) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ValidasiTransaksi()){
                finish();
            }else{
                CustomeDialogCancel();
            }
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }

        return false;
    }

    boolean ValidasiTransaksi(){
        if (dSubtotal == 0){
            return true;
        }else{
            return false;
        }
    }

    void CustomeDialogCancel(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_not_so_transaction));
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();

            }
        });

        dialog.show();
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
                    AppController.getInstance().CustomeDialog(InputStockActivity.this, "Stock belum diisi!");
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
                    FillGrid(PCODE);
                }

            }
        });

        dialog.show();
    }

}
