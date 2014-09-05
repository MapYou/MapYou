package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.network.SettingsNotificationServer;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
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
	private String idnotification;
	private int notificationID;

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


		// Code use for notifications (Alert)
//		Intent i = getIntent();
//		if(i.getStringExtra("notification") != null)
//		{
//			notification=true;
//			try {
//				notificationID = i.getExtras().getInt("notification_id");
//			} catch (Exception e) {
//				notificationID = -1;
//			}
//		}else{
//			notification=false;
//			notificationID = -1;
//		}
		notification=true;
		notificationID=109;

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
		Bundle b = new Bundle();
		b.putInt("notification_id", notificationID);
		Intent i = new Intent(Login.this, NotificationActivity.class);
		i.putExtras(b);
		i.putExtra("viewnotification", "viewnotification");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


		//Intent i = new Intent(getApplicationContext(), ServiceConnection.class);
		//startService(i);


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

								else{

									//									for(int i=0; i<1000; i++){
									//										MapMe m = new MapMe();
									//										m.setAdministrator(userLogin);
									//										m.setMaxNumUsers(15);
									//										m.setName("nome_"+i);
									//										SimpleSegment sg = new SimpleSegment();
									//										Point endPoint = new Point();
									//										Point startPoint = new Point();
									//										startPoint.setLatitude(41.129761285949);
									//										startPoint.setLongitude(14.782620817423);
									//										startPoint.setLocation("Benevento Benevento, Italy");
									//										endPoint.setLatitude(41.560254489813);
									//										endPoint.setLongitude(14.662716016173);
									//										endPoint.setLocation("Campobasso, Italy");
									//										sg.setEndPoint(endPoint);
									//										sg.setStartPoint(startPoint);
									//										m.setSegment(sg);
									//										new SaveMapMe().execute(m);
									//									}


									UtilAndroid.makeToast(getApplicationContext(), "Welcome on MapYou", 5000);
									Intent intent= new Intent(Login.this,DrawerMain.class);
									startActivity(intent);
								}

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

	class SaveMapMe extends AsyncTask<MapMe, Void, MapMe>{

		String response="";
		HashMap<String, String> parameters= new HashMap<String, String>();

		@Override
		protected MapMe doInBackground(MapMe... params) {


			try {
				Segment r= params[0].getSegment();
				parameters.put("user", URLEncoder.encode(params[0].getAdministrator().getNickname().toString(), "UTF-8"));

				parameters.put("name", URLEncoder.encode(params[0].getName().toString(), "UTF-8"));
				parameters.put("slat", ""+r.getStartPoint().getLatitude());
				parameters.put("slon", ""+r.getStartPoint().getLongitude());
				parameters.put("elat", ""+r.getEndPoint().getLatitude());
				parameters.put("elon", ""+r.getEndPoint().getLongitude());
				parameters.put("mnu", ""+params[0].getMaxNumUsers());
				parameters.put("sadd", URLEncoder.encode(r.getStartPoint().getLocation().toString(), "UTF-8"));
				parameters.put("eadd", URLEncoder.encode(r.getEndPoint().getLocation().toString(), "UTF-8"));
				response=DeviceController.getInstance().getServer().
						request(SettingsServer.NEW_MAPME, DeviceController.getInstance().getServer().setParameters(parameters));

				return params[0];
			} catch (UnsupportedEncodingException e) {
				return null;
			}

		}

		@Override
		protected void onPostExecute(MapMe result) {
			super.onPostExecute(result);
		}

	}



}

