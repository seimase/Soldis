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
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.stock.ListStockActivity;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterListStock extends  RecyclerView.Adapter<AdapterListStock.ViewHolder>{
    ArrayList <Obj_MASTER> ObjMaster;
    private Context context;
    boolean bStock;
    public AdapterListStock(Context context, ArrayList <Obj_MASTER> ObjMaster,
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
        public void OnDownloadClicked(String PCODE, String PCODENAME);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_list_stock, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Obj_MASTER dat = ObjMaster.get(holder.getAdapterPosition());
        String sXQTY = "0";

        boolean b3UOM = false;
        holder.txtPCode.setText(dat.getPCode());
        holder.txtPCodeName.setText(dat.getPCodeName());


        String sPhotoName = "";
        if (dat.getPHOTO_NAME() != null) sPhotoName = AppConstant.PATH_PHOTO + "/" + dat.getPHOTO_NAME();

        if (dat.getPHOTO_NAME()!= null && !dat.getPHOTO_NAME().equals("null")){
            AppController.getInstance().displayImage(context, AppConstant.PHOTO_PRODUCT_URL + dat.getPHOTO_NAME(), holder.imgProduct);
        }else{
            AppController.getInstance().displayImage(context, sPhotoName, holder.imgProduct);
        }

        //holder.txtQty.setText(Long.toString(dat.getSTOCK_MOTORIS()));
        if (dat.getUnit1() != null && dat.getUnit2() != null ){
            if (dat.getUnit1().toUpperCase().equals(dat.getUnit2().toUpperCase())){
                sXQTY = AppController.getInstance().convertQtyToString(
                        (int)dat.getConvUnit2(),
                        (int)dat.getConvUnit2(),
                        dat.getSTOCK_MOTORIS());

            }
        }

        holder.txtIn.setText(AppController.getInstance().toCurrency(dat.getSTOCK_IN()));
        holder.txtOut.setText(AppController.getInstance().toCurrency((dat.getSTOCK_OUT() + dat.getQtyJual())));
        holder.txtStock.setText(AppController.getInstance().toCurrency(dat.getSTOCK_IN() - (dat.getSTOCK_OUT() + dat.getQtyJual())));

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getPCode(), dat.getPCodeName());
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
                txtIn,
                txtOut,
                txtStock
                        ;


        ImageView imgProduct;

        LinearLayout layout_row;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterListStock mCourseAdapter) {
            super(itemView);
            txtPCode = (TextView)itemView.findViewById(R.id.txtPCode);
            txtPCodeName = (TextView)itemView.findViewById(R.id.txtPCodeName);
            txtIn = (TextView)itemView.findViewById(R.id.txtIn);
            txtOut = (TextView)itemView.findViewById(R.id.txtOut);
            txtStock = (TextView)itemView.findViewById(R.id.txtStock);
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

}
