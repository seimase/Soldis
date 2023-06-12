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
import android.widget.Toast;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.DashboardPresenceSummaryActivity;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_Presence;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardListPresenceSummary extends  RecyclerView.Adapter<AdapterDashboardListPresenceSummary.ViewHolder>{
    Tbl_ListDepo tblListDepo;
    Tbl_Presence tblPresence;
    private Context context;
    ProgressDialog progress;
    String sDATE;
    public AdapterDashboardListPresenceSummary(Context context, Tbl_ListDepo tblListDepo, OnDownloadClicked listener) {
        this.context = context;
        this.tblListDepo = tblListDepo;
        this.listener = listener;

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
                    Call<Tbl_Presence> call;
                    call = NetworkManager.getNetworkService().ListPresence(dat.KODECABANG,
                            sDATE);

                    call.enqueue(new Callback<Tbl_Presence>() {
                        @Override
                        public void onResponse(Call<Tbl_Presence> call, Response<Tbl_Presence> response) {
                            progress.dismiss();
                            int code = response.code();
                            if (code == 200){
                                tblPresence = response.body();
                                if (!tblPresence.error){
                                    AppController.getInstance().getSessionManager().setListPresence(null);
                                    AppController.getInstance().getSessionManager().setListPresence(tblPresence);
                                    Intent mIntent = new Intent(context, DashboardPresenceSummaryActivity.class);
                                    mIntent.putExtra("NAMACABANG", dat.NAMACABANG);
                                    mIntent.putExtra("KODECABANG", dat.KODECABANG);
                                    context.startActivity(mIntent);
                                }else{
                                    Toast.makeText(context,tblPresence.message, Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Tbl_Presence> call, Throwable t) {
                            Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
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
                          final AdapterDashboardListPresenceSummary mCourseAdapter) {
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
