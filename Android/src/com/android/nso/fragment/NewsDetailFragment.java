package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.model.News;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.RenderPriority;
import android.widget.TextView;

public class NewsDetailFragment extends Fragment {

	TextView title, description, time;
	WebView webView;

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
		webView = (WebView) view.findViewById(R.id.webview);
		initWebView(news.getContent());
		
		title.setText(news.getName());
		description.setText(news.getDescription());
		time.setText(news.getCreated_at());
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	private void initWebView(String data){
		WebSettings setting = webView.getSettings();
//		setting.setMinimumFontSize(26);
		
		setting.setRenderPriority(RenderPriority.HIGH);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		setting.setAppCacheEnabled(false);
		setting.setBlockNetworkImage(true);
		setting.setLoadsImagesAutomatically(true);
		setting.setGeolocationEnabled(false);
		setting.setNeedInitialFocus(false);
		setting.setSaveFormData(false);
		
		setting.setTextSize(WebSettings.TextSize.LARGEST);
		setting.setUseWideViewPort(true);
		setting.setLoadWithOverviewMode(true);
		setting.setJavaScriptEnabled(true);
		// setting.setDefaultZoom(ZoomDensity.FAR);
		setting.setBuiltInZoomControls(false);
		setting.setDisplayZoomControls(false);
		setting.setSupportZoom(false);

		webView.setPadding(10, 10, 10, 10);
		webView.setInitialScale(1);
		
		if (data != "") {
			webView.loadData(data, "text/html", "UTF-8");
		}
	}
}
