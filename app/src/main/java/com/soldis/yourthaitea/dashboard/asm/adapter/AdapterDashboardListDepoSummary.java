package com.soldis.yourthaitea.dashboard.asm.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.DashboardLeaderSummaryXLSActivity;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardListDepoSummary extends  RecyclerView.Adapter<AdapterDashboardListDepoSummary.ViewHolder>{
    Tbl_ListDepo tblListDepo;
    Tbl_List_Sales_Motoris listSalesMotoris;
    private Context context;
    ProgressDialog progress;
    boolean bDays;
    String sDATE;
    public AdapterDashboardListDepoSummary(Context context, Tbl_ListDepo tblListDepo, OnDownloadClicked listener) {
        this.context = context;
        this.tblListDepo = tblListDepo;
        this.listener = listener;
        this.bDays = bDays;
        this.sDATE = sDATE;
        if (tblListDepo == null) {
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
                R.layout.row_dashboard_asm_summary, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tbl_ListDepo.Datum dat = tblListDepo.data.get(position);

        holder.txtDepo.setText(dat.NAMACABANG == null ? "" : dat.NAMACABANG);

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(context, context.getResources().getString(R.string.main_Information),
                        context.getResources().getString(R.string.setting_sync_data), true);

                try{
                    sDATE = AppController.getInstance().getDateYYYYMMDD();
                    Call<Tbl_List_Sales_Motoris> call;
                    call = NetworkManager.getNetworkService().ListSalesDepoMotoris(dat.KODECABANG,
                            AppConstant.strSlsNo,
                            sDATE);

                    call.enqueue(new Callback<Tbl_List_Sales_Motoris>() {
                        @Override
                        public void onResponse(Call<Tbl_List_Sales_Motoris> call, Response<Tbl_List_Sales_Motoris> response) {
                            progress.dismiss();
                            int code = response.code();
                            if (code == 200){
                                listSalesMotoris = response.body();
                                if (!listSalesMotoris.error){
                                    AppController.getInstance().getSessionManager().setListSalesMotoris(null);
                                    AppController.getInstance().getSessionManager().setListSalesMotoris(listSalesMotoris);
                                    Intent mIntent = new Intent(context, DashboardLeaderSummaryXLSActivity.class);
                                    mIntent.putExtra("NAMACABANG", dat.NAMACABANG);
                                    mIntent.putExtra("KODECABANG", dat.KODECABANG);
                                    context.startActivity(mIntent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Tbl_List_Sales_Motoris> call, Throwable t) {
                            progress.dismiss();
                        }
                    });
                }catch (Exception e){
                    progress.dismiss();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return tblListDepo.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDepo
        ;
        LinearLayout layout_row;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardListDepoSummary mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);

            txtDepo = (TextView)itemView.findViewById(R.id.txtDepo);
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
