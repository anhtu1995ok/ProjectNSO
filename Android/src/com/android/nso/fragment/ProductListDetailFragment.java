package com.android.nso.fragment;

import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.adapter.ProductAdapter;
import com.android.nso.model.Product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProductListDetailFragment extends Fragment {

	ListView listView;
	ProductAdapter adapter;
	ArrayList<Product> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_news, container, false);
		
		initView(view);
		
		return view;
	}

	private void initView(View view) {
		data = new ArrayList<Product>();
		listView = (ListView) view.findViewById(R.id.listView);

//		Product product = new Product();
//		product.getData(getActivity(), data, listView, adapter);
	}
}
