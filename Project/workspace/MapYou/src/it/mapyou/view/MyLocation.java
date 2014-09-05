/**
 * 
 */
package it.mapyou.view;

import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MyLocation implements LocationListener {


	private  Context c;
	private double latitude;
	private double longitude;

	private SharedPreferences sp;
	private boolean isInsertMapping=false;

	/**
	 * 
	 */
	public MyLocation(Context context ) {
		this.c=context;

		sp=PreferenceManager.getDefaultSharedPreferences(context);

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
				Toast.makeText(c, latitude+""+longitude, 2000).show();

				new UpdateLocationUser().execute();

			}catch (Exception e) {
				Log.v("Error thread", e.getMessage());
			}
		}else
			Toast.makeText(c, "location null", 2000).show();
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
					UtilAndroid.makeToast(c, "Error rete", 5000);
					isInsertMapping=false;
				}else{
					//update
					isInsertMapping=true;
				}
			}else
				UtilAndroid.makeToast(c, "Error rete", 5000);

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
					UtilAndroid.makeToast(c, "Error insert", 5000);
					isInsertMapping=false;
				}else if(result.contains("-1")){
					UtilAndroid.makeToast(c, "Error rete", 5000);
					isInsertMapping=false;
				}else{
					isInsertMapping=true;
				}

			}else
				UtilAndroid.makeToast(c, "Error rete", 5000);
		}
	}
}
