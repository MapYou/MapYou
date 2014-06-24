package it.mapyou.gcm;

import it.mapyou.util.SettingsNotificationServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
 


public class NotificationController extends Application implements ManagerController {

	private  final int MAX_ATTEMPTS = 5;
	private  final int BACKOFF_MILLI_SECONDS = 2000;
	private  final Random random = new Random();


	// register account with the server
	@Override
	public void register(final Context context, String name, String email, final String regId) {

		String serverURL= SettingsNotificationServer.SERVER_URL;

		HashMap<String, String> params= new HashMap<String, String>();
		params.put("name", name);
		params.put("email", email);
		params.put("regId", regId);
		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

		// Once GCM returns a registration id, we need to register on our server
		// As the server might be down, we will retry it a couple
		// times.
		for (int i = 1; i <= MAX_ATTEMPTS; i++) {

			Log.d(Configuration.TAG, "Attempt #" + i + " to register");

			try {
				//Send Broadcast to Show message on screen
				//displayMessageOnScreen(context, context.getString(R.string.ServerInReg, i, MAX_ATTEMPTS));

				// Post registration values to web server
				post(serverURL, params);

				GCMRegistrar.setRegisteredOnServer(context, true);

				//Send Broadcast to Show message on screen
//				String message = context.getString(R.string.ServerRre);
//				displayMessageOnScreen(context, message);

				return;


			} catch (Exception e) {

				//Log.e(Configuration.TAG, "Failed to register on attempt " + i + ":" + e);

				if (i == MAX_ATTEMPTS) {
					break;
				}
				try {
					//Log.d(Configuration.TAG, "Sleeping for " + backoff + " ms before retry");
					Thread.sleep(backoff);

				} catch (InterruptedException e1) {
					// Activity finished before we complete - exit.
					Log.d(Configuration.TAG, "Thread interrupted: abort remaining retries!");
					Thread.currentThread().interrupt();
					return;
				}
				backoff *= 2;
			}
		}

 
	}

	//unregister account/ device within the server
	@Override
	public void unregiter(Context context, String name) {

		String serverUnreg= Configuration.UNREGISTER;
		HashMap<String, String> params= new HashMap<String, String>();
		params.put("name", name);
		post(serverUnreg, params);
		GCMRegistrar.setRegisteredOnServer(context, false);
		displayMessageOnScreen(context, "Unregistred_device");
	}
	// send a POST request with the SERVER
	@Override
	public void post(String endPoint, Map<String, String> parameters) {

		URL url;

		try {
			url=new URL(endPoint);
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid url"+endPoint);
		}

		String b= Utility.setParameters(parameters);
		byte[] bytes= b.getBytes();
		HttpURLConnection conn=null;

		try {

			conn=(HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(bytes);
			wr.flush();
			wr.close();

			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
		} catch (Exception e) {

		}finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public void get (String endPoint, HashMap<String, String> parameters){

		URL url;
		try {
			url=new URL(endPoint);
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid url"+endPoint);
		}

		StringBuilder body = new StringBuilder();
		Iterator<Entry<String, String>> iterator= parameters.entrySet().iterator();

		while(iterator.hasNext()){

			Entry<String, String> param= iterator.next();
			body.append(param.getKey()).append('=').append(param.getValue());

			if(iterator.hasNext()){
				body.append('&');
			}
		}

		String b= body.toString();
		byte[] bytes= b.getBytes();
		HttpURLConnection conn=null;

		try {

			conn=(HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");

			int status = conn.getResponseCode();

			// If response is not success
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}



	//CHECKING CONNCECTION
	@Override
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null){
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
		}
		return false;

	}

	//notifies UI for display message
	@Override
	public void displayMessageOnScreen(Context c,String message) {

		Intent intent = new Intent(Configuration.DISPLAY_MESSAGE_ACTION);
		intent.putExtra(Configuration.EXTRA_MESSAGE, message);
		// Send Broadcast to Broadcast receiver with message
		c.sendBroadcast(intent);

	}

	@Override

	public  void acquireWakeLock(Context context) {
		if (wakeLock != null) 
			wakeLock.release();

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
				PowerManager.ACQUIRE_CAUSES_WAKEUP |
				PowerManager.ON_AFTER_RELEASE, "WakeLock");

		wakeLock.acquire();
	}

	public  void releaseWakeLock() {
		if (wakeLock != null) wakeLock.release(); wakeLock = null;
	}


	@SuppressWarnings("deprecation")
	public void showAlertDialog (Context t, String title, String message, Boolean state){

		AlertDialog alert= new AlertDialog.Builder(t).create();
		alert.setTitle(title);
		alert.setMessage(message);

//		if(state !=null)
//			alert.setIcon((state)? R.drawable.success: R.drawable.fail);
//
//		alert.setButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//
//			}
//		});
		alert.show();
	}

	private PowerManager.WakeLock wakeLock;

}


