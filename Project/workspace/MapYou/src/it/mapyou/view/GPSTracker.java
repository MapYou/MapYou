/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;
import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class GPSTracker extends Service implements LocationListener{


	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;


	boolean canGetLocation = true;
	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	String provider;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	private static final long MIN_TIME_BW_UPDATES = 1000; // 6 seconds

	protected LocationManager locationManager;
	private int iduser;
	private GoogleMap googleMap;

	public GPSTracker() {
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

		locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
		Criteria c = new Criteria();

		//c.setAccuracy(Criteria.ACCURACY_FINE);
//		c.setPowerRequirement(Criteria.POWER_LOW);
//		c.setAltitudeRequired(false);
//		c.setSpeedRequired(false);

		provider = locationManager.getBestProvider(c, false);


		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


		if(provider!=null && !provider.equals("")){
			locationManager.requestLocationUpdates(
					provider,
					MIN_TIME_BW_UPDATES,
					MIN_DISTANCE_CHANGE_FOR_UPDATES, this);


			getLocation();
		}else{
			Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
		}

	}




	@Override
	public void onDestroy() {
		super.onDestroy();
		getLocation( );
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}else;
		Thread.currentThread().interrupt();
	}




	private void getLocation(){

		

		location = locationManager.getLastKnownLocation(provider);


		if (location != null) {
			onLocationChanged(location);
		}


		//		try {
			//			if (!isGPSEnabled && !isNetworkEnabled) {
		//			} else {
		////				if (isNetworkEnabled) {
		////					if (locationManager != null) {
		////						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		////						if (location != null) {
		////							latitude = location.getLatitude();
		////							longitude = location.getLongitude();
		////						}
		////					}else
		////						locationManager.requestLocationUpdates(
		////								LocationManager.NETWORK_PROVIDER,
		////								MIN_TIME_BW_UPDATES,
		////								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		////
		////					onLocationChanged(location);
		////				}
		//				// if GPS Enabled get lat/long using GPS Services
		//				if (isGPSEnabled) {
		//
		//					if (locationManager != null) {
		//						location = locationManager.getLastKnownLocation(provider);
		//						
		//								
		//						if (location != null) {
		//							latitude = location.getLatitude();
		//							longitude = location.getLongitude();
		//						}
		//					}else{
		//						locationManager.requestLocationUpdates(
		//								provider,
		//								MIN_TIME_BW_UPDATES,
		//								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		//					}
		//
		//					onLocationChanged(location);
		//				}
		//			}
		//
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {




		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try{
						//						if(location!=null)
						getLocation();
						//						else{
						//							Toast.makeText(getBaseContext(), "Please attend GPS signal!", Toast.LENGTH_SHORT).show();
						//						}
						Thread.sleep(3000);
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start();


		return 0;

	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	//	@Override
	//	public int onStartCommand(Intent intent, int flags, int startId) {
	//		final Handler handler = new Handler(){
	//			@Override
	//			public void handleMessage(Message msg) {
	//				// TODO Auto-generated method stub
	//				super.handleMessage(msg);
	//				getLocation(true);
	//			}
	//		};
	//		new Thread(new Runnable() {
	//
	//			@Override
	//			public void run() {
	//				while(true){
	//					try {
	//						handler.sendEmptyMessage(0);
	//						Thread.sleep(3000);
	//					} catch (Exception e) {
	//						// TODO: handle exception
	//					}
	//				}
	//			}
	//		}).start();
	//		return 0;
	//	}


	@Override
	public void onLocationChanged(Location loc) {
		latitude=loc.getLatitude();
		longitude=loc.getLongitude();
		location = loc;
		Toast.makeText(getApplicationContext(), latitude+"****"+longitude, Toast.LENGTH_SHORT).show();

	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}




}
