package com.soldis.yourthaitea.transaction.maintenance.adapter;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterInputDispenser extends  RecyclerView.Adapter<AdapterInputDispenser.ViewHolder>  {
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;
    boolean bTRX;

    public AdapterInputDispenser(Context context, ArrayList <Obj_MASTER> ObjMaster,
                                 boolean bTRX,
                                 OnDownloadClicked listener) {
        this.context = context;
        this.ObjMaster = ObjMaster;
        this.listener = listener;
        this.bTRX = bTRX;
        if (ObjMaster == null) {
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
                R.layout.row_inputproduct, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Obj_MASTER dat = ObjMaster.get(position);

        holder.txtPCode.setText(dat.getPCode());
        holder.txtPCodeName.setText(dat.getPCodeName());

        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            holder.imgProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
        }


        holder.txtQty.setText(Long.toString(dat.getQTY_TRX()));

        holder.txtBtnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjMaster.get(position).getQTY_TRX() > 0){
                    ObjMaster.get(position).setQTY_TRX(ObjMaster.get(position).getQTY_TRX() - 1);
                    holder.txtQty.setText(Long.toString(ObjMaster.get(position).getQTY_TRX()));

                    listener.OnDownloadClicked();
                }

            }
        });

        holder.txtBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjMaster.get(position).setQTY_TRX(ObjMaster.get(position).getQTY_TRX() + 1);
                holder.txtQty.setText(Long.toString(ObjMaster.get(position).getQTY_TRX()));

                listener.OnDownloadClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ObjMaster.size();
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
                txtBtnMin,
                txtBtnPlus,
                txtQty
        ;

        public TextView txtTotal;

        ImageView imgProduct;

        RelativeLayout layout_row;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterInputDispenser mCourseAdapter) {
            super(itemView);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtBtnMin = (TextView)itemView.findViewById(R.id.txtBtnMin);
            txtBtnPlus = (TextView)itemView.findViewById(R.id.txtBtnPlus);
            txtQty = (TextView)itemView.findViewById(R.id.txtQty);

            txtTotal = (TextView)itemView.findViewById(R.id.text_total);
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

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int position) {
            this.position = position;
        }

        public void updateHolder(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ObjMaster.get(position).setINP_UOM1(charSequence.toString());

            int iQty_B = 0;
            int iQty_T = 0;
            int iQty_K = 0;

            if (!ObjMaster.get(position).getINP_UOM1().equals("")) iQty_B = Integer.parseInt(ObjMaster.get(position).getINP_UOM1());
            if (!ObjMaster.get(position).getINP_UOM2().equals("")) iQty_T = Integer.parseInt(ObjMaster.get(position).getINP_UOM2());
            if (!ObjMaster.get(position).getINP_UOM3().equals("")) iQty_K = Integer.parseInt(ObjMaster.get(position).getINP_UOM3());

            double dPrice = (iQty_B * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K * ObjMaster.get(position).getSellPrice1())
                    ;

            long iQtyTotal = (iQty_B * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K)
                    ;

            /*if (iQtyTotal > (ObjMaster.get(position).getSTOCK_MOTORIS() - ObjMaster.get(position).getQTY_PCS())){
                if (!holder.edtUOM1.getText().toString().trim().equals("")){
                    CustomeDialog(context, "Stock tidak cukup!");
                    ObjMaster.get(position).setINP_UOM1("");
                    holder.edtUOM1.setText("");
                }
                //CustomeDialog(context, "Stock tidak cukup!");
            }else{
                ObjMaster.get(position).setTOTAL(dPrice);
                holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
                listener.OnDownloadClicked();
            }*/

            ObjMaster.get(position).setTOTAL(dPrice);
            holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
            listener.OnDownloadClicked();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEditTextListener2 implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int position) {
            this.position = position;
        }

        public void updateHolder(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ObjMaster.get(position).setINP_UOM2(charSequence.toString());
            boolean bDone = false;
            int iQty_B = 0;
            int iQty_T = 0;
            int iQty_K = 0;

            if (!ObjMaster.get(position).getINP_UOM1().equals("")) iQty_B = Integer.parseInt(ObjMaster.get(position).getINP_UOM1());
            if (!ObjMaster.get(position).getINP_UOM2().equals("")) iQty_T = Integer.parseInt(ObjMaster.get(position).getINP_UOM2());
            if (!ObjMaster.get(position).getINP_UOM3().equals("")) iQty_K = Integer.parseInt(ObjMaster.get(position).getINP_UOM3());

            double dPrice = (iQty_B * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K * ObjMaster.get(position).getSellPrice1())
                    ;

            long iQtyTotal = (iQty_B * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K)
                    ;

            /*if (iQtyTotal > (ObjMaster.get(position).getSTOCK_MOTORIS() - ObjMaster.get(position).getQTY_PCS())){
                if (!holder.edtUOM2.getText().toString().trim().equals("")){
                    CustomeDialog(context, "Stock tidak cukup!");
                    ObjMaster.get(position).setINP_UOM2("");
                    holder.edtUOM2.setText("");
                }
                //CustomeDialog(context, "Stock tidak cukup!");
            }else{
                ObjMaster.get(position).setTOTAL(dPrice);
                holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
                listener.OnDownloadClicked();
            }*/

            ObjMaster.get(position).setTOTAL(dPrice);
            holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
            listener.OnDownloadClicked();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEditTextListener3 implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int position) {
            this.position = position;
        }

        public void updateHolder(ViewHolder holder) {
            this.holder = holder;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ObjMaster.get(position).setINP_UOM3(charSequence.toString());

            int iQty_B = 0;
            int iQty_T = 0;
            int iQty_K = 0;

            if (!ObjMaster.get(position).getINP_UOM1().equals("")) iQty_B = Integer.parseInt(ObjMaster.get(position).getINP_UOM1());
            if (!ObjMaster.get(position).getINP_UOM2().equals("")) iQty_T = Integer.parseInt(ObjMaster.get(position).getINP_UOM2());
            if (!ObjMaster.get(position).getINP_UOM3().equals("")) iQty_K = Integer.parseInt(ObjMaster.get(position).getINP_UOM3());

            double dPrice = (iQty_B * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getSellPrice1() * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K * ObjMaster.get(position).getSellPrice1())
                    ;

            long iQtyTotal = (iQty_B * ObjMaster.get(position).getConvUnit2() * ObjMaster.get(position).getConvUnit3()) +
                    (iQty_T * ObjMaster.get(position).getConvUnit2()) +
                    (iQty_K)
                    ;

            /*if (iQtyTotal > (ObjMaster.get(position).getSTOCK_MOTORIS() - ObjMaster.get(position).getQTY_PCS())){
                if (!holder.edtUOM3.getText().toString().trim().equals("")){
                    CustomeDialog(context, "Stock tidak cukup!");
                    ObjMaster.get(position).setINP_UOM3("");
                    holder.edtUOM3.setText("");
                }
                //CustomeDialog(context, "Stock tidak cukup!");
            }else{
                ObjMaster.get(position).setTOTAL(dPrice);
                holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
                listener.OnDownloadClicked();
            }*/


            ObjMaster.get(position).setTOTAL(dPrice);
            holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
            listener.OnDownloadClicked();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
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
