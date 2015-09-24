package com.android.nso.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.adapter.TradingAdapter;
import com.android.nso.model.Product;
import com.android.nso.model.Trading;
import com.android.nso.utils.Time;

import android.annotation.SuppressLint;
import android.content.Context;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_trading, container, false);

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
				initData();
				initSpinnerData();
			}
		});

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		// listView_2 = (ListView) view.findViewById(R.id.listView_2);
		// listView_3 = (ListView) view.findViewById(R.id.listView_3);
		// listView_4 = (ListView) view.findViewById(R.id.listView_4);

		// Add Button
		// add_1 = (TextView) view.findViewById(R.id.add_1);
		// add_1.setOnClickListener(this);
		//
		// add_2 = (TextView) view.findViewById(R.id.add_2);
		// add_2.setOnClickListener(this);
		//
		// add_3 = (TextView) view.findViewById(R.id.add_3);
		// add_3.setOnClickListener(this);
		//
		// add_4 = (TextView) view.findViewById(R.id.add_4);
		// add_4.setOnClickListener(this);

		// add_layout = (LinearLayout) view.findViewById(R.id.add_layout);
		// add_layout.setOnClickListener(this);

		spinner = (Spinner) view.findViewById(R.id.spinner);

		add_layout = (LinearLayout) view.findViewById(R.id.add_layout);
		add_layout.setOnClickListener(this);
		
		result = (TextView) view.findViewById(R.id.spinner_result);
		
	}

	// private void setListViewHeightBasedOnChildren(ListView listView) {
	// ListAdapter listAdapter = listView.getAdapter();
	// if (listAdapter == null)
	// return;
	//
	// int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
	// MeasureSpec.UNSPECIFIED);
	// int totalHeight = 0;
	// View view = null;
	// for (int i = 0; i < listAdapter.getCount(); i++) {
	// view = listAdapter.getView(i, view, listView);
	// if (i == 0)
	// view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
	// LayoutParams.WRAP_CONTENT));
	//
	// view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	// totalHeight += view.getMeasuredHeight();
	// }
	// ViewGroup.LayoutParams params = listView.getLayoutParams();
	// params.height = totalHeight + (listView.getDividerHeight() *
	// (listAdapter.getCount() - 1) + 20);
	// listView.setLayoutParams(params);
	// listView.requestLayout();
	// }

	private void initData() {
		data = initData(data, listView, adapter);
		// data_2 = initData(data_2, listView_2, adapter);
		// data_3 = initData(data_3, listView_3, adapter);
		// data_4 = initData(data_4, listView_4, adapter);
		//
		// setListViewHeightBasedOnChildren(listView);
		// setListViewHeightBasedOnChildren(listView_2);
		// setListViewHeightBasedOnChildren(listView_3);
		// setListViewHeightBasedOnChildren(listView_4);
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

	private ArrayList<Trading> initData(ArrayList<Trading> data, ListView listView, TradingAdapter adapter) {
		swipeRefreshLayout.setRefreshing(true);
		Random rand = new Random();
		int max = rand.nextInt(10) + 5;
		data = new ArrayList<Trading>();
		String time = Time.getCurrentDate();
		test(data);
		for (int i = 0; i < max; i++) {
			data.add(new Trading(i, "Tin rao vat so " + i, "Tp.HCM", "Admin", "Ca nhan", "Can ban", "Noi dung Tin rao vat so " + i, "",
					time, time, "", ""));
		}

		adapter = new TradingAdapter(getActivity(), R.layout.item_trading, data);
		listView.setAdapter(adapter);
		listView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom));
		adapter.notifyDataSetChanged();
		result.setText("" + data.size());
		
		// stopping swipe refresh
		swipeRefreshLayout.setRefreshing(false);
		return data;
	}

	@Override
	public void onRefresh() {
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// // case R.id.add_layout:
		// // Toast.makeText(getActivity(), "You need to login first!!!",
		// // Toast.LENGTH_SHORT).show();
		// // break;
		// case R.id.add_1:
		// Toast.makeText(getActivity(), "Add_1.onclick",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.add_2:
		// Toast.makeText(getActivity(), "Add_2.onclick",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.add_3:
		// Toast.makeText(getActivity(), "Add_3.onclick",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.add_4:
		// Toast.makeText(getActivity(), "Add_4.onclick",
		// Toast.LENGTH_SHORT).show();
		// break;
		// default:
		// break;
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
			TradingSpinnerData tsd = sData.get(position);
			textView.setText(tsd.getName());
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
	
	void test(ArrayList<Trading> data){
		String time = Time.getCurrentDate();
		String need_to_sell = getActivity().getResources().getString(R.string.need_to_sell);
		String need_to_buy = getActivity().getResources().getString(R.string.need_to_buy);
		
		String testTitle_1 = getActivity().getResources().getString(R.string.trading_title_1);
		String testTitle_2 = getActivity().getResources().getString(R.string.trading_title_2);
		String testContent_1 = getActivity().getResources().getString(R.string.trading_content_1);
		String testContent_2 = getActivity().getResources().getString(R.string.trading_content_2);
		data.add(new Trading(11, testTitle_1, "Tp.HCM", "Admin", "Ca nhan", need_to_sell, testContent_1, "",
				time, time, "", ""));
		data.add(new Trading(12, testTitle_2, "Tp.HCM", "Mod", "Doanh nghiep", need_to_buy, testContent_2, "",
				time, time, "", ""));
		data.add(new Trading(13, testTitle_1, "Tp.HCM", "SMod", "Ca nhan", need_to_sell, testContent_1, "",
				time, time, "", ""));
		data.add(new Trading(14, testTitle_2, "Tp.HCM", "Admin", "Doanh nghiep", need_to_buy, testContent_2, "",
				time, time, "", ""));
	}
}
