/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.EndPoint;
import it.mapyou.model.Mapping;
import it.mapyou.model.Route;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

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
		initMap();
		Mapping m = getIntent().getExtras().getParcelable("mapping");
		changeMapping(m);
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MapMeSecondTab_User.class);
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
	
	public void changeMapping(Mapping m){
		googleMap.clear();
		MarkerOptions opt = new MarkerOptions();
		opt.position(new LatLng(m.getLatitude(), m.getLongitude()));
		opt.title(m.getUser().getNickname());
		opt.snippet(m.getUser().getEmail());
		googleMap.addMarker(opt);
		CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
	}

	/**
	 * 
	 */
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
