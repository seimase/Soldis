package com.soldis.yourthaitea.dashboard.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.soldis.yourthaitea.entity.Obj_MASTER;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterViewProduct extends  RecyclerView.Adapter<AdapterViewProduct.ViewHolder>  {
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;
    boolean bTRX;

    public AdapterViewProduct(Context context, ArrayList <Obj_MASTER> ObjMaster,
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
                R.layout.row_view_product, null);

        return new ViewHolder(itemView, context, this, new MyCustomEditTextListener(),
                new MyCustomEditTextListener2(),
                new MyCustomEditTextListener3());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Obj_MASTER dat = ObjMaster.get(holder.getAdapterPosition());


        boolean b3UOM = false;
        holder.txtPCode.setText(dat.getPCode());
        holder.txtPCodeName.setText(dat.getPCodeName());
        holder.txtPrice.setText(AppController.getInstance().toCurrencyRp(dat.getSellPrice1()));

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.myCustomEditTextListener.updateHolder(holder);
        holder.myCustomEditTextListener2.updatePosition(holder.getAdapterPosition());
        holder.myCustomEditTextListener2.updateHolder(holder);
        holder.myCustomEditTextListener3.updatePosition(holder.getAdapterPosition());
        holder.myCustomEditTextListener3.updateHolder(holder);

        if (dat.getUnit1() != null && !dat.getUnit1().equals("")){
            holder.txtUOM1.setText(dat.getUnit1());
            holder.txtLabelUOM1.setText(dat.getUnit1());
            holder.lyUOM1.setVisibility(View.VISIBLE);
            holder.lySTOCKUOM1.setVisibility(View.VISIBLE);
        }else{
            holder.lySTOCKUOM1.setVisibility(View.GONE);
            holder.lyUOM1.setVisibility(View.GONE);
        }
        if (dat.getUnit2() != null && !dat.getUnit2().equals("")){
            b3UOM = true;
            if (dat.getUnit1() != null && !dat.getUnit1().equals("")){
                if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                    holder.lyUOM1.setVisibility(View.GONE);
                    holder.lySTOCKUOM1.setVisibility(View.GONE);
                    b3UOM = false;
                }
            }
            holder.txtUOM2.setText(dat.getUnit2());
            holder.txtLabelUOM2.setText(dat.getUnit2());
            holder.lyUOM2.setVisibility(View.VISIBLE);
            holder.lySTOCKUOM2.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM2.setVisibility(View.GONE);
            holder.lySTOCKUOM1.setVisibility(View.GONE);
        }
        if (dat.getUnit3() != null && !dat.getUnit3().equals("")){
            holder.txtUOM3.setText(dat.getUnit3());
            holder.txtLabelUOM3.setText(dat.getUnit3());
            holder.lyUOM3.setVisibility(View.VISIBLE);
            holder.lySTOCKUOM3.setVisibility(View.VISIBLE);
        }else {
            holder.lyUOM3.setVisibility(View.GONE);
            holder.lySTOCKUOM3.setVisibility(View.VISIBLE);
        }

        if (b3UOM){
            holder.lyUOM3.setVisibility(View.GONE);
        }

        holder.edtUOM1.setText(dat.getINP_UOM1());
        holder.edtUOM2.setText(dat.getINP_UOM2());
        holder.edtUOM3.setText(dat.getINP_UOM3());

        if (dat.getUnit3() != null && !dat.getUnit3().equals("")){
            if (dat.getUnit3().equals(dat.getUnit2()) && dat.getUnit3().equals(dat.getUnit1())){
                Log.w("UOM", "3");
                holder.lyUOM1.setVisibility(View.GONE);
                holder.lyUOM2.setVisibility(View.GONE);
            }
        }

        if ((dat.getINP_UOM1().equals("") || dat.getINP_UOM1().equals("0")) &&
                (dat.getINP_UOM2().equals("") || dat.getINP_UOM2().equals("0")) &&
                (dat.getINP_UOM3().equals("") || dat.getINP_UOM3().equals("0"))){
            holder.txtTotal.setText("Rp 0");
        }

        //AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + "/" + dat.getPHOTO_NAME(), holder.imgProduct);
        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            holder.imgProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
        }


        if (bTRX){
            holder.edtUOM1.setEnabled(false);
            holder.edtUOM2.setEnabled(false);
            holder.edtUOM3.setEnabled(false);

        }else{
            holder.edtUOM1.setEnabled(true);
            holder.edtUOM2.setEnabled(true);
            holder.edtUOM3.setEnabled(true);
            holder.edtUOM1.addTextChangedListener(holder.myCustomEditTextListener);
            holder.edtUOM2.addTextChangedListener(holder.myCustomEditTextListener2);
            holder.edtUOM3.addTextChangedListener(holder.myCustomEditTextListener3);

            holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dat.getTOTAL()));
        }


        //Sisa Stock---------------------------------------------------------------------
        String sXQTY_Sisa = AppController.getInstance().convertQtyToString(
                (int)dat.getConvUnit3(),
                (int)dat.getConvUnit2(),
                dat.getSTOCK_MOTORIS() - dat.getQTY_PCS());
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY_Sisa = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getSTOCK_MOTORIS() - dat.getQTY_PCS());
            }
        }

        String[] lstQtySisa = sXQTY_Sisa.split("\\/");
        holder.txtSTOCKUOM1.setText(lstQtySisa[0].toString());
        holder.txtSTOCKUOM2.setText(lstQtySisa[1].toString());
        holder.txtSTOCKUOM3.setText(lstQtySisa[2].toString());

        //QTY TRX---------------------------------------------------------------------
        String sXQTY = AppController.getInstance().convertQtyToString(
                (int)dat.getConvUnit3(),
                (int)dat.getConvUnit2(),
                dat.getQTY_TRX());
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getQTY_TRX());
            }
        }

        if (!sXQTY.equals("0/0/0")){
            String[] lstQty = sXQTY.split("\\/");
            holder.edtUOM1.setText(lstQty[0].toString());
            holder.edtUOM2.setText(lstQty[1].toString());
            holder.edtUOM3.setText(lstQty[2].toString());

            int iQty_B = 0;
            int iQty_T = 0;
            int iQty_K = 0;

            if(bTRX){
                try{
                    iQty_B = Integer.parseInt(lstQty[0].toString());
                    iQty_T = Integer.parseInt(lstQty[1].toString());
                    iQty_K = Integer.parseInt(lstQty[2].toString());
                    double dPrice = (iQty_B * dat.getSellPrice1() * dat.getConvUnit2() * dat.getConvUnit3()) +
                            (iQty_T * dat.getSellPrice1() * dat.getConvUnit2()) +
                            (iQty_K * dat.getSellPrice1())
                            ;

                    holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(dPrice));
                }catch (Exception e){
                    holder.txtTotal.setText(AppController.getInstance().toCurrencyRp(0));
                }
            }
        }

        if(bTRX){
            holder.edtUOM3.setText(Long.toString(dat.getQTY_TRX()));
        }

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
                txtUOM1,
                txtUOM2,
                txtUOM3,
                txtPrice,
                txtLabelUOM1,
                txtLabelUOM2,
                txtLabelUOM3,
                txtSTOCKUOM1,
                txtSTOCKUOM2,
                txtSTOCKUOM3

        ;

        public TextView txtTotal;
        EditText edtUOM1,
                edtUOM2,
                edtUOM3
                        ;

        ImageView imgProduct;

        RelativeLayout layout_row;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;
        LinearLayout lySTOCKUOM1, lySTOCKUOM2, lySTOCKUOM3;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEditTextListener2 myCustomEditTextListener2;
        public MyCustomEditTextListener3 myCustomEditTextListener3;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterViewProduct mCourseAdapter,
                          MyCustomEditTextListener myCustomEditTextListener,
                          MyCustomEditTextListener2 myCustomEditTextListener2,
                          MyCustomEditTextListener3 myCustomEditTextListener3) {
            super(itemView);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.myCustomEditTextListener2 = myCustomEditTextListener2;
            this.myCustomEditTextListener3 = myCustomEditTextListener3;
            //layout_row = (RelativeLayout)itemView.findViewById(R.id.layout_row);
            lyUOM1 = (LinearLayout)itemView.findViewById(R.id.layout_uom1);
            lyUOM2 = (LinearLayout)itemView.findViewById(R.id.layout_uom2);
            lyUOM3 = (LinearLayout)itemView.findViewById(R.id.layout_uom3);
            lySTOCKUOM1 = (LinearLayout)itemView.findViewById(R.id.layout_stockuom1);
            lySTOCKUOM2 = (LinearLayout)itemView.findViewById(R.id.layout_stockuom2);
            lySTOCKUOM3 = (LinearLayout)itemView.findViewById(R.id.layout_stockuom3);
            txtLabelUOM1 = (TextView)itemView.findViewById(R.id.txtLabelUOM1);
            txtLabelUOM2 = (TextView)itemView.findViewById(R.id.txtLabelUOM2);
            txtLabelUOM3 = (TextView)itemView.findViewById(R.id.txtLabelUOM3);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtUOM1 = (TextView)itemView.findViewById(R.id.text_uom1);
            txtUOM2 = (TextView)itemView.findViewById(R.id.text_uom2);
            txtUOM3 = (TextView)itemView.findViewById(R.id.text_uom3);
            txtSTOCKUOM1 = (TextView)itemView.findViewById(R.id.txtUOM1);
            txtSTOCKUOM2 = (TextView)itemView.findViewById(R.id.txtUOM2);
            txtSTOCKUOM3 = (TextView)itemView.findViewById(R.id.txtUOM3);
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
