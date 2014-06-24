package it.mapyou.gcm;


import it.mapyou.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
 

public class Gcm extends Activity {

	private SharedPreferences sp;
	public static String user,email;
	private TextView lblMessage;
	private NotificationController aController;
	private AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcm);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//		user=sp.getString("name", "");
//		email=sp.getString("email", "");
//		
		
		aController = (NotificationController)getApplicationContext();

		GCMRegistrar.checkDevice(this);
		// Make sure the manifest permissions was properly set 
		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.textView1);

		// Register custom Broadcast receiver to show messages on activity
		registerReceiver(mHandleMessageReceiver, new IntentFilter(Configuration.DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		//Toast.makeText(getApplicationContext(), regId, 5000).show();
		// Check if regid already presents
		if (regId.equals("")) {

			// Register with GCM            
			GCMRegistrar.register(this, Configuration.GOOGLE_SENDER_ID);
			//final String regIdd = GCMRegistrar.getRegistrationId(this);
			Log.v("code", regId);
			 
		} else {
			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {

				// Skips registration.              
				Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).
				show();
//				} else {
//
//				final Context context = this;
//				mRegisterTask = new AsyncTask<Void, Void, Void>() {
//
//					@Override
//					protected Void doInBackground(Void... params) {
//
//						// Register on our server
//						// On server creates a new user
//						aController.register(context, user, email, regId);
//						return null;
//
//						 
//					}
//
//					@Override
//					protected void onPostExecute(Void result) {
//						mRegisterTask = null;
//					}
//
//				};

				// execute AsyncTask
				//mRegisterTask.execute();
			}
		}
	}
	     


	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			  String newMessage = intent.getExtras().getString(Configuration.EXTRA_MESSAGE);
	             
	           
	            aController.acquireWakeLock(getApplicationContext());  
	            lblMessage.append(newMessage + "");         
	            Toast.makeText(getApplicationContext(),  "Got Message: " + newMessage, Toast.LENGTH_LONG).show();
	            aController.releaseWakeLock();

		}
	};
	
    @Override
    protected void onDestroy() {
        // Cancel AsyncTask
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            // Unregister Broadcast Receiver
            unregisterReceiver(mHandleMessageReceiver);
             
            //Clear internal resources.
            GCMRegistrar.onDestroy(this);
             
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", 
                      "> " + e.getMessage());
        }
        super.onDestroy();
    }
    
    public void notification (View v){
    	
//    	Intent i= new Intent(this, Send.class);
//    	startActivity(i);
    	
//    	final String regId = GCMRegistrar.getRegistrationId(this);
//    	
//    	new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//		    	HashMap<String, String> params = new HashMap<String, String>();
//		    	params.put("message", "Alert");
//		    	params.put("regId", regId);
//		    	
//		    	aController.post("http://infooweb.altervista.org/gio/GCM/send_push_notification_message.php", params);
//				
//			}
//		}).start();
//    	
    }
}


