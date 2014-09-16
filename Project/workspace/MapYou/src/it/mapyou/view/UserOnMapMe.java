/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MappingUser;
import it.mapyou.util.UtilAndroid;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UserOnMapMe extends FragmentActivity{

	private GoogleMap googleMap;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.user_on_mapme_layout);
		Bundle b = getIntent().getExtras();
//		MappingUser m = b.getParcelable("mapping");
				MappingUser m = UtilAndroid.CURRENT_MAPPING;
		if(m!=null){
			initMap();
			changeMapping(m);
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MapMeLayoutHome.class);
		startActivity(i);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResumeFragments()
	 */
	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
		//		Mapping m = getIntent().getExtras().getParcelable("mapping");
		//		changeMapping(m);
	}

	public void changeMapping(MappingUser map){
		googleMap.clear();
		MarkerOptions opt1 = new MarkerOptions();
		opt1.position(new LatLng(map.getPoint().getLatitude(), map.getPoint().getLongitude()));
		opt1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		googleMap.addMarker(opt1);
		CameraPosition c = new CameraPosition.Builder().target(
				opt1.getPosition()).zoom(15).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
	}

	private void initMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapUserOnMapMe)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
