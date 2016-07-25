package com.very_news;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class JsonTask extends AsyncTask<String, Void, List<News>> {
	private OnGetJsonResultListener mListener;

	public JsonTask(MyFragment fragment) {
		mListener = fragment;
	}

	@Override
	protected List<News> doInBackground(String... params) {
		try {
			byte[] jsonData = HttpUtil.downData(params[0]);
			String json = new String(jsonData);
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("news");
			List<News> list = new ArrayList<News>();
			for (int i = 1; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);

				String sort_time_str = jsonObject2.getString("sort_time_str");
				String imageUrl = jsonObject2.getString("imageUrl");
				String title = jsonObject2.getString("title");
				String source = jsonObject2.getString("source");

				News news = new News();

				news.setSort_time_str(sort_time_str);
				news.setImageUrl(imageUrl);
				news.setTitle(title);
				news.setSource(source);

				list.add(news);

			}
			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<News> result) {
		super.onPostExecute(result);
		mListener.onGetJsonResult(result);
	}

}
