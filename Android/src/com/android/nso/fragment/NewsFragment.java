package com.android.nso.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.NewsAdapter;
import com.android.nso.adapter.TradingAdapter;
import com.android.nso.model.News;
import com.android.nso.model.Trading;
import com.android.nso.utils.Time;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private SwipeRefreshLayout swipeRefreshLayout;
	ListView listView;
	NewsAdapter adapter;
	ArrayList<News> data;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);

		View footer = inflater.inflate(R.layout.footer_newsfragment, null);
		View empty_view = inflater.inflate(R.layout.empty_view, null);
		
		// View footer = ((LayoutInflater)
		// getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
		// .inflate(R.layout.footer_newsfragment, null, false);

		data = new ArrayList<News>();
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setEmptyView(empty_view);
		listView.addFooterView(footer);
		
		TextView footer_text = (TextView) footer.findViewById(R.id.footer_text);
		footer_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listView.smoothScrollToPosition(0);
			}
		});

		listView.setOnItemClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				getData();
			}
		});

		return view;
	}

	@Override
	public void onRefresh() {
		getData();
	}

	private void getData() {
		swipeRefreshLayout.setRefreshing(true);
		
		MyAsync();
		
//		int max = 10;
//		String time = Time.getCurrentDate();
//		// ======================== \\
//		String test_title_1 = getActivity().getResources().getString(R.string.test_new_title);
//		String test_title_2 = getActivity().getResources().getString(R.string.test_new_title_1);
//		String test_des_1 = getActivity().getResources().getString(R.string.test_new_des);
//		String test_des_2 = getActivity().getResources().getString(R.string.test_new_des_1);
//		News news_1 = new News(11, test_title_1, test_des_1, " content ", "test_1", time, "");
//		data.add(news_1);
//		News news_2 = new News(12, test_title_2, test_des_2, " content ", "test_2", time, "");
//		data.add(news_2);
//		// ======================== \\
//		for (int i = 0; i < max; i++) {
//			News news = new News(i, "Tin moi thu : " + i,
//					"descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription", " content " + i,
//					"", time, "");
//			data.add(news);
//		}
//		adapter = new NewsAdapter(getActivity(), R.layout.item_news, data);
//		listView.setAdapter(adapter);
//		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
//		adapter.notifyDataSetChanged();

		// stopping swipe refresh
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		// News news = (News) arg0.getAdapter().getItem(arg2);
		try {
			News news = data.get(arg2);
			intent.putExtra("NEW_DATA", news);
			intent.putExtra("TYPE", 1);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	void MyAsync() {
		String url = getResources().getString(R.string.url_news);

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String jsonRead = response.toString();
				if (!jsonRead.isEmpty()) {

					try {
						JSONObject object = new JSONObject(jsonRead);

							JSONArray rows = object.getJSONArray("row");
							for (int i = 0; i < rows.length(); i++) {
								JSONObject row = rows.getJSONObject(i);
								int id = row.getInt("id");
								String tieude = row.getString("tieu_de");
								String noi_dung = row.getString("noi_dung");
								String ngay_tao = row.getString("ngay_tao");
								String mota = row.getString("mota");

								News news = new News(id, tieude, mota, noi_dung, "test_2", ngay_tao, "");
								data.add(news);
								
								adapter = new NewsAdapter(getActivity(), R.layout.item_news, data);
								listView.setAdapter(adapter);
								listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
								adapter.notifyDataSetChanged();

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});

	}
}
