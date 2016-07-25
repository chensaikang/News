package com.very_news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

	public static Bitmap decodeSampledBitmapFromByteArray(byte[] data,
			BitmapSize maxSize, Bitmap.Config config) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		options.inInputShareable = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);

		options.inSampleSize = calculateInSampleSize(options,
				maxSize.getWidth(), maxSize.getHeight());
		options.inJustDecodeBounds = false;
		if (config != null) {
			options.inPreferredConfig = config;
		}

		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length, options);
		} catch (Throwable e) {
			return null;
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int maxWidth, int maxHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;

		int inSampleSize = 1;

		if (width > maxWidth || height > maxHeight) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) maxHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) maxWidth);
			}

			final float totalPixels = width * height;

			final float maxTotalPixels = maxWidth * maxHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
				inSampleSize++;
			}
		}

		return inSampleSize;
	}

}
