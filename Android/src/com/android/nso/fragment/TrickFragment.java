package com.android.nso.fragment;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.TrickAdapter;
import com.android.nso.model.Trading;
import com.android.nso.model.Trick;
import com.android.nso.utils.Time;
import com.android.nso.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrickFragment extends Fragment
		implements OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private TextView tab1, tab2;
	private LinearLayout included_tab_1, included_tab_2;

	private ListView listView, listView_2;
	private SwipeRefreshLayout swipeRefreshLayout, swipeRefreshLayout_2;

	ArrayList<Trick> data, data_2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trick, container, false);

		tab1 = (TextView) view.findViewById(R.id.tab1);
		tab2 = (TextView) view.findViewById(R.id.tab2);

		included_tab_1 = (LinearLayout) view.findViewById(R.id.included_tab_1);
		included_tab_2 = (LinearLayout) view.findViewById(R.id.included_tab_2);

		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);

		// tab
		initTab1(included_tab_1);
		initTab2(included_tab_2);
		initData();

		return view;
	}

	private void initTab1(LinearLayout view) {
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				initData();
			}
		});
		
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
	}

	private void initData() {
		data = initData(data, listView);
		data_2 = initData2(data_2, listView_2);
	}

	private ArrayList<Trick> initData(ArrayList<Trick> data, ListView listView) {
		swipeRefreshLayout.setRefreshing(true);
		Random rand = new Random();
		int max = rand.nextInt(10) + 5;
		data = new ArrayList<Trick>();
		test(data);
		for (int i = 0; i < max; i++) {
			data.add(new Trick(i, "Meo vat San xuat thu : " + i, "Content", "1", "", ""));
		}

		TrickAdapter adapter = new TrickAdapter(getActivity(), R.layout.item_trading, data);
		listView.setAdapter(adapter);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
		adapter.notifyDataSetChanged();

		// stopping swipe refresh
		swipeRefreshLayout.setRefreshing(false);
		return data;
	}

	private ArrayList<Trick> initData2(ArrayList<Trick> data, ListView listView) {
		swipeRefreshLayout_2.setRefreshing(true);
		Random rand = new Random();
		int max = rand.nextInt(10) + 5;
		data = new ArrayList<Trick>();
		test1(data);
		for (int i = 0; i < max; i++) {
			data.add(new Trick(i, "Meo vat Che bien thu : " + i, "Content", "2", "", ""));
		}

		TrickAdapter adapter = new TrickAdapter(getActivity(), R.layout.item_trading, data);
		listView.setAdapter(adapter);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
		adapter.notifyDataSetChanged();

		// stopping swipe refresh
		swipeRefreshLayout_2.setRefreshing(false);
		return data;
	}

	private void initTab2(LinearLayout view) {
		swipeRefreshLayout_2 = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_2);
		swipeRefreshLayout_2.setOnRefreshListener(this);

		swipeRefreshLayout_2.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout_2.setRefreshing(true);
				initData2(data_2, listView_2);
			}
		});
		
		listView_2 = (ListView) view.findViewById(R.id.listView_2);
		listView_2.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int url_image = getActivity().getResources().getIdentifier("xml/" + "trick_tab_onselected", null,
				getActivity().getPackageName());
		switch (v.getId()) {

		case R.id.tab1:
			// set visible
			included_tab_1.setVisibility(View.VISIBLE);
			included_tab_2.setVisibility(View.GONE);
			// set color
			tab1.setTextColor(getResources().getColor(R.color.list_background));
			tab1.setBackgroundResource(url_image);
			//
			tab2.setTextColor(getResources().getColor(R.color.color_bdbdbd));
			tab2.setBackgroundResource(getResources().getColor(R.color.bg_null));
			break;
		case R.id.tab2:
			// set visible
			included_tab_1.setVisibility(View.GONE);
			included_tab_2.setVisibility(View.VISIBLE);
			// set color
			tab2.setTextColor(getResources().getColor(R.color.list_background));
			tab2.setBackgroundResource(url_image);
			//
			tab1.setTextColor(getResources().getColor(R.color.color_bdbdbd));
			tab1.setBackgroundResource(getResources().getColor(R.color.bg_null));
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		// News news = (News) arg0.getAdapter().getItem(arg2);
		if (included_tab_1.getVisibility() == View.VISIBLE) {
			Trick trick = data.get(arg2);
			intent.putExtra("TRICK_DATA", trick);
			intent.putExtra("TYPE", 3);
			startActivity(intent);
		} else if (included_tab_2.getVisibility() == View.VISIBLE) {
			Trick trick = data_2.get(arg2);
			intent.putExtra("TRICK_DATA", trick);
			intent.putExtra("TYPE", 3);
			startActivity(intent);
		}

	}

	@Override
	public void onRefresh() {
		initData();
	}
	
	void test(ArrayList<Trick> data){
		
		String testTitle_1 = getActivity().getResources().getString(R.string.test_trick_title_1);
		String testTitle_2 = getActivity().getResources().getString(R.string.test_trick_title_2);
		String testContent_1 = getActivity().getResources().getString(R.string.test_trick_content_1);
		String testContent_2 = getActivity().getResources().getString(R.string.test_trick_content_2);
		data.add(new Trick(10, testTitle_1, testContent_1, "1", "test1", ""));
		data.add(new Trick(11, testTitle_2, testContent_2, "1", "test2", ""));
		data.add(new Trick(12, testTitle_1, testContent_1, "1", "test1", ""));
		data.add(new Trick(13, testTitle_2, testContent_2, "1", "test2", ""));
	}
	
	void test1(ArrayList<Trick> data){
		
		String testTitle_1 = getActivity().getResources().getString(R.string.test_trick_title_3);
		String testTitle_2 = getActivity().getResources().getString(R.string.test_trick_title_4);
		String testContent_1 = getActivity().getResources().getString(R.string.test_trick_content_3);
		String testContent_2 = getActivity().getResources().getString(R.string.test_trick_content_4);
		data.add(new Trick(10, testTitle_1, testContent_1, "2", "test3", ""));
		data.add(new Trick(11, testTitle_2, testContent_2, "2", "test4", ""));
		data.add(new Trick(12, testTitle_1, testContent_1, "2", "test3", ""));
		data.add(new Trick(13, testTitle_2, testContent_2, "2", "test4", ""));
	}
	
	void MyAsync() {
		String url = getResources().getString(R.string.url_trading);

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String jsonRead = response.toString();
				if (!jsonRead.isEmpty()) {

					try {
						JSONObject object = new JSONObject(jsonRead);
						// JSONArray rows = object.getJSONArray("row");
						// for (int i = 0; i < rows.length(); i++) {
						// JSONObject row = rows.getJSONObject(i);
						String id = object.getString("id");
						String ten_dang_nhap = object.getString("ten_dang_nhap");
						String hovaten = object.getString("ho_va_ten");
						String diachi = object.getString("dia_chi");
						String sdt = object.getString("sdt");
						String mail = object.getString("email");
						// }

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				String s = getActivity().getResources().getString(R.string.login_error);
				Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});

	}
}
