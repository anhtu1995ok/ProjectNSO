package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.extras.StretchyImageView;
import com.android.nso.model.Product;
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

		title.setText(product.getTen());
		description.setText(product.getMoTa());
		address.setText(product.getDiaChi());
		price.setText(product.getGia() + " "+vnd);
		updated_at.setText(product.getNgayCapNhat());
		region.setText(product.getTenTinhThanh());

		if (product.getArrAnh().size() > 0) {
			String url_image = product.getArrAnh().get(0);
			Glide.with(getActivity())
				.load(url_image)
				.placeholder(getResources().getDrawable(R.drawable.ic_launcher))
				.error(R.drawable.ic_launcher)
				.into(image);
		}else{
			image.setImageResource(R.drawable.ic_launcher);
		}
	}
}
