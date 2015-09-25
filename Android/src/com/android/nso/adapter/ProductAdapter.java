package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Product;

import android.content.Context;
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
		Log.d("TuNT", "size: "+this.data.size());
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
			convertView = LayoutInflater.from(context).inflate(resource, null, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			viewHolder.address = (TextView) convertView.findViewById(R.id.address);
			
			convertView.setTag(viewHolder);
		}
		
		viewHolder.title.setText(product.getName());
		String vnd = context.getResources().getString(R.string.vnd);
		viewHolder.price.setText(product.getPrice() + " " + vnd + "/" + product.getQuantity());
		viewHolder.address.setText(product.getAddress());
		// image.setImageResource(news.getUrl_image());
		
		String url_image = product.getUrl_image();
		if(url_image.equals("test1")){
			viewHolder.image.setImageResource(R.drawable.ic_test_product_1);
		}else if(url_image.equals("test2")){
			viewHolder.image.setImageResource(R.drawable.ic_test_product_2);
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
		TextView title, price, address;
		ProgressBar loadingImage;

	}
}
