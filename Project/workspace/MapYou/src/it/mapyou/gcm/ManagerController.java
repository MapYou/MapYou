package it.mapyou.gcm;

import java.util.Map;

import android.content.Context;

public interface ManagerController {
	
	public void register(Context c, String name, String email, String regId );
	public void unregiter(Context context, String regId);
	public void post(String endPoint, Map<String,String> parameters);
	public boolean isConnectingToInternet();
	public void displayMessageOnScreen(Context c,String message);
	public void acquireWakeLock(Context context);

}
