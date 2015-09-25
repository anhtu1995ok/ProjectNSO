package com.android.nso.adapter;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.model.Trading;
import com.android.nso.model.Trick;
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

public class TrickAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Trick> data;
	int resource;

	public TrickAdapter(Context context, int resource, ArrayList<Trick> data) {
		this.context = context;
		this.resource = resource;
		this.data = data;
	}

	public void notifyDataSetChanged(ArrayList<Trick> data) {
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
		final Trick trick = data.get(pos);
		final ViewHolder viewHolder;

		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_trick, null, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.content);

			convertView.setTag(viewHolder);
		}
		viewHolder.title.setText(trick.getTieuDe());
		viewHolder.content.setText(trick.getNoiDung());

		final ProgressBar loadingImage = (ProgressBar) convertView
				.findViewById(R.id.loading);
		loadingImage.setVisibility(View.VISIBLE);

		if (trick.getArrAnh().size() > 0) {
			String url_image = trick.getArrAnh().get(0);
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
		TextView title, content;
		ProgressBar loadingImage;

	}
}
