package com.android.nso.fragment;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.TrickAdapter;
import com.android.nso.model.Product;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrickFragment extends Fragment implements OnClickListener,
		SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private TextView tab1, tab2;
	private LinearLayout included_tab_1, included_tab_2;

	private ListView listView, listView_2;
	private SwipeRefreshLayout swipeRefreshLayout, swipeRefreshLayout_2;

	private TrickAdapter trickAdapter1, trickAdapter2;
	ArrayList<Trick> data, data_2;

	private String url_trick1;
	private String url_trick2;
	int tab_active = R.id.tab1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trick, container, false);
		url_trick1 = getResources().getString(R.string.url_trick1);
		url_trick2 = getResources().getString(R.string.url_trick2);
		
		tab1 = (TextView) view.findViewById(R.id.tab1);
		tab2 = (TextView) view.findViewById(R.id.tab2);

		included_tab_1 = (LinearLayout) view.findViewById(R.id.included_tab_1);
		included_tab_2 = (LinearLayout) view.findViewById(R.id.included_tab_2);

		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);

		// tab
		initTab1(included_tab_1);
		initTab2(included_tab_2);

		return view;
	}

	private void initTab1(LinearLayout view) {
		data = new ArrayList<Trick>();
		trickAdapter1 = new TrickAdapter(getActivity(), R.layout.item_trading,
				data);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				data = getDSMeoVatSanXuat(trickAdapter1, url_trick1, swipeRefreshLayout);
			}
		});

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(trickAdapter1);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.abc_slide_in_bottom));
		listView.setOnItemClickListener(this);
	}

	private void initTab2(LinearLayout view) {
		data_2 = new ArrayList<Trick>();
		trickAdapter2 = new TrickAdapter(getActivity(), R.layout.item_trading,
				data_2);
		swipeRefreshLayout_2 = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout_2);
		swipeRefreshLayout_2.setOnRefreshListener(this);

		swipeRefreshLayout_2.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout_2.setRefreshing(true);
				data_2 = getDSMeoVatSanXuat(trickAdapter2, url_trick2, swipeRefreshLayout_2);
			}
		});

		listView_2 = (ListView) view.findViewById(R.id.listView_2);
		listView_2.setAdapter(trickAdapter2);
		listView_2.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.abc_slide_in_bottom));
		listView_2.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int url_image = getActivity().getResources().getIdentifier(
				"xml/" + "trick_tab_onselected", null,
				getActivity().getPackageName());
		switch (v.getId()) {

		case R.id.tab1:
			tab_active = R.id.tab1;
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
			tab_active = R.id.tab2;
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
		if(tab_active == R.id.tab1)
			getDSMeoVatSanXuat(trickAdapter1, url_trick1, swipeRefreshLayout);
		if(tab_active == R.id.tab2)
			getDSMeoVatSanXuat(trickAdapter2, url_trick2, swipeRefreshLayout_2);
	}

	// void test(ArrayList<Trick> data){
	//
	// String testTitle_1 =
	// getActivity().getResources().getString(R.string.test_trick_title_1);
	// String testTitle_2 =
	// getActivity().getResources().getString(R.string.test_trick_title_2);
	// String testContent_1 =
	// getActivity().getResources().getString(R.string.test_trick_content_1);
	// String testContent_2 =
	// getActivity().getResources().getString(R.string.test_trick_content_2);
	// data.add(new Trick(10, testTitle_1, testContent_1, "1", "test1", ""));
	// data.add(new Trick(11, testTitle_2, testContent_2, "1", "test2", ""));
	// data.add(new Trick(12, testTitle_1, testContent_1, "1", "test1", ""));
	// data.add(new Trick(13, testTitle_2, testContent_2, "1", "test2", ""));
	// }
	//
	// void test1(ArrayList<Trick> data){
	//
	// String testTitle_1 =
	// getActivity().getResources().getString(R.string.test_trick_title_3);
	// String testTitle_2 =
	// getActivity().getResources().getString(R.string.test_trick_title_4);
	// String testContent_1 =
	// getActivity().getResources().getString(R.string.test_trick_content_3);
	// String testContent_2 =
	// getActivity().getResources().getString(R.string.test_trick_content_4);
	// data.add(new Trick(10, testTitle_1, testContent_1, "2", "test3", ""));
	// data.add(new Trick(11, testTitle_2, testContent_2, "2", "test4", ""));
	// data.add(new Trick(12, testTitle_1, testContent_1, "2", "test3", ""));
	// data.add(new Trick(13, testTitle_2, testContent_2, "2", "test4", ""));
	// }

	private ArrayList<Trick> getDSMeoVatSanXuat(final Adapter adapter, String url, final SwipeRefreshLayout swipeRefreshLayout) {
		final ArrayList<Trick> data = new ArrayList<Trick>();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		client.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				String jsonRead = response.toString();
				if (!jsonRead.isEmpty()) {
					try {
						JSONObject object = new JSONObject(jsonRead);
						JSONArray rows = object.getJSONArray("row");
						Log.d("TuNT", "Json: "+jsonRead);
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							int id = row.getInt("id");
							String tieuDe = row.getString("tieu_de");
							String noiDung = row.getString("noi_dung");

							ArrayList<String> arrAnh = new ArrayList<String>();
							JSONArray jArrAnh = row.getJSONArray("anh");
							for (int j = 0; j < jArrAnh.length(); j++) {
								JSONObject anh = jArrAnh.getJSONObject(j);
								String url_image = anh.getString("url");
								arrAnh.add(url_image);
							}

							Trick trick = new Trick(id, tieuDe, noiDung, arrAnh);
							data.add(trick);
						}
						Log.d("TuNT", "refresh");
						// stopping swipe refresh
						swipeRefreshLayout.setRefreshing(false);
						((TrickAdapter) adapter).notifyDataSetChanged(data);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Toast.makeText(getActivity(), "Khong the tai du lieu.",
						Toast.LENGTH_SHORT).show();
				// stopping swipe refresh
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Toast.makeText(getActivity(), "Khong the tai du lieu.",
						Toast.LENGTH_SHORT).show();
				// stopping swipe refresh
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		return data;
	}
}
