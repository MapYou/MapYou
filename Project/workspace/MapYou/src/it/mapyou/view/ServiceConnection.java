/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
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
public class ServiceConnection extends Service {


	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = true;
	Location location;  
	private String provider;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	private static final long MIN_TIME_BW_UPDATES = 1000; // 6 seconds

	protected LocationManager locationManager;
	private int iduser;
	private MyLocation myloc;


	public ServiceConnection() {
		super();
	}


	@Override
	public void onCreate() {

		super.onCreate();
		iduser = PreferenceManager.getDefaultSharedPreferences(this).getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0);

		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_FINE);
		c.setPowerRequirement(Criteria.POWER_LOW);
		c.setAltitudeRequired(false);
		c.setSpeedRequired(false);
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		String provider = locationManager.getBestProvider(c, false);
		myloc = new MyLocation(this) ;
		locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, myloc);
	 


	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while(true){
//					try{
//
//						Thread.sleep(3000);
//					}catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}
//		}).start();

		return 0;

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//		getLocation( );
		//		if(locationManager != null){
		//			locationManager.removeUpdates(ServiceConnection.this);
		//		}else;
		//		Thread.currentThread().interrupt();
	}

	 
}
