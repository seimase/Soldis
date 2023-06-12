package com.soldis.yourthaitea.transaction.taking_order.adapter;

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
import com.soldis.yourthaitea.entity.temp_array.Tmp_MASTER;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterInputProductTmp extends  RecyclerView.Adapter<AdapterInputProductTmp.ViewHolder>  {
    ArrayList <Tmp_MASTER> tmpMasters;
    private Context context;
    boolean bTRX;


    public AdapterInputProductTmp(Context context, ArrayList<Tmp_MASTER> tmpMasters, OnDownloadClicked listener) {
        this.context = context;
        this.tmpMasters = tmpMasters;
        this.listener = listener;
        this.bTRX = bTRX;
        if (tmpMasters == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }

    }


    public interface OnDownloadClicked {
        public void OnDownloadClicked(int position);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_inputproduct_tmp, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        final Tmp_MASTER dat = tmpMasters.get(position);

        holder.txtNo.setText((position + 1) + ".");
        holder.txtProduct.setText(dat.PRODUCT);

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tmpMasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener  {

        TextView txtProduct,
                txtNo
        ;
        public TextView txtTotal;

        LinearLayout layout_row;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterInputProductTmp mCourseAdapter) {
            super(itemView);
            txtProduct = (TextView)itemView.findViewById(R.id.txtProduct);
            txtNo = (TextView)itemView.findViewById(R.id.txtNo);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
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
