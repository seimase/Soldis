package com.soldis.yourthaitea.master.adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.transaction.ImagePreview;

import java.io.File;
import java.util.ArrayList;

import static com.soldis.yourthaitea.transaction.ImagePreview.AVATAR_IMAGE;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterMasterOutletFrag extends  RecyclerView.Adapter<AdapterMasterOutletFrag.ViewHolder>{
    ArrayList <Obj_CUSTMST> ObjCustmst;
    private Context context;

    public AdapterMasterOutletFrag(Context context, ArrayList <Obj_CUSTMST> ObjCustmst, OnDownloadClicked listener) {
        this.context = context;
        this.ObjCustmst = ObjCustmst;
        this.listener = listener;
        if (ObjCustmst == null) {
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
        final Obj_CUSTMST dat = ObjCustmst.get(position);

        holder.txtOutletName.setText(dat.getCUSTNAME());
        //holder.txtAddress.setText(dat.getCUSTADD1() == null ? "" : dat.getCUSTADD1());
        holder.txtAddress.setText("-");
        if (dat.getCUSTADD1() != null){
            if (!dat.getCUSTADD1().toUpperCase().equals("NULL")){
                holder.txtAddress.setText(dat.getCUSTADD1());
            }
        }
        holder.txtTelp.setText(dat.getCPHONE1());

        String sPhotoName = "";
        if (dat.getPHOTO_NAME() != null) sPhotoName = AppConstant.PATH_PHOTO + "/" + dat.getPHOTO_NAME();
        File file = new File(sPhotoName);
        if (file.exists()){
            AppController.getInstance().displayImage(context, sPhotoName, holder.imgProduct);
        }else{
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_OUTLET_URL + "/" + dat.getPHOTO_NAME(), holder.imgProduct);
        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ALAMAT = "-";
                if (dat.getCUSTADD1() != null){
                    if (!dat.getCUSTADD1().toUpperCase().equals("NULL")){
                        ALAMAT = dat.getCUSTADD1();
                    }
                }
                listener.OnDownloadClicked(dat.getCUSTNO(), dat.getCUSTNAME(), ALAMAT, dat.getFLAGOUT());
            }
        });

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AVATAR_IMAGE, AppConstant.PHOTO_OUTLET_URL + "/" + dat.getPHOTO_NAME());
                android.app.FragmentManager fm = ((AppCompatActivity)context).getFragmentManager();
                ImagePreview powerDialog = new ImagePreview();
                powerDialog.setArguments(bundle);
                powerDialog.show(fm, "fragment_power");
            }
        });

        /*if (dat.getFLAGOUT() != null && !dat.getFLAGOUT().equals("")){
            holder.viewStatus.setVisibility(View.VISIBLE);
            if (dat.getFLAGOUT().equals("1")){
                holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            }else{
                holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.Green));
            }
        }else{
            holder.viewStatus.setVisibility(View.GONE);
        }*/



        if (dat.getFLAGOUT() != null ){
            holder.txtStatus.setVisibility(View.VISIBLE);
            if (dat.getFLAGOUT().equals("1")){
                holder.lyNextVisit.setVisibility(View.VISIBLE);
                holder.txtStatus.setText(context.getResources().getString(R.string.master_pre_reg));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_yellow));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black));
            }else if (dat.getFLAGOUT().equals("2")){
                holder.lyNextVisit.setVisibility(View.GONE);
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_orange));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black));
            }else{
                holder.lyNextVisit.setVisibility(View.GONE);
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_green_young));
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else{
            holder.lyNextVisit.setVisibility(View.GONE);
            holder.txtStatus.setVisibility(View.GONE);
        }


        if (dat.getDATE_NEXT_VISIT() != null && !dat.getDATE_NEXT_VISIT().equals("")){
            Log.w("DATENEXT", dat.getDATE_NEXT_VISIT());
            //holder.lyNextVisit.setVisibility(View.VISIBLE);
            holder.txtTotalKunjungan.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(dat.getDATE_NEXT_VISIT()));
            if (AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(dat.getDATE_NEXT_VISIT()).equals("")){
                holder.lyNextVisit.setVisibility(View.GONE);
            }
        }else{
            //holder.lyNextVisit.setVisibility(View.GONE);
            holder.txtTotalKunjungan.setText("-");
        }

        holder.txtTypeName.setText(dat.getTYPENAME() == null ? "" : dat.getTYPENAME());
        holder.layout_stars.setVisibility(View.GONE);

        if (dat.getTYPEOUT() != null){
            if (dat.getTYPEOUT().equals("DB")){
                holder.layout_stars.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return ObjCustmst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtOutletName,
                txtAddress,
                txtTelp,
                txtTotalKunjungan,
                txtTypeName,
                txtStatus
        ;

        ImageView imgProduct;

        LinearLayout layout_row, lyNextVisit;
        RelativeLayout layout_stars;

        View viewStatus;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterMasterOutletFrag mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            lyNextVisit = (LinearLayout)itemView.findViewById(R.id.layout_nextvisit);
            txtOutletName = (TextView)itemView.findViewById(R.id.txtOutletName);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtTelp = (TextView)itemView.findViewById(R.id.txtTelp);
            txtTotalKunjungan = (TextView)itemView.findViewById(R.id.txtTotalKunjungan);

            txtTypeName = (TextView)itemView.findViewById(R.id.txtTypeName);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            viewStatus = (View)itemView.findViewById(R.id.layout_status);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);

            layout_stars = (RelativeLayout)itemView.findViewById(R.id.layout_stars);
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
