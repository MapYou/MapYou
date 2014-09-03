/**
 * 
 */
package it.mapyou.view;

import it.mapyou.util.UtilAndroid;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MyLocation implements LocationListener {


	private Context c;
	private double latitude;
	private double longitude;

	/**
	 * 
	 */
	public MyLocation(Context context ) {
		this.c=context;

	}

	@Override
	public void onLocationChanged(Location location) {


		if(location!=null){
			latitude=location.getLatitude();
			longitude=location.getLongitude();

			new Thread(new Runnable() {

				@Override
				public void run() {
					while(true)
						try{
							UtilAndroid.makeToast(c, latitude+""+longitude, 5000);
							Thread.sleep(3000);
						}catch (Exception e) {
							Log.v("Error thread", e.getMessage());
						}
				}

			}).start();
		}
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
