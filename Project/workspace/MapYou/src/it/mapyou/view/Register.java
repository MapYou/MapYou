/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.gcm.Gcm;

import it.mapyou.util.EmailValidator;
import it.mapyou.util.SettingsNotificationServer;
import it.mapyou.util.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Register extends Activity{

	private EditText nickname;
	private EditText password;
	private EditText confirmP;
	private EditText email;
	private DeviceController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		nickname= (EditText) findViewById(R.id.user_registration);
		password= (EditText) findViewById(R.id.user_password_registration);
		confirmP= (EditText) findViewById(R.id.userconfirm_password);
		email= (EditText) findViewById(R.id.user_email);

	}

	public void register (View v){

		boolean isCorrect= verifyItems();
		if(isCorrect)
			new Thread(new Runnable() {
				@Override
				public void run() {
					new Registration().execute();

				}
			}).start();
	}



	class Registration extends AsyncTask<Void, Void, String>{
		private String b;
		private HashMap<String, String> parameters=new HashMap<String, String>();


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				parameters.put("nickname", URLEncoder.encode(nickname.getText().toString(), "UTF-8"));
				parameters.put("password",  URLEncoder.encode(password.getText().toString(), "UTF-8"));
				parameters.put("email", email.getText().toString());

				b=controller.getServer().request(SettingsServer.REGISTER_PAGE, controller.getServer().setParameters(parameters));

				return b;
			} catch (Exception e) {
				return null;
			}
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result.toString().equalsIgnoreCase("1")){
				UtilAndroid.makeToast(getApplicationContext(), "Registred", 5000);
				Intent intentMessage=new Intent();
				intentMessage.putExtra("user",nickname.getText().toString());
				intentMessage.putExtra("password",password.getText().toString());
				setResult(2,intentMessage);
				finish();

			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Username already exists ", 5000);
			}
		}
	}

	public boolean verifyItems(){

		EmailValidator val= new EmailValidator();
		boolean correct=false;
		if(nickname.getText().toString().equalsIgnoreCase("") || 
				password.getText().toString().equalsIgnoreCase("") || 
				confirmP.getText().toString().equalsIgnoreCase("") || 
				email.getText().toString().equalsIgnoreCase("")){
			UtilAndroid.makeToast(getApplicationContext(), "The Fields cannot be empty", 4000);
			correct=false;
		}
		else if(nickname.getText().toString().length() >30 ||
				password.getText().toString().length() >30 ||
				confirmP.getText().toString().length() >30){
			UtilAndroid.makeToast(getApplicationContext(), "The Fields must be length 30 Chacarcters", 4000);
			correct=false;
		}
		else if( 
				password.getText().toString().compareTo(confirmP.getText().toString())!=0){
			UtilAndroid.makeToast(getApplicationContext(), "Password doesn't match", 4000);
			correct=false;
		}else if(!val.validate(email.getText().toString())){
			UtilAndroid.makeToast(getApplicationContext(), "Email is not ammissible", 4000);
			correct=false;
		}else
			correct=true;

		return correct;
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent=new Intent(Register.this,Login.class);  
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void gcmm (View v){
		Intent i = new Intent(Register.this, Gcm.class);
		startActivity(i);
	}


}
