
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.cache.FileControllerCache;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.AbstractAsyncTask;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MyLocation implements LocationListener  {

	private double latitude;
	private double longitude;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = true;
	private Location location; 
	private Activity act;
	private FileControllerCache fileCahce;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
	private static final long MIN_TIME_BW_UPDATES = 10000; // 15 seconds
	protected LocationManager locationManager;
	private SharedPreferences sp;


	public MyLocation(Activity act) {
		this.act = act;
		sp=PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext());
		fileCahce = new FileControllerCache(UtilAndroid.NAME_OF_FILE_CACHE, act.getApplicationContext());

	}

	public void stop(){
		location=null;
		locationManager.removeUpdates(this);
	}

	public void start(){

		Criteria c = new Criteria();
//		c.setAccuracy(Criteria.ACCURACY_FINE);
//		c.setPowerRequirement(Criteria.POWER_LOW);
		locationManager = (LocationManager)act.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if(isGPSEnabled || isNetworkEnabled){
			String provider = locationManager.getBestProvider(c, true);
			location = locationManager.getLastKnownLocation(provider);

			if(location!=null)
				onLocationChanged(location);

			locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		}else{
			alertGPS("GPS disabled", "Do you want enable gps?");
			if(isGPSEnabled || isNetworkEnabled){
				Intent i = new Intent(act, CompleteMapMeFirstTab.class);
				act.finish();
				act.startActivity(i);
			}
		}
	}

	public double getLatitude() {
		return latitude;
	}


	public double getLongitude() {
		return longitude;
	}

	@Override
	public void onLocationChanged(Location location) {


		if(location!=null){
			try{
				latitude=location.getLatitude();
				longitude=location.getLongitude();
				Toast.makeText(act.getApplicationContext(), latitude+""+longitude, 2000).show();

				new UpdateLocationUser(act).execute();

			}catch (Exception e) {
				Log.v("Error thread", e.getMessage());
			}
		}else
			Toast.makeText(act.getApplicationContext(), "location null", 3000).show();
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {


	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}


	class UpdateLocationUser extends AbstractAsyncTask<Void, Void, String>{

		/**
		 * @param act
		 */
		public UpdateLocationUser(Activity act) {
			super(act);
			// TODO Auto-generated constructor stub
		}

		private String resp;

		@Override
		protected String doInBackground(Void... params) {

			try{
				parameters.put("mapme", ""+sp.getInt("mapmeid", -1));
				parameters.put("user", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("lat", ""+latitude);
				parameters.put("lon", ""+longitude);
				parameters.put("loc", "Locatioin");
				resp=DeviceController.getInstance().getServer().request(SettingsServer.INSERT_MAPPING_IN_LIVE, DeviceController.getInstance().getServer().setParameters(parameters));
				return resp;
			}catch (Exception e) {
				Log.v("Errorlogin", ""+e.getMessage());
				return null;
			}
		}

		@Override
		protected void newOnPostExecute(String result) {

			if(result!=null){
				if(result.contains("1")){
					new RetrieveMapping(act).execute();
				}else;
			}else
				UtilAndroid.makeToast(act, "Wait for the loading of the position", 1000);
		}
	}

	class RetrieveMapping extends AbstractAsyncTask<Void, Void, JSONObject>{

		

		/**
		 * @param act
		 */
		public RetrieveMapping(Context act) {
			super(act);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			try {
				parameters.put("mapme", String.valueOf(sp.getInt("mapmeid", -1)));
				JSONObject response=DeviceController.getInstance().getServer().requestJson(SettingsServer.GET_ALL_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void newOnPostExecute(JSONObject result) {

			if(result==null){
				UtilAndroid.makeToast(act.getApplicationContext(), "Please refresh Server....", 500);
			}else{
				try {
					fileCahce.write(result.toString());
					fileCahce.read();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void alertGPS( String title, String message ){

		new AlertDialog.Builder(act)
		.setIcon(R.drawable.ic_launcher)
		.setTitle( title )
		.setMessage( message )
		.setCancelable(false)
		.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		})
		.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				act.startActivity(intent);
			}
		}).show();
	}

}
