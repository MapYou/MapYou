package it.mapyou.gcm;

public interface Configuration {
	
	//url server
	public static final String SERVER_URL="http://infooweb.altervista.org/gio/GCM/register.php";
	public static final String UNREGISTER="http://infooweb.altervista.org/gio/GCM/unregister.php";
	
	//Google project id
	public static final String GOOGLE_SENDER_ID ="244609467363";
	//228763172463

	//Tag
	public static final String TAG="GCM Android";
	public static final String MESSAGE="Help me";
	public static final String DISPLAY_MESSAGE_ACTION ="com.androidexample.gcm.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "extraaa";

}
