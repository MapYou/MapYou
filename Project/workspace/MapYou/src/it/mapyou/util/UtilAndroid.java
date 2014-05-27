/**
 * 
 */
package it.mapyou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UtilAndroid {


	public static boolean findConnection(Context c){
		//check Internet conenction.

		boolean conn=false;
		ConnectivityManager check = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (check != null) 
		{
			NetworkInfo[] info = check.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i <info.length; i++) 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						conn=true;
					}
		}
		return conn;
	}
	
	public static void makeToast(Context c, String text, int mill){
		Toast.makeText(c.getApplicationContext(), text, mill).show();
	}
}



