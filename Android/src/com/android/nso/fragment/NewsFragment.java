package com.android.nso.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.NewsAdapter;
import com.android.nso.model.News;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class NewsFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private SwipeRefreshLayout swipeRefreshLayout;
	ListView listView;
	NewsAdapter adapter;
	ArrayList<News> data;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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

		adapter = new NewsAdapter(getActivity(), R.layout.item_news, data);
		listView.setAdapter(adapter);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.abc_slide_in_bottom));

		listView.setOnItemClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				data = getDSTinTuc();
			}
		});

		return view;
	}

	@Override
	public void onRefresh() {
		data = getDSTinTuc();
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

	private ArrayList<News> getDSTinTuc() {
		final ArrayList<News> data = new ArrayList<News>();
		String url = getResources().getString(R.string.url_news);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
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
							String mota = row.getString("mo_ta");

							News news = new News(id, tieude, mota, noi_dung,
									"", ngay_tao, "");
							data.add(news);

						}
						Log.d("TuNT", "no");
						adapter.notifyDataSetChanged(data);
						swipeRefreshLayout.setRefreshing(false);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
		return data;
	}
}
