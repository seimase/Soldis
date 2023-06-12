package com.soldis.yourthaitea.stock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AdapterInputStock extends  RecyclerView.Adapter<AdapterInputStock.ViewHolder>{
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;
    boolean bStock;
    public AdapterInputStock(Context context, ArrayList <Obj_MASTER> ObjMaster,
                             boolean bStock,
                             OnDownloadClicked listener) {
        this.context = context;
        this.ObjMaster = ObjMaster;
        this.listener = listener;
        this.bStock = bStock;
        if (ObjMaster == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked();
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_input_stock, null);

        return new ViewHolder(itemView, context, this,  new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Obj_MASTER dat = ObjMaster.get(holder.getAdapterPosition());
        String sXQTY = "0";

        boolean b3UOM = false;
        holder.txtPCode.setText(dat.getPCode());
        holder.txtPCodeName.setText(dat.getPCodeName());
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.myCustomEditTextListener.updateHolder(holder);
        holder.txtQty.addTextChangedListener(holder.myCustomEditTextListener);

        String sPhotoName = "";
        if (dat.getPHOTO_NAME() != null) sPhotoName = AppConstant.PATH_PHOTO + "/" + dat.getPHOTO_NAME();

        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            AppController.getInstance().displayImage(context, sPhotoName, holder.imgProduct);
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

        //AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + "/" + dat.getPHOTO_NAME(), holder.imgProduct);
        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            holder.imgProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
        }
        //First Stock---------------------------------------------------------------------
        sXQTY = AppController.getInstance().convertQtyToString(
                (int)dat.getConvUnit3(),
                (int)dat.getConvUnit2(),
                dat.getSTOCK_MOTORIS());

        holder.txtQty.setText(Long.toString(dat.getSTOCK_MOTORIS()));
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getSTOCK_MOTORIS());

            }
        }

        //holder.edtUOM3.setText(Long.toString(dat.getSTOCK_MOTORIS()));
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
                txtPrice

                        ;

        EditText                 txtQty
                        ;

        ImageView imgProduct;

        LinearLayout layout_row;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;

        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEditTextListener2 myCustomEditTextListener2;
        public MyCustomEditTextListener3 myCustomEditTextListener3;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterInputStock mCourseAdapter,
                          MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            this.myCustomEditTextListener = myCustomEditTextListener;
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtBtnMin = (TextView)itemView.findViewById(R.id.txtBtnMin);
            txtBtnPlus = (TextView)itemView.findViewById(R.id.txtBtnPlus);
            txtQty = (EditText)itemView.findViewById(R.id.txtQty);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
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
            ObjMaster.get(position).setTOTAL(dPrice);

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
            ObjMaster.get(position).setTOTAL(dPrice);

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
            ObjMaster.get(position).setTOTAL(dPrice);

            listener.OnDownloadClicked();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
