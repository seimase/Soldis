package com.soldis.yourthaitea.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardSummaryLeaderXLS extends  RecyclerView.Adapter<AdapterDashboardSummaryLeaderXLS.ViewHolder>{
    Tbl_List_Sales_Motoris tblListSalesMotoris;
    private Context context;

    public AdapterDashboardSummaryLeaderXLS(Context context, Tbl_List_Sales_Motoris tblListSalesMotoris, OnDownloadClicked listener) {
        this.context = context;
        this.tblListSalesMotoris = tblListSalesMotoris;
        this.listener = listener;
        if (tblListSalesMotoris == null) {
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
                R.layout.row_dashboard_summary_leader, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_List_Sales_Motoris.Trx dat = tblListSalesMotoris.trx.get(position);
        holder.txtPCodename.setText(dat.PCODENAME);
        holder.txtTotalToko.setText(AppController.getInstance().toCurrency(dat.TOTAL_TOKO));
        holder.txtTotalTokoMTD.setText(AppController.getInstance().toCurrency(dat.TOTAL_TOKO_MTD));
    }

    @Override
    public int getItemCount() {
        return tblListSalesMotoris.trx.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtPCodename,
                txtTotalToko,
                txtTotalTokoMTD
                        ;


        LinearLayout layout_row;
        View viewStatus;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardSummaryLeaderXLS mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtPCodename = (TextView)itemView.findViewById(R.id.txtPCode);
            txtTotalToko = (TextView)itemView.findViewById(R.id.txtTotalToko);
            txtTotalTokoMTD = (TextView)itemView.findViewById(R.id.txtTotalTokoMTD);
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
