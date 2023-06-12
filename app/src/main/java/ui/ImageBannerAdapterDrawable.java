package ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.soldis.yourthaitea.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageBannerAdapterDrawable extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Drawable> mResources;
    int layout;

    public ImageBannerAdapterDrawable(Context context, List<Drawable> resource, int layout) {
        mResources = resource;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(layout, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.header_cover_image);
        //((AppController) mContext).displayImage(mContext, mResources.get(position).toString(), imageView);
        Picasso picasso = Picasso.with(mContext);
        picasso.setIndicatorsEnabled(false);
        picasso.load(mResources.get(position).toString())
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
