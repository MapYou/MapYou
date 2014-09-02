/**
 * 
 */
package it.mapyou.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */

public class NotificationServer extends Application  implements NotificationServerInterface {

	public static NotificationServer instance;
	private  final int MAX = 5;
	private  final int BACKOFF = 2000;
	private  final Random random = new Random();
	private PowerManager.WakeLock wakeLock;
	
	private NotificationServer(){
		
	}

	public static NotificationServer getNotificationServer() {
		if(instance==null)
			instance = new NotificationServer();
		return instance;
	}

	@Override
	public String request(String page, String parameters) {

		StringBuilder response = new StringBuilder();
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection) new URL(SettingsNotificationServer.SERVER_URL+page).openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream()); 
			wr.write(parameters); 
			wr.flush(); 

			int responseCode = urlConnection.getResponseCode();

			if(responseCode == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine=null;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return response.toString();
		}

		return response.length()>0?
				response.toString().replaceFirst("\t", "").replaceFirst("\n", "").replaceFirst("\r", "")
				:response.toString();

	}

	@Override
	public void register(Context context) {
	 
		long backoff = BACKOFF + random.nextInt(1000);
		for (int i = 1; i <= MAX; i++) {
			Log.d(SettingsNotificationServer.TAG, "Attempt #" + i + " to register");

			try {
				GCMRegistrar.setRegisteredOnServer(context, true);
				return;

			} catch (Exception e) {

				if (i == MAX) {
					break;
				}
				try {
					Thread.sleep(backoff);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					return;
				}
				backoff *= 2;
			}
		}
	}

	@Override
	public void unregister(Context context, String name) {

		String serverUnreg= SettingsNotificationServer.PAGE_UNREGISTER;
		HashMap<String, String> params= new HashMap<String, String>();
		params.put("name", name);
		request(serverUnreg, setParameters(params));
		GCMRegistrar.setRegisteredOnServer(context, false);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void acquireWakeLock(Context c) {
		if (wakeLock != null) 
			wakeLock.release();

		PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);

		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
				PowerManager.ACQUIRE_CAUSES_WAKEUP |
				PowerManager.ON_AFTER_RELEASE, "WakeLock");

		wakeLock.acquire();
	}
	
	public  void releaseWakeLock() {
		if (wakeLock != null) 
			wakeLock.release(); 
		wakeLock = null;
	}


	public String setParameters(HashMap<String, String> params) {

		Iterator<Entry<String, String>> iterator=params.entrySet().iterator();
		StringBuilder parameters= new StringBuilder();

		while(iterator.hasNext()){

			Entry<String, String> p= iterator.next();
			parameters.append(p.getKey()).append('=').append(p.getValue());

			if(iterator.hasNext())
				parameters.append('&');
		}
		return parameters.toString();
	}



}
