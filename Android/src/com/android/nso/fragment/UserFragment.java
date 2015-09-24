package com.android.nso.fragment;

import java.lang.reflect.Field;

import com.android.nso.R;
import com.android.nso.SwipeBackActivity;
import com.android.nso.utils.Validation;

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

public class UserFragment extends Fragment implements OnClickListener, OnFocusChangeListener {

	private LinearLayout loginLayout, loggedin_layout;
	private FrameLayout logoutLayout;

	private EditText input_name, input_password;
	private Button signup, login;

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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup:
			Intent intent = new Intent(getActivity(), SwipeBackActivity.class);
			intent.putExtra("INPUT_NAME", input_name.getText().toString());
			intent.putExtra("INPUT_PASSWORD", input_password.getText().toString());
			intent.putExtra("TYPE", 5);
			startActivity(intent);
			break;
		case R.id.login:
			// Validation.isName(getActivity(), input_name, true);
			// Validation.isPassword(getActivity(), input_password, true);
			loginLayout.setVisibility(View.GONE);
			loggedin_layout.setVisibility(View.VISIBLE);
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
}
