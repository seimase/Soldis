package com.soldis.yourthaitea.transaction.pembelian;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
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
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.stock.ListStockActivity;
import com.soldis.yourthaitea.stock.adapter.AdapterListStock;
import com.soldis.yourthaitea.transaction.pembelian.adapter.AdapterListPembelian;
import com.soldis.yourthaitea.util.NumberTextWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PembelianActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    RelativeLayout layout_back;

    TextView txtAddMoreItems,
            txtTotalPrice,
            txtTgl;

    Obj_KAS objKas;
    ArrayList<Obj_KAS> objKasArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_transaction_pembelian);

        InitControl();
        FillGrid();
    }

    void InitControl(){
        txtAddMoreItems = (TextView)findViewById(R.id.txtAddMoreItems);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
        txtTgl = (TextView)findViewById(R.id.txtTgl);
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);


        try{
            txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(AppConstant.strTglTrx));
        }catch (Exception e){
            txtTgl.setText("");
        }

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeDialogInputBarang();
            }
        });
    }

    void FillGrid(){
        objKas = new Obj_KAS();
        objKasArrayList = new ArrayList<>();
        objKasArrayList = objKas.getDataList();

        double dKas = 0;
        for (Obj_KAS dat : objKasArrayList){
            if (dat.getTYPE_KAS().equals("Y")){
                dKas += dat.getAMOUNT();
            }else{
                dKas -= dat.getAMOUNT();
            }
        }

        txtTotalPrice.setText(AppController.getInstance().toCurrencyRp(dKas));
        if (dKas > 0){
            txtTotalPrice.setTextColor(getResources().getColor(R.color.black));
        }else{
            txtTotalPrice.setTextColor(getResources().getColor(R.color.red));
        }

        mAdapter = new AdapterListPembelian(this, objKasArrayList,   new AdapterListPembelian.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String DOCNO, String KET) {
                CustomeDialogDelete("Data " + KET + " di hapus?",  DOCNO);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    void CustomeDialogDelete(String ISI, final String DOCNO){
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
                objKas = new Obj_KAS();
                objKas.DeleteData(DOCNO);
                FillGrid();
            }
        });

        dialog.show();
    }

    void CustomeDialogInputBarang() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialoginputpembelian_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView) dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView) dialog.findViewById(R.id.text_yes);
        final EditText edtRemark = (EditText)dialog.findViewById(R.id.edtRemark);
        final EditText edtStock = (EditText)dialog.findViewById(R.id.edtStock);

        TextWatcher tw = new NumberTextWatcher(edtStock, Locale.US, 0);
        edtStock.addTextChangedListener(tw);

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

                String sStock = edtStock.getText().toString().trim().replace(",","");
                String sKet = edtRemark.getText().toString().trim();
                if (sKet.equals("")){
                    AppController.getInstance().CustomeDialog(PembelianActivity.this, "Keterangan belum diisi!");
                }else{
                    if (sStock.equals("") || sStock.equals("0")){
                        AppController.getInstance().CustomeDialog(PembelianActivity.this, "Jumlah belum diisi!");
                    }else{
                        String sFlagIn;
                        if (spnStatus.getSelectedItem().toString().trim().equals("Masuk")){
                            sFlagIn = "Y";
                        }else{
                            sFlagIn = "N";
                        }
                        Obj_KAS objKas = new Obj_KAS();
                        objKas.setREMARK(edtRemark.getText().toString().trim());
                        objKas.setTIMEIN(AppController.getInstance().getCurrentTime());
                        objKas.setTYPE_KAS(sFlagIn);
                        objKas.setAMOUNT(Long.parseLong(sStock));
                        objKas.CreateDate(objKas);
                        dialog.dismiss();
                        FillGrid();
                    }
                }


            }
        });

        dialog.show();
    }
}
