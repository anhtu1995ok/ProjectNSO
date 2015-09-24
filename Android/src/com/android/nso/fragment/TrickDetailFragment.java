package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.extras.StretchyImageView;
import com.android.nso.model.Trick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrickDetailFragment extends Fragment {

	TextView title, content;
	StretchyImageView image;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.item_trick_full, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		
		Intent intent = getActivity().getIntent();
		Trick trick = (Trick) intent.getSerializableExtra("TRICK_DATA");
		
		title = (TextView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		image = (StretchyImageView) view.findViewById(R.id.image);
		
		title.setText(trick.getName());
		content.setText(trick.getContent());
		
		String url_image = trick.getUrl_image();
		String type = trick.getType();
		if (url_image.equals("test1") && type.equals("1")) {
			image.setImageResource(R.drawable.ic_trick_1);
		} else if (url_image.equals("test2") && type.equals("1")) {
			image.setImageResource(R.drawable.ic_trick_2);
		} else if (url_image.equals("test3") && type.equals("2")) {
			image.setImageResource(R.drawable.ic_trick_3);
		} else if (url_image.equals("test4") && type.equals("2")) {
			image.setImageResource(R.drawable.ic_trick_4);
		}
	}
}
