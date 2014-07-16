/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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

			//Log.d(TAG, "AccessToken: " + token);
			//Log.d(TAG, "AccessExpires: " + token_expires);
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
				FacebookController.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						editor.putString("idface", id);
						editor.putString("emailFace", email);
						editor.putString("nameFace", name);
						editor.commit();
						
						Intent i = new Intent(getApplicationContext(), DrawerMain.class);
						
//						i.putExtra("idface", id);
//						i.putExtra("emailFace", email);
//						i.putExtra("nameFace", name);
						startActivity(i);
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
}


