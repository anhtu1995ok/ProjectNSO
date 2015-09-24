package com.android.nso.fragment;

import com.android.nso.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TradingAddMoreFragment extends Fragment implements OnClickListener, OnFocusChangeListener {

	TextView address, phone, email;
	EditText input_title, input_price, input_expired_at, input_content;
	LinearLayout profile;
	Button publish;
	ImageView image;
	ProgressBar loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.trading_addmore, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		address = (TextView) view.findViewById(R.id.address);
		phone = (TextView) view.findViewById(R.id.phone);
		email = (TextView) view.findViewById(R.id.email);

		input_title = (EditText) view.findViewById(R.id.input_title);
		input_price = (EditText) view.findViewById(R.id.input_price);
		input_expired_at = (EditText) view.findViewById(R.id.input_expired_at);
		input_content = (EditText) view.findViewById(R.id.input_content);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		input_title.setOnFocusChangeListener(this);
		input_price.setOnFocusChangeListener(this);
		input_expired_at.setOnFocusChangeListener(this);
		input_content.setOnFocusChangeListener(this);

		profile = (LinearLayout) view.findViewById(R.id.profile);
		publish = (Button) view.findViewById(R.id.publish);

		profile.setOnClickListener(this);
		publish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profile:
			Toast.makeText(getActivity(), "profile listener", Toast.LENGTH_SHORT).show();
			break;
		case R.id.publish:
			Toast.makeText(getActivity(), "publish listener", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.input_title:
			setEditTextBorder(R.id.input_title);
			break;
		case R.id.input_price:
			setEditTextBorder(R.id.input_price);
			break;
		case R.id.input_expired_at:
			setEditTextBorder(R.id.input_expired_at);
			break;
		case R.id.input_content:
			setEditTextBorder(R.id.input_content);
			break;
		default:
			break;
		}
	}

	private void setEditTextBorder(int seleteced) {
		int selected = getActivity().getResources().getIdentifier("xml/" + "input_bg_onselected", null,
				getActivity().getPackageName());
		int bg = getActivity().getResources().getIdentifier("xml/" + "input_bg", null, getActivity().getPackageName());
		input_title.setBackgroundResource(bg);
		input_price.setBackgroundResource(bg);
		input_expired_at.setBackgroundResource(bg);
		input_content.setBackgroundResource(bg);
		if (seleteced == R.id.input_title) {
			input_title.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_price) {
			input_price.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_expired_at) {
			input_expired_at.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_content) {
			input_content.setBackgroundResource(selected);
		}
	}
}
