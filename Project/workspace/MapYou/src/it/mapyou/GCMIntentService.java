package it.mapyou;

import it.mapyou.gcm.Mess;
import it.mapyou.gcm.NotificationController;
import it.mapyou.util.SettingsNotificationServer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
 


public class GCMIntentService extends GCMBaseIntentService {
	 
    private static final String TAG = "GCMIntentService";
    private NotificationController aController = null;
 
    public GCMIntentService() {
        super(SettingsNotificationServer.GOOGLE_SENDER_ID);
    }
    
    
    @Override
    protected void onRegistered(Context context, String registrationId) {
         
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        if(aController == null)
           aController = (NotificationController) getApplicationContext();
         
        Log.i(TAG, "Device registereddddddddddddddddddddd: regId = " + registrationId);
        
       GCMRegistrar.setRegisteredOnServer(context, true); // register on server GCM
      // aController.register(context, "Mario", "ggioo91@hotmail.it", registrationId); // register user 
    }
 
    /**
     * Method called on device unregistred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        if(aController == null)
            aController = (NotificationController) getApplicationContext();
        Log.i(TAG, "Device unregistered");
    
        aController.unregiter(context, registrationId);
    }
 
    /**
     * Method called on Receiving a new message from GCM server
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
         
        if(aController == null)
            aController = (NotificationController) getApplicationContext();
         
        Log.i(TAG, "Received messageEEEEEEEEEEEEEEEEEEEEEEEE");
        String message = intent.getExtras().getString("price");
        
      //  Toast.makeText(context, "Notifica", 6000).show();
        aController.displayMessageOnScreen(context, message);
        // notifies user
        generateNotification(context, message);
    }
 
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
         
        if(aController == null)
            aController = (NotificationController) getApplicationContext();
         
        Log.i(TAG, "Received deleted messages notification");
     
        generateNotification(context, "delete");
    }
 
    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
         
        if(aController == null)
        	aController = (NotificationController) getApplicationContext();
         
        Log.i(TAG, "Received error: " + errorId);
        Toast.makeText(context, "Errore", 6000).show(); 
        
     
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
         
        if(aController == null)
            aController = (NotificationController) getApplicationContext();
         
        
        Log.i(TAG, "Received recoverable error: " + errorId);
     
        return super.onRecoverableError(context, errorId);
    }
 
    /**
     * Create a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
     
    	SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(context);
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
         
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, "Messaggio da "+sp.getString("uu", "")+"" , when);
         
        String title =sp.getString("uu", "");
         
        Intent notificationIntent = new Intent(context, Mess.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        PendingIntent intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        
        notification.defaults |= Notification.DEFAULT_SOUND;
         
       
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      
 
    }
 
}
