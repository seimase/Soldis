package com.soldis.yourthaitea.master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterMasterGroupClassification extends  RecyclerView.Adapter<AdapterMasterGroupClassification.ViewHolder>{
    ArrayList <Obj_TYPEOUT> objTypeouts;
    private Context context;

    public AdapterMasterGroupClassification(Context context, ArrayList <Obj_TYPEOUT> objTypeouts, AdapterMasterGroupClassification.OnDownloadClicked listener) {
        this.context = context;
        this.objTypeouts = objTypeouts;
        this.listener = listener;
        if (objTypeouts == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String TYPEOUT, String TYPENAME);
    }

    private AdapterMasterGroupClassification.OnDownloadClicked listener;

    @Override
    public AdapterMasterGroupClassification.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_list_kelurahan, null);

        return new AdapterMasterGroupClassification.ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final AdapterMasterGroupClassification.ViewHolder holder, int position) {
        final Obj_TYPEOUT dat = objTypeouts.get(position);

        holder.txtKelurahan.setText(dat.getGROUP_NAME().replace("AFH ", ""));
        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getGROUP_CODE(), dat.getGROUP_NAME().replace("AFH ", ""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return objTypeouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtKelurahan
                ;

        LinearLayout layout_row;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterMasterGroupClassification mCourseAdapter) {
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
