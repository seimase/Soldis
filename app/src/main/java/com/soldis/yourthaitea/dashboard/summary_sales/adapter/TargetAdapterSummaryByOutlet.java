package com.soldis.yourthaitea.dashboard.summary_sales.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.model.Tbl_TargetTmp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TargetAdapterSummaryByOutlet extends PagerAdapter {
    ArrayList<Tbl_TargetTmp> data;
    private Context context;
    private ArrayList<View> views;
    public HashMap<Integer, ArrayList<View>> sliderMapping = new HashMap<Integer, ArrayList<View>>();

    public TargetAdapterSummaryByOutlet(Context context) {
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


    public void setData(final ArrayList<Tbl_TargetTmp> data) {
        this.data = data;
        double lStatus = 0;
        double dTarget, dAch;
        DecimalFormat formatter = new DecimalFormat(".00");


        views = new ArrayList<View>();
        for (Tbl_TargetTmp dat : data){
            lStatus = 0;
            dTarget = 0;
            dAch = 0;

            View convertView = LayoutInflater.from(context).inflate(R.layout.row_dashboard_pager_summary_sales, null);
            ViewHolder holder = new ViewHolder();
            //holder.txtGTTarget = (TextView)convertView.findViewById(R.id.txtGTTarget);
            holder.layout_target = (RelativeLayout)convertView.findViewById(R.id.layout_target);
            holder.txtTargetDay = (TextView)convertView.findViewById(R.id.txtTargetDay);
            holder.txtAchDay = (TextView)convertView.findViewById(R.id.txtAchDay);
            holder.txtLabelTarget = (TextView)convertView.findViewById(R.id.txtLabelTarget);
            holder.imgMaps = (ImageView)convertView.findViewById(R.id.imgMaps);

            holder.txtLabelTarget.setText(dat.TARGET_TYPE);
            holder.txtAchDay.setText(AppController.getInstance().toCurrency(dat.ACH_DAY));
            holder.txtTargetDay.setText(AppController.getInstance().toCurrency(dat.TARGET_DAY));

            if (dat.TARGET_TYPE.equals("EC Target")){
                holder.layout_target.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_red));
                holder.imgMaps.setColorFilter(context.getResources().getColor(R.color.young_red));
            }else if (dat.TARGET_TYPE.equals("Call Target")){
                holder.layout_target.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_darkorange));
                holder.imgMaps.setColorFilter(context.getResources().getColor(R.color.young_orange));
            }else if (dat.TARGET_TYPE.equals("Penjualan")){
                holder.txtAchDay.setText(AppController.getInstance().toCurrencyRp(dat.ACH_DAY));
                holder.txtTargetDay.setText(AppController.getInstance().toCurrencyRp(dat.TARGET_DAY));
                holder.layout_target.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_green));
                holder.imgMaps.setColorFilter(context.getResources().getColor(R.color.young_green));
            }

            views.add(convertView);

        }

    }

    private class ViewHolder {
        TextView txtTargetDay,
                txtAchDay,
                txtLabelTarget
                        ;

        RelativeLayout layout_target;
        ImageView imgMaps;
    }
}
