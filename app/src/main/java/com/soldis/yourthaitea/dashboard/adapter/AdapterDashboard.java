package com.soldis.yourthaitea.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboard extends  RecyclerView.Adapter<AdapterDashboard.ViewHolder>{
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;

    public AdapterDashboard(Context context, ArrayList <Obj_MASTER> ObjMaster, OnDownloadClicked listener) {
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
                R.layout.row_dashboard_stock, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Obj_MASTER dat = ObjMaster.get(position);
        String sXQTY = "0";
        String sXQTY_Out = "0";
        String sXQTY_Sisa = "0";

        holder.txtPCode.setText(dat.getPCodeName());
        holder.txtInvAmount.setText(AppController.getInstance().toCurrencyRp(dat.getINVAMOUNT()));
        if (dat.getUnit1() != null && !dat.getUnit1().equals("")){
            holder.txtLblUOM1.setText(dat.getUnit1());
            holder.txtLblUOM1_Out.setText(dat.getUnit1());
            holder.txtLblUOM1_Sisa.setText(dat.getUnit1());
            holder.lyUOM1.setVisibility(View.VISIBLE);
            holder.lyUOM1_Out.setVisibility(View.VISIBLE);
            holder.lyUOM1_Sisa.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM1.setVisibility(View.GONE);
            holder.lyUOM1_Out.setVisibility(View.GONE);
            holder.lyUOM1_Sisa.setVisibility(View.GONE);
        }
        if (dat.getUnit2() != null && !dat.getUnit2().equals("")){
            holder.txtLblUOM2.setText(dat.getUnit2());
            holder.txtLblUOM2_Out.setText(dat.getUnit2());
            holder.txtLblUOM2_Sisa.setText(dat.getUnit2());
            holder.lyUOM2.setVisibility(View.VISIBLE);
            holder.lyUOM2_Out.setVisibility(View.VISIBLE);
            holder.lyUOM2_sisa.setVisibility(View.VISIBLE);
        }else{
            holder.lyUOM2.setVisibility(View.GONE);
            holder.lyUOM2_Out.setVisibility(View.GONE);
            holder.lyUOM2_sisa.setVisibility(View.GONE);
        }
        if (dat.getUnit3() != null && !dat.getUnit3().equals("")){
            holder.txtLblUOM3.setText(dat.getUnit3());
            holder.txtLblUOM3_Out.setText(dat.getUnit3());
            holder.txtLblUOM3_Sisa.setText(dat.getUnit3());
            holder.lyUOM3.setVisibility(View.VISIBLE);
            holder.lyUOM3_Out.setVisibility(View.VISIBLE);
            holder.lyUOM3_Sisa.setVisibility(View.VISIBLE);

        }else {
            holder.lyUOM3.setVisibility(View.GONE);
            holder.lyUOM3_Out.setVisibility(View.GONE);
            holder.lyUOM3_Sisa.setVisibility(View.GONE);
        }

        //First Stock---------------------------------------------------------------------
        sXQTY = AppController.getInstance().convertQtyToString(
                (int)dat.getConvUnit3(),
                (int)dat.getConvUnit2(),
                dat.getSTOCK_MOTORIS());
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getSTOCK_MOTORIS());

                holder.lyUOM1.setVisibility(View.GONE);
                holder.lyUOM1_Out.setVisibility(View.GONE);
                holder.lyUOM1_Sisa.setVisibility(View.GONE);
            }
        }

        String[] lstQty = sXQTY.split("\\/");
        holder.txtUOM1.setText(lstQty[0].toString());
        holder.txtUOM2.setText(lstQty[1].toString());
        holder.txtUOM3.setText(lstQty[2].toString());

        //Sales Stock---------------------------------------------------------------------
        sXQTY_Out = AppController.getInstance().convertQtyToString(
                (int)dat.getConvUnit3(),
                (int)dat.getConvUnit2(),
                dat.getQTY_PCS());
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY_Out = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getQTY_PCS());
            }
        }

        String[] lstQtyOut = sXQTY_Out.split("\\/");
        holder.txtUOM1_Out.setText(lstQtyOut[0].toString());
        holder.txtUOM2_Out.setText(lstQtyOut[1].toString());
        holder.txtUOM3_Out.setText(lstQtyOut[2].toString());


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
        holder.txtUOM1_Sisa.setText(lstQtySisa[0].toString());
        holder.txtUOM2_Sisa.setText(lstQtySisa[1].toString());
        holder.txtUOM3_Sisa.setText(lstQtySisa[2].toString());
        //AppController.getInstance().displayImage(context, dat.getPCODE_URL(), holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return ObjMaster.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtPCode,
                txtUOM1,
                txtUOM2,
                txtUOM3,
                txtUOM1_Out,
                txtUOM2_Out,
                txtUOM3_Out,
                txtUOM1_Sisa,
                txtUOM2_Sisa,
                txtUOM3_Sisa,
                txtLblUOM1,
                txtLblUOM2,
                txtLblUOM3,
                txtLblUOM1_Out,
                txtLblUOM2_Out,
                txtLblUOM3_Out,
                txtLblUOM1_Sisa,
                txtLblUOM2_Sisa,
                txtLblUOM3_Sisa,
                txtInvAmount
        ;

        ImageView imgProduct;

        RelativeLayout layout_row;

        LinearLayout lyUOM1, lyUOM2, lyUOM3;
        LinearLayout lyUOM1_Out, lyUOM2_Out, lyUOM3_Out;
        LinearLayout lyUOM1_Sisa, lyUOM2_sisa, lyUOM3_Sisa;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboard mCourseAdapter) {
            super(itemView);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            lyUOM1 = (LinearLayout)itemView.findViewById(R.id.layout_uom1);
            lyUOM2 = (LinearLayout)itemView.findViewById(R.id.layout_uom2);
            lyUOM3 = (LinearLayout)itemView.findViewById(R.id.layout_uom3);
            lyUOM1_Out = (LinearLayout)itemView.findViewById(R.id.layout_uom1_out );
            lyUOM2_Out = (LinearLayout)itemView.findViewById(R.id.layout_uom2_out);
            lyUOM3_Out = (LinearLayout)itemView.findViewById(R.id.layout_uom3_out);
            lyUOM1_Sisa = (LinearLayout)itemView.findViewById(R.id.layout_uom1_sisa);
            lyUOM2_sisa = (LinearLayout)itemView.findViewById(R.id.layout_uom2_sisa);
            lyUOM3_Sisa = (LinearLayout)itemView.findViewById(R.id.layout_uom3_sisa);

            txtUOM1 = (TextView)itemView.findViewById(R.id.text_uom1);
            txtUOM2 = (TextView)itemView.findViewById(R.id.text_uom2);
            txtUOM3 = (TextView)itemView.findViewById(R.id.text_uom3);
            txtUOM1_Out = (TextView)itemView.findViewById(R.id.text_uom1_out);
            txtUOM2_Out = (TextView)itemView.findViewById(R.id.text_uom2_out);
            txtUOM3_Out = (TextView)itemView.findViewById(R.id.text_uom3_out);
            txtUOM1_Sisa = (TextView)itemView.findViewById(R.id.text_uom1_sisa);
            txtUOM2_Sisa = (TextView)itemView.findViewById(R.id.text_uom2_sisa);
            txtUOM3_Sisa = (TextView)itemView.findViewById(R.id.text_uom3_sisa);

            txtLblUOM1= (TextView)itemView.findViewById(R.id.text_label_uom1);
            txtLblUOM2= (TextView)itemView.findViewById(R.id.text_label_uom2);
            txtLblUOM3= (TextView)itemView.findViewById(R.id.text_label_uom3);
            txtLblUOM1_Out= (TextView)itemView.findViewById(R.id.text_label_uom1_out);
            txtLblUOM2_Out= (TextView)itemView.findViewById(R.id.text_label_uom2_out);
            txtLblUOM3_Out= (TextView)itemView.findViewById(R.id.text_label_uom3_out);
            txtLblUOM1_Sisa= (TextView)itemView.findViewById(R.id.text_label_uom1_sisa);
            txtLblUOM2_Sisa= (TextView)itemView.findViewById(R.id.text_label_uom2_sisa);
            txtLblUOM3_Sisa= (TextView)itemView.findViewById(R.id.text_label_uom3_sisa);

            txtInvAmount = (TextView)itemView.findViewById(R.id.txtInvAmount);
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
