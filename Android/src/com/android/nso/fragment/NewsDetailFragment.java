package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.model.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsDetailFragment extends Fragment {

	TextView title, description, time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.item_news_full, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		
		Intent intent = getActivity().getIntent();
		News news = (News) intent.getSerializableExtra("NEW_DATA");
		
		title = (TextView) view.findViewById(R.id.title);
		description = (TextView) view.findViewById(R.id.description);
		time = (TextView) view.findViewById(R.id.time);
		
		title.setText(news.getName());
		description.setText(news.getDescription());
		time.setText(news.getCreated_at());
	}
}
