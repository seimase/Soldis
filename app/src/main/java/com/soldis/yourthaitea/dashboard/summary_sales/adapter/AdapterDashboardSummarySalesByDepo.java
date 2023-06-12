package com.soldis.yourthaitea.dashboard.summary_sales.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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
import com.soldis.yourthaitea.dashboard.summary_sales.DashboardSummarySalesBySalesman;
import com.soldis.yourthaitea.model.Tbl_ListSummarySales_ByDepo;
import com.soldis.yourthaitea.model.Tbl_ListSummarySales_ByDepoTmp;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.widget.CircleIndicator;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardSummarySalesByDepo extends  RecyclerView.Adapter<AdapterDashboardSummarySalesByDepo.ViewHolder>{
    Tbl_ListSummarySales_ByDepo tblListTrxByDepo;
    Tbl_List_Motoris listMotoris;

    Tbl_ListSummarySales_ByDepoTmp tblListTrxByDepoTmp;
    ArrayList<Tbl_ListSummarySales_ByDepoTmp> tblListTrxByDepoTmps;

    private Context context;
    ProgressDialog progress;
    boolean bDays;
    String sDATE;
    boolean bDone;
    public AdapterDashboardSummarySalesByDepo(Context context, Tbl_ListSummarySales_ByDepo tblListTrxByDepo, boolean bDays, String sDATE, OnDownloadClicked listener) {
        this.context = context;
        this.tblListTrxByDepo = tblListTrxByDepo;
        this.listener = listener;
        this.bDays = bDays;
        this.sDATE = sDATE;
        if (tblListTrxByDepo == null) {
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
                R.layout.row_dashboard_summarysales_bydepo, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tbl_ListSummarySales_ByDepo.DataAll dat = tblListTrxByDepo.data_all.get(position);

        holder.txtDepo.setText(dat.NAMACABANG == null ? "" : dat.NAMACABANG);
        holder.txtTotalSalesman.setText(AppController.getInstance().toCurrency(dat.TOTAL_SALESMAN));
        holder.txtTotalHadir.setText(AppController.getInstance().toCurrency(dat.TOTAL_HADIR));

        double lStatus = 0;

        if (dat.BROW){
            holder.layout_row.setVisibility(View.VISIBLE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
        }else{
            holder.layout_row.setVisibility(View.GONE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
        }

        holder.layout_depo.setOnClickListener(new View.OnClickListener() {
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

                    bDone = false;
                    tblListTrxByDepoTmp = new Tbl_ListSummarySales_ByDepoTmp();
                    tblListTrxByDepoTmps = new ArrayList<>();

                    tblListTrxByDepoTmp = new Tbl_ListSummarySales_ByDepoTmp();
                    bDone = true;
                    tblListTrxByDepoTmp.TOTAL_SALESMAN = dat.TOTAL_SALESMAN;
                    tblListTrxByDepoTmp.TOTAL_HADIR = dat.TOTAL_HADIR;
                    tblListTrxByDepoTmp.DATA_TYPE = "ALL";
                    tblListTrxByDepoTmp.TARGET_CALL = dat.TARGET_CALL;
                    tblListTrxByDepoTmp.TARGET_EC = dat.TARGET_EC;
                    tblListTrxByDepoTmp.TOTAL_CALL = dat.TOTAL_CALL;
                    tblListTrxByDepoTmp.TOTAL_EC = dat.TOTAL_EC;
                    tblListTrxByDepoTmp.bDays = bDays;
                    tblListTrxByDepoTmp.KODECABANG = dat.KODECABANG;
                    tblListTrxByDepoTmp.NAMACABANG = dat.NAMACABANG;
                    tblListTrxByDepoTmp.TGL_TRX = tblListTrxByDepo.tgl_trx;

                    if (bDone) tblListTrxByDepoTmps.add(tblListTrxByDepoTmp);

                    bDone = false;

                    for(Tbl_ListSummarySales_ByDepo.DataOrg dat1 : tblListTrxByDepo.data_org){

                        if (dat1.KODECABANG.equals(dat.KODECABANG)){
                            tblListTrxByDepoTmp = new Tbl_ListSummarySales_ByDepoTmp();
                            bDone = true;
                            tblListTrxByDepoTmp.TOTAL_SALESMAN = dat1.TOTAL_SALESMAN;
                            tblListTrxByDepoTmp.TOTAL_HADIR = dat1.TOTAL_HADIR;
                            tblListTrxByDepoTmp.DATA_TYPE = "ORGANIZE";
                            tblListTrxByDepoTmp.TARGET_CALL = dat1.TARGET_CALL;
                            tblListTrxByDepoTmp.TARGET_EC = dat.TARGET_EC;
                            tblListTrxByDepoTmp.TOTAL_CALL = dat1.TOTAL_CALL;
                            tblListTrxByDepoTmp.TOTAL_EC = dat1.TOTAL_EC;
                            tblListTrxByDepoTmp.bDays = bDays;
                            tblListTrxByDepoTmp.KODECABANG = dat.KODECABANG;
                            tblListTrxByDepoTmp.NAMACABANG = dat.NAMACABANG;
                            tblListTrxByDepoTmp.TGL_TRX = tblListTrxByDepo.tgl_trx;
                        }
                    }

                    if (bDone) tblListTrxByDepoTmps.add(tblListTrxByDepoTmp);

                    holder.targetAdapter = new RowAdapterDashboardSummarySalesByDepo(context);
                    holder.targetAdapter.setData(tblListTrxByDepoTmps);
                    holder.pager.setAdapter(holder.targetAdapter);

                    holder.viewPagerIndicator.setViewPager(holder.pager, tblListTrxByDepoTmps.size());
                }


            }
        });



        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(context, context.getResources().getString(R.string.main_Information),
                        context.getResources().getString(R.string.setting_sync_data), true);

                try{
                    Call<Tbl_List_Motoris> call;
                    if (bDays){
                        call = NetworkManager.getNetworkService().ListDepoMotoris(dat.KODECABANG,
                                AppConstant.strSlsNo,
                                sDATE);
                    }else{
                        call = NetworkManager.getNetworkService().ListDepoMotorisMTD(dat.KODECABANG,
                                AppConstant.strSlsNo,
                                sDATE);
                    }

                    call.enqueue(new Callback<Tbl_List_Motoris>() {
                        @Override
                        public void onResponse(Call<Tbl_List_Motoris> call, Response<Tbl_List_Motoris> response) {
                            progress.dismiss();
                            int code = response.code();
                            if (code == 200){
                                listMotoris = response.body();
                                if (!listMotoris.error){
                                    AppController.getInstance().getSessionManager().setListMotoris(null);
                                    AppController.getInstance().getSessionManager().setListMotoris(listMotoris);
                                    AppController.getInstance().getSessionManager().putBooleanData(AppConstant.DATA_SALES_MOTORIS, bDays);

                                    Intent mIntent = new Intent(context, DashboardSummarySalesBySalesman.class);
                                    mIntent.putExtra("NAMACABANG", dat.NAMACABANG);
                                    mIntent.putExtra("KODECABANG", dat.KODECABANG);
                                    context.startActivity(mIntent);
                                }else{
                                    AppController.getInstance().CustomeDialog(context,context.getResources().getString(R.string.text_data_not_found));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Tbl_List_Motoris> call, Throwable t) {
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
        return tblListTrxByDepo.data_all.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDepo,
        txtTotalSalesman,
        txtTotalHadir
        ;

        LinearLayout layout_row, layout_depo;
        ImageView imgArrow;

        ViewPager pager;
        RowAdapterDashboardSummarySalesByDepo targetAdapter;
        CircleIndicator viewPagerIndicator;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardSummarySalesByDepo mCourseAdapter) {
            super(itemView);

            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            layout_depo = (LinearLayout)itemView.findViewById(R.id.layout_depo);
            txtDepo = (TextView)itemView.findViewById(R.id.txtDepo);
            txtTotalSalesman = (TextView)itemView.findViewById(R.id.txtTotalSalesman);
            txtTotalHadir = (TextView)itemView.findViewById(R.id.txtTotalHadir);

            viewPagerIndicator = (CircleIndicator) itemView.findViewById(R.id.indicator);

            imgArrow = (ImageView)itemView.findViewById(R.id.img_arrow);
            pager = (ViewPager)itemView.findViewById(R.id.pager);
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
