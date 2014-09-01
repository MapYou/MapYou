/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class GPSTracker extends Service implements LocationListener{


	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = true;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1; // 6 seconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	private int iduser;

	public GPSTracker() {
		// TODO Auto-generated constructor stub
		super();
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		iduser = PreferenceManager.getDefaultSharedPreferences(this).getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0);
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(LOCATION_SERVICE);
		// getting GPS status
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// getting network status
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		getLocation(false);
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}else;
		Thread.currentThread().interrupt();
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		Log.i("latitude", String.valueOf(latitude));
		Log.i("longitude", String.valueOf(longitude));
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	private void getLocation(boolean v){
		try {
			if (!isGPSEnabled && !isNetworkEnabled) {
			} else {
				if (isNetworkEnabled) {
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}else
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

					onLocationChanged(location);
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {

					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}else
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

					onLocationChanged(location);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				getLocation(true);
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try {
						handler.sendEmptyMessage(0);
						Thread.sleep(3000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start();

		return 0;
	}

	//	class UpdateMapping extends AsyncTask<Void, Void, JSONObject>{
	//
	//
	//		private HashMap<String, String> parameters=new HashMap<String, String>();
	//
	//		@Override
	//		protected JSONObject doInBackground(Void... params) {
	//
	//			try {
	//				parameters.put("user",String.valueOf(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0)));
	//				parameters.put("mode","2");
	//				parameters.put("lat",String.valueOf(latitude));
	//				parameters.put("lon",String.valueOf(longitude));
	//				parameters.put("loc",String.valueOf("address"));
	//				controller.getServer().requestJson(SettingsServer.INSERT_MAPPING, controller.getServer().setParameters(parameters));
	//			} catch (Exception e) {
	//				return null;
	//			}
	//			return null;
	//		}
	//
	//		@Override
	//		protected void onPostExecute(JSONObject result) {
	//			super.onPostExecute(result);
	//		}
	//	}

}
