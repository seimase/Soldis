package com.soldis.yourthaitea.dashboard.summary_sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.ListDashboardProductActivity;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardSummarySalesByOutlet extends  RecyclerView.Adapter<AdapterDashboardSummarySalesByOutlet.ViewHolder>{
    Tbl_List_Motoris listMotoris;
    private Context context;

    public AdapterDashboardSummarySalesByOutlet(Context context, Tbl_List_Motoris listMotoris, OnDownloadClicked listener) {
        this.context = context;
        this.listMotoris = listMotoris;
        this.listener = listener;
        if (listMotoris == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_dashboard_summarysales_byoutlet, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_List_Motoris.Trx dat = listMotoris.trx.get(position);
        holder.txtOutletName.setText(dat.CUSTNAME);
        holder.txtAddress.setText("-");
        if (dat.CUSTADD1 != null){
            if (!dat.CUSTADD1.toUpperCase().equals("NULL")){
                holder.txtAddress.setText(dat.CUSTADD1);
            }
        }
        holder.txtSKU.setText(Integer.toString(dat.SKU));
        holder.txtInvAmount.setText(dat.ORDERNO);

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, ListDashboardProductActivity.class);
                mIntent.putExtra("CUSTNO", dat.CUSTNO);
                mIntent.putExtra("CUSTNAME", dat.CUSTNAME);
                mIntent.putExtra("ADDRESS", dat.CUSTADD1);
                mIntent.putExtra("ORDERNO", dat.ORDERNO);
                mIntent.putExtra("TANGGAL", dat.ORDERDATE);
                mIntent.putExtra("PHOTO", dat.PHOTO);
                mIntent.putExtra("REMARK", dat.REMARK);
                ListDashboardProductActivity.listTrx = new Tbl_List_Motoris();
                ListDashboardProductActivity.listTrx.trx_d = new ArrayList<Tbl_List_Motoris.TrxD>();
                for (Tbl_List_Motoris.TrxD trx : listMotoris.trx_d){
                    if (trx.SLSNO != null && trx.ORDERNO.equals(dat.ORDERNO)){
                        if ( trx.SLSNO.toLowerCase(Locale.getDefault()).contains(dat.SLSNO.toLowerCase()))
                            ListDashboardProductActivity.listTrx.trx_d.add(trx);
                    }
                }

                context.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMotoris.trx.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtOutletName,
                txtAddress,
                txtInvAmount,
                txtSKU
                        ;


        LinearLayout layout_row;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardSummarySalesByOutlet mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtOutletName = (TextView)itemView.findViewById(R.id.txtOutletName);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtInvAmount = (TextView)itemView.findViewById(R.id.txtInvAmount);
            txtSKU = (TextView)itemView.findViewById(R.id.txtSKU);
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
