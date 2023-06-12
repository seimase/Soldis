package com.soldis.yourthaitea.dashboard.summary_sales.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.summary_sales.DashboardSummarySalesBySalesman;
import com.soldis.yourthaitea.model.Tbl_ListSummarySales_ByDepoTmp;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RowAdapterDashboardSummarySalesByDepo extends PagerAdapter {
    ArrayList<Tbl_ListSummarySales_ByDepoTmp> data;
    Tbl_List_Motoris listMotoris;
    ProgressDialog progress;
    private Context context;
    private ArrayList<View> views;
    public HashMap<Integer, ArrayList<View>> sliderMapping = new HashMap<Integer, ArrayList<View>>();

    public RowAdapterDashboardSummarySalesByDepo(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return  data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup group = (ViewGroup) views.get(position).getParent();
        if (group != null) {
            group.removeView(views.get(position));
        }
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        // TODO Auto-generated method stub
        return view.equals(obj);
    }


    public void setData(final ArrayList<Tbl_ListSummarySales_ByDepoTmp> data) {
        this.data = data;
        double lStatus = 0;
        double dTarget, dAch;
        DecimalFormat formatter = new DecimalFormat(".00");


        views = new ArrayList<View>();
        for (final Tbl_ListSummarySales_ByDepoTmp dat : data){
            View convertView = LayoutInflater.from(context).inflate(R.layout.row_item_dashboard_summarysales_bydepo, null);
            ViewHolder holder = new ViewHolder();

            holder.txtECTarget = (TextView)convertView.findViewById(R.id.txtECTarget);
            holder.txtTotalEC = (TextView)convertView.findViewById(R.id.txtTotalEC);
            holder.txtCallTarget = (TextView)convertView.findViewById(R.id.txtCallTarget);
            holder.txtTotalCall = (TextView)convertView.findViewById(R.id.txtTotalCall);
            holder.txtRegular = (TextView)convertView.findViewById(R.id.txtRegular);

            holder.progressEC = (ProgressBar)convertView.findViewById(R.id.progressEC);
            holder.progressCall = (ProgressBar)convertView.findViewById(R.id.progressCall);

            holder.layout_row = (LinearLayout)convertView.findViewById(R.id.layout_row);
            convertView.setTag(holder);

            holder.txtRegular.setText(dat.DATA_TYPE);
            holder.txtCallTarget.setText(AppController.getInstance().toCurrency((long)dat.TARGET_CALL));
            holder.txtECTarget.setText(AppController.getInstance().toCurrency((long)dat.TARGET_EC));
            holder.txtTotalCall.setText(AppController.getInstance().toCurrency((long)dat.TOTAL_CALL));
            holder.txtTotalEC.setText(AppController.getInstance().toCurrency((long)dat.TOTAL_EC));

            if (dat.TARGET_CALL > 0){
                lStatus = (dat.TOTAL_CALL/dat.TARGET_CALL) * 100;
                if (lStatus >= 100){
                    holder.progressCall.setProgress(100);
                    Drawable progressDrawable = holder.progressCall.getProgressDrawable().mutate();
                    progressDrawable.setColorFilter(context.getResources().getColor(R.color.Green_chat), android.graphics.PorterDuff.Mode.SRC_IN);
                    holder.progressCall.setProgressDrawable(progressDrawable);
                }else{
                    if (lStatus > 85 && lStatus < 100){
                        Drawable progressDrawable = holder.progressCall.getProgressDrawable().mutate();
                        progressDrawable.setColorFilter(context.getResources().getColor(R.color.darkorange), android.graphics.PorterDuff.Mode.SRC_IN);
                        holder.progressCall.setProgressDrawable(progressDrawable);
                    }else{
                        Drawable progressDrawable = holder.progressCall.getProgressDrawable().mutate();
                        progressDrawable.setColorFilter(context.getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                        holder.progressCall.setProgressDrawable(progressDrawable);
                    }
                    holder.progressCall.setProgress((int) lStatus);
                }
            }else{
                holder.progressCall.setProgress(0);
            }

            if (dat.TARGET_EC > 0){
                lStatus = (dat.TOTAL_EC/dat.TARGET_EC) * 100;
                if (lStatus >= 100){
                    holder.progressEC.setProgress(100);
                    Drawable progressDrawable = holder.progressEC.getProgressDrawable().mutate();
                    progressDrawable.setColorFilter(context.getResources().getColor(R.color.Green_chat), android.graphics.PorterDuff.Mode.SRC_IN);
                    holder.progressEC.setProgressDrawable(progressDrawable);
                }else{
                    if (lStatus > 85 && lStatus < 100){
                        Drawable progressDrawable = holder.progressEC.getProgressDrawable().mutate();
                        progressDrawable.setColorFilter(context.getResources().getColor(R.color.darkorange), android.graphics.PorterDuff.Mode.SRC_IN);
                        holder.progressEC.setProgressDrawable(progressDrawable);
                    }else{
                        Drawable progressDrawable = holder.progressEC.getProgressDrawable().mutate();
                        progressDrawable.setColorFilter(context.getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                        holder.progressEC.setProgressDrawable(progressDrawable);
                    }
                    holder.progressEC.setProgress((int) lStatus);
                }
            }else{
                holder.progressEC.setProgress(0);
            }

            holder.layout_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        progress = ProgressDialog.show(context, context.getResources().getString(R.string.main_Information),
                                context.getResources().getString(R.string.setting_sync_data), true);

                        Call<Tbl_List_Motoris> call;
                        if (dat.bDays){
                            call = NetworkManager.getNetworkService().ListDepoMotoris(dat.KODECABANG,
                                    AppConstant.strSlsNo,
                                    dat.TGL_TRX);
                        }else{
                            call = NetworkManager.getNetworkService().ListDepoMotorisMTD(dat.KODECABANG,
                                    AppConstant.strSlsNo,
                                    dat.TGL_TRX);
                        }

                        call.enqueue(new Callback<Tbl_List_Motoris>() {
                            @Override
                            public void onResponse(Call<Tbl_List_Motoris> call, Response<Tbl_List_Motoris> response) {
                                progress.dismiss();
                                int code = response.code();
                                if (code == 200){
                                    listMotoris = response.body();
                                    if (!listMotoris.error){
                                        AppController.getInstance().getSessionManager().setListMotoris(null);
                                        AppController.getInstance().getSessionManager().setListMotoris(listMotoris);
                                        AppController.getInstance().getSessionManager().putBooleanData(AppConstant.DATA_SALES_MOTORIS, dat.bDays);

                                        Intent mIntent = new Intent(context, DashboardSummarySalesBySalesman.class);
                                        mIntent.putExtra("NAMACABANG", dat.NAMACABANG);
                                        mIntent.putExtra("KODECABANG", dat.KODECABANG);
                                        context.startActivity(mIntent);
                                    }else{
                                        AppController.getInstance().CustomeDialog(context,context.getResources().getString(R.string.text_data_not_found));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Tbl_List_Motoris> call, Throwable t) {
                                progress.dismiss();
                            }
                        });
                    }catch (Exception e) {

                    }
                }
            });

            views.add(convertView);

        }

    }

    private class ViewHolder {
        TextView
                txtECTarget,
                txtTotalEC,
                txtCallTarget,
                txtTotalCall,
                txtRegular

                        ;

        ProgressBar
                progressEC,
                progressCall
                        ;

        LinearLayout layout_row;
    }
}
