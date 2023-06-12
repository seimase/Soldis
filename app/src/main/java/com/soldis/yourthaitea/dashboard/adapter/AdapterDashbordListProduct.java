package com.soldis.yourthaitea.dashboard.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashbordListProduct extends  RecyclerView.Adapter<AdapterDashbordListProduct.ViewHolder>  {
    Tbl_List_Motoris listMotoris;
    private Context context;

    public AdapterDashbordListProduct(Context context, Tbl_List_Motoris listMotoris,
                                      OnDownloadClicked listener) {
        this.context = context;
        this.listMotoris = listMotoris;
        this.listener = listener;
        if (listMotoris == null) {
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
                R.layout.row_dashboard_product, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Tbl_List_Motoris.TrxD dat = listMotoris.trx_d.get(holder.getAdapterPosition());

        holder.txtPCode.setText(dat.PCODE);
        holder.txtPCodeName.setText(dat.PCODENAME);
        holder.txtPrice.setText(AppController.getInstance().toCurrencyRp(dat.SELLPRICE1));

        if (dat.UNIT1 != null && !dat.UNIT1.equals("")){
            holder.txtUOM1.setText(dat.UNIT1);
            holder.lyUOM1.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM1.setVisibility(View.GONE);
        }
        if (dat.UNIT2 != null && !dat.UNIT2.equals("")){
            if (dat.UNIT1 != null && !dat.UNIT1.equals("")){
                if (dat.UNIT1.toUpperCase().equals(dat.UNIT2.toUpperCase())){
                    holder.lyUOM1.setVisibility(View.GONE);
                }
            }
            holder.txtUOM2.setText(dat.UNIT2);
            holder.lyUOM2.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM2.setVisibility(View.GONE);
        }
        if (dat.UNIT3 != null && !dat.UNIT3.equals("")){
            holder.txtUOM3.setText(dat.UNIT3);
            holder.lyUOM3.setVisibility(View.VISIBLE);
        }else {
            holder.lyUOM3.setVisibility(View.VISIBLE);
        }

        holder.edtUOM1.setText(Integer.toString(dat.QTY_B));
        holder.edtUOM2.setText(Integer.toString(dat.QTY_T));
        holder.edtUOM3.setText(Integer.toString(dat.QTY_K));

        if ((dat.QTY_B == 0) &&
                (dat.QTY_T == 0) &&
                (dat.QTY_K == 0)){
            holder.txtTotal.setText("Rp 0");
        }

        holder.lyUOM2.setVisibility(View.GONE);

        AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + "/" + dat.PHOTO_NAME, holder.imgProduct);

        holder.edtUOM1.setEnabled(false);
        holder.edtUOM2.setEnabled(false);
        holder.edtUOM3.setEnabled(false);
        holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dat.AMOUNT));

    }

    @Override
    public int getItemCount() {
        return listMotoris.trx_d.size();
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
                txtUOM1,
                txtUOM2,
                txtUOM3,
                txtPrice

        ;

        public TextView txtTotal;
        EditText edtUOM1,
                edtUOM2,
                edtUOM3
                        ;

        ImageView imgProduct;

        RelativeLayout layout_row;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashbordListProduct mCourseAdapter) {
            super(itemView);
            //layout_row = (RelativeLayout)itemView.findViewById(R.id.layout_row);
            lyUOM1 = (LinearLayout)itemView.findViewById(R.id.layout_uom1);
            lyUOM2 = (LinearLayout)itemView.findViewById(R.id.layout_uom2);
            lyUOM3 = (LinearLayout)itemView.findViewById(R.id.layout_uom3);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtUOM1 = (TextView)itemView.findViewById(R.id.text_uom1);
            txtUOM2 = (TextView)itemView.findViewById(R.id.text_uom2);
            txtUOM3 = (TextView)itemView.findViewById(R.id.text_uom3);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            txtTotal = (TextView)itemView.findViewById(R.id.text_total);
            edtUOM1 = (EditText)itemView.findViewById(R.id.edt_uom1);
            edtUOM2 = (EditText)itemView.findViewById(R.id.edt_uom2);
            edtUOM3 = (EditText)itemView.findViewById(R.id.edt_uom3);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }


    }

    void CustomeDialog(Context context, String ISI){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtIsi = (TextView)dialog.findViewById(R.id.text_isi);

        txtIsi.setText(ISI);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
