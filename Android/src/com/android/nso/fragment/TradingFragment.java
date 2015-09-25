package com.android.nso.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.TradingAdapter;
import com.android.nso.model.Trading;
import com.android.nso.utils.AccentRemover;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TradingFragment extends Fragment
		implements SwipeRefreshLayout.OnRefreshListener, OnClickListener, OnItemClickListener {

	private SwipeRefreshLayout swipeRefreshLayout;
	private ArrayList<Trading> data, data_2, data_3, data_4, newData;
	private ListView listView, listView_2, listView_3, listView_4;
	private TradingAdapter adapter;
	private LinearLayout add_layout;

	private TextView add_1, add_2, add_3, add_4, add_5, add_6, result;
	View view;

	private Spinner spinner;
	private ArrayList<TradingSpinnerData> sData;

	View empty_view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_trading, container, false);
		empty_view = inflater.inflate(R.layout.empty_view, null);

		initView(view);

		return view;
	}

	private void initView(View view) {
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				data = getDSRaoVat();
			}
		});

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setEmptyView(empty_view);
		listView.setOnItemClickListener(this);

		data = new ArrayList<Trading>();
		adapter = new TradingAdapter(getActivity(), R.layout.item_trading, data);
		listView.setAdapter(adapter);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));

		spinner = (Spinner) view.findViewById(R.id.spinner);

		add_layout = (LinearLayout) view.findViewById(R.id.add_layout);
		add_layout.setOnClickListener(this);

		result = (TextView) view.findViewById(R.id.spinner_result);
		initSpinnerData();
	}

	private void initSpinnerData() {
		String[] array = getActivity().getResources().getStringArray(R.array.trick_spinner_array);
		sData = new ArrayList<TradingSpinnerData>();
		int max = array.length;
		for (int i = 0; i < max; i++) {
			TradingSpinnerData tsd = new TradingSpinnerData(array[i]);
			sData.add(tsd);
		}
		SpinnerAdapter sAdapter = new SpinnerAdapter(getActivity(), R.layout.item_spinner, sData);
		spinner.setAdapter(sAdapter);
	}

	@Override
	public void onRefresh() {
		data = getDSRaoVat();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_layout:
			Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
			intent.putExtra("TYPE", 44);
			startActivity(intent);
			break;
		}
	}

	private class TradingSpinnerData {
		String name;

		public TradingSpinnerData(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	private class SpinnerAdapter extends ArrayAdapter<TradingSpinnerData> {
		Context context;

		public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<TradingSpinnerData> data) {
			super(context, textViewResourceId, data);
			this.context = context;
			sData = data;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View mView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.item_spinner, parent, false);
			TextView textView = (TextView) mView.findViewById(R.id.spinner_text);
			TradingSpinnerData tsd = sData.get(position);
			textView.setText(tsd.getName());
			return mView;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View mView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.item_spinner_dropdown, parent, false);
			TextView textView = (TextView) mView.findViewById(R.id.spinner_dropdown_text);
			final TradingSpinnerData tsd = sData.get(position);
			textView.setText(tsd.getName());
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					filter(tsd.getName(), data);
				}
			});
			return mView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		// News news = (News) arg0.getAdapter().getItem(arg2);
		Trading trading = data.get(arg2);
		intent.putExtra("TRADING_DATA", trading);
		intent.putExtra("TYPE", 4);
		startActivity(intent);
	}

	private ArrayList<Trading> getDSRaoVat() {
		final ArrayList<Trading> data = new ArrayList<Trading>();
		String url = getResources().getString(R.string.url_trading);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String jsonRead = response.toString();
				if (!jsonRead.isEmpty()) {
					try {
						JSONObject object = new JSONObject(jsonRead);
						JSONArray rows = object.getJSONArray("row");
						Log.d("TuNT", "Json: " + jsonRead);
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							int id = row.getInt("id");
							String tieuDe = row.getString("tieu_de");
							String gia = row.getString("gia");
							String donVi = row.getString("don_vi");
							String tenDonVi = row.getString("ten_don_vi");
							String diaChi = row.getString("dia_chi");
							String tinhThanhId = row.getString("tinh_thanh_id");
							String tenTinhThanh = row.getString("ten_tinh_thanh");
							String tenVungMien = row.getString("ten_vung_mien");
							String taoBoiId = row.getString("tao_boi_id");
							String taoBoiTen = row.getString("tao_boi_ten");
							String doiTuong = row.getString("doi_tuong");
							String tenDoiTuong = row.getString("ten_doi_tuong");
							String loai = row.getString("loai");
							String tenLoai = row.getString("ten_loai");
							String noiDung = row.getString("noi_dung");
							String ngayTao = row.getString("ngay_tao");
							String ngayHetHan = row.getString("ngay_het_han");

							ArrayList<String> arrAnh = new ArrayList<String>();
							JSONArray jArrAnh = row.getJSONArray("anh");
							for (int j = 0; j < jArrAnh.length(); j++) {
								JSONObject anh = jArrAnh.getJSONObject(j);
								String url_image = anh.getString("url");
								arrAnh.add(url_image);
							}

							Trading trading = new Trading(id, tieuDe, gia, donVi, tenDonVi, diaChi, tinhThanhId,
									tenTinhThanh, tenVungMien, taoBoiId, taoBoiTen, doiTuong, tenDoiTuong, loai,
									tenLoai, noiDung, ngayTao, ngayHetHan, arrAnh);
							data.add(trading);
						}
						Log.d("TuNT", "refresh");
						// stopping swipe refresh
						swipeRefreshLayout.setRefreshing(false);
						adapter.notifyDataSetChanged(data);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Toast.makeText(getActivity(), "Khong the tai du lieu.", Toast.LENGTH_SHORT).show();
				// stopping swipe refresh
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Toast.makeText(getActivity(), "Khong the tai du lieu.", Toast.LENGTH_SHORT).show();
				// stopping swipe refresh
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		return data;
	}

	private void filter(String filter, ArrayList<Trading> parentData) {
		ArrayList<Trading> newData = new ArrayList<Trading>();

		String vReplace = AccentRemover.getInstance(getActivity()).removeAccent(filter);
		if (!parentData.isEmpty()) {
			for (int i = 0; i < parentData.size(); i++) {

				String type = AccentRemover.getInstance(getActivity()).removeAccent(parentData.get(i).getLoai());
				type.replace(" ", "");
				if (type.toLowerCase().contains(vReplace.toLowerCase())) {
					newData.add(parentData.get(i));
				}
			}
			String s = getActivity().getResources().getString(R.string.result);
			result.setText("" + newData.size() + " " + s);

			adapter = new TradingAdapter(getActivity(), R.layout.item_trading, newData);
			listView.setAdapter(adapter);
			listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
		}

	}
}
