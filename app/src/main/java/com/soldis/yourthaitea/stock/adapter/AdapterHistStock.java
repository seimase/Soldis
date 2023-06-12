package com.soldis.yourthaitea.stock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.entity.Obj_STOCK;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterHistStock extends  RecyclerView.Adapter<AdapterHistStock.ViewHolder>{
    ArrayList <Obj_STOCK> objStocks;
    private Context context;
    public AdapterHistStock(Context context, ArrayList <Obj_STOCK> objStocks,
                            OnDownloadClicked listener) {
        this.context = context;
        this.objStocks = objStocks;
        this.listener = listener;
        if (objStocks == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String FLAG_IN, String TGL_TXR, String TIMEIN);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_hist_stock, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Obj_STOCK dat = objStocks.get(holder.getAdapterPosition());

        holder.txtNo.setText(Integer.toString(position + 1));

        holder.txtNo.setTextColor(context.getResources().getColor(R.color.black));
        holder.txtTgl.setTextColor(context.getResources().getColor(R.color.black));
        holder.txtQty.setTextColor(context.getResources().getColor(R.color.black));

        if(dat.getFLAG_IN().equals("A")){
            holder.txtTgl.setText("Stock Awal");
            holder.txtQty.setText(AppController.getInstance().toCurrency(dat.getSTOCK()));
        }else{
            String sTgl = (dat.getTGL_TRX() == null ? AppController.getInstance().getDateYYYYMMDD() : dat.getTGL_TRX());
            holder.txtTgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(sTgl) + " " + (dat.getTIMEIN() == null ? "" : dat.getTIMEIN()));
            if (dat.getFLAG_IN().equals("Y")){
                holder.txtQty.setText(AppController.getInstance().toCurrency(dat.getSTOCK()));
            }else {
                holder.txtNo.setTextColor(context.getResources().getColor(R.color.red));
                holder.txtTgl.setTextColor(context.getResources().getColor(R.color.red));
                holder.txtQty.setTextColor(context.getResources().getColor(R.color.red));
                holder.txtQty.setText(AppController.getInstance().toCurrency(dat.getSTOCK() * -1));
            }

        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getFLAG_IN(), dat.getTGL_TRX(), dat.getTIMEIN());
            }
        });

    }

    @Override
    public int getItemCount() {
        return objStocks.size();
    }

    private TextWatcher watcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void afterTextChanged(Editable s) { }
    };

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener  {

        TextView txtNo,
                txtTgl,
                txtQty
                        ;

        LinearLayout layout_row;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterHistStock mCourseAdapter) {
            super(itemView);
            txtNo = (TextView)itemView.findViewById(R.id.txtNo);
            txtTgl = (TextView)itemView.findViewById(R.id.txtTgl);
            txtQty = (TextView)itemView.findViewById(R.id.txtQty);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
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
