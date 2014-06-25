package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.network.SettingsNotificationServer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;


public class GcmTest extends Activity {

	private TextView lblMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcm);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.textView1);

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {

			// Register with GCM            
			GCMRegistrar.register(this, SettingsNotificationServer.GOOGLE_SENDER_ID);
			Log.v("code", regId);

		} else {
			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {

				// Skips registration.              
				Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).show();
				return;
			}
		}
	}
}


