package com.soldis.yourthaitea.admin.absensi.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashboardPresenceDetail;
import com.soldis.yourthaitea.model.Tbl_Presence;

import java.util.ArrayList;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterAdminAbsensi extends  RecyclerView.Adapter<AdapterAdminAbsensi.ViewHolder>{
    Tbl_Presence tblPresence;
    private Context context;
    RecyclerView.Adapter mAdapter;

    public AdapterAdminAbsensi(Context context, Tbl_Presence tblPresence, OnDownloadClicked listener) {
        this.context = context;
        this.tblPresence = tblPresence;
        this.listener = listener;
        if (tblPresence == null) {
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
                R.layout.row_dashboard_list_presence, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tbl_Presence.DataH dat = tblPresence.data_h.get(position);

        if (dat.PHOTO != null && !dat.PHOTO.equals("")){
            AppController.getInstance().displayImage(context , AppConstant.PHOTO_MOTORIS_URL + dat.PHOTO, holder.imgProfile);
        }else{
            holder.imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar));
        }

        holder.txtName.setText(dat.SLSNAME);
        holder.txtVersion.setText(dat.APP_VERSION);
        holder.txtType.setText(dat.ADDRESS);
        holder.txtKehadiran.setText(AppController.getInstance().toCurrency(dat.TOTAL_VISIT));

        holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_red));

        if(!dat.TGLVISIT.equals("")){
            holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_yellow));
        }

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dat.BROW){

                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
                    dat.BROW = false;
                    holder.layout_row_detail.setVisibility(View.GONE);

                }else {
                    holder.layout_row_detail.setVisibility(View.VISIBLE);
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
                    dat.BROW = true;
                }

                Tbl_Presence presence = new Tbl_Presence();
                presence.data_d = new ArrayList<>();
                for (Tbl_Presence.DataD dat1 : tblPresence.data_d){
                    if(dat1.SLSNO.equals(dat.SLSNO)){
                        presence.data_d.add(dat1);
                    }
                }

                mAdapter = new AdapterDashboardPresenceDetail(context, presence,
                        new AdapterDashboardPresenceDetail.OnDownloadClicked() {
                            @Override
                            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

                            }
                        });

                holder.mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tblPresence.data_h.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtName,
                txtVersion,
                txtType,
                txtKehadiran
                        ;
        LinearLayout layout_row, layout_row_detail;
        ImageView imgProfile, imgArrow, imgStatus;
        RecyclerView mRecyclerView;

        LinearLayoutManager mLayoutManager;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterAdminAbsensi mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            layout_row_detail = (LinearLayout)itemView.findViewById(R.id.layout_row_detail);
            txtName = (TextView)itemView.findViewById(R.id.text_name);
            txtVersion = (TextView)itemView.findViewById(R.id.text_version);
            txtType = (TextView)itemView.findViewById(R.id.text_type);
            txtKehadiran = (TextView)itemView.findViewById(R.id.text_kehadiran);
            imgProfile = (ImageView)itemView.findViewById(R.id.imgProfile);
            imgArrow = (ImageView)itemView.findViewById(R.id.img_arrow);
            imgStatus = (ImageView)itemView.findViewById(R.id.image_status);

            mRecyclerView = (RecyclerView)itemView.findViewById(R.id.my_recyclerview);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            // use a LinearLayoutManager
            mRecyclerView.setLayoutManager(mLayoutManager);

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
