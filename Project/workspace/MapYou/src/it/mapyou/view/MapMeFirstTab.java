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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
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
				googleMap.clear();
				textNickname.setText(mapme.getAdministrator().getNickname());
				Segment route = mapme.getSegment();
				Point sp = route.getStartPoint();
				Point ep = route.getEndPoint();
				textend.setText(ep.getLocation());
				textStart.setText(sp.getLocation());
				MarkerOptions optStart = new MarkerOptions();
				MarkerOptions optEnd = new MarkerOptions();
				optStart.snippet(sp.getLocation());
				optStart.position(new LatLng(sp.getLatitude(), sp.getLongitude()));
				optStart.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

				optEnd.snippet(ep.getLocation());
				optEnd.position(new LatLng(ep.getLatitude(), ep.getLongitude()));
				optEnd.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				
				googleMap.addMarker(optStart);
				googleMap.addMarker(optEnd);
			}
		}
	}
}
