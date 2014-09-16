
package it.mapyou.view;

import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.SettingsNotificationServer;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.BitmapParser;
import it.mapyou.util.UtilAndroid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.gcm.GCMRegistrar;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */

@SuppressWarnings("deprecation")
public abstract class FacebookController extends Activity {

	public static final String TAG = "FACEBOOK";
	private Facebook mFacebook;
	public static final String APP_ID = "1516574998563667";

	private AsyncFacebookRunner mAsyncRunner;
	private static final String[] PERMS = new String[] { "read_stream" };
	public SharedPreferences sharedPrefs;
	private Context mContext;
	private String idnotification;
	private User userFaceLogged;



	public void setConnection() {
		mContext = this;
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	}


	public void getID() {
		if (isSession()) {
			mAsyncRunner.request("me", new IDRequestListener());
		} else {
			Log.d(TAG, "sessionNOTValid, relogin");
			mFacebook.authorize(this, PERMS, new LoginDialogListener());
		}
	}

	public boolean isSession() {

		String access_token = sharedPrefs.getString("access_token", "x");
		Long expires = sharedPrefs.getLong("access_expires", -1);
		//Log.d(TAG, access_token);

		if (access_token != null && expires != -1) {
			mFacebook.setAccessToken(access_token);
			mFacebook.setAccessExpires(expires);
		}
		return mFacebook.isSessionValid();
	}


	//porco di Zukkkkemberg
	public void logoutFacebookSession(){
		new AsyncTask<Void, Void, Boolean>(){

			@Override
			protected Boolean doInBackground(Void... params){
				setConnection();
				if (Session.getActiveSession() != null)
					Session.getActiveSession().closeAndClearTokenInformation();

				Editor  editor = sharedPrefs.edit();
				editor.clear();
				editor.commit(); 

				Log.v("accesscix", sharedPrefs.getString("access_token", "x"));
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result){

				if (result == null|| result == false){
					UtilAndroid.makeToast(mContext, "Not_logout", 5000);
					return;
				}else{
					Intent i = new Intent(getApplicationContext(), Login.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			}
		}.execute();
	}
	public void logoutFacebookSession2(){

		setConnection();
		new AsyncTask<Void, Void, Boolean>(){

			@Override
			protected Boolean doInBackground(Void... params){
				setConnection();
				if (Session.getActiveSession() != null)
					Session.getActiveSession().closeAndClearTokenInformation();

				Editor  editor = sharedPrefs.edit();
				editor.clear();
				editor.commit(); 

				Log.v("accesscix", sharedPrefs.getString("access_token", "x"));
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result){
				return;

			}
		}.execute();
	}


	private class LoginDialogListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = mFacebook.getAccessToken();
			long token_expires = mFacebook.getAccessExpires();
			sharedPrefs.edit().putLong("access_expires", token_expires).commit();
			sharedPrefs.edit().putString("access_token", token).commit();
			mAsyncRunner.request("me", new IDRequestListener());
		}

		@Override
		public void onFacebookError(FacebookError e) {
			Log.d(TAG, "FacebookError: " + e.getMessage());
		}

		@Override
		public void onError(DialogError e) {
			Log.d(TAG, "Error: " + e.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d(TAG, "OnCancel");

		}
	}

	private class IDRequestListener implements RequestListener {

		@Override
		public void onComplete(String response, Object state) {
			try {
				JSONObject json = Util.parseJson(response);
				SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(mContext);
				final Editor editor=sp.edit();
				final String id = json.getString("id");
				final String name = json.getString("name");
				final String email = json.getString("email");
				userFaceLogged= new User();
				userFaceLogged.setEmail(email);
				userFaceLogged.setNickname(name);
				userFaceLogged.setPassword(id);
				FacebookController.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						new FacebookLoginTask().execute();
					}
				});
			} catch (JSONException e) {
				Log.d(TAG, "JSONException: " + e.getMessage());
			} catch (FacebookError e) {
				Log.d(TAG, "FacebookError: " + e.getMessage());
			}
		}

		@Override
		public void onIOException(IOException e, Object state) {
			Log.d(TAG, "IOException: " + e.getMessage());
		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			Log.d(TAG, "FileNotFoundException: " + e.getMessage());
		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			Log.d(TAG, "MalformedURLException: " + e.getMessage());
		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			Log.d(TAG, "FacebookError: " + e.getMessage());
		}

	}

	class FacebookLoginTask extends AsyncTask<Void, Void, Boolean>{

		private boolean isCorrectLogin;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {

				GCMRegistrar.checkDevice(FacebookController.this);
				GCMRegistrar.checkManifest(FacebookController.this);

				if (!GCMRegistrar.isRegisteredOnServer(FacebookController.this)) {
					GCMRegistrar.register(FacebookController.this, SettingsNotificationServer.GOOGLE_SENDER_ID);
				} else;

				final String regId = GCMRegistrar.getRegistrationId(FacebookController.this);
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

			if(result && userFaceLogged!=null)
				new UpdateFacebookTask().execute(userFaceLogged);
			else
				UtilAndroid.makeToast(getApplicationContext(), "Error access with facebook!", 5000);

		}
	}

	class UpdateFacebookTask extends AsyncTask<User, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else;

		}
		@Override
		protected JSONObject doInBackground(User... params) {

			try{
				JSONObject jobj=null;
				parameters.put("n", ""+params[0].getNickname());
				parameters.put("e", ""+params[0].getEmail());
				parameters.put("id", ""+idnotification);
				parameters.put("p", ""+params[0].getPassword());
				jobj=DeviceController.getInstance().getServer().requestJson(SettingsServer.FACEBOOK, DeviceController.getInstance().getServer().setParameters(parameters));

				return jobj;
			}catch (Exception e) {
				Log.v("Errorlogin", ""+e.getMessage());
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			super.onPostExecute(result);

			User userLogin=null;
			try{
				if(idnotification !=null && idnotification.length() >0){
					if(result!=null ){

						userLogin=DeviceController.getInstance().getParsingController().getUserParser().getParsingUserJobj(result);
						if(userLogin!=null && userLogin.getModelID() >0){

							Editor ed = sp.edit();
							ed.putString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, userLogin.getNickname());
							ed.putString(UtilAndroid.KEY_EMAIL_USER_LOGGED, userLogin.getEmail());
							ed.putInt(UtilAndroid.KEY_ID_USER_LOGGED, userLogin.getModelID());
							ed.putString(UtilAndroid.KEY_NOTIFICATION,idnotification);
							ed.putString(UtilAndroid.ID_FACEBOOK,userFaceLogged.getPassword());
							ed.commit();
							UtilAndroid.makeToast(getApplicationContext(), "Welcome on MapYou", 5000);
							Intent intent= new Intent(FacebookController.this,DrawerMain.class);
							startActivity(intent);
						}else{
							UtilAndroid.makeToast(getApplicationContext(), "Error access with facebook.", 5000);
						}

					}else
						UtilAndroid.makeToast(getApplicationContext(), "Error Access", 5000);
				}else{
					UtilAndroid.makeToast(getApplicationContext(), "GCm error", 5000);
				}
			}catch (Exception e) {
				UtilAndroid.makeToast(getApplicationContext(), "User not registred", 5000);
			}
		}
	}


	
	public void getImageProfile(final String id, final ImageView img){

		new AsyncTask<Void, Void, Bitmap>(){

			Bitmap bm = null;

			@Override
			protected Bitmap doInBackground(Void... params) {

				try{
					URL aURL = new URL("http://graph.facebook.com/"+id+"/picture?type=large");
					HttpGet httpRequest = new HttpGet(URI.create(aURL.toString()) ); 
					HttpClient httpclient = new DefaultHttpClient(); 
					HttpResponse response = httpclient.execute(httpRequest); 
					HttpEntity entrty = response.getEntity(); 
					BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entrty); 
					bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
				}catch (Exception e) {
					e.printStackTrace();
				}
				return bm;
			}
			@Override
			protected void onPostExecute(Bitmap result) {

				try {
					if(result!=null){
						img.setImageBitmap(result);
					}
				} catch (Exception e) {
					UtilAndroid.makeToast(getApplicationContext(), "Error", 5000);
				}
			};

		}.execute();
	}


	
	public void saveImageFacebookProfile(final String id){

		new AsyncTask<Void, Void, Bitmap>(){

			Bitmap bm = null;
			

			@Override
			protected Bitmap doInBackground(Void... params) {

				try{
					
					URL aURL = new URL("http://graph.facebook.com/"+id+"/picture?type=small");
					HttpGet httpRequest = new HttpGet(URI.create(aURL.toString()) ); 
					HttpClient httpclient = new DefaultHttpClient(); 
					HttpResponse response = httpclient.execute(httpRequest); 
					HttpEntity entrty = response.getEntity(); 
					BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entrty); 
					bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
				}catch (Exception e) {
					e.printStackTrace();
				}
				return bm;
			}
			@Override
			protected void onPostExecute(Bitmap result) {

				try {
					if(result!=null){
						BitmapParser.saveImageToInternalStorage(result, getApplicationContext());
					}
				} catch (Exception e) {
					UtilAndroid.makeToast(getApplicationContext(), "Error", 5000);
				}
			};

		}.execute();
	}
 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
}



