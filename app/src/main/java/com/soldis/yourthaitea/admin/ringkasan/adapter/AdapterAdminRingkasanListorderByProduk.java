package com.soldis.yourthaitea.admin.ringkasan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
public class AdapterAdminRingkasanListorderByProduk extends  RecyclerView.Adapter<AdapterAdminRingkasanListorderByProduk.ViewHolder>  {
    Tbl_Ringkasan tblRingkasan;
    private Context context;
    boolean bTRX;

    public AdapterAdminRingkasanListorderByProduk(Context context, Tbl_Ringkasan tblRingkasan,
                                                  OnDownloadClicked listener) {
        this.context = context;
        this.tblRingkasan = tblRingkasan;
        this.listener = listener;
        this.bTRX = bTRX;
        if (tblRingkasan == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }


    public interface OnDownloadClicked {
        public void OnDownloadClicked();
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_listorder_byproduct, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        Tbl_Ringkasan.DataSale dat = tblRingkasan.data_sales.get(position);

        holder.txtPCodeName.setText(dat.PCODENAME);
        holder.txtPrice.setText(dat.QTY + " x " + AppController.getInstance().toCurrency(dat.SELLPRICE1));

        holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dat.AMOUNT));
    }

    @Override
    public int getItemCount() {
        return tblRingkasan.data_sales.size();
    }

    private TextWatcher watcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void afterTextChanged(Editable s) { }
    };

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener  {

        TextView txtPCode,
                txtPCodeName,
                txtPrice,
                txtTotal
        ;


        //ImageView imgProduct;

        LinearLayout layout_row;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterAdminRingkasanListorderByProduk mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);

            txtTotal = (TextView)itemView.findViewById(R.id.txtTotal);
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
