/**
 * 
 */
package it.mapyou.view;

import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ServiceConnection extends Service {


	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = true;
	Location location; 
	private final String NAME="mapyou";



	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	private static final long MIN_TIME_BW_UPDATES = 15000; // 15 seconds

	protected LocationManager locationManager;

	private MyLocation myloc;
	private SharedPreferences sp;




	@Override
	public void onCreate() {
		super.onCreate();
	 

		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Criteria c = new Criteria();
		//		c.setAccuracy(Criteria.ACCURACY_FINE);
		//		c.setPowerRequirement(Criteria.POWER_LOW);
		//		c.setAltitudeRequired(false);
		//		c.setSpeedRequired(false);
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		String provider = locationManager.getBestProvider(c, true);
		location = locationManager.getLastKnownLocation(provider);

		myloc = new MyLocation(ServiceConnection.this) ;


		if(location!=null)
			myloc.onLocationChanged(location);

		locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, myloc);


	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			
			@Override
			public void run() {
				new RetrieveMapping().execute();
			}
		};
		t.schedule(tt, 0, 15000);

		return Service.START_NOT_STICKY;//Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	
	class RetrieveMapping extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
			{
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 500);
				super.onCancelled();
			}

		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			try {
				parameters.put("mapme", String.valueOf(sp.getInt("mapmeid", -1)));
				JSONObject response=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_ALL_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null){
				UtilAndroid.makeToast(getApplicationContext(), "Please refresh Server....", 500);
			}else{
				try {
					write(result.toString());
					read();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void write(String text) throws Exception{

		FileOutputStream f=null;
		try {
			f= openFileOutput(NAME, MODE_PRIVATE);
			f.write(text.toString().getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			f.flush();
			f.close();
		}


	}

	public void read() throws Exception{

		BufferedReader reader=null;
		try {
			FileInputStream f= openFileInput(NAME);
			reader= new BufferedReader(new InputStreamReader(f));
			StringBuffer bf=new StringBuffer();
			String line=null;

			while((line=reader.readLine()) !=null){
				bf.append(line);
			}
			UtilAndroid.makeToast(this, ""+bf.length(), 500);

		} catch (Exception e) {
			UtilAndroid.makeToast(getApplicationContext(), "Non legge", 500);
			
		}
		finally{
			reader.close();

		}
	}



}
