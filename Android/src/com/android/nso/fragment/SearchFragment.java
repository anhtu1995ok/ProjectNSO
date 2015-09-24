package com.android.nso.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.android.nso.R;
import com.android.nso.adapter.SearchAdapter;
import com.android.nso.model.Search;
import com.android.nso.utils.Keyboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment implements OnItemClickListener{

	EditText input;
	ImageView close;
	private ListView listView;
	ArrayList<Search> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {

		input = (EditText) view.findViewById(R.id.input);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					return true;
				}
				return false;
			}
		});
		
		try {
			// https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
			Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
			f.setAccessible(true);
			f.set(input, R.xml.cursor_actionbarcolor);
		} catch (Exception ignored) {
		}
		String s = getResources().getString(R.string.search_hint);
		input.setHint(s);
		close = (ImageView) view.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				input.setText("");
			}
		});

		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (input.getText().toString().trim().length() > 0) {
					close.setVisibility(View.VISIBLE);
				} else {
					input.clearFocus();
					close.setVisibility(View.GONE);
					Keyboard.hideKeyboard(getActivity(), input);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// init ListView
		listView = (ListView) view.findViewById(R.id.listView);
		data = initData();
	}

	private ArrayList<Search> initData() {
		ArrayList<Search> data = new ArrayList<Search>();

		int max = 4;
		for (int i = 0; i < max; i++) {
			data.add(new Search(i, "Search number : " + i, 1));
		}

		SearchAdapter adapter = new SearchAdapter(getActivity(), R.layout.item_search_list, data);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		return data;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
	
}
