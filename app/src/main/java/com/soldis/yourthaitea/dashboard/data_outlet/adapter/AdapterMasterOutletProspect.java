package com.soldis.yourthaitea.dashboard.data_outlet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_Dashboard_DataOutlet;
import com.soldis.yourthaitea.transaction.ImagePreview;

import java.io.File;

import static com.soldis.yourthaitea.transaction.ImagePreview.AVATAR_IMAGE;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterMasterOutletProspect extends  RecyclerView.Adapter<AdapterMasterOutletProspect.ViewHolder>{
    Tbl_Dashboard_DataOutlet dashboardDataOutlet;
    private Context context;

    public AdapterMasterOutletProspect(Context context, Tbl_Dashboard_DataOutlet dashboardDataOutlet, OnDownloadClicked listener) {
        this.context = context;
        this.dashboardDataOutlet = dashboardDataOutlet;
        this.listener = listener;
        if (dashboardDataOutlet == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS, String FLAGOUT);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_master_outlet_frag, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_Dashboard_DataOutlet.Prospect dat = dashboardDataOutlet.prospect.get(position);

        holder.txtOutletName.setText(dat.CUSTNAME);
        //holder.txtAddress.setText(dat.getCUSTADD1() == null ? "" : dat.getCUSTADD1());
        holder.txtAddress.setText("-");
        if (dat.CUSTADD1 != null){
            if (!dat.CUSTADD1.toUpperCase().equals("NULL")){
                holder.txtAddress.setText(dat.CUSTADD1);
            }
        }
        holder.txtTelp.setText((dat.CPHONE1 == null ? "" : dat.CPHONE1));

        String sPhotoName = "";
        if (dat.PHOTO != null) sPhotoName = AppConstant.PATH_PHOTO + "/" + dat.PHOTO;
        File file = new File(sPhotoName);
        if (file.exists()){
            AppController.getInstance().displayImage(context, sPhotoName, holder.imgProduct);
        }else{
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_OUTLET_URL + "/" + dat.PHOTO, holder.imgProduct);
        }


        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AVATAR_IMAGE, AppConstant.PHOTO_OUTLET_URL + "/" + dat.PHOTO);
                android.app.FragmentManager fm = ((AppCompatActivity)context).getFragmentManager();
                ImagePreview powerDialog = new ImagePreview();
                powerDialog.setArguments(bundle);
                powerDialog.show(fm, "fragment_power");
            }
        });

        if (dat.FLAGOUT != null && !dat.FLAGOUT.equals("")){
            holder.viewStatus.setVisibility(View.VISIBLE);
            if (dat.FLAGOUT.equals("1")){
                holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            }else{
                holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.Green));
            }
        }else{
            holder.viewStatus.setVisibility(View.GONE);
        }

        if (dat.FLAGOUT != null ){
            holder.txtStatus.setVisibility(View.VISIBLE);
            if (dat.FLAGOUT.equals("1")){
                holder.txtStatus.setText(context.getResources().getString(R.string.master_pre_reg));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_yellow));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black));
            }else if (dat.FLAGOUT.equals("2")){
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_orange));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black));
            }else{
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_green_young));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else{
            holder.txtStatus.setVisibility(View.GONE);
        }


        if (dat.DATE_NEXT_VISIT != null && !dat.DATE_NEXT_VISIT.equals("")){
            Log.w("DATENEXT", dat.DATE_NEXT_VISIT);
            holder.lyNextVisit.setVisibility(View.VISIBLE);
            holder.txtTotalKunjungan.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(dat.DATE_NEXT_VISIT));
            if (AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(dat.DATE_NEXT_VISIT).equals("")){
                holder.lyNextVisit.setVisibility(View.GONE);
            }
        }else{
            holder.lyNextVisit.setVisibility(View.GONE);
            holder.txtTotalKunjungan.setText("-");
        }

        holder.txtTypeName.setText(dat.TYPENAME == null ? "" : dat.TYPENAME);
        /*if(dat.SLSNAME != null) {
            holder.txtSlsman.setText(dat.SLSNAME);
            holder.txtSlsman.setVisibility(View.VISIBLE);
        }else{
            holder.txtSlsman.setVisibility(View.GONE);
        }*/
    }

    @Override
    public int getItemCount() {
        return dashboardDataOutlet.prospect.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtOutletName,
                txtAddress,
                txtTelp,
                txtTotalKunjungan,
                txtTypeName,
                txtStatus,
                txtSlsman
        ;

        ImageView imgProduct;

        LinearLayout layout_row, lyNextVisit;
        View viewStatus;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterMasterOutletProspect mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            lyNextVisit = (LinearLayout)itemView.findViewById(R.id.layout_nextvisit);
            txtOutletName = (TextView)itemView.findViewById(R.id.txtOutletName);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtTelp = (TextView)itemView.findViewById(R.id.txtTelp);
            txtTotalKunjungan = (TextView)itemView.findViewById(R.id.txtTotalKunjungan);
            txtSlsman = (TextView)itemView.findViewById(R.id.txtSlsman);

            txtTypeName = (TextView)itemView.findViewById(R.id.txtTypeName);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            viewStatus = (View)itemView.findViewById(R.id.layout_status);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
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
