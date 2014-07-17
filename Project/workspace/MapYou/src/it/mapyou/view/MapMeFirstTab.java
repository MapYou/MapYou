/**
 * 
 */
package it.mapyou.view;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;

import it.mapyou.R;
import it.mapyou.model.MapMe;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeFirstTab extends Activity {

	private GoogleMap googleMap;
	private MapMe mapme;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_first_tab);
		mapme = (MapMe) getIntent().getSerializableExtra("mapme");
		initilizeMap();
	}

	public void changeSatelliteMap(View v){
		googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}

	public void changeIbridaMap(View v){
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}

	public void changeNormaleMap(View v){
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
