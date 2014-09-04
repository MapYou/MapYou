/**
 * 
 */
package it.mapyou.view;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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

	/**
	 * 
	 */
	public MyLocation(Context context ) {
		this.c=context;

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
				
			}catch (Exception e) {
				Log.v("Error thread", e.getMessage());
			}
			}else
				Toast.makeText(c, "location null", 2000).show();
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
