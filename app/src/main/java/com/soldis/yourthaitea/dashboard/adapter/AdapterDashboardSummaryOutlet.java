package com.soldis.yourthaitea.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.ViewProductActivity;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardSummaryOutlet extends  RecyclerView.Adapter<AdapterDashboardSummaryOutlet.ViewHolder>{
    ArrayList<Obj_CUSTMST> objCustmsts;
    private Context context;

    public AdapterDashboardSummaryOutlet(Context context, ArrayList <Obj_CUSTMST> objCustmsts, OnDownloadClicked listener) {
        this.context = context;
        this.objCustmsts = objCustmsts;
        this.listener = listener;
        if (objCustmsts == null) {
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
                R.layout.row_dashboard_summary_outlet, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Obj_CUSTMST dat = objCustmsts.get(position);
        holder.txtOutletName.setText(dat.getORDERNO());

        holder.txtSKU.setText(Integer.toString(dat.getSKU()));
        holder.txtInvAmount.setText(AppController.getInstance().toCurrencyRp(dat.getINVAMOUNT()));
        if(dat.getFLAG_KIRIM().equals("Y")){
            holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.Green));
        }else{
            holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, ViewProductActivity.class);
                mIntent.putExtra("CUSTNO", dat.getCUSTNO());
                mIntent.putExtra("CUSTNAME", dat.getCUSTNAME());
                mIntent.putExtra("ADDRESS", dat.getCUSTADD1());
                mIntent.putExtra("ORDERNO", dat.getORDERNO());
                mIntent.putExtra("STATUS", true);
                context.startActivity(mIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return objCustmsts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtOutletName,
                txtInvAmount,
                txtSKU
                        ;


        LinearLayout layout_row;
        View viewStatus;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardSummaryOutlet mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtOutletName = (TextView)itemView.findViewById(R.id.txtOutletName);
            txtInvAmount = (TextView)itemView.findViewById(R.id.txtInvAmount);
            txtSKU = (TextView)itemView.findViewById(R.id.txtSKU);
            viewStatus = (View)itemView.findViewById(R.id.layout_status);
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
