package com.android.nso;

import com.android.nso.extras.StretchyImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TipActivity extends Activity {

	LinearLayout main;
	StretchyImageView image;
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip_layout);

		Intent intent = getIntent();
		int type = intent.getIntExtra("FIRSTRUN_VALUE", 0);

		main = (LinearLayout) findViewById(R.id.main);
		main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		image = (StretchyImageView) findViewById(R.id.image);
		text = (TextView) findViewById(R.id.text);
		
		String tip_1 = getResources().getString(R.string.tip_1);
		String tip_2 = getResources().getString(R.string.tip_2);

		if (type == 0) {
			image.setImageResource(R.drawable.ic_hand);
			image.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_top));
			text.setText(tip_1);
		} else if (type == 1) {
			image.setImageResource(R.drawable.ic_hand_horizontal);
			image.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_in_right));
			text.setText(tip_2);
		}

	}

}
