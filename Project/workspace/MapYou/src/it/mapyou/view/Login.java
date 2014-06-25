package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Login extends Activity {

	private DeviceController controller;
	private EditText user;
	private EditText password;
	private SharedPreferences sp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		user=(EditText) findViewById(R.id.user_login_Login);
		password=(EditText) findViewById(R.id.user_password_Login);
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login (View v){
		if(verifyField())
			new  LoginTask().execute();
	}

	public void registerMapYou(View v){
		Intent intent=new Intent(Login.this,Register.class);  
		startActivityForResult(intent, 2);
	}
//	public void face(View v){
//		Intent intent=new Intent(Login.this,LoginFacebook.class);  
//		startActivity(intent);
//	}


	public void forgotMapYou(View v){
		Intent intent=new Intent(Login.this,Forgot.class);  
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data){  
		super.onActivityResult(requestCode, resultCode, data);  
		if(requestCode==2){  
			String u=data.getStringExtra("user");
			String p=data.getStringExtra("password");   
			user.setText(u);  
			password.setText(p);
		}  
	}  

	class LoginTask extends AsyncTask<Void, Void, String>{
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
				parameters.put("nickname", URLEncoder.encode(user.getText().toString(), "UTF-8"));
				parameters.put("password",  URLEncoder.encode(password.getText().toString(), "UTF-8"));
				b=controller.getServer().request(SettingsServer.LOGIN_PAGE, controller.getServer().setParameters(parameters));

				return b;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result.toString().equalsIgnoreCase("true")){
				UtilAndroid.makeToast(getApplicationContext(), "Login", 5000);
				Editor ed = sp.edit();
				ed.putString("nickname", user.getText().toString());
				ed.commit();
				Intent intent= new Intent(Login.this,MapMeMenu.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Error Login", 5000);
			}
		}
	}

	public boolean verifyField(){

		boolean verify=false;
		String nick = user.getText().toString().replace(" ", "");
		String pass = password.getText().toString().replace(" ", "");

		if(nick.equalsIgnoreCase("")){
			UtilAndroid.makeToast(getApplicationContext(), "User cannot be empty!", 5000);
			verify=false;}
		else if(pass.equalsIgnoreCase("")){
			UtilAndroid.makeToast(getApplicationContext(), "Password cannot be empty!", 5000);
			verify=false;}
		else if(pass.length() >30){ 
			UtilAndroid.makeToast(getApplicationContext(), "Password is not valid!", 5000);
			verify=false;}
		else if(nick.length() >30){ 
			UtilAndroid.makeToast(getApplicationContext(), "User is not valid!", 5000);
			verify=false;}
		else 
			verify=true;
		return verify;
	}
}
