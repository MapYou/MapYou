
package it.mapyou.controller.network;

import android.content.Context;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface NotificationServerInterface {

	 public String request(String page, String parameters);
	 
	 public void register (Context c);
	 
	 public void unregister (Context c, String name);
	 
	 public void acquireWakeLock (Context c);
}
