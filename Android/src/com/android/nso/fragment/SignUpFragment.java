package com.android.nso.fragment;

import java.lang.reflect.Field;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.utils.Validation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SignUpFragment extends Fragment implements OnClickListener, OnFocusChangeListener {

	private EditText input_name, input_password, input_password_2, input_email, input_fullname, input_address,
			input_phone;
	private Button signup;
	private ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.signup_layout, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {

		input_name = (EditText) view.findViewById(R.id.input_name);
		input_password = (EditText) view.findViewById(R.id.input_password);
		input_password_2 = (EditText) view.findViewById(R.id.input_password_2);
		input_email = (EditText) view.findViewById(R.id.input_email);
		input_fullname = (EditText) view.findViewById(R.id.input_fullname);
		input_address = (EditText) view.findViewById(R.id.input_address);
		input_phone = (EditText) view.findViewById(R.id.input_phone);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		input_name.setOnFocusChangeListener(this);
		input_password.setOnFocusChangeListener(this);
		input_password_2.setOnFocusChangeListener(this);
		input_email.setOnFocusChangeListener(this);
		input_fullname.setOnFocusChangeListener(this);
		input_address.setOnFocusChangeListener(this);
		input_phone.setOnFocusChangeListener(this);
		
		try {
			// https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
			Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
			f.setAccessible(true);
			f.set(input_name, R.xml.cursor_actionbarcolor);
			f.set(input_password, R.xml.cursor_actionbarcolor);
			f.set(input_password_2, R.xml.cursor_actionbarcolor);
			f.set(input_email, R.xml.cursor_actionbarcolor);
			f.set(input_fullname, R.xml.cursor_actionbarcolor);
			f.set(input_address, R.xml.cursor_actionbarcolor);
			f.set(input_phone, R.xml.cursor_actionbarcolor);
		} catch (Exception ignored) {
		}

		String name_value = "", password_value = "";
		try {
			name_value = getActivity().getIntent().getStringExtra("INPUT_NAME");
			password_value = getActivity().getIntent().getStringExtra("INPUT_PASSWORD");
		} catch (Exception e) {
		}

		if (!name_value.equals("")) {
			input_name.setText(name_value);
		}
		if (!password_value.equals("")) {
			input_password.setText(name_value);
		}

		signup = (Button) view.findViewById(R.id.signup);
		signup.setOnClickListener(this);

		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		Log.d("ToanNM", "done initView SignupActivity" + name_value);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup:
			validateData();
			break;

		default:
			break;
		}
	}

	private void validateData() {

//		String name = input_name.getText().toString();
//		String password = input_password.getText().toString();
//		String email = input_email.getText().toString();
//		String fullname = input_fullname.getText().toString();
//		String address = input_address.getText().toString();
//		String phone = input_name.getText().toString();
//
//		Log.d("ToanNM", "validateData : " + name);
//		if (!name.equals("") && !password.equals("") && !email.equals("") && !fullname.equals("") && !address.equals("")
//				&& !phone.equals("")) {
//			Toast.makeText(getActivity(), "SignUp Completed", Toast.LENGTH_SHORT).show();
//		}
//		if (name.equals("") || password.equals("") || email.equals("") || fullname.equals("") || address.equals("")
//				|| phone.equals("")) {
//			Toast.makeText(getActivity(), "Data is missing", Toast.LENGTH_SHORT).show();
//		}
		Validation.isName(getActivity(), input_name, true);
		Validation.isPassword(getActivity(), input_password, true);
		Validation.isSamePassword(getActivity(), input_password_2, input_password.getText().toString(), true);
		Validation.isEmailAddress(getActivity(), input_email, true);
		Validation.isFullName(getActivity(), input_fullname, true);
		Validation.isAddress(getActivity(), input_address, true);
		Validation.isPhoneNumber(getActivity(), input_phone, true);
	}

	private void setEditTextBorder(int seleteced) {
		int selected = getActivity().getResources().getIdentifier("xml/" + "input_bg_onselected", null,
				getActivity().getPackageName());
		int bg = getActivity().getResources().getIdentifier("xml/" + "input_bg", null,
				getActivity().getPackageName());
		input_name.setBackgroundResource(bg);
		input_password.setBackgroundResource(bg);
		input_password_2.setBackgroundResource(bg);
		input_email.setBackgroundResource(bg);
		input_fullname.setBackgroundResource(bg);
		input_address.setBackgroundResource(bg);
		input_phone.setBackgroundResource(bg);
		if (seleteced == R.id.input_name) {
			input_name.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_password) {
			input_password.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_password_2) {
			input_password_2.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_email) {
			input_email.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_fullname) {
			input_fullname.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_address) {
			input_address.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_phone) {
			input_phone.setBackgroundResource(selected);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.input_name:
			setEditTextBorder(R.id.input_name);
			break;
		case R.id.input_password:
			setEditTextBorder(R.id.input_password);
			break;
		case R.id.input_password_2:
			setEditTextBorder(R.id.input_password_2);
			break;
		case R.id.input_email:
			setEditTextBorder(R.id.input_email);
			break;
		case R.id.input_fullname:
			setEditTextBorder(R.id.input_fullname);
			break;
		case R.id.input_address:
			setEditTextBorder(R.id.input_address);
			break;
		case R.id.input_phone:
			setEditTextBorder(R.id.input_phone);
			break;
		default:
			break;
		}
	}
	
	void CongVanXuLyAsync() {
		AsyncHttpClient handler = new AsyncHttpClient();
		String url = getActivity().getResources().getString(R.string.url_signup);

		RequestParams params = new RequestParams();
		params.add("ten_dang_nhap", input_name.getText().toString());
		params.add("matkhau", input_password.getText().toString());
		params.add("email", input_email.getText().toString());
		params.add("ho_va_ten", input_fullname.getText().toString());
		params.add("diachi", input_address.getText().toString());
		params.add("sdt", input_phone.getText().toString());

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
