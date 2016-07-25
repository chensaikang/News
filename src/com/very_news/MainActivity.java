package com.very_news;

import com.qiaoqizhen.very_news.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] channelName = new String[] { "推荐", "热点", "社会", "娱乐", "财经",
				"互联网", "时尚" };
		ActionBar bar = getActionBar();
		bar.setNavigationMode(bar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < channelName.length; i++) {
			bar.addTab(bar.newTab().setText(channelName[i])
					.setTabListener(new MyListener()));
		}
	}

	class MyListener implements TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			MyFragment fragment = new MyFragment();
			Bundle args = new Bundle();
			args.putInt("index", tab.getPosition());
			fragment.setArguments(args);
			ft.replace(R.id.ll_container, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

	}

}
