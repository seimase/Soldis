package com.soldis.yourthaitea.dashboard.summary_sales.adapter;

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

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.summary_sales.DashboardSummarySalesByOutlet;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardSummarySalesBySalesman extends  RecyclerView.Adapter<AdapterDashboardSummarySalesBySalesman.ViewHolder>{
    Tbl_List_Motoris listMotoris;
    private Context context;
    boolean bDays;

    public AdapterDashboardSummarySalesBySalesman(Context context, Tbl_List_Motoris listMotoris, boolean bDays, OnDownloadClicked listener) {
        this.context = context;
        this.listMotoris = listMotoris;
        this.listener = listener;
        this.bDays = bDays;
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
                R.layout.row_dashboard_list_motoris, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tbl_List_Motoris.Datum dat = listMotoris.data.get(position);

        holder.txtName.setText(dat.SLSNAME);
        double iStatus = 0;
        try{

            if (bDays){
                if (dat.TARGET_SALES > 0){
                    iStatus = (dat.TOTAL_SALES / dat.TARGET_SALES) * 100;
                    if (iStatus > 100){
                        holder.txtStatus.setText("(100%)");
                        holder.progress.setProgress(100);
                    } else{
                        holder.txtStatus.setText("(" + String.format("%.2f",iStatus) + "%)");
                        holder.progress.setProgress((int)iStatus);
                    }
                }else{
                    holder.txtStatus.setText("(0%)");
                    holder.progress.setProgress(0);
                }
            }else{
                if (dat.TARGET_SALES_MTD > 0){
                    iStatus = (dat.TOTAL_SALES / dat.TARGET_SALES_MTD) * 100;
                    if (iStatus > 100){
                        holder.txtStatus.setText("(100%)");
                        holder.progress.setProgress(100);
                    } else{
                        holder.txtStatus.setText("(" + String.format("%.2f",iStatus) + "%)");
                        holder.progress.setProgress((int)iStatus);
                    }
                }else{
                    holder.txtStatus.setText("(0%)");
                    holder.progress.setProgress(0);
                }
            }


        }catch (Exception e){
            holder.txtStatus.setText("(0%)");
            holder.progress.setProgress(0);
        }

        if (dat.SALES_TYPE != null){
            holder.txtType.setText("REGULER");
            if (dat.SALES_TYPE.equals("11")) holder.txtType.setText("AFH UO");
        }

        if (dat.PHOTO != null && !dat.PHOTO.equals("")){
            AppController.getInstance().displayImage(context , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, holder.imgProfile);
        }else{
            holder.imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar));
        }

        holder.imgStatus.setVisibility(View.GONE);
        if (bDays){
            holder.imgStatus.setVisibility(View.VISIBLE);
        }
        holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_red));

        for (Tbl_List_Motoris.Kehadiran kehadiran : listMotoris.kehadiran){
            if(kehadiran.SLSNO.toLowerCase(Locale.getDefault()).contains(dat.SLSNO.toLowerCase())){
                holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_yellow));
            }
        }

        if (dat.TOTAL_EC > 0 )holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_green));

        holder.txtTotal.setText(AppController.getInstance().toCurrency(dat.TOTAL_SALES));

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, DashboardSummarySalesByOutlet.class);
                DashboardSummarySalesByOutlet.tblListMotoris = dat;
                DashboardSummarySalesByOutlet.bDays = bDays;
                DashboardSummarySalesByOutlet.listTrx = new Tbl_List_Motoris();
                DashboardSummarySalesByOutlet.listTrx.trx = new ArrayList<Tbl_List_Motoris.Trx>();
                DashboardSummarySalesByOutlet.listTrx.trx_d = new ArrayList<Tbl_List_Motoris.TrxD>();

                if (listMotoris.trx_d != null && listMotoris.trx != null){
                    for (Tbl_List_Motoris.TrxD trdD : listMotoris.trx_d){
                        if (trdD.SLSNO.equals(dat.SLSNO)){
                            DashboardSummarySalesByOutlet.listTrx.trx_d.add(trdD);
                        }
                    }


                    for (Tbl_List_Motoris.Trx trx : listMotoris.trx){
                        if ( trx.SLSNO != null){
                            if ( trx.SLSNO.toLowerCase(Locale.getDefault()).contains(dat.SLSNO.toLowerCase()))
                                DashboardSummarySalesByOutlet.listTrx.trx.add(trx);
                        }

                    }
                    context.startActivity(mIntent);
                }else{
                    AppController.getInstance().CustomeDialog(context,context.getResources().getString(R.string.text_data_not_found));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return listMotoris.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtName,
                txtStatus,
                txtTotal,
                txtType
        ;
        LinearLayout layout_row;
        ProgressBar progress;

        ImageView imgStatus, imgProfile;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardSummarySalesBySalesman mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtName = (TextView)itemView.findViewById(R.id.text_name);
            txtType = (TextView)itemView.findViewById(R.id.text_type);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTotal = (TextView)itemView.findViewById(R.id.text_total);
            progress = (ProgressBar)itemView.findViewById(R.id.progress);
            imgStatus = (ImageView)itemView.findViewById(R.id.image_status);
            imgProfile = (ImageView)itemView.findViewById(R.id.imgProfile);

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
