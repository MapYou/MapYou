package it.mapyou.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapParser {
	
	
	public static boolean saveImageToInternalStorage(Bitmap b,Context c) {

		try {
			 
			FileOutputStream fos = c.openFileOutput("profile.png", Context.MODE_PRIVATE);

			b.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();

			return true;
		} catch (Exception e) {
			Log.e("saveToInternalStorage()", e.getMessage());
			return false;
		}
	}
	
	public static Bitmap getThumbnail(Context c) {

		Bitmap thumbnail = null;

		try {
			File filePath = c.getFileStreamPath("profile.png");
			FileInputStream fi = new FileInputStream(filePath);
			thumbnail = BitmapFactory.decodeStream(fi);
			
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
			return null;
		}
		return thumbnail;

		
	}
	

	 
	public static boolean deleteBitmapInternalStorage (Context c, String nameFile){
		File file = c.getFileStreamPath(nameFile);
		boolean deleted = file.delete();
		return deleted;
	}

}
