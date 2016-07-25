package com.very_news;

import com.qiaoqizhen.very_news.R;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
	private ImageView image;

	public ImageTask(ImageView iv_image) {
		this.image = iv_image;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		byte[] data = HttpUtil.downData(params[0]);
		BitmapSize maxSize = new BitmapSize(200, 300);
		return BitmapUtil.decodeSampledBitmapFromByteArray(data, maxSize,
				Config.ARGB_4444);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null) {
			image.setImageBitmap(result);
		} else {
			image.setImageResource(R.drawable.icn_no_data);
		}
	}

}
