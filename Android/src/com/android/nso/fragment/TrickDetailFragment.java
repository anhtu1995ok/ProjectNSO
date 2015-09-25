package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.extras.StretchyImageView;
import com.android.nso.model.Trick;
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrickDetailFragment extends Fragment {

	TextView title, content;
	StretchyImageView image;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.item_trick_full, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		Intent intent = getActivity().getIntent();
		Trick trick = (Trick) intent.getSerializableExtra("TRICK_DATA");

		title = (TextView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		image = (StretchyImageView) view.findViewById(R.id.image);

		title.setText(trick.getTieuDe());
		content.setText(trick.getNoiDung());
		if (trick.getArrAnh().size() > 0) {
			String url_image = trick.getArrAnh().get(0);
			Log.d("TuNT", "image_url: "+url_image);
			Glide.with(getActivity())
				.load(url_image)
				.placeholder(getResources().getDrawable(R.drawable.ic_launcher))
				.error(R.drawable.ic_launcher)
				.into(image);
		}else{
			image.setImageResource(R.drawable.ic_launcher);
		}
	}
}
