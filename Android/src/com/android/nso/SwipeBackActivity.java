package com.android.nso;

import java.util.ArrayList;
import java.util.List;

import com.android.nso.adapter.PagerAdapter;
import com.android.nso.fragment.NewsDetailFragment;
import com.android.nso.fragment.ProductDetailFragment;
import com.android.nso.fragment.ProductListDetailFragment;
import com.android.nso.fragment.SearchFragment;
import com.android.nso.fragment.SignUpFragment;
import com.android.nso.fragment.TradingAddMoreFragment;
import com.android.nso.fragment.TradingDetailFragment;
import com.android.nso.fragment.TranslucentFragment;
import com.android.nso.fragment.TrickDetailFragment;
import com.android.nso.utils.Keyboard;
import com.android.nso.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class SwipeBackActivity extends FragmentActivity implements OnPageChangeListener {

	PagerAdapter pageAdapter;
	private ViewPager mViewPager;
	int type = 1;// 1 New 2 Product 22 Product Detail 3 Trick_CB 4 Trading 44
					// Trading_Addmore 5 SignUp

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);

		Intent intent = getIntent();
		type = intent.getIntExtra("TYPE", 1);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		// Fragments and ViewPager Initialization
		ArrayList<Fragment> fragments = (ArrayList<Fragment>) getFragments();
		pageAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(pageAdapter);
		mViewPager.setOnPageChangeListener(SwipeBackActivity.this);
		mViewPager.setCurrentItem(1);

		onFirstRun();
	}

	private void onFirstRun() {
		boolean firstRun = Utils.getBoolean(getApplicationContext(), "FIRSTRUN_SWIPE", true);
		if (firstRun) {
			Utils.saveBoolean(getApplicationContext(), "FIRSTRUN_SWIPE", true);
			Intent intent = new Intent(getApplicationContext(), TipActivity.class);
			intent.putExtra("FIRSTRUN_VALUE", 1);
			startActivity(intent);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		int pos = this.mViewPager.getCurrentItem();
		if (pos == 0) {
			finish();
			Keyboard.hideKeyboard(getApplicationContext(), mViewPager);
		} else
			this.mViewPager.setCurrentItem(pos);
	}

	@Override
	public void onPageSelected(int arg0) {
		int pos = this.mViewPager.getCurrentItem();
		if (pos == 0) {
			finish();
		} else
			this.mViewPager.setCurrentItem(pos);
	}

	// get ListFragment
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();

		TranslucentFragment f1 = new TranslucentFragment();
		fList.add(f1);
		if (type == 1) {
			NewsDetailFragment f2 = new NewsDetailFragment();
			fList.add(f2);
		} else if (type == 2) {
			ProductListDetailFragment f2 = new ProductListDetailFragment();
			fList.add(f2);
		} else if (type == 22) {
			ProductDetailFragment f2 = new ProductDetailFragment();
			fList.add(f2);
		} else if (type == 3) {
			TrickDetailFragment f2 = new TrickDetailFragment();
			fList.add(f2);
		} else if (type == 4) {
			TradingDetailFragment f2 = new TradingDetailFragment();
			fList.add(f2);
		} else if (type == 44) {
			TradingAddMoreFragment f2 = new TradingAddMoreFragment();
			fList.add(f2);
		} else if (type == 5) {
			SignUpFragment f2 = new SignUpFragment();
			fList.add(f2);
		} else if (type == 6) {
			SearchFragment f2 = new SearchFragment();
			fList.add(f2);
		}

		mViewPager.setCurrentItem(1);

		return fList;
	}
}
