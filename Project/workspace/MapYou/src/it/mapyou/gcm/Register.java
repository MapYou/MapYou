package it.mapyou.gcm;


import it.mapyou.R;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Register extends Activity {

	private EditText username;
	private EditText email;
	private SharedPreferences sp;
	private NotificationController controller;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		username= (EditText) findViewById(R.id.editText1);
		email= (EditText) findViewById(R.id.editText2);

		controller =(NotificationController) getApplicationContext();
		if(!controller.isConnectingToInternet()){
			controller.showAlertDialog(Register.this, "NoConncection", "Active it", false);
			return;
		}

		if(Configuration.SERVER_URL ==null || Configuration.GOOGLE_SENDER_ID==null || Configuration.SERVER_URL.length()==0 || Configuration.GOOGLE_SENDER_ID.length()==0){
			controller.showAlertDialog(Register.this, "Error Configuration", "Solve it", false);
			return;
		}

	}

	public void register (View v){

		String user= username.getText().toString();
		String em= email.getText().toString();

		if((user.trim().length() >0 && em.trim().length() >0) && (user.length() >0 && em.length() >0)){

			Intent i = new Intent(getApplicationContext(), Gcm.class);
			Editor edit=sp.edit();
			edit.putString("name", user);
			edit.putString("email", em);
			edit.commit();
			startActivity(i);
			finish();
		}
		else{
			controller.showAlertDialog(Register.this, "registration error", "Error", false);
		}
	}

	public void unreg(View v){

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(!GCMRegistrar.isRegistered(getApplicationContext())){
					Toast.makeText(getApplicationContext(), "Device not registered", 5000).show();
				}else{
					GCMRegistrar.unregister(getApplicationContext());
					//controller.unregiter(Register.this, "cc");
				}
				
			}
		}).start();
		
	}
	

}
