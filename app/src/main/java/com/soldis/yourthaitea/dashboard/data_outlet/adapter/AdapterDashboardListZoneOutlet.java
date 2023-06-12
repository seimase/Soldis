package com.soldis.yourthaitea.dashboard.data_outlet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.asm.Dashboard_ASM_SummaryActivity;
import com.soldis.yourthaitea.dashboard.data_outlet.Dashboard_ASM_OutletActivity;
import com.soldis.yourthaitea.model.Tbl_ListZone;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardListZoneOutlet extends  RecyclerView.Adapter<AdapterDashboardListZoneOutlet.ViewHolder>{
    Tbl_ListZone listZone;
    private Context context;

    public AdapterDashboardListZoneOutlet(Context context, Tbl_ListZone listZone, OnDownloadClicked listener) {
        this.context = context;
        this.listZone = listZone;
        this.listener = listener;
        if (listZone == null) {
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
                R.layout.row_zone_only, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_ListZone.Datum dat = listZone.data.get(position);
        holder.txtZoneId.setText((dat.ZONEID == null ? "" : dat.ZONEID));

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context, Dashboard_ASM_OutletActivity.class);
                mIntent.putExtra("ZONEID", dat.ZONEID);
                context.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listZone.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtZoneId
                ;

        ImageView imgProduct;

        LinearLayout layout_row;
        LinearLayout lyUOM1, lyUOM2, lyUOM3;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardListZoneOutlet mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            txtZoneId = (TextView)itemView.findViewById(R.id.txtZoneId);
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
