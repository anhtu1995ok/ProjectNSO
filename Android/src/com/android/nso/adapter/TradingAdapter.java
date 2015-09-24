package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Product;
import com.android.nso.model.Trading;

import android.content.Context;
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
		String region = trading.getRegion();
		viewHolder.region.setText(region);
		viewHolder.title.setText(trading.getName());

		viewHolder.user.setText(trading.getCreated_by());
		viewHolder.time.setText(trading.getCreated_at());
		viewHolder.type.setText(trading.getType());
		// image.setImageResource(news.getUrl_image());

		ProgressBar loadingImage = (ProgressBar) convertView.findViewById(R.id.loading);

		// String imageUrl = news.getUrl_image();
		// if (!imageUrl.startsWith("http://") &&
		// !imageUrl.startsWith("https://"))
		// imageUrl = "http://" + imageUrl;
		// Picasso.with(context).load(imageUrl).placeholder(R.drawable.no_image)
		// .error(R.drawable.no_image).into(image, new Callback() {
		//
		// @Override
		// public void onSuccess() {
		// loadingImage.setVisibility(View.INVISIBLE);
		// image.setVisibility(View.VISIBLE);
		//
		// }
		//
		// @Override
		// public void onError() {
		// loadingImage.setVisibility(View.INVISIBLE);
		// image.setVisibility(View.VISIBLE);
		// }
		// });

		return convertView;
	}

	private class ViewHolder {

		ImageView image;
		TextView title, region, user, time, type;
		ProgressBar loadingImage;

	}
}
