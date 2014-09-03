/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class CompleteMapMeFirstTab extends Activity {

	private GoogleMap googleMap;
	private MapMe mapme;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_first_tab);
		mapme = (MapMe) getIntent().getExtras().getParcelable("mapme");
		initilizeMap();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}
	
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapMapMeFirstTab)).getMap();
			googleMap.setMyLocationEnabled(true);
			

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}else{
				
			}
		}
	}
}
