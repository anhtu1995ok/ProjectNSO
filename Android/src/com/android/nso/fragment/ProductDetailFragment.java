package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.extras.StretchyImageView;
import com.android.nso.model.Product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment {

	TextView title, description, address, price, updated_at, region;
	StretchyImageView image;
	ProgressBar loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.item_product_full, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {

		Intent intent = getActivity().getIntent();
		Product product = (Product) intent.getSerializableExtra("PRODUCT_DATA");

		title = (TextView) view.findViewById(R.id.title);
		description = (TextView) view.findViewById(R.id.description);
		address = (TextView) view.findViewById(R.id.address);
		price = (TextView) view.findViewById(R.id.price);
		updated_at = (TextView) view.findViewById(R.id.updated_at);
		region = (TextView) view.findViewById(R.id.region);
		image = (StretchyImageView) view.findViewById(R.id.image);

		String vnd = getActivity().getResources().getString(R.string.vnd);

		title.setText(product.getName());
		// description.setText(product.getDescription());
		address.setText(product.getAddress());
		price.setText(product.getPrice() + " " + vnd + "/" + product.getQuantity());
		updated_at.setText(product.getUpdated_at());
		region.setText(product.getProvince());

		String url_image = product.getUrl_image();
		if (url_image.equals("test1")) {
			image.setImageResource(R.drawable.ic_test_product_1);
		} else if (url_image.equals("test2")) {
			image.setImageResource(R.drawable.ic_test_product_2);
		}
	}
}
