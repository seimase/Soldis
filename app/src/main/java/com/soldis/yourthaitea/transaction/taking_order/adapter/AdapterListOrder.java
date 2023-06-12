package com.soldis.yourthaitea.transaction.taking_order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.transaction.ImagePreview;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;
import com.soldis.yourthaitea.transaction.taking_order.ListOrderActivity;

import java.io.File;
import java.util.ArrayList;

import static com.soldis.yourthaitea.transaction.ImagePreview.AVATAR_IMAGE;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterListOrder extends  RecyclerView.Adapter<AdapterListOrder.ViewHolder>{
    ArrayList <Obj_TRX_H> objTrxHS;
    private Context context;

    public AdapterListOrder(Context context, ArrayList <Obj_TRX_H> objTrxHS, OnDownloadClicked listener) {
        this.context = context;
        this.objTrxHS = objTrxHS;
        this.listener = listener;
        if (objTrxHS == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String CUSTNO, String ORDERNO);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_transaction_listorder, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Obj_TRX_H dat = objTrxHS.get(position);
        holder.txtSONumber.setText((dat.getORDERNO() == null ? "" : dat.getORDERNO()));
        holder.txtAmount.setText(AppController.getInstance().toCurrencyRp(dat.getINVAMOUNT()));
        holder.txtSKU.setText(AppController.getInstance().toCurrency(dat.getSKU()));

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getCUSTNO(), dat.getORDERNO());
            }
        });

        holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
        if (dat.getFLAG_VOID() !=null){
            if (dat.getFLAG_VOID().equals("N")){
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else{
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }

    }

    @Override
    public int getItemCount() {
        return objTrxHS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtSONumber,
                txtAmount,
                txtSKU
        ;

        LinearLayout layout_row;

        View layout_status;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterListOrder mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtSONumber = (TextView)itemView.findViewById(R.id.txtSONumber);
            txtAmount = (TextView)itemView.findViewById(R.id.txtAmount);
            txtSKU = (TextView)itemView.findViewById(R.id.txtSKU);

            layout_status = (View)itemView.findViewById(R.id.layout_status);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
