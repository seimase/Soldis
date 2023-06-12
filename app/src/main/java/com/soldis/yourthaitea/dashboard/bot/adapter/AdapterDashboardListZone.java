package com.soldis.yourthaitea.dashboard.bot.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.asm.Dashboard_ASM_Activity;
import com.soldis.yourthaitea.model.Tbl_ListZone;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardListZone extends  RecyclerView.Adapter<AdapterDashboardListZone.ViewHolder>{
    Tbl_ListZone listZone;
    private Context context;

    public AdapterDashboardListZone(Context context, Tbl_ListZone listZone, OnDownloadClicked listener) {
        this.context = context;
        this.listZone = listZone;
        this.listener = listener;
        if (listZone == null) {
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
                R.layout.row_zone, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_ListZone.Datum dat = listZone.data.get(position);
        final Tbl_ListZone.Target datTarget = listZone.target.get(0);
        holder.txtZoneId.setText((dat.ZONEID == null ? "" : dat.ZONEID));
        holder.txtTotalEC.setText(AppController.getInstance().toCurrency(dat.TOTAL_EC_REGULAR));
        holder.txtTotalECAFH.setText(AppController.getInstance().toCurrency(dat.TOTAL_EC_AFH));
        holder.txtECTarget.setText(AppController.getInstance().toCurrency(datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_REGULAR) );
        holder.txtECTargetAFH.setText(AppController.getInstance().toCurrency(datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_AFH) );
        holder.txtCallTarget.setText(AppController.getInstance().toCurrency(datTarget.TARGET_CALL_MTD * dat.TOTAL_MOTORIS_REGULAR) );
        holder.txtCallTargetAFH.setText(AppController.getInstance().toCurrency(datTarget.TARGET_CALL_MTD  * dat.TOTAL_MOTORIS_AFH) );
        holder.txtSalesTarget.setText(AppController.getInstance().toCurrency(datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_REGULAR) );
        holder.txtSalesTargetAFH.setText(AppController.getInstance().toCurrency(datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_AFH) );

        holder.txtTotalCall.setText(AppController.getInstance().toCurrency(dat.TOTAL_CALL_REGULAR));
        holder.txtTotalSales.setText(AppController.getInstance().toCurrency(dat.TOTAL_SALES_REGULAR));
        holder.txtTotalCallAFH.setText(AppController.getInstance().toCurrency(dat.TOTAL_CALL_AFH));
        holder.txtTotalSalesAFH.setText(AppController.getInstance().toCurrency(dat.TOTAL_SALES_AFH));

        holder.txtTotalMotoris.setText(AppController.getInstance().toCurrency(dat.TOTAL_MOTORIS_REGULAR));
        holder.txtTotalMotorisAFH.setText(AppController.getInstance().toCurrency(dat.TOTAL_MOTORIS_AFH));
        holder.txtHadir.setText(AppController.getInstance().toCurrency(dat.KEHADIRAN_REGULAR));
        holder.txtHadirAFH.setText(AppController.getInstance().toCurrency(dat.KEHADIRAN_AFH));

        holder.txtTotalRo.setText(AppController.getInstance().toCurrency(dat.TOTAL_RO));

        double lStatus = 0;
        if (dat.BROW){
            holder.layout_row.setVisibility(View.VISIBLE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
        }else{
            holder.layout_row.setVisibility(View.GONE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
        }

        if (dat.TOTAL_MOTORIS_AFH > 0){
            holder.layout_mtr.setVisibility(View.VISIBLE);
            holder.layout_ec.setVisibility(View.VISIBLE);
            holder.layout_call.setVisibility(View.VISIBLE);
            holder.layout_sales.setVisibility(View.VISIBLE);
            holder.txtAFH.setVisibility(View.VISIBLE);
            holder.vwLabel.setVisibility(View.VISIBLE);
            holder.txtMTR.setText("SLS");
            holder.txtRegular.setText("REGULER");
        }else {
            holder.layout_mtr.setVisibility(View.GONE);
            holder.layout_ec.setVisibility(View.GONE);
            holder.layout_call.setVisibility(View.GONE);
            holder.layout_sales.setVisibility(View.GONE);
            holder.txtAFH.setVisibility(View.GONE);
            holder.vwLabel.setVisibility(View.GONE);
            holder.txtMTR.setText("SALESMAN");
            holder.txtRegular.setText("REGULER");
        }

        try{
            if (dat.TOTAL_MOTORIS_REGULAR > 0){
                lStatus = (dat.KEHADIRAN_REGULAR / dat.TOTAL_MOTORIS_REGULAR) * 100;
                holder.progressHadir.setProgress((int)lStatus);
            }

            if (dat.TOTAL_MOTORIS_AFH > 0){
                lStatus = (dat.KEHADIRAN_AFH / dat.TOTAL_MOTORIS_AFH) * 100;
                holder.progressHadirAFH.setProgress((int)lStatus);
            }

        }catch (Exception e){
            holder.progressHadir.setProgress(0);
            holder.progressHadirAFH.setProgress(0);
        }

        //Call
        try{
            if ((datTarget.TARGET_CALL_MTD * dat.TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (dat.TOTAL_CALL_REGULAR / (datTarget.TARGET_CALL_MTD * dat.TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    holder.progressCall.setProgress(100);
                } else{
                    holder.progressCall.setProgress((int)lStatus);
                }
            }else{
                holder.progressCall.setProgress(0);

            }

        }catch (Exception e){
            holder.progressCall.setProgress(0);

        }

        if ((datTarget.TARGET_CALL_MTD * dat.TOTAL_MOTORIS_AFH) > 0){
            lStatus = 0;
            lStatus = (dat.TOTAL_CALL_AFH / (datTarget.TARGET_CALL_MTD * dat.TOTAL_MOTORIS_AFH)) * 100;
            if (lStatus > 100){
                holder.progressCallAFH.setProgress(100);
            } else{
                holder.progressCallAFH.setProgress((int)lStatus);
            }
        }else{
            holder.progressCallAFH.setProgress(0);
        }

        try{
            if ((datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (dat.TOTAL_EC_REGULAR / (datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    holder.progressEC.setProgress(100);
                } else{
                    holder.progressEC.setProgress((int)lStatus);
                }

            }else{
                holder.progressEC.setProgress(0);

            }

        }catch (Exception e){
            holder.progressEC.setProgress(0);
        }

        if ((datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_AFH) > 0){
            lStatus = 0;
            lStatus = (dat.TOTAL_EC_AFH / (datTarget.TARGET_EC_MTD * dat.TOTAL_MOTORIS_AFH)) * 100;
            if (lStatus > 100){
                holder.progressECAFH.setProgress(100);
            } else{
                holder.progressECAFH.setProgress((int)lStatus);
            }
        }else{
            holder.progressECAFH.setProgress(0);
        }

        try{
            if ((datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_REGULAR) > 0){
                lStatus = (dat.TOTAL_SALES_REGULAR / (datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_REGULAR)) * 100;
                if (lStatus > 100){
                    holder.progressSales.setProgress(100);
                } else{
                    holder.progressSales.setProgress((int)lStatus);
                }


            }else{
                holder.progressSales.setProgress(0);
            }


            if ((datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_AFH) > 0){
                lStatus = (dat.TOTAL_SALES_AFH / (datTarget.TARGET_SALES_MTD * dat.TOTAL_MOTORIS_AFH)) * 100;
                if (lStatus > 100){
                    holder.progressSalesAFH.setProgress(100);
                } else{
                    holder.progressSalesAFH.setProgress((int)lStatus);
                }
            }else{
                holder.progressSalesAFH.setProgress(0);
            }
        }catch (Exception e){
            holder.progressSales.setProgress(0);
            holder.progressSalesAFH.setProgress(0);
        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dat.BROW){
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
                    //holder.layout_row.setVisibility(View.GONE);
                    holder.layout_row.setVisibility(View.GONE);
                    dat.BROW = false;
                } else{
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
                    //holder.layout_row.setVisibility(View.VISIBLE);
                    holder.layout_row.setVisibility(View.VISIBLE);
                    dat.BROW = true;
                }


            }
        });


        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, Dashboard_ASM_Activity.class);
                mIntent.putExtra("ZONEID", dat.ZONEID);
                context.startActivity(mIntent);
            }
        });

        holder.layout_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dat.BROW){
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
                    holder.layout_row.setVisibility(View.GONE);
                    dat.BROW = false;
                } else{
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
                    holder.layout_row.setVisibility(View.VISIBLE);
                    dat.BROW = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listZone.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtZoneId
                ;

        TextView txtDepo,
                txtTotalEC,
                txtECTarget,
                txtCallTarget,
                txtTotalCall,
                txtSalesTarget,
                txtTotalSales,
                txtTotalECAFH,
                txtECTargetAFH,
                txtCallTargetAFH,
                txtTotalCallAFH,
                txtSalesTargetAFH,
                txtTotalSalesAFH,
                txtTotalMotoris,
                txtTotalMotorisAFH,
                txtHadir,
                txtHadirAFH,
                txtTotalRo
                        ;

        ProgressBar progressEC,
                progressCall,
                progressSales,
                progressECAFH,
                progressCallAFH,
                progressSalesAFH,
                progressHadir,
                progressHadirAFH
                        ;

        ImageView imgArrow;
        LinearLayout layout_mtr, layout_ec, layout_call, layout_sales;
        View vwLabel;
        LinearLayout layout_row, layout_zone;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;

        TextView txtAFH, txtRegular,
                txtMTR
                        ;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardListZone mCourseAdapter) {
            super(itemView);
            layout_mtr = (LinearLayout)itemView.findViewById(R.id.layout_mtr);
            layout_ec = (LinearLayout)itemView.findViewById(R.id.layout_ec);
            layout_call = (LinearLayout)itemView.findViewById(R.id.layout_call);
            layout_sales = (LinearLayout)itemView.findViewById(R.id.layout_sales);

            vwLabel = (View)itemView.findViewById(R.id.vwLabel);
            txtRegular = (TextView) itemView.findViewById(R.id.txtRegular);
            txtAFH = (TextView) itemView.findViewById(R.id.txtAFH);
            txtMTR = (TextView) itemView.findViewById(R.id.txtMTR);

            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            layout_zone = (LinearLayout)itemView.findViewById(R.id.layout_zone);
            txtZoneId = (TextView)itemView.findViewById(R.id.txtZoneId);

            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtDepo = (TextView)itemView.findViewById(R.id.txtDepo);
            txtTotalEC = (TextView)itemView.findViewById(R.id.txtTotalEC);
            txtECTarget = (TextView)itemView.findViewById(R.id.txtECTarget);
            txtCallTarget = (TextView)itemView.findViewById(R.id.txtCallTarget);
            txtSalesTarget = (TextView)itemView.findViewById(R.id.txtSalesTarget);
            txtTotalECAFH = (TextView)itemView.findViewById(R.id.txtTotalECAFH);
            txtECTargetAFH = (TextView)itemView.findViewById(R.id.txtECTargetAFH);
            txtCallTargetAFH = (TextView)itemView.findViewById(R.id.txtCallTargetAFH);
            txtSalesTargetAFH = (TextView)itemView.findViewById(R.id.txtSalesTargetAFH);

            txtTotalCall = (TextView)itemView.findViewById(R.id.txtTotalCall);
            txtTotalSales = (TextView)itemView.findViewById(R.id.txtTotalSales);
            txtTotalCallAFH = (TextView)itemView.findViewById(R.id.txtTotalCallAFH);
            txtTotalSalesAFH = (TextView)itemView.findViewById(R.id.txtTotalSalesAFH);

            txtTotalMotoris = (TextView)itemView.findViewById(R.id.txtTotalMotoris);
            txtTotalMotorisAFH = (TextView)itemView.findViewById(R.id.txtTotalMotorisAFH);
            txtHadir = (TextView)itemView.findViewById(R.id.txtHadir);
            txtHadirAFH = (TextView)itemView.findViewById(R.id.txtHadirAFH);

            txtTotalRo = (TextView)itemView.findViewById(R.id.txtTotalRo);
            progressEC = (ProgressBar)itemView.findViewById(R.id.progressEC);
            progressCall = (ProgressBar)itemView.findViewById(R.id.progressCall);
            progressSales = (ProgressBar)itemView.findViewById(R.id.progressSales);
            progressECAFH = (ProgressBar)itemView.findViewById(R.id.progressECAFH);
            progressCallAFH = (ProgressBar)itemView.findViewById(R.id.progressCallAFH);
            progressSalesAFH = (ProgressBar)itemView.findViewById(R.id.progressSalesAFH);
            progressHadir = (ProgressBar)itemView.findViewById(R.id.progressHadir);
            progressHadirAFH = (ProgressBar)itemView.findViewById(R.id.progressHadirAFH);

            imgArrow = (ImageView)itemView.findViewById(R.id.img_arrow);
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
