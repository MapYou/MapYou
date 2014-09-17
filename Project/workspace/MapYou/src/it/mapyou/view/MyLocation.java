
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.cache.FileControllerCache;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

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
import android.os.AsyncTask;
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
	private static final long MIN_TIME_BW_UPDATES = 6000; // 15 seconds
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
			Intent i= new Intent(act, MapMeLayoutHome.class);
			act.startActivity(i);
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

				new UpdateLocationUser().execute();

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


	class UpdateLocationUser extends AsyncTask<Void, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		private String resp;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			}else;
		}

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
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result!=null){
				if(result.contains("1")){
					new RetrieveMapping().execute();
				}else;
			}else
				UtilAndroid.makeToast(act, "Wait for the loading of the position", 1000);
		}
	}

	class RetrieveMapping extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			}else;
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
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if(result==null){
				UtilAndroid.makeToast(act.getApplicationContext(), "Please refresh Server....", 500);
			}else{
				try {
					fileCahce.write(result.toString());
					//fileCahce.read();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	

}
