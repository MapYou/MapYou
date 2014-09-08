/**
 * 
 */
package it.mapyou.view;

import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MyLocation extends Activity  implements LocationListener  {


	
	private double latitude;
	private double longitude;

	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = true;
	Location location; 
	private final String NAME="mapyou";



	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	private static final long MIN_TIME_BW_UPDATES = 5000; // 15 seconds
	protected LocationManager locationManager;
	private SharedPreferences sp;
	private boolean isInsertMapping=false;


	public MyLocation( ) {

		sp=PreferenceManager.getDefaultSharedPreferences(this);
		

	}
	
	public void stop(){
		location=null;
		locationManager.removeUpdates(this);
	}
	
	 
	
	
	public void start(){
		
		Criteria c = new Criteria();
		//		c.setAccuracy(Criteria.ACCURACY_FINE);
		//		c.setPowerRequirement(Criteria.POWER_LOW);
		//		c.setAltitudeRequired(false);
		//		c.setSpeedRequired(false);
		locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		String provider = locationManager.getBestProvider(c, true);
		location = locationManager.getLastKnownLocation(provider);


		if(location!=null)
			onLocationChanged(location);

		locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void onLocationChanged(Location location) {


		if(location!=null){
			try{
				latitude=location.getLatitude();
				longitude=location.getLongitude();
				Toast.makeText(this, latitude+""+longitude, 2000).show();

				new UpdateLocationUser().execute();

			}catch (Exception e) {
				Log.v("Error thread", e.getMessage());
			}
		}else
			Toast.makeText(this, "location null", 2000).show();
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
		protected String doInBackground(Void... params) {

			try{

				parameters.put("mapme", ""+sp.getInt("mapmeid", -1));
				parameters.put("user", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("lat", ""+latitude);
				parameters.put("lon", ""+longitude);
				parameters.put("mode", "2");
				resp=DeviceController.getInstance().getServer().request(SettingsServer.INSERT_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));

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
				if(result.contains("0") ){
					if(!isInsertMapping){
						new InsertMappingUser().execute();
					}

				}else if(result.contains("-1")){
					UtilAndroid.makeToast(getApplicationContext(), "Error rete", 5000);
					isInsertMapping=false;
				}else{
					//update
					isInsertMapping=true;
					new RetrieveMapping().execute();
					
					
				}
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error rete", 5000);

		}
	}

	class InsertMappingUser extends AsyncTask<MapMe, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String resp;

		@Override
		protected String doInBackground(MapMe... params) {

			try{

				parameters.put("mapme", ""+sp.getInt("mapmeid", -1));
				parameters.put("user", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("lat", ""+latitude);
				parameters.put("lon", ""+longitude);
				parameters.put("mode", "1");
				resp=DeviceController.getInstance().getServer().request(SettingsServer.INSERT_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));

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
				if(result.contains("0")){
					UtilAndroid.makeToast(getApplicationContext(), "Error insert", 5000);
					isInsertMapping=false;
				}else if(result.contains("-1")){
					UtilAndroid.makeToast(getApplicationContext(), "Error rete", 5000);
					isInsertMapping=false;
				}else{
					isInsertMapping=true;
					new RetrieveMapping().execute();
				}

			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error rete", 5000);
		}
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
			UtilAndroid.makeToast(getApplicationContext(), ""+bf.length(), 500);

		} catch (Exception e) {
			UtilAndroid.makeToast(getApplicationContext(), "Non legge", 500);
			
		}
		finally{
			reader.close();

		}
	}

}
