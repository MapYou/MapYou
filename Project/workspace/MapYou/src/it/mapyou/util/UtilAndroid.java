/**
 * 
 */
package it.mapyou.util;

import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UtilAndroid {

	public static final int MAX_NUM_USERS_FOR_MAPME = 15;
	
	public static final String KEY_NICKNAME_USER_LOGGED = "nickname_user_logged";
	public static final String KEY_EMAIL_USER_LOGGED = "email_user_logged";
	public static final String KEY_ID_USER_LOGGED = "id_user_logged";
	public static final String KEY_NOTIFICATION = "id_notification";
	public static final String ID_FACEBOOK = "facebook";
	public static final String CURRENT_ACTIVITY_TAG = "currentAct";
	
	// NAME OF FILE FOR CACHE
	
	public static final String NAME_OF_FILE_CACHE="mapyou";
	public static final String ROUTES="routes";

	public static MapMe CURRENT_MAPME;

	public static MappingUser CURRENT_MAPPING;
	
	public static final String NAME ="mapyou";
	
	public static final int MAX_NUM_USER_IN_MAPME = 20;


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



