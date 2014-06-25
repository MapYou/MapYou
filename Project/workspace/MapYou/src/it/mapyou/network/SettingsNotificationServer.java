/**
 * 
 */
package it.mapyou.network;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface SettingsNotificationServer {

	public static final String SERVER_URL="http://mapyou.altervista.org/myMapYou/Controller_GCM/";
	
	public static final String PAGE_REGISTER="register.php";
	public static final String PAGE_UNREGISTER="unregister.php";
	
	//Google project id
	public static final String GOOGLE_SENDER_ID ="244609467363";
 
	//Tag
	public static final String TAG="GCM Android";
	public static final String MESSAGE="Help me";
	 

	public static final String EXTRA_MESSAGE = "extraaa";

}
