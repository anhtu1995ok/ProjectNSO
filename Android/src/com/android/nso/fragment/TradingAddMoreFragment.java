package com.android.nso.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TradingAddMoreFragment extends Fragment implements OnClickListener, OnFocusChangeListener {

	TextView address, phone, email;
	EditText input_title, input_quantity, input_price, input_expired_at, input_content;
	LinearLayout profile;
	Button publish;
	ImageView image;
	ProgressBar loading;

	RadioGroup radioTypeGroup, radioTargetGroup;
	RadioButton radioTypeButton, radioTargetButton;
	View view;
	String radioTypeText, radioTargetText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.trading_addmore, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		address = (TextView) view.findViewById(R.id.address);
		phone = (TextView) view.findViewById(R.id.phone);
		email = (TextView) view.findViewById(R.id.email);

		input_title = (EditText) view.findViewById(R.id.input_title);
		input_price = (EditText) view.findViewById(R.id.input_price);
		input_quantity = (EditText) view.findViewById(R.id.input_quantity);
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

		radioTypeGroup = (RadioGroup) view.findViewById(R.id.radioTypeGroup);
		radioTargetGroup = (RadioGroup) view.findViewById(R.id.radioTargetGroup);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profile:
			Toast.makeText(getActivity(), "profile listener", Toast.LENGTH_SHORT).show();
			break;
		case R.id.publish:
			MyAsync();			
			break;
		default:
			break;
		}
	}
	
	private void getRadioData(){
		int selectedTypeId = radioTypeGroup.getCheckedRadioButtonId();
		radioTypeButton = (RadioButton) view.findViewById(selectedTypeId);
		radioTypeText = radioTypeButton.getText().toString();
		
		int selectedTargetId = radioTargetGroup.getCheckedRadioButtonId();
		radioTargetButton = (RadioButton) view.findViewById(selectedTargetId);
		radioTargetText = radioTargetButton.getText().toString();
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
	
	void MyAsync() {
		AsyncHttpClient handler = new AsyncHttpClient();
		String url = getActivity().getResources().getString(R.string.url_signup);
		
		getRadioData();
		
		RequestParams params = new RequestParams();
		params.add("tieude", input_title.getText().toString());
		params.add("gia", input_price.getText().toString());
		params.add("donvi", input_quantity.getText().toString());
//		params.add("ngay_het_han", input_expired_at.getText().toString());
		params.add("diachi", Utils.getString(getActivity(), "ADDRESS", ""));
		
		params.add("tinh_thanh_id", "");
		
		params.add("taoboi", Utils.getString(getActivity(), "NAME", ""));
		params.add("doituong", radioTypeText);
		params.add("loai", radioTargetText);
		params.add("noidung", input_content.getText().toString());
		

		handler.post(getActivity(), url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String st = response.toString();

				try {
					JSONObject json = new JSONObject(st);
					JSONArray rows = json.getJSONArray("row");
					for (int i = 0; i < rows.length(); i++) {

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

		});

	}
}
