package com.very_news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qiaoqizhen.very_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyFragment extends Fragment implements OnGetJsonResultListener {

	private MyAdapter adapter;
	private Activity activity;
	private ListView mLv;
	private LoaderManager manager;
	private Newsdao newsdao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myfragment, null);
		mLv = (ListView) view.findViewById(R.id.lv_list);
		int index = getArguments().getInt("Index", 0);
		String[] channels = new String[] { "recommend", "hotspot", "society",
				"entertainment", "finance", "internet", "fashion" };
		String baseUrl = MyConfig.NEWS_URL_START + channels[index]
				+ MyConfig.NEWS_URL_END;
		adapter = new MyAdapter();
		new JsonTask(this).execute(baseUrl);
		mLv.setAdapter(adapter);
		manager = getLoaderManager();
		// manager.initLoader(1, null, this);
		newsdao = new Newsdao(activity);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onGetJsonResult(final List<News> result) {
		adapter.setDatas(result);
		mLv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder mDialog = new AlertDialog.Builder(activity);
				String[] collect = new String[] { "收藏", "查看收藏" };
				List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
				for (int i = 0; i < collect.length; i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("collect", collect[i]);
					mList.add(map);
				}
				SimpleAdapter adapter2 = new SimpleAdapter(activity, mList,
						R.layout.dialog_item, new String[] { "collect" },
						new int[] { R.id.tv_collect });
				mDialog.setAdapter(adapter2, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							String title = result.get(position).getTitle();
							String imageUrl = result.get(position)
									.getImageUrl();
							Log.i("TAG", imageUrl);
							ContentValues values = new ContentValues();
							values.put("title", title);
							values.put("imageUrl", imageUrl);
							long result = newsdao.insert("news", values);
							if (result > 0) {
								Toast.makeText(activity, "收藏成功", 0).show();
							} else {
								Toast.makeText(activity, "收藏失败", 0).show();
							}
							break;
						case 1:
							Intent intent = new Intent(activity,
									CollectActivity.class);
							startActivity(intent);
							break;

						default:
							break;
						}
					}
				});
				mDialog.create();
				mDialog.show();
				return false;
			}
		});
	}

}
