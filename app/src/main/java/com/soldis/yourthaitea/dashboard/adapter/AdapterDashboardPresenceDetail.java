package com.soldis.yourthaitea.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_Presence;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardPresenceDetail extends  RecyclerView.Adapter<AdapterDashboardPresenceDetail.ViewHolder>{
    Tbl_Presence tblPresence;
    private Context context;

    public AdapterDashboardPresenceDetail(Context context, Tbl_Presence tblPresence, OnDownloadClicked listener) {
        this.context = context;
        this.tblPresence = tblPresence;
        this.listener = listener;
        if (tblPresence == null) {
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
                R.layout.row_dashboard_list_presence_detail, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_Presence.DataD dat = tblPresence.data_d.get(position);
        holder.txtWktPergi.setText(dat.TMGO);
        holder.txtWktPulang.setText(dat.TMBCK);

        String DateGo = "", DateBack = "";
        DateGo = dat.TGLVISIT == null ? "" : dat.TGLVISIT;
        DateBack = dat.TGLBACK == null ? "" : dat.TGLBACK;

        holder.txtTglPergi.setText("");
        holder.txtTglPergi.setText("");
        if (!DateGo.equals("")){
            holder.txtTglPergi.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(DateGo));
        }

        if (!DateBack.equals("")){
            holder.txtTglPulang.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(DateBack));
        }

        if (position % 2 == 0){
            holder.layout_row.setBackgroundColor(context.getResources().getColor(R.color.teledokter_lightgreen));
        }else{
            holder.layout_row.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.txtNo.setText(Integer.toString(position + 1));

    }

    @Override
    public int getItemCount() {
        return tblPresence.data_d.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtWktPergi,
                txtWktPulang,
                txtTglPergi,
                txtTglPulang,
                txtNo
                        ;
        LinearLayout layout_row;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardPresenceDetail mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtWktPergi = (TextView) itemView.findViewById(R.id.txtWaktuPergi);
            txtWktPulang = (TextView) itemView.findViewById(R.id.txtWaktuPulang);
            txtTglPergi = (TextView) itemView.findViewById(R.id.txtTglPergi);
            txtTglPulang = (TextView) itemView.findViewById(R.id.txtTglPulang);
            txtNo = (TextView) itemView.findViewById(R.id.txtNo);
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
