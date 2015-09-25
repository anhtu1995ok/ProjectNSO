package com.android.nso.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.adapter.ProductAdapter;
import com.android.nso.utils.Time;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, description, price, quantity, created_at, updated_at, url_image, address, province, state;

	public Product() {

	}

	public Product(int id, String name, String description, String price
//			, String quantity
			, String created_at,
			String updated_at, String url_image, String address, String province, String state) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
//		this.quantity = quantity;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.url_image = url_image;
		this.address = address;
		this.province = province;
		this.state = state;
	}

	public ArrayList<Product> getData(Context context, ArrayList<Product> data, ListView listView,
			ProductAdapter adapter) {
		int max = 10;
		Log.d("ToanNM", "int max : ===== : " + max);
		data = new ArrayList<Product>();
		for (int i = 0; i < max; i++) {
			data.add(new Product(i, "San pham thu " + i, "", "16.000", "", "", "", "", "", ""));
		}
		adapter = new ProductAdapter(context, R.layout.item_product_full, data);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		return data;
	}

	public ArrayList<Product> getData(Context context, ArrayList<Product> data, SwipeRefreshLayout swipeRefreshLayout,
			ListView listView, ProductAdapter adapter) {
		swipeRefreshLayout.setRefreshing(true);

		int max = 10;
		Log.d("ToanNM", "int max : ===== : " + max);
		data = new ArrayList<Product>();
		String time = Time.getCurrentDate();
		for (int i = 0; i < max; i++) {
			data.add(
					new Product(i, "San pham thu " + i, "", "16.000", "", time, "", "Cau giay - HaNoi", "Nam", ""));
		}
		adapter = new ProductAdapter(context, R.layout.item_product_full, data);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		// stopping swipe refresh
		swipeRefreshLayout.setRefreshing(false);
		return data;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
