package com.soldis.yourthaitea.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.soldis.yourthaitea.R;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private float mBaseElevation;
    Context context;

    public interface OnDownloadClicked {
        public void OnDownloadClicked(int position);
    }

    private OnDownloadClicked listener;

    public CardPagerAdapter(Context context, OnDownloadClicked listener) {
        this.context = context;
        this.listener = listener;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            mData.add("");
            mViews.add(null);
        }
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.row_menu, container, false);
        container.addView(view);
        RelativeLayout cardView = (RelativeLayout) view.findViewById(R.id.cardView);
        ImageView imgMenu = (ImageView)view.findViewById(R.id.img_menu);

        switch (position){
            case 0:
                imgMenu.setImageDrawable(context.getResources().getDrawable(R.drawable.hrm_menu));
                break;
            case 1:
                imgMenu.setImageDrawable(context.getResources().getDrawable(R.drawable.finance_menu));
                break;
            case 2:
                imgMenu.setImageDrawable(context.getResources().getDrawable(R.drawable.land_menu));
                break;
            case 3:
                imgMenu.setImageDrawable(context.getResources().getDrawable(R.drawable.admin_menu));
                break;


        }

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(position);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

}
