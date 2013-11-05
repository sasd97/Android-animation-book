package com.example.animationdemos.pojo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.animationdemos.R;

public class BitmapUtils {
	int[] mPhotos = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
			R.drawable.e };

	String[] mDescriptions = {
			"This picture was taken while sunbathing in a natural hot spring, which was "
					+ "unfortunately filled with acid, which is a lasting memory from that trip, whenever I "
					+ "I look at my own skin.",
			"I took this shot with a pinhole camera mounted on a tripod constructed out of "
					+ "soda straws. I felt that that combination best captured the beauty of the landscape "
					+ "in juxtaposition with the detritus of mankind.",
			"I don't remember where or when I took this picture. All I know is that I was really "
					+ "drunk at the time, and I woke up without my left sock.",
			"Right before I took this picture, there was a busload of school children right "
					+ "in my way. I knew the perfect shot was coming, so I quickly yelled 'Free candy!!!' "
					+ "and they scattered.",
			"I don't expected a good life,I just want my family's good enough,my daughter have a "
					+ "good education and good food and good place to live." };

	static HashMap<Integer, Bitmap> sBitmapResourceMap = new HashMap<Integer, Bitmap>();

	public ArrayList<PictureData> loadPhotos(Resources resources) {
		ArrayList<PictureData> pictures = new ArrayList<PictureData>();
		for (int i = 0; i < 30; ++i) {
			int resourceId = mPhotos[(int)(Math.random() * mPhotos.length)];
			Bitmap bitmap = getBitmap(resources, resourceId);
			Bitmap thumbnail = getThumbnail(bitmap, 200);
			String description = mDescriptions[(int)(Math.random() * mDescriptions.length)];
			pictures.add(new PictureData(resourceId, description, thumbnail));
		}
		return pictures;
	}

	public static Bitmap getBitmap(Resources resources, int resourceId) {
		Bitmap bitmap = sBitmapResourceMap.get(resourceId);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(resources, resourceId);
			sBitmapResourceMap.put(resourceId, bitmap);
		}
		return bitmap;
	}

	private Bitmap getThumbnail(Bitmap original, int maxDimension) {
		int originalWidth = original.getWidth();
		int originalHeight = original.getHeight();
		int scaleWidth, scaleHeight;
		Log.d("sssssssss", "width:" + originalWidth + ",height:" + originalHeight);

		if (originalWidth >= originalHeight) {
			float scale = (float) maxDimension / originalWidth;
			scaleWidth = 200;
			scaleHeight = (int)(scale * originalHeight);
			Log.d("sssssssss", "scale:" + scale + "scaleWidth:" + scaleWidth + ",scaleHeight:" + scaleHeight);
		} else {
			float scale = (float) maxDimension / originalHeight;
			scaleWidth = (int) (scale * originalWidth);
			scaleHeight = 200;
			Log.d("sssssssss", "scale:" + scale + "scaleWidth:" + scaleWidth + ",scaleHeight:" + scaleHeight);
		}
		return Bitmap.createScaledBitmap(original, scaleWidth, scaleHeight,
				true);
	}

}
