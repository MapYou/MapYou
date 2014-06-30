package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class Login extends FacebookController {

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

		logoutFacebookSession2();
	}

	// onclick Login
	public void login (View v){
		if(verifyField())
			new  LoginTask().execute();
	}

	// onclick Registration
	public void registerMapYou(View v){
		Intent intent=new Intent(Login.this,Register.class);  
		startActivityForResult(intent, 2);
	}

	// onclick Facebook login
	public void face(View v){
		setConnection();
		getID();
	}


	public void forgotMapYou(View v){
		Intent intent=new Intent(Login.this,Forgot.class);  
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	// Result activity registration for set user && password 
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


	class LoginTask extends AsyncTask<Void, Void, JSONObject>{
		private JSONObject jobj;
		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
		}

		@Override
		protected JSONObject doInBackground(Void... params) {

			try {
				parameters.put("nickname", URLEncoder.encode(user.getText().toString(), "UTF-8"));
				parameters.put("password",  URLEncoder.encode(password.getText().toString(), "UTF-8"));
				jobj=controller.getServer().requestJson(SettingsServer.LOGIN_PAGE, controller.getServer().setParameters(parameters));

				return jobj;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			if(result!=null){
				User userLogin=getUserLogin(result);
				UtilAndroid.makeToast(getApplicationContext(), "Login", 5000);
				Editor ed = sp.edit();
				ed.putString("nickname", userLogin.getNickname());
				ed.putString("email", userLogin.getEmail());
				ed.commit();
				Intent intent= new Intent(Login.this,DrawerMain.class);
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


	public User getUserLogin (JSONObject json){

		User user= new User();
		try {
			JSONArray jsonArr= json.getJSONArray("User");
			for(int i=0; i<jsonArr.length(); i++){

				json=jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	return user;
}
}
