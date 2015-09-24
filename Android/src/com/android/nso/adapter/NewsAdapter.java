package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NewsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<News> data;
	int resource;

	public NewsAdapter(Context context, int resource, ArrayList<News> data) {
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
		final News news = data.get(pos);
		final ViewHolder viewHolder;

		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(resource, null, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.description = (TextView) convertView.findViewById(R.id.description);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			viewHolder.readmore = (TextView) convertView.findViewById(R.id.readmore);

			convertView.setTag(viewHolder);
		}

		// if (convertView == null) {
		// LayoutInflater mInflater = (LayoutInflater) context
		// .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// convertView = mInflater.inflate(resource, parent, false);
		// }

		try {

			String des = news.getDescription();
			viewHolder.description.setText(des);

			if (des.length() >= 200 && resource != R.layout.item_news_full) {
				viewHolder.description.setText(des.substring(0, 200) + "...");
			}
			viewHolder.title.setText(news.getName());
			viewHolder.time.setText(news.getCreated_at());

		} catch (Exception e) {
			// TODO: handle exception
		}

		String url_image = news.getUrl_image();
		if (url_image.equals("test_2")) {
			viewHolder.image.setImageResource(R.drawable.ic_test_new_2);
		} else if (url_image.equals("test_1")) {
			viewHolder.image.setImageResource(R.drawable.ic_test_new_1);
		}
		// if (viewHolder.description.getText().toString().equals(des)) {
		// viewHolder.description.setText(des);
		// } else {
		// viewHolder.description.setText(des.substring(0, 200) + "...");
		// }

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

		viewHolder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "onImageClick", Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	private class ViewHolder {

		ImageView image;
		TextView title, description, time, readmore;
		ProgressBar loadingImage;

	}
}
