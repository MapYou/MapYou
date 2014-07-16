
package it.mapyou.navigator;

import it.mapyou.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapsProva extends FragmentActivity {

	private GoogleMap googleMap;
	
	 
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.map);
		
		initilizeMap();
	}
	
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	
}





