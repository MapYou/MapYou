package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.model.EndPoint;
import it.mapyou.model.Mapping;
import it.mapyou.model.Route;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 
 */

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UserDialogOnMapMe extends DialogFragment{

	private static UserDialogOnMapMe instance;
	private Mapping map;
	private GoogleMap googleMap;

	public void changeMapping(Mapping map){
		this.map = map;
		Route r = this.map.getRoute();
		EndPoint ep = r.getEndPoint();
		MarkerOptions opt = new MarkerOptions();
		opt.position(new LatLng(ep.getLatitude(), ep.getLongitude()));
		googleMap.addMarker(opt);
		CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
	}

	/**
	 * @return the instance
	 */
	public static UserDialogOnMapMe getInstance() {
		if(instance == null)
			instance = new UserDialogOnMapMe();
		return instance;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.user_on_mapme_layout, container);
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapUserOnMapMe)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getActivity(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}else;
		Button but  =(Button) v.findViewById(R.id.buttonDismissUserOnMapMe);
		but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		return v;
	}
	
	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapUserOnMapMe)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getActivity(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}else;
	}

}
