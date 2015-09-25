package com.android.nso.fragment;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.ProductAdapter;
import com.android.nso.extras.ExpandableHeightGridView;
import com.android.nso.model.Product;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
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
import android.widget.ScrollView;
import android.widget.TextView;

public class ProductFragment extends Fragment
		implements OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private SwipeRefreshLayout swipeRefreshLayout;
	private ScrollView scrollView;
	View empty_view;

	private ArrayList<Product> data, data_2, data_3, data_4, data_5, data_6;
	private ExpandableHeightGridView gridView, gridView_2, gridView_3, gridView_4, gridView_5, gridView_6;
	private ProductAdapter adapter, adapter_2, adapter_3, adapter_4, adapter_5, adapter_6;
	private TextView add_1, add_2, add_3, add_4, add_5, add_6;

	String url_image = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product, container, false);
		empty_view = inflater.inflate(R.layout.empty_view, null);

		scrollView = (ScrollView) view.findViewById(R.id.scrollView);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				data = getDSSanPham();
			}
		});

		// init View + Data
		initGridView(view);
		initView();
		return view;
	}
	
	private void initGridView(View view) {
		// GridView
		gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
		gridView.setExpanded(true);
		gridView.setOnItemClickListener(this);
		gridView.setEmptyView(empty_view);

		gridView_2 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_2);
		gridView_2.setExpanded(true);
		gridView_2.setOnItemClickListener(this);
		gridView_2.setEmptyView(empty_view);

		gridView_3 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_3);
		gridView_3.setExpanded(true);
		gridView_3.setOnItemClickListener(this);
		gridView_3.setEmptyView(empty_view);

		gridView_4 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_4);
		gridView_4.setExpanded(true);
		gridView_4.setOnItemClickListener(this);
		gridView_4.setEmptyView(empty_view);

		gridView_5 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_5);
		gridView_5.setExpanded(true);
		gridView_5.setOnItemClickListener(this);
		gridView_5.setEmptyView(empty_view);

		gridView_6 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_6);
		gridView_6.setExpanded(true);
		gridView_6.setOnItemClickListener(this);
		gridView_6.setEmptyView(empty_view);

		// Add Button
		add_1 = (TextView) view.findViewById(R.id.add_1);
		add_1.setOnClickListener(this);

		add_2 = (TextView) view.findViewById(R.id.add_2);
		add_2.setOnClickListener(this);

		add_3 = (TextView) view.findViewById(R.id.add_3);
		add_3.setOnClickListener(this);

		add_4 = (TextView) view.findViewById(R.id.add_4);
		add_4.setOnClickListener(this);

		add_5 = (TextView) view.findViewById(R.id.add_5);
		add_5.setOnClickListener(this);

		add_6 = (TextView) view.findViewById(R.id.add_6);
		add_6.setOnClickListener(this);
	}

	private void initView() {
		data = new ArrayList<Product>();
		adapter = new ProductAdapter(getActivity(), R.layout.item_product, data);
		gridView.setAdapter(adapter);
		scrollView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		intent.putExtra("TYPE", 2);
		startActivity(intent);
		switch (v.getId()) {
		case R.id.add_1:
			break;
		case R.id.add_2:
			break;
		case R.id.add_3:
			break;
		case R.id.add_4:
			break;
		case R.id.add_5:
			break;
		case R.id.add_6:
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		data = getDSSanPham();
	}

	private void resizeArrayList(int resize, ArrayList<Product> data, ExpandableHeightGridView gridView,
			ProductAdapter adapter) {
		Random rand = new Random();
		int rs = 2;
		if (resize != 0) {
			rs = rand.nextInt(resize) + 2;
		}
		int max = data.size();
		ArrayList<Product> newData = new ArrayList<Product>();

		if (resize <= max) {
			for (int i = 0; i < rs; i++) {
				newData.add(data.get(i));
			}
			adapter = new ProductAdapter(getActivity(), R.layout.item_product, newData);
			gridView.setAdapter(adapter);
			scrollView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
			adapter.notifyDataSetChanged();

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		Product product = data.get(arg2);
		intent.putExtra("PRODUCT_DATA", product);
		intent.putExtra("TYPE", 22);
		startActivity(intent);
	}

	private ArrayList<Product> getDSSanPham() {
		String url = getResources().getString(R.string.url_product);
		final ArrayList<Product> data = new ArrayList<Product>();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new JsonHttpResponseHandler() {
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
							String ten = row.getString("ten");
							String gia = row.getString("gia");
							String moTa = row.getString("mo_ta");
							String diaChi = row.getString("dia_chi");
							String ngayTao = row.getString("ngay_tao");
							String ngayCapNhat = row.getString("ngay_cap_nhat");
							String tinhThanhId = row.getString("tinh_thanh");
							String tenTinhThanh = row.getString("ten_tinh_thanh");
							String tenVungMien = row.getString("ten_vung_mien");

							ArrayList<String> arrAnh = new ArrayList<String>();
							JSONArray jArrAnh = row.getJSONArray("anh");
							for (int j = 0; j < jArrAnh.length(); j++) {
								JSONObject rowsss = jArrAnh.getJSONObject(j);
								String url_image = rowsss.getString("url");
								arrAnh.add(url_image);
							}
							Product product = new Product(id, ten, gia, moTa, ngayTao, ngayCapNhat, diaChi, tinhThanhId, tenTinhThanh, tenVungMien, arrAnh);
							data.add(product);
						}
						adapter.notifyDataSetChanged(data);
						swipeRefreshLayout.setRefreshing(false);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Log.d("TuNT", "onFailure");
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Log.d("TuNT", "onFailure2");
				swipeRefreshLayout.setRefreshing(false);
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		return data;
	}

}
