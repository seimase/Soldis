package com.soldis.yourthaitea.transaction.taking_order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterListOrder;
import com.soldis.yourthaitea.util.NumberTextWatcher;

import java.util.ArrayList;
import java.util.Locale;

public class OrderPaymentActivity extends AppCompatActivity {
    TextView txtAmount,
            txtUangPas,
            txt20,
            txt50,
            txt100,
            txtOrderNo,
            txtSave
    ;

    double dPay1 = 20000, dPay2 = 50000, dPay3 = 100000;
    EditText edtPay;
    RelativeLayout layout_back;
    public static ArrayList<Tmp_MASTER> tmpMasters;
    double dTotalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBar));
        }
        setContentView(R.layout.activity_order_payment);

        InitControl();
    }

    void InitControl(){
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);

        txtAmount = (TextView)findViewById(R.id.txtAmount);
        txtUangPas = (TextView)findViewById(R.id.txtUangPas);
        txt20 = (TextView)findViewById(R.id.txt20);
        txt50 = (TextView)findViewById(R.id.txt50);
        txt100 = (TextView)findViewById(R.id.txt100);
        txtSave = (TextView)findViewById(R.id.txtSave);
        edtPay = (EditText)findViewById(R.id.edtPay);
        txtOrderNo = (TextView)findViewById(R.id.text_orderno);
        txtOrderNo.setText(AppConstant.strOrderNo);

        for (Obj_MASTER dat : AppConstant.objMasters){
            if (dat.getQTY_TRX() > 0) {
                dTotalPrice += (dat.getQTY_TRX() * dat.getSellPrice1()) ;
            }
        }

        double dAmount = dTotalPrice;
        if (dTotalPrice > 20000) {

            if ((dTotalPrice + 50000) > 900000){
                dAmount = 900000;
            }else if ((dTotalPrice + 50000) > 800000){
                dAmount = 800000;
            }else if ((dTotalPrice + 50000) > 700000){
                dAmount = 700000;
            }else if ((dTotalPrice + 50000) > 600000){
                dAmount = 600000;
            }else if ((dTotalPrice + 50000) > 500000){
                dAmount = 500000;
            }else if ((dTotalPrice + 50000) > 400000){
                dAmount = 400000;
            }else if ((dTotalPrice + 50000) > 300000){
                dAmount = 300000;
            }else if ((dTotalPrice + 50000) > 200000){
                dAmount = 200000;
            }else if ((dTotalPrice + 50000) > 150000){
                dAmount = 150000;
            }else if ((dTotalPrice + 50000) > 100000){
                dAmount = 100000;
            }else if ((dTotalPrice + 50000) > 50000){
                dAmount = 50000;
            }else if ((dTotalPrice + 20000) > 20000){
                dAmount = 50000;
            }else{
                dAmount = 50000;
            }

            dPay1 = dAmount;
            dPay2 = dPay1 + 50000;
            dPay3 = dPay2 + 50000;
        }

        txt20.setText(AppController.getInstance().toCurrency(dPay1));
        txt50.setText(AppController.getInstance().toCurrency(dPay2));
        txt100.setText(AppController.getInstance().toCurrency(dPay3));

        txtAmount.setText(AppController.getInstance().toCurrencyRp(dTotalPrice));

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtUangPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(OrderPaymentActivity.this, OrderPaymentSuccessActivity.class);
                OrderPaymentSuccessActivity.tmpMasters = tmpMasters;
                mIntent.putExtra("RESIDUAL", 0);
                mIntent.putExtra("PAY", dTotalPrice);
                startActivity(mIntent);
            }
        });

        txt20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(OrderPaymentActivity.this, OrderPaymentSuccessActivity.class);
                OrderPaymentSuccessActivity.tmpMasters = tmpMasters;
                mIntent.putExtra("RESIDUAL",  dPay1 - dTotalPrice);
                mIntent.putExtra("PAY", dPay1);
                startActivity(mIntent);
            }
        });

        txt50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(OrderPaymentActivity.this, OrderPaymentSuccessActivity.class);
                OrderPaymentSuccessActivity.tmpMasters = tmpMasters;
                mIntent.putExtra("RESIDUAL",  dPay2 - dTotalPrice);
                mIntent.putExtra("PAY", dPay2);
                startActivity(mIntent);
            }
        });

        txt100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(OrderPaymentActivity.this, OrderPaymentSuccessActivity.class);
                OrderPaymentSuccessActivity.tmpMasters = tmpMasters;
                mIntent.putExtra("RESIDUAL",  dPay3 - dTotalPrice);
                mIntent.putExtra("PAY", dPay3);
                startActivity(mIntent);
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sPay = edtPay.getText().toString().replace(",","");
                if (sPay.equals("")) sPay = "0";

                long lPay = Long.parseLong(sPay);
                if ((lPay - dTotalPrice ) < 0){
                    AppController.getInstance().CustomeDialog(OrderPaymentActivity.this, "Uang yang dibayar kurang!");
                }else{
                    Intent mIntent = new Intent(OrderPaymentActivity.this, OrderPaymentSuccessActivity.class);
                    OrderPaymentSuccessActivity.tmpMasters = tmpMasters;
                    mIntent.putExtra("RESIDUAL",  lPay - dTotalPrice);
                    mIntent.putExtra("PAY", lPay);
                    startActivity(mIntent);
                }
            }
        });

        TextWatcher tw = new NumberTextWatcher(edtPay, Locale.US, 0);
        edtPay.addTextChangedListener(tw);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(AppConstant.bClose) finish();
    }
}
