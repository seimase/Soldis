package com.soldis.yourthaitea.transaction.maintenance.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.transaction.maintenance.DispenserConfirmationActivity;
import com.soldis.yourthaitea.transaction.maintenance.adapter.AdapterInputDispenser;
import com.soldis.yourthaitea.transaction.taking_order.OrderConfirmationActivity;
import com.soldis.yourthaitea.transaction.taking_order.adapter.AdapterInputProductCategory;
import com.soldis.yourthaitea.util.GPSTracker;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class frag_InputDispenser extends Fragment {
    static int REQ_NEXT = 100;
    static int REQ_CATEGORY = 110;
    TextView   txtSubtotal,
            badge_notification_1,
            text_amount1
            ;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;


    Obj_MASTER objEmaster;

    String sCustNo, sCustName, sAddress, sTgl, sOrderNo;

    double dSubtotal = 0;
    long lQty = 0;
    boolean bStatus;
    //LinearLayout lySave, lyAdd, lyProcess, lyButton;
    String TIMEIN = "";
    String sLat, sLng;
    String sSpeed;

    boolean bDone = false;

    LinearLayout layoutReview;

    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input_dispenser, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AppConstant.objMasters = new ArrayList<>();

        AppController.getInstance().hideKeyboardFrom(getActivity());

        objEmaster = new Obj_MASTER();
        AppConstant.objMasters = objEmaster.getDataListStockDispenser(AppConstant.strCustNo);

        InitControl(view);
    }



    void InitControl(View v){
        layoutReview = (LinearLayout)v.findViewById(R.id.layoutReview);
        badge_notification_1 = (TextView)v.findViewById(R.id.badge_notification_1);
        text_amount1 = (TextView)v.findViewById(R.id.text_amount1);

        txtSubtotal = (TextView)v.findViewById(R.id.text_subtotal);


        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        layoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), DispenserConfirmationActivity.class);
                mIntent.putExtra("STATUS", bStatus);
                startActivityForResult(mIntent, REQ_NEXT);
            }
        });

        FillGrid();
    }


    void FillGrid(){
        progress = ProgressDialog.show(getActivity(), getResources().getString(R.string.main_Information),
                getResources().getString(R.string.text_checking_data), true);

        Log.w("FillGrid", "Load");
        objEmaster = new Obj_MASTER();
        Obj_TRX_H objTrxH = new Obj_TRX_H();
        objTrxH = objTrxH.getDataCustNo(sCustNo);
        String Status = "";
        boolean bTRX = false;

        //Init Fisrt--------------------------------------------------------------------------------
        dSubtotal = 0;
        lQty = 0;
        badge_notification_1.setText("0");
        for (Obj_MASTER dat : AppConstant.objMasters){
            dSubtotal += dat.getTOTAL();
            lQty += dat.getQTY_TRX();
        }

        if (lQty == 0){
            layoutReview.setVisibility(View.GONE);
        }else{
            layoutReview.setVisibility(View.VISIBLE);
        }

        badge_notification_1.setText(Long.toString(lQty));
        text_amount1.setText(AppController.getInstance().toCurrencyRp(dSubtotal));

        //------------------------------------------------------------------------------------------

       //AppController.getInstance().CustomeDialog(InputProductActivity.this, Status);
        mAdapter = new AdapterInputDispenser(getActivity(), AppConstant.objMasters, bTRX, new AdapterInputDispenser.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(){
                dSubtotal = 0;
                lQty = 0;
                badge_notification_1.setText("0");
                for (Obj_MASTER dat : AppConstant.objMasters){
                    dSubtotal += dat.getTOTAL();
                    lQty += dat.getQTY_TRX();
                }

                badge_notification_1.setText(Long.toString(lQty));
                text_amount1.setText(AppController.getInstance().toCurrencyRp(dSubtotal));

                if (lQty == 0){
                    layoutReview.setVisibility(View.GONE);
                }else{
                    layoutReview.setVisibility(View.VISIBLE);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        progress.dismiss();
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.stock_saved));
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
                if (bDone){
                    //lyAdd.setVisibility(View.VISIBLE);
                    //lySave.setVisibility(View.GONE);
                    //AppController.getInstance().CustomeDialog(InputProductActivity.this, getResources().getString(R.string.daily_data_hasbeen_process));
                    AppController.getInstance().BackUpDB(getActivity());
                    CustomeDialogSave(getResources().getString(R.string.stock_hasben_saved));
                }else{
                    AppController.getInstance().CustomeDialog(getActivity(), getResources().getString(R.string.daily_product_not_selected));
                }

            }
        });

        dialog.show();
    }

    void CustomeDialogProses(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_yes_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtNo = (TextView)dialog.findViewById(R.id.text_no);
        TextView txtYes = (TextView)dialog.findViewById(R.id.text_yes);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);

        txtDialog.setText(getResources().getString(R.string.daily_data_in_process));
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
                Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();
                objCustcard.UpdateFlagProses(sCustNo);
                AppController.getInstance().CustomeDialog(getActivity(), getResources().getString(R.string.daily_data_hasbeen_process));
                AppController.getInstance().BackUpDB(getActivity());
            }
        });

        dialog.show();
    }


    void CustomeDialogTidakBeli(){
        final Dialog dialog = new Dialog(getActivity());
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
                AppConstant.bRefresh = true;

            }
        });

        dialog.show();
    }



    void CustomeDialog(String ISI){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void CustomeDialogSave(String ISI){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
