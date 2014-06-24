package it.mapyou.gcm;

import it.mapyou.R;

import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gcm.GCMRegistrar;

public class Mess extends Activity {
	
	
	private EditText edit;
	private NotificationController controller;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mess);
		
		edit=(EditText) findViewById(R.id.editText1);
		controller= (NotificationController) getApplicationContext();
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}
	
	public void send (View v){
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		final String id= sp.getString("idreg", "");
		new Thread(new Runnable() {
			 

			@Override
			public void run() {
				
		    	HashMap<String, String> params = new HashMap<String, String>();
		    	
		    	params.put("regId", id );
		    	params.put("message", edit.getText().toString());
		    	
		    	Log.v("Message", params.get("message"));
		    	Log.v("Reggggggggggg", params.get("regId"));
		    	Log.v("RegggggggggggWithGoogle", regId);
		    	
		    	
		    	controller.post("http://infooweb.altervista.org/gio/GCM/send_push_notification_message.php", params);
				
			}
		}).start();
		
		
	}

}
