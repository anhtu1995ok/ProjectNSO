package com.android.nso.fragment;

import com.android.nso.R;
import com.android.nso.model.Trading;
import com.android.nso.utils.Utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TradingDetailFragment extends Fragment implements OnClickListener {

	TextView title, content, region, type, user, time, target, expired_at;
	LinearLayout contact_Layout, mail_Layout, user_Layout;
	ImageView image;
	ProgressBar loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.item_trading_full, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {

		Intent intent = getActivity().getIntent();
		Trading trading = (Trading) intent.getSerializableExtra("TRADING_DATA");

		title = (TextView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		region = (TextView) view.findViewById(R.id.region);
		type = (TextView) view.findViewById(R.id.type);
		user = (TextView) view.findViewById(R.id.user);
		time = (TextView) view.findViewById(R.id.time);
		target = (TextView) view.findViewById(R.id.target);
		expired_at = (TextView) view.findViewById(R.id.expired_at);

		title.setText(trading.getName());
		content.setText(trading.getContent());
		region.setText(trading.getRegion());
		type.setText(trading.getType());
		user.setText(trading.getCreated_by());
		time.setText(trading.getCreated_at());
		target.setText(trading.getTarget());
		expired_at.setText(trading.getExpired_at());

		contact_Layout = (LinearLayout) view.findViewById(R.id.contact_layout);
		mail_Layout = (LinearLayout) view.findViewById(R.id.mail_layout);
//		user_Layout = (LinearLayout) view.findViewById(R.id.user_layout);
		contact_Layout.setOnClickListener(this);
		mail_Layout.setOnClickListener(this);
//		user_Layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact_layout:
			Long sdt = Utils.getLong(getActivity(), "PHONE");
			if (sdt != 0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + sdt));
				startActivity(callIntent);
			}
			break;
		case R.id.mail_layout:
			String email = Utils.getString(getActivity(), "EMAIL", "");
			if (email.length() > 2) {
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);

				/* Fill it with Data */
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { email });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						"Text");

				/* Send it off to the Activity-Chooser */
				startActivity(Intent.createChooser(emailIntent,
						"Send mail..."));
			}
			break;
		case R.id.user_layout:
			Toast.makeText(getActivity(), "on User listener", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
