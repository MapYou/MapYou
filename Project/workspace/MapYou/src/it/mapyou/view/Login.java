package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.SettingsNotificationServer;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gcm.GCMRegistrar;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Login extends FacebookController {

	private EditText user;
	private EditText password;
	private SharedPreferences sp;
	private User userLogin=null;
	private boolean notification=false;
	private String idnotification="";


	 


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//	stopService(new Intent(getBaseContext(), GPSTracker.class));
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//stopService(new Intent(getBaseContext(), GPSTracker.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


		//				Editor ed = sp.edit();
		//				ed.putString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "p");
		//				ed.putInt(UtilAndroid.KEY_ID_USER_LOGGED, 1);
		//				ed.commit();
		//		
		//				Intent intent= new Intent(Login.this,DrawerMain.class);
		//				//intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		//				startActivity(intent);

		//		Editor ed = sp.edit();
		//		ed.putString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "p");
		//		ed.putInt(UtilAndroid.KEY_ID_USER_LOGGED, 1);
		//		ed.commit();
		//
		//
		//		startService(new Intent(getBaseContext(), GPSTracker.class));
		//
		//		Intent intent= new Intent(Login.this,DrawerMain.class);
		//		//intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		//		startActivity(intent);




		// Code use for notifications (Alert)
		Intent i = getIntent();
		if(i.getStringExtra("notification") != null)
			notification=true;

		user=(EditText) findViewById(R.id.user_login_Login);
		password=(EditText) findViewById(R.id.user_password_Login);
		try {
			DeviceController.getInstance().init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logoutFacebookSession2();

	}

	public void goToNotificationActivity(){
		Intent i = new Intent(Login.this, NotificationActivity.class);
		i.putExtra("viewnotification", "viewnotification");
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
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


	class LoginTask extends AsyncTask<Void, Void, Boolean>{
		private JSONObject jobj;
		private HashMap<String, String> parameters=new HashMap<String, String>();

		private boolean isCorrectLogin;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {

				GCMRegistrar.checkDevice(Login.this);
				GCMRegistrar.checkManifest(Login.this);

				if (!GCMRegistrar.isRegisteredOnServer(Login.this)) {
					GCMRegistrar.register(Login.this, SettingsNotificationServer.GOOGLE_SENDER_ID);
				} else;

				final String regId = GCMRegistrar.getRegistrationId(Login.this);
				if(regId !=null && regId.length() >0){
					idnotification=regId;
					isCorrectLogin=true;
					return isCorrectLogin;
				}else
					return false;

			} catch (Exception e) {
				return false;
			}
		}


		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if(result)
				new UpdateT().execute(result);
			else
				UtilAndroid.makeToast(getApplicationContext(), "Please log-in!", 5000);

		}


		class UpdateT extends AsyncTask<Boolean, Void, JSONObject>{

			@Override
			protected JSONObject doInBackground(Boolean... params) {

				try{

					parameters.put("nickname", ""+user.getText().toString());
					parameters.put("password", ""+password.getText().toString());
					parameters.put("idnot", ""+idnotification);
					jobj=DeviceController.getInstance().getServer().requestJson(SettingsServer.LOGIN_PAGE, DeviceController.getInstance().getServer().setParameters(parameters));

					return jobj;
				}catch (Exception e) {
					Log.v("Errorlogin", ""+e.getMessage());
					return null;
				}
			}

			@Override
			protected void onPostExecute(JSONObject result) {

				super.onPostExecute(result);
				try{
					if(idnotification !=null && idnotification.length() >0){
						if(result!=null ){

							userLogin=getUserLogin(result);
							if(userLogin!=null && userLogin.getModelID() >0){

								Editor ed = sp.edit();
								ed.putString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, userLogin.getNickname());
								ed.putString(UtilAndroid.KEY_EMAIL_USER_LOGGED, userLogin.getEmail());
								ed.putInt(UtilAndroid.KEY_ID_USER_LOGGED, userLogin.getModelID());
								ed.putString(UtilAndroid.KEY_NOTIFICATION,idnotification);
								ed.commit();
								if(notification){
									notification=false;
									goToNotificationActivity();
								}
								
								UtilAndroid.makeToast(getApplicationContext(), "Welcome on MapYou", 5000);
								Intent intent= new Intent(Login.this,DrawerMain.class);
								startActivity(intent);
							}else{
								UtilAndroid.makeToast(getApplicationContext(), "Error Login. Check your credentials.", 5000);
							}

						}else
							UtilAndroid.makeToast(getApplicationContext(), "Error Login", 5000);
					}else{
						UtilAndroid.makeToast(getApplicationContext(), "GCm error", 5000);
					}
				}catch (Exception e) {
					UtilAndroid.makeToast(getApplicationContext(), "User not registred", 5000);
				}
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

		try {
			User user= new User();
			JSONArray jsonArr= json.getJSONArray("User");
			for(int i=0; i<jsonArr.length(); i++){

				json=jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}




}

