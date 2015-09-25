package com.android.nso;

import java.util.ArrayList;
import java.util.List;

import com.android.nso.adapter.PagerAdapter;
import com.android.nso.fragment.NewsFragment;
import com.android.nso.fragment.ProductFragment;
import com.android.nso.fragment.TradingFragment;
import com.android.nso.fragment.TrickFragment;
import com.android.nso.fragment.UserFragment;
import com.android.nso.utils.Keyboard;
import com.android.nso.utils.NetworkUtil;
import com.android.nso.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnTabChangeListener, OnPageChangeListener {

	PagerAdapter pageAdapter;
	private ViewPager mViewPager;
	private TabHost mTabHost;
	String trick_fragment, product_fragment, news_fragment, trading_fragment, user_fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Frame Actionbar <=
		// supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);

		initActionBar();

		trick_fragment = getResources().getString(R.string.trick_fragment);
		product_fragment = getResources().getString(R.string.product_fragment);
		news_fragment = getResources().getString(R.string.news_fragment);
		trading_fragment = getResources().getString(R.string.trading_fragment);
		user_fragment = getResources().getString(R.string.user_fragment);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		initialiseTabHost();

		// Fragments and ViewPager Initialization
		ArrayList<Fragment> fragments = (ArrayList<Fragment>) getFragments();
		pageAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(pageAdapter);
		mViewPager.setOnPageChangeListener(MainActivity.this);

		onFirstRun();
		if (checkInternet()) {
		} else {
			String s = getResources().getString(R.string.no_internet_access);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
		}
	}

	// initActionbar
	private void initActionBar() {

		ActionBar mActionBar = getSupportActionBar();

		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.secondary_action_bar)));

		mActionBar.setCustomView(R.layout.search_layout);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setElevation(0);

		final TextView input = (TextView) mActionBar.getCustomView().findViewById(R.id.input);
		LinearLayout searchLayout = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.search_layout);
		String s = getResources().getString(R.string.search_hint);
		// input.setText(s);
		input.setHint(s);

		searchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SwipeBackActivity.class);
				intent.putExtra("TYPE", 6);
				startActivity(intent);
			}
		});

		// final ImageView close = (ImageView)
		// mActionBar.getCustomView().findViewById(R.id.close);
		// close.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// input.setText("");
		// }
		// });
		//
		// input.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// if (input.getText().toString().trim().length() > 0) {
		// close.setVisibility(View.VISIBLE);
		// } else {
		// input.clearFocus();
		// close.setVisibility(View.GONE);
		// Keyboard.hideKeyboard(MainActivity.this, input);
		// }
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		//
		// }
		// });
	}

	private void onFirstRun() {
		boolean firstRun = Utils.getBoolean(getApplicationContext(), "FIRSTRUN_MAIN", true);
		if (firstRun) {
			Utils.saveBoolean(getApplicationContext(), "FIRSTRUN_MAIN", true);
			Intent intent = new Intent(getApplicationContext(), TipActivity.class);
			intent.putExtra("FIRSTRUN_VALUE", 0);
			startActivity(intent);
		}
	}

	// Method to add a TabHost
	@SuppressLint("InflateParams")
	private static void AddTab(MainActivity activity, TabHost tabHost, String title, int imgRes) {
		TabHost.TabSpec tabSpec = tabHost.newTabSpec(title);
		LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
		View view = inflater.inflate(R.layout.tab_indicator, null, false);
		TextView tv = (TextView) view.findViewById(R.id.title);
		tv.setText(title);
		ImageView img = (ImageView) view.findViewById(R.id.icon);
		img.setImageResource(imgRes);
		tabSpec.setIndicator(view);
		tabSpec.setContent(activity.new MyTabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	// Tabs Creation
	private void initialiseTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		MainActivity.AddTab(this, this.mTabHost, news_fragment, R.drawable.news_selector);
		MainActivity.AddTab(this, this.mTabHost, product_fragment, R.drawable.product_selector);
		MainActivity.AddTab(this, this.mTabHost, trick_fragment, R.drawable.trick_selector);
		MainActivity.AddTab(this, this.mTabHost, trading_fragment, R.drawable.trading_selector);
		MainActivity.AddTab(this, this.mTabHost, user_fragment, R.drawable.user_selector);

		mTabHost.setOnTabChangedListener(this);
	}

	public class MyTabFactory implements TabContentFactory {

		private final Context mContext;

		public MyTabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		int pos = this.mViewPager.getCurrentItem();
		this.mTabHost.setCurrentTab(pos);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		int pos = this.mViewPager.getCurrentItem();
		this.mTabHost.setCurrentTab(pos);
		Keyboard.hideKeyboard(getApplicationContext(), mTabHost);
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	public void onTabChanged(String arg0) {
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
		Keyboard.hideKeyboard(getApplicationContext(), mTabHost);
	}

	// get ListFragment
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();

		TrickFragment fTrick = new TrickFragment();
		ProductFragment fProduct = new ProductFragment();
		NewsFragment fNew = new NewsFragment();
		TradingFragment fTrading = new TradingFragment();
		UserFragment fUser = new UserFragment();

		// FragmentManager fragmentManager = getFragmentManager();
		// FragmentTransaction fragmentTransaction =
		// fragmentManager.beginTransaction();
		// Fragment newFragment = new YourFragmentClass();
		// FragmentTransaction transaction =
		// getFragmentManager().beginTransaction();
		// transaction.replace(R.id.your_fragment_id, newFragment);
		// transaction.addToBackStack(null);
		// transaction.commit();

		fList.add(fNew);
		fList.add(fProduct);
		fList.add(fTrick);
		fList.add(fTrading);
		fList.add(fUser);

		return fList;
	}

	// // back onclick <=
	// private static final int TIME_INTERVAL = 2000; // # milliseconds, desired
	// // time passed between two
	// // back presses.
	// private long mBackPressed;
	//
	// @Override
	// public void onBackPressed() {
	// String s =
	// getApplicationContext().getResources().getString(R.string.exit);
	// if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
	// super.onBackPressed();
	// return;
	// } else {
	// Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
	// }
	// mBackPressed = System.currentTimeMillis();
	//
	// }

	boolean checkInternet() {
		int conn = NetworkUtil.getConnectivityStatus(getApplicationContext());
		if (conn == NetworkUtil.TYPE_WIFI || conn == NetworkUtil.TYPE_MOBILE) {
			return true;
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
			return false;
		}
		return false;
	}
}
