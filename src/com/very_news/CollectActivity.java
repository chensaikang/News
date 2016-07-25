package com.very_news;

import com.qiaoqizhen.very_news.R;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	private ListView mCollect;
	private static Newsdao newsdao;
	private TextView tv_empty;
	private SimpleCursorAdapter adapter;
	private LoaderManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect);
		initView();
		manager = getLoaderManager();
		manager.initLoader(1, null, this);
		newsdao = new Newsdao(this);
	}

	private void initView() {
		mCollect = (ListView) findViewById(R.id.lv_collect);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
		mCollect.setEmptyView(tv_empty);

		registerForContextMenu(mCollect);
		adapter = new SimpleCursorAdapter(CollectActivity.this,
				R.layout.collect_item, null,
				new String[] { "title", "imageUrl" }, new int[] {
						R.id.tv_title, R.id.tv_imageUrl },
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mCollect.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.collect, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.collect, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo menuInfo = item.getMenuInfo();
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int position = info.position;
		long itemId = adapter.getItemId(position);
		switch (item.getItemId()) {
		case R.id.item_delete:
			int delete = newsdao.delete("news", "_id=?",
					new String[] { String.valueOf(itemId) });
			if (delete > 0) {
				manager.restartLoader(1, null, CollectActivity.this);
				Toast.makeText(CollectActivity.this, "É¾³ý³É¹¦", 0).show();
			} else {
				Toast.makeText(CollectActivity.this, "É¾³ýÊ§°Ü", 0).show();
			}
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new MyLoader(this, args);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

	static class MyLoader extends AsyncTaskLoader<Cursor> {
		private Bundle mBundle;

		public MyLoader(Context context, Bundle args) {
			super(context);
			this.mBundle = args;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

		@Override
		public Cursor loadInBackground() {
			String sql = "select * from news";
			return newsdao.select(sql, null);
		}
	}

}
