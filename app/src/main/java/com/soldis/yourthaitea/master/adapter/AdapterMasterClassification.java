package com.soldis.yourthaitea.master.adapter;

import android.content.Context;
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
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.transaction.adapter.AdapterListTypeOut;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterMasterClassification extends  RecyclerView.Adapter<AdapterMasterClassification.ViewHolder>{
    ArrayList <Obj_TYPEOUT> objTypeouts;
    private Context context;

    public AdapterMasterClassification(Context context, ArrayList <Obj_TYPEOUT> objTypeouts, AdapterMasterClassification.OnDownloadClicked listener) {
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

    private AdapterMasterClassification.OnDownloadClicked listener;

    @Override
    public AdapterMasterClassification.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_list_kelurahan, null);

        return new AdapterMasterClassification.ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final AdapterMasterClassification.ViewHolder holder, int position) {
        final Obj_TYPEOUT dat = objTypeouts.get(position);

        holder.txtKelurahan.setText(dat.getTYPENAME().replace("AFH ", ""));
        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(dat.getGROUP_CODE(), dat.getGROUP_NAME());
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
                          final AdapterMasterClassification mCourseAdapter) {
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
