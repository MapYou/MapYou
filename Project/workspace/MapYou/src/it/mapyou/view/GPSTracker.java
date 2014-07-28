/**
 * 
 */
package it.mapyou.view;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
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
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1; // 6 seconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	private Timer timer;


	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(LOCATION_SERVICE);
		// getting GPS status
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// getting network status
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//		TimerTask task = new TimerTask() {
//
//			@Override
//			public void run() {
//				getLocation();
//			}
//		};
//		timer = new Timer(true);
//		timer.schedule(task, 0, 1000);
		
		try {



			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				// First get location from Network Provider
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
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
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

	private void getLocation() {
		try {



			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				// First get location from Network Provider
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
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		Log.i("latitude", "ciao");
		Log.i("longitude", "ccccc");
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
