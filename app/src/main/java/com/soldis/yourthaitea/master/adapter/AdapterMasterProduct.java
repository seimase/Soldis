package com.soldis.yourthaitea.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterMasterProduct extends  RecyclerView.Adapter<AdapterMasterProduct.ViewHolder>{
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;

    public AdapterMasterProduct(Context context, ArrayList <Obj_MASTER> ObjMaster, OnDownloadClicked listener) {
        this.context = context;
        this.ObjMaster = ObjMaster;
        this.listener = listener;
        if (ObjMaster == null) {
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
                R.layout.row_master_product, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Obj_MASTER dat = ObjMaster.get(position);
        String sXQTY_Sisa = "0";
        holder.txtPCode.setText(dat.getPCode());
        holder.txtPCodeName.setText(dat.getPCodeName());

        if (dat.getUnit1() != null && !dat.getUnit1().equals("")){

            holder.txtLabelUOM1.setText(dat.getUnit1());
            holder.lyUOM1.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM1.setVisibility(View.GONE);
        }

        if (dat.getUnit2() != null && !dat.getUnit2().equals("")){
            if (dat.getUnit1() != null && !dat.getUnit1().equals("")){
                if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                    holder.lyUOM1.setVisibility(View.GONE);
                }
            }
            holder.txtLabelUOM2.setText(dat.getUnit2());
            holder.lyUOM2.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM2.setVisibility(View.GONE);
        }


        if (dat.getUnit3() != null && !dat.getUnit3().equals("")){
            holder.txtLabelUOM3.setText(dat.getUnit3());
            holder.lyUOM3.setVisibility(View.VISIBLE);
        }else {
            holder.lyUOM3.setVisibility(View.GONE);
        }


        holder.txtPrice.setText(AppController.getInstance().toCurrencyRp(dat.getSellPrice1()));
        holder.imgProduct.setBackgroundColor(context.getResources().getColor(R.color.transparant_pure));
        holder.imgProduct.setImageDrawable(null);
        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            //holder.imgProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.imgProduct.setBackgroundColor(color);
            /*if (position%2 ==0){
                holder.imgProduct.setBackgroundColor(context.getResources().getColor(R.color.b7_coklat));
            }else{
                holder.imgProduct.setBackgroundColor(context.getResources().getColor(R.color.darkorange));
            }*/

            String sInitials[] = dat.getPCodeName().split(" ");
            if (sInitials.length > 1){
                holder.icon_text.setText(sInitials[0].toString().toUpperCase().substring(0,1) +
                        sInitials[1].toString().toUpperCase().substring(0,1));
            }else{
                holder.icon_text.setText(sInitials[0].toString().toUpperCase().substring(0,1) );
            }
        }

        //Sisa Stock---------------------------------------------------------------------
        sXQTY_Sisa = AppController.getInstance().convertQtyToString(
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
        holder.txtUOM1.setText(lstQtySisa[0].toString());
        holder.txtUOM2.setText(lstQtySisa[1].toString());
        holder.txtUOM3.setText(lstQtySisa[2].toString());
    }

    @Override
    public int getItemCount() {
        return ObjMaster.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtPCode,
                txtPCodeName,
                txtLabelUOM1,
                txtLabelUOM2,
                txtLabelUOM3,
                txtUOM1,
                txtUOM2,
                txtUOM3,
                txtPrice,
                icon_text
        ;

        ImageView imgProduct;

        LinearLayout layout_row;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterMasterProduct mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtUOM1 = (TextView)itemView.findViewById(R.id.txtUOM1);
            txtUOM2 = (TextView)itemView.findViewById(R.id.txtUOM2);
            txtUOM3 = (TextView)itemView.findViewById(R.id.txtUOM3);
            txtLabelUOM1 = (TextView)itemView.findViewById(R.id.txtLabelUOM1);
            txtLabelUOM2 = (TextView)itemView.findViewById(R.id.txtLabelUOM2);
            txtLabelUOM3 = (TextView)itemView.findViewById(R.id.txtLabelUOM3);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            lyUOM1 = (LinearLayout)itemView.findViewById(R.id.layout_uom1);
            lyUOM2 = (LinearLayout)itemView.findViewById(R.id.layout_uom2);
            lyUOM3 = (LinearLayout)itemView.findViewById(R.id.layout_uom3);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            icon_text = (TextView)itemView.findViewById(R.id.icon_text);
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
