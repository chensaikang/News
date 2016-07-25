package com.very_news;

import java.util.List;

import com.qiaoqizhen.very_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private List<News> list;

	public void setDatas(List<News> result) {
		this.list = result;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			Context context = parent.getContext();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);

			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_source = (TextView) convertView
					.findViewById(R.id.tv_source);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.iv_image = (ImageView) convertView.findViewById(R.id.image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		News news = list.get(position);

		String title = news.getTitle();
		String imageUrl = news.getImageUrl();
		String time = news.getSort_time_str();
		String source = news.getSource();

		holder.tv_title.setText(title);
		holder.tv_source.setText(source);
		holder.tv_time.setText(time);

		if ("".equals(imageUrl)) {
			holder.iv_image.setImageResource(R.drawable.icn_no_data);
		} else {
			new ImageTask(holder.iv_image).execute(imageUrl);
		}

		return convertView;
	}

	class ViewHolder {
		TextView tv_title, tv_source, tv_time;
		ImageView iv_image;
	}

}
