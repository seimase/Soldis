package com.soldis.yourthaitea.admin.ringkasan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_Ringkasan;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterAdminRingkasanListPembelian extends  RecyclerView.Adapter<AdapterAdminRingkasanListPembelian.ViewHolder>{
    Tbl_Ringkasan tblRingkasan;
    private Context context;

    public AdapterAdminRingkasanListPembelian(Context context, Tbl_Ringkasan tblRingkasan, OnDownloadClicked listener) {
        this.context = context;
        this.tblRingkasan = tblRingkasan;
        this.listener = listener;
        if (tblRingkasan == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String DOCNO, String KET);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_transaction_listpembelian, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Tbl_Ringkasan.DataKa dat = tblRingkasan.data_kas.get(position);
        holder.txtKet.setText((dat.REMARK == null ? "" : dat.REMARK));


        holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
        if (dat.TYPE_KAS !=null){
            if (dat.TYPE_KAS.equals("Y")){
                holder.txtAmount.setText(AppController.getInstance().toCurrencyRp(dat.AMOUNT));
                holder.txtJenisKas.setText("Masuk");
                holder.txtAmount.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtJenisKas.setTextColor(context.getResources().getColor(R.color.black));
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else{
                holder.txtJenisKas.setText("Keluar");
                holder.txtAmount.setText(AppController.getInstance().toCurrencyRp(dat.AMOUNT * -1));
                holder.txtAmount.setTextColor(context.getResources().getColor(R.color.red));
                holder.txtJenisKas.setTextColor(context.getResources().getColor(R.color.red));
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }
    }

    @Override
    public int getItemCount() {
        return tblRingkasan.data_kas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtKet,
                txtAmount,
                txtJenisKas
        ;

        LinearLayout layout_row;

        View layout_status;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterAdminRingkasanListPembelian mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtKet = (TextView)itemView.findViewById(R.id.txtKet);
            txtAmount = (TextView)itemView.findViewById(R.id.txtAmount);
            txtJenisKas = (TextView)itemView.findViewById(R.id.txtJenisKas);

            layout_status = (View)itemView.findViewById(R.id.layout_status);
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
