package it.mapyou;


import it.mapyou.network.NotificationServer;
import it.mapyou.network.SettingsNotificationServer;
import it.mapyou.view.Login;
import it.mapyou.view.NotificationActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;



public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentServiceCix";
	private NotificationServer notificationController = null;


	public GCMIntentService() {
		super(SettingsNotificationServer.GOOGLE_SENDER_ID);

		notificationController= NotificationServer.getNotificationServer();

	}


	@Override
	protected void onRegistered(Context context, String registrationId) {


		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();

		Log.i(TAG, "Device registered: regId = " + registrationId);
		notificationController.register(context); // register user 
	}


	/**
	 * Method called on device unregistred
	 * 
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();

		Log.i(TAG, "Device unregistered");
		notificationController.unregister(context, registrationId);
	}



	/**
	 * Method called on Receiving a new message from GCM server
	 * 
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {

	
		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();

		Log.i(TAG, "Received messageEEEEEEEEEEEEEEEEEEEEEEEE");
		
		generateNotification(context);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {

		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();

		Log.i(TAG, "Received deleted messages notification");

		generateNotification(context);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {

		int sec=6000;
		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();

		Log.i(TAG, "Received error: " + errorId);
		Toast.makeText(context, "Errore", sec).show(); 
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {

		if(notificationController == null)
			notificationController = (NotificationServer) getApplicationContext();
		Log.i(TAG, "Received recoverable error: " + errorId);

		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Create a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context) {

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, "Messaggio da MapYou" , when);

		String title ="Invite for MapMe";

		Intent notificationIntent = new Intent(context, NotificationActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


		PendingIntent intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);
	
		String mess="MapYou";
		notification.setLatestEventInfo(context, title, mess, intent);
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);      

	}

}
