/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Route;
import it.mapyou.model.StartPoint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
public class MapMeFirstTab extends Activity {

	private GoogleMap googleMap;
	private MapMe mapme;
	private TextView textNickname, textStart, textend;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_first_tab);
		textNickname = (TextView) findViewById(R.id.textViewNickname);
		textStart = (TextView) findViewById(R.id.textViewStartLocation);
		textend = (TextView) findViewById(R.id.textViewEndLocation);
		mapme = (MapMe) getIntent().getExtras().getParcelable("mapme");
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
				textNickname.setText(mapme.getAdministrator().getNickname());
				textend.setText(mapme.getEndAddress());
				textStart.setText(mapme.getStartAddress());
				Route route = mapme.getRoute();
				StartPoint sp = route.getStartPoint();
				EndPoint ep = route.getEndPoint();
				MarkerOptions optStart = new MarkerOptions();
				MarkerOptions optEnd = new MarkerOptions();
				optStart.snippet(mapme.getStartAddress());
				optStart.position(new LatLng(sp.getLatitude(), sp.getLongitude()));
				optStart.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

				optEnd.snippet(mapme.getEndAddress());
				optEnd.position(new LatLng(ep.getLatitude(), ep.getLongitude()));
				optEnd.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				
				googleMap.addMarker(optStart);
				googleMap.addMarker(optEnd);
			}
		}
	}
}
