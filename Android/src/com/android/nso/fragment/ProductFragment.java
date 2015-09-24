package com.android.nso.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.ProductAdapter;
import com.android.nso.extras.ExpandableHeightGridView;
import com.android.nso.model.Product;
import com.android.nso.utils.Time;

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
import android.widget.ScrollView;
import android.widget.TextView;

public class ProductFragment extends Fragment
		implements OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private SwipeRefreshLayout swipeRefreshLayout;
	private ScrollView scrollView;

	private ArrayList<Product> data, data_2, data_3, data_4, data_5, data_6;
	private ExpandableHeightGridView gridView, gridView_2, gridView_3, gridView_4, gridView_5, gridView_6;
	private ProductAdapter adapter, adapter_2, adapter_3, adapter_4, adapter_5, adapter_6;
	private TextView add_1, add_2, add_3, add_4, add_5, add_6;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product, container, false);
		
		scrollView = (ScrollView) view.findViewById(R.id.scrollView);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				initData();
			}
		});

		// init View + Data
		initGridView(view);
		initData();

		return view;
	}

	private void initData() {
		data = initData(data);
		data_2 = initData(data_2);
		data_3 = initData(data_3);
		data_4 = initData(data_4);
		data_5 = initData(data_5);
		data_6 = initData(data_6);

		//
		resizeArrayList(4, data, gridView, adapter);
		resizeArrayList(3, data_2, gridView_2, adapter_2);
		resizeArrayList(3, data_3, gridView_3, adapter_3);
		resizeArrayList(3, data_4, gridView_4, adapter_4);
		resizeArrayList(3, data_5, gridView_5, adapter_5);
		resizeArrayList(0, data_6, gridView_6, adapter_6);
	}

	private void initGridView(View view) {
		// GridView
		gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
		gridView.setExpanded(true);
		gridView.setOnItemClickListener(this);

		gridView_2 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_2);
		gridView_2.setExpanded(true);
		gridView_2.setOnItemClickListener(this);

		gridView_3 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_3);
		gridView_3.setExpanded(true);
		gridView_3.setOnItemClickListener(this);

		gridView_4 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_4);
		gridView_4.setExpanded(true);
		gridView_4.setOnItemClickListener(this);

		gridView_5 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_5);
		gridView_5.setExpanded(true);
		gridView_5.setOnItemClickListener(this);

		gridView_6 = (ExpandableHeightGridView) view.findViewById(R.id.gridView_6);
		gridView_6.setExpanded(true);
		gridView_6.setOnItemClickListener(this);

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

	private ArrayList<Product> initData(ArrayList<Product> data) {
		swipeRefreshLayout.setRefreshing(true);

		int max = 10;
		Log.d("ToanNM", "int max : ===== : " + max);
		data = new ArrayList<Product>();
		String time = Time.getCurrentDate();
		
		test(data);
		for (int i = 0; i < max; i++) {
			data.add(new Product(i, "Carot", "", "16.000", "kg", time, "", "", "Cau giay - HaNoi", "Nam", ""));
		}

		// stopping swipe refresh
		swipeRefreshLayout.setRefreshing(false);
		return data;
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
		initData();
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
	
	void test(ArrayList<Product> data){
		String time = Time.getCurrentDate();
		String testTitle_1 = getActivity().getResources().getString(R.string.test_product_title_1);
		String testTitle_2 = getActivity().getResources().getString(R.string.test_product_title_2);
		String testTitle_3 = getActivity().getResources().getString(R.string.test_product_title_3);
		String testAdress_1 = getActivity().getResources().getString(R.string.test_product_address_1);
		String testAdress_2 = getActivity().getResources().getString(R.string.test_product_address_2);
		String testPrice_1 = getActivity().getResources().getString(R.string.test_product_price_1);
		data.add(new Product(11, testTitle_1, "", "16.000", "kg", time, "", "test1", testAdress_1, "Nam", ""));
		data.add(new Product(12, testTitle_2, "", "27.000", "kg", time, "", "test2", testAdress_2, "Nam", ""));
		data.add(new Product(13, testTitle_3, "", "12.000", testPrice_1, time, "test3", "", testAdress_1, "Nam", ""));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
		Product product = data.get(arg2);
		intent.putExtra("PRODUCT_DATA", product);
		intent.putExtra("TYPE", 22);
		startActivity(intent);
	}

}
