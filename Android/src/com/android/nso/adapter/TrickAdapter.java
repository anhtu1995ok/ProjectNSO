package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Trading;
import com.android.nso.model.Trick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TrickAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Trick> data;
	int resource;

	public TrickAdapter(Context context, int resource, ArrayList<Trick> data) {
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
		final Trick trick = data.get(pos);
		final ViewHolder viewHolder;

		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_trick, null, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.content = (TextView) convertView.findViewById(R.id.content);

			convertView.setTag(viewHolder);
		}
		viewHolder.title.setText(trick.getName());
		viewHolder.content.setText(trick.getContent());
		// image.setImageResource(news.getUrl_image());

		String url_image = trick.getUrl_image();
		String type = trick.getType();
		if (url_image.equals("test1") && type.equals("1")) {
			viewHolder.image.setImageResource(R.drawable.ic_trick_1);
		} else if (url_image.equals("test2") && type.equals("1")) {
			viewHolder.image.setImageResource(R.drawable.ic_trick_2);
		} else if (url_image.equals("test3") && type.equals("2")) {
			viewHolder.image.setImageResource(R.drawable.ic_trick_3);
		} else if (url_image.equals("test4") && type.equals("2")) {
			viewHolder.image.setImageResource(R.drawable.ic_trick_4);
		}

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
		TextView title, content;
		ProgressBar loadingImage;

	}
}
