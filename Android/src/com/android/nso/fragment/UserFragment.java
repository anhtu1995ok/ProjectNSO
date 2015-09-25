package com.android.nso.fragment;

import java.lang.reflect.Field;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.manager.SessionManager;
import com.android.nso.utils.NetworkUtil;
import com.android.nso.utils.Utils;
import com.android.nso.utils.Validation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragment extends Fragment implements OnClickListener, OnFocusChangeListener {

	private LinearLayout loginLayout, loggedin_layout;
	private FrameLayout logoutLayout;

	private EditText input_name, input_password;
	private Button signup, login;

	private TextView fName, uName, address, email, phone;
	String id, ten_dang_nhap, matkhau, hovaten, diachi, sdt, mail, makichhoat, trangthai;

	String username, password;
	SessionManager session;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
		loggedin_layout = (LinearLayout) view.findViewById(R.id.loggedin_layout);
		logoutLayout = (FrameLayout) view.findViewById(R.id.logout_layout);
		logoutLayout.setOnClickListener(this);

		input_name = (EditText) view.findViewById(R.id.input_name);
		input_password = (EditText) view.findViewById(R.id.input_password);
		input_name.setOnFocusChangeListener(this);
		input_password.setOnFocusChangeListener(this);

		try {
			// https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
			Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
			f.setAccessible(true);
			f.set(input_name, R.xml.cursor_actionbarcolor);
			f.set(input_password, R.xml.cursor_actionbarcolor);
		} catch (Exception ignored) {
		}

		signup = (Button) view.findViewById(R.id.signup);
		login = (Button) view.findViewById(R.id.login);
		signup.setOnClickListener(this);
		login.setOnClickListener(this);

		fName = (TextView) view.findViewById(R.id.fullname);
		uName = (TextView) view.findViewById(R.id.name);
		address = (TextView) view.findViewById(R.id.address);
		email = (TextView) view.findViewById(R.id.email);
		phone = (TextView) view.findViewById(R.id.phone);

		session = SessionManager.getInstance(getActivity());

		// if (checkInternet()) {
		// MyAsync();
		// }
		boolean loggedin = Utils.getBoolean(getActivity(), "LOGGEDIN", false);
		if (loggedin) {
			loggedin();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup:
			if (checkInternet()) {
				Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
				intent.putExtra("INPUT_NAME", input_name.getText().toString());
				intent.putExtra("INPUT_PASSWORD", input_password.getText().toString());
				intent.putExtra("TYPE", 5);
				startActivity(intent);
			} else {
				String s = getActivity().getResources().getString(R.string.no_internet_access);
				Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.login:
			if (checkInternet()) {
				login();
			} else {
				String s = getActivity().getResources().getString(R.string.no_internet_access);
				Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.logout_layout:
			getActivity().finish();
			session.logoutUser();
			Utils.saveBoolean(getActivity(), "LOGGEDIN", false);

			break;
		default:
			break;
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
		}

	}

	private void setEditTextBorder(int seleteced) {
		int selected = getActivity().getResources().getIdentifier("xml/" + "input_bg_onselected", null,
				getActivity().getPackageName());
		int bg = getActivity().getResources().getIdentifier("xml/" + "input_bg", null, getActivity().getPackageName());
		input_name.setBackgroundResource(bg);
		input_password.setBackgroundResource(bg);
		if (seleteced == R.id.input_name) {
			input_name.setBackgroundResource(selected);
		} else if (seleteced == R.id.input_password) {
			input_password.setBackgroundResource(selected);
		}
	}

	boolean checkInternet() {
		int conn = NetworkUtil.getConnectivityStatus(getActivity());
		if (conn == NetworkUtil.TYPE_WIFI || conn == NetworkUtil.TYPE_MOBILE) {
			return true;
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
			return false;
		}
		return false;
	}

	private void login() {
		username = input_name.getText().toString().trim().toLowerCase();
		password = input_password.getText().toString().trim().toLowerCase();

		Validation.isName(getActivity(), input_name, true);
		Validation.isPassword(getActivity(), input_password, true);

		if (username.trim().length() > 0 && password.trim().length() > 0
				&& Validation.isName(getActivity(), input_name, true)
				&& Validation.isPassword(getActivity(), input_password, true)) {
			boolean logggedin = Utils.getBoolean(getActivity(), "LOGGEDIN", false);
			if (logggedin) {
				loggedin();
			} else {
				MyAsync();
			}

		}
	}

	void MyAsync() {
		String url = getResources().getString(R.string.url_login);

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("tendangnhap", username);
		params.add("matkhau", password);
		login.setEnabled(false);

		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String jsonRead = response.toString();
				login.setEnabled(true);
				if (!jsonRead.isEmpty()) {

					try {
						JSONObject object = new JSONObject(jsonRead);
						// JSONArray rows = object.getJSONArray("row");
						// for (int i = 0; i < rows.length(); i++) {
						// JSONObject row = rows.getJSONObject(i);
						id = object.getString("id");
						ten_dang_nhap = object.getString("ten_dang_nhap");
						hovaten = object.getString("ho_va_ten");
						diachi = object.getString("dia_chi");
						sdt = object.getString("sdt");
						mail = object.getString("email");

						Long phone = Long.parseLong(sdt);

						Utils.saveString(getActivity(), "USERNAME", ten_dang_nhap);
						Utils.saveString(getActivity(), "FULLNAME", hovaten);
						Utils.saveString(getActivity(), "ADDRESS", diachi);
						Utils.saveLong(getActivity(), "PHONE", phone);
						Utils.saveString(getActivity(), "EMAIL", mail);
						Utils.saveBoolean(getActivity(), "LOGGEDIN", true);
						// }

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				String s = getActivity().getResources().getString(R.string.login_error);
				Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				login.setEnabled(true);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});

	}

	void loggedin() {
		loginLayout.setVisibility(View.GONE);
		loggedin_layout.setVisibility(View.VISIBLE);
		logoutLayout.setVisibility(View.VISIBLE);

		fName.setText(Utils.getString(getActivity(), "FULLNAME", ""));
		uName.setText(Utils.getString(getActivity(), "USERNAME", ""));
		address.setText(Utils.getString(getActivity(), "ADDRESS", ""));
		email.setText(Utils.getString(getActivity(), "EMAIL", ""));
		phone.setText(Utils.getString(getActivity(), "PHONE", ""));
	}
}
