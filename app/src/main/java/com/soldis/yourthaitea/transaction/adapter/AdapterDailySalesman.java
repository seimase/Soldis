package com.soldis.yourthaitea.transaction.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.transaction.ImagePreview;

import java.io.File;
import java.util.ArrayList;

import static com.soldis.yourthaitea.transaction.ImagePreview.AVATAR_IMAGE;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDailySalesman extends  RecyclerView.Adapter<AdapterDailySalesman.ViewHolder>{
    ArrayList <Obj_CUSTMST> ObjCustmst;
    private Context context;

    public AdapterDailySalesman(Context context, ArrayList <Obj_CUSTMST> ObjCustmst, OnDownloadClicked listener) {
        this.context = context;
        this.ObjCustmst = ObjCustmst;
        this.listener = listener;
        if (ObjCustmst == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String CUSTNO, String CUSTNAME, String ADDRESS,
                                      String TYPENAME, String PHOTO_NAME);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_master_outlet, null);

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
                listener.OnDownloadClicked(dat.getCUSTNO(), dat.getCUSTNAME(), ALAMAT, dat.getTYPENAME(), dat.getPHOTO_NAME());
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

        if (dat.getISI_TRANSAKSI() != null && !dat.getISI_TRANSAKSI().equals("")){
            holder.viewStatus.setVisibility(View.VISIBLE);
            if (dat.getISI_TRANSAKSI().equals("Y")){
                holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.Green));
            }else{
                Obj_CUSTCARD1  objCustcard1 = new Obj_CUSTCARD1();
                objCustcard1 = objCustcard1.getData(dat.getCUSTNO());

                if (objCustcard1.getCUSTNO() != null){
                    if (objCustcard1.getTIMEOUT() == null || objCustcard1.getTIMEOUT().equals("")){
                        holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.darkorange));
                    }
                }else{
                    holder.viewStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
                }

            }
        }else{


            holder.viewStatus.setVisibility(View.GONE);
        }

        if (dat.getFLAGOUT() != null ){
            holder.txtStatus.setVisibility(View.VISIBLE);
            if (dat.getFLAGOUT().equals("1")){
                holder.txtStatus.setText(context.getResources().getString(R.string.master_pre_reg));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_yellow));
            }else if (dat.getFLAGOUT().equals("2")){
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_orange));
            }else{
                holder.txtStatus.setText(context.getResources().getString(R.string.master_register));
                holder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_green_young));
            }
        }else{
            holder.txtStatus.setVisibility(View.GONE);
        }

        holder.txtStatus.setVisibility(View.GONE);

        if (dat.getLAST_TRANSACTION() != null){
            holder.txtTotalKunjungan.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(dat.getLAST_TRANSACTION()));
        }else{
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
                txtStatus,
                txtTypeName
        ;

        ImageView imgProduct;

        LinearLayout layout_row;
        RelativeLayout layout_stars;
        View viewStatus;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDailySalesman mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtOutletName = (TextView)itemView.findViewById(R.id.txtOutletName);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtTelp = (TextView)itemView.findViewById(R.id.txtTelp);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTotalKunjungan = (TextView)itemView.findViewById(R.id.txtTotalKunjungan);
            txtTypeName = (TextView)itemView.findViewById(R.id.txtTypeName);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            viewStatus = (View)itemView.findViewById(R.id.layout_status);

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
