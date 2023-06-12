package com.soldis.yourthaitea.transaction.pembelian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_KAS;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterListPembelian extends  RecyclerView.Adapter<AdapterListPembelian.ViewHolder>{
    ArrayList <Obj_KAS> objKas;
    private Context context;

    public AdapterListPembelian(Context context, ArrayList <Obj_KAS> objKas, OnDownloadClicked listener) {
        this.context = context;
        this.objKas = objKas;
        this.listener = listener;
        if (objKas == null) {
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
        final Obj_KAS dat = objKas.get(position);
        holder.txtKet.setText((dat.getREMARK() == null ? "" : dat.getREMARK()));


        holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
        if (dat.getTYPE_KAS() !=null){
            if (dat.getTYPE_KAS().equals("Y")){
                holder.txtAmount.setText(AppController.getInstance().toCurrencyRp(dat.getAMOUNT()));
                holder.txtJenisKas.setText("Masuk");
                holder.txtAmount.setTextColor(context.getResources().getColor(R.color.black));
                holder.txtJenisKas.setTextColor(context.getResources().getColor(R.color.black));
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else{
                holder.txtJenisKas.setText("Keluar");
                holder.txtAmount.setText(AppController.getInstance().toCurrencyRp(dat.getAMOUNT() * -1));
                holder.txtAmount.setTextColor(context.getResources().getColor(R.color.red));
                holder.txtJenisKas.setTextColor(context.getResources().getColor(R.color.red));
                holder.layout_status.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getDOCNO(),dat.getREMARK());
            }
        });

    }

    @Override
    public int getItemCount() {
        return objKas.size();
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
                          final AdapterListPembelian mCourseAdapter) {
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
