package com.soldis.yourthaitea.master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_PRODUCT_CATEGORY;

import java.util.ArrayList;


/**
 * Created by User on 8/22/2017.
 */
public class AdapterListProductCategory extends  RecyclerView.Adapter<AdapterListProductCategory.ViewHolder>{
    ArrayList <Obj_PRODUCT_CATEGORY> objProductCategories;
    private Context context;

    public AdapterListProductCategory(Context context, ArrayList <Obj_PRODUCT_CATEGORY> objProductCategories, OnDownloadClicked listener) {
        this.context = context;
        this.objProductCategories = objProductCategories;
        this.listener = listener;
        if (objProductCategories == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String CATEGORY_ID, String CATEGORY_NAME);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_list_kelurahan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Obj_PRODUCT_CATEGORY dat = objProductCategories.get(position);

        holder.txtKelurahan.setText(dat.getCATEGORY_NAME());
        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getCATEGORY_ID(), dat.getCATEGORY_NAME());
            }
        });
    }

    @Override
    public int getItemCount() {
        return objProductCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtKelurahan
        ;

        LinearLayout layout_row;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterListProductCategory mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtKelurahan = (TextView)itemView.findViewById(R.id.txtKelurahan);
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
