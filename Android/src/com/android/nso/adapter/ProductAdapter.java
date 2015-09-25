package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Product> data;
	int resource;

	public ProductAdapter(Context context, int resource, ArrayList<Product> data) {
		this.context = context;
		this.resource = resource;
		this.data = data;
	}

	public void notifyDataSetChanged(ArrayList<Product> data) {
		this.data = data;
		Log.d("TuNT", "size: " + this.data.size());
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
		final Product product = data.get(pos);
		final ViewHolder viewHolder;

		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(resource, null,
					false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.address);

			convertView.setTag(viewHolder);
		}

		viewHolder.title.setText(product.getTen());
		String vnd = context.getResources().getString(R.string.vnd);
		viewHolder.price.setText(product.getGia() + " " + vnd);
		viewHolder.address.setText(product.getDiaChi());

		final ProgressBar loadingImage = (ProgressBar) convertView
				.findViewById(R.id.loading);
		loadingImage.setVisibility(View.VISIBLE);
		
		if (product.getArrAnh().size() > 0) {
			String url_image = product.getArrAnh().get(0);
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
		TextView title, price, address;
		ProgressBar loadingImage;

	}
}
