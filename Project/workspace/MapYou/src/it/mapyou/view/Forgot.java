 
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.EmailValidator;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Forgot extends Activity {


	private EditText email;
	private DeviceController controller;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		 
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		email= (EditText) findViewById(R.id.email_forgot);
	}

	public void forgot(View v){

		if(verifyField()){
			new Thread(new Runnable() {
				@Override
				public void run() {
					new ForgotTask().execute();
				}
			}).start();
		}
	}

	class ForgotTask extends AsyncTask<Void, Void, String>{

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
				parameters.put("email",  email.getText().toString());
				b=controller.getServer().request(SettingsServer.FORGOT_PAGE, controller.getServer().setParameters(parameters));
				return b.toString();
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result.equalsIgnoreCase("send")){
				UtilAndroid.makeToast(getApplicationContext(), "Please check your e-mail address", 5000);
				 
				Intent i = new Intent(Forgot.this, Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Email or User not registred", 5000);
			}
		}

	}

	public boolean verifyField(){

		EmailValidator e= new EmailValidator();
		boolean verify=false;
		String mail = email.getText().toString().replace(" ", "");

		if(mail.equalsIgnoreCase("")){
			UtilAndroid.makeToast(getApplicationContext(), "Email cannot be empty!", 5000);
			verify=false;}
		else if(!e.validate(mail)){ 
			UtilAndroid.makeToast(getApplicationContext(), "Email is not valid!", 5000);
			verify=false;}
		else 
			verify=true;
		return verify;
	}

	@SuppressWarnings("deprecation")
	public void helpForgot (View v){

		AlertDialog	alert2= new AlertDialog.Builder(this).create();
		alert2.setTitle("Help!");
		alert2.setMessage("Enter the email address with which you registered. "+
				"You will receive an email to reset your password!");
		alert2.setIcon(R.drawable.ic_launcher);
		alert2.setButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alert2.show();
	}
}
