package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Product;
import com.android.nso.model.Trading;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TradingAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Trading> data;
	int resource;

	public TradingAdapter(Context context, int resource, ArrayList<Trading> data) {
		this.context = context;
		this.resource = resource;
		this.data = data;
	}
	
	public void notifyDataSetChanged(ArrayList<Trading> data) {
		this.data = data;
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int pos) {
		data.get(pos);
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		final Trading trading = data.get(pos);
		final ViewHolder viewHolder;

		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_trading, null, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.region = (TextView) convertView.findViewById(R.id.region);
			viewHolder.user = (TextView) convertView.findViewById(R.id.user);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			viewHolder.type = (TextView) convertView.findViewById(R.id.type);
 
			convertView.setTag(viewHolder);
		}
		String region = trading.getTenTinhThanh();
		viewHolder.region.setText(region);
		viewHolder.title.setText(trading.getTieuDe());

		viewHolder.user.setText(trading.getTaoBoiTen());
		viewHolder.time.setText(trading.getNgayTao());
		viewHolder.type.setText(trading.getTenLoai());

		final ProgressBar loadingImage = (ProgressBar) convertView.findViewById(R.id.loading);
		loadingImage.setVisibility(View.VISIBLE);

		if (trading.getArrAnh().size() > 0) {
			String url_image = trading.getArrAnh().get(0);
			Glide.with(context).load(url_image)
					.placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher)
					.into(new GlideDrawableImageViewTarget(viewHolder.image) {
						@Override
						public void onResourceReady(GlideDrawable drawable,
								GlideAnimation anim) {
							super.onResourceReady(drawable, anim);
							loadingImage.setVisibility(View.GONE);
						}
						
						@Override
						public void onLoadFailed(Exception e,
								Drawable errorDrawable) {
							loadingImage.setVisibility(View.GONE);
							super.onLoadFailed(e, errorDrawable);
						}
					});

		} else {
			viewHolder.image.setImageResource(R.drawable.ic_launcher);
			loadingImage.setVisibility(View.GONE);
		}

		return convertView;
	}

	private class ViewHolder {

		ImageView image;
		TextView title, region, user, time, type;
		ProgressBar loadingImage;

	}
}
