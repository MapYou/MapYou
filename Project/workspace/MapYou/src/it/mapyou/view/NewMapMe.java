
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.navigator.ConfigurationGeocodingApi;
import it.mapyou.navigator.ParserDataFromGeocodingApi;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.test.UiThreadTest;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NewMapMe extends FragmentActivity {

	private GoogleMap googleMap;
	private EditText nameMapMe;
	private EditText start;
	private EditText dest;
	private DeviceController controller;

	private Marker markerStart;
	private Marker markerEnd;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.new_map_me);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		nameMapMe=(EditText) findViewById(R.id.editTextMapMeName);
		start=(EditText) findViewById(R.id.EditTextStartMapme);
		dest=(EditText) findViewById(R.id.editTextDestinazione);

		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initilizeMap();
	}

	public void start (View v){
		if(markerStart!=null){
			UtilAndroid.makeToast(getApplicationContext(), "The start can be setted once.", Toast.LENGTH_LONG);
		}
		else{
			String startPoint = start.getText().toString().replace(" ", "");
			if(startPoint!=null && startPoint.length()>0){

				String url= ConfigurationGeocodingApi.getUrlFrom(startPoint);
				new Settingstask(true).execute(url);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "You must set a start location", Toast.LENGTH_LONG);

		}
	}

	public void cleanMap (View v){
		if(googleMap!=null && (markerEnd!=null || markerStart!=null)){
			googleMap.clear();
			markerEnd=null;
			markerStart=null;
		}else
			UtilAndroid.makeToast(getApplicationContext(), "You must set a position before cleaning map", Toast.LENGTH_LONG);
	}

	public void dest (View v){

		if(markerEnd!=null){
			UtilAndroid.makeToast(getApplicationContext(), "The destination can be setted once.", Toast.LENGTH_LONG);
		}
		else{
			String startPoint = dest.getText().toString().replace(" ", "");
			if(startPoint!=null && startPoint.length()>0){

				String url= ConfigurationGeocodingApi.getUrlFrom(startPoint);
				new Settingstask(false).execute(url);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "You must set a desination.", Toast.LENGTH_LONG);

		}
	}


	class Settingstask extends AsyncTask<String, Void, List<HashMap<String,String>>>{

		private boolean isStart;

		public Settingstask(boolean s) {
			this.isStart = s;
		}


		@Override
		protected List<HashMap<String, String>> doInBackground(String... params) {

			List<HashMap<String, String>> points=null;
			ParserDataFromGeocodingApi p = new ParserDataFromGeocodingApi();
			String response=p.getDataFromAnyServer(params[0]);
			try {
				points=p.parse(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return points;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			super.onPostExecute(result);

			if(result!=null){
				String address="";
				String lat="";
				String lon="";
				for(HashMap<String, String> map: result){
					address=map.get("formatted_address");
					lat=map.get("lat");
					lon=map.get("lng");

				}

				double latitude = Double.parseDouble(""+lat);
				double longitude = Double.parseDouble(""+lon);

				UtilAndroid.makeToast(getApplicationContext(), address +"Lat->"+latitude +"Lon->"+longitude, 8000);

				MarkerOptions opt= new MarkerOptions();
				opt.snippet(address);
				opt.title(isStart?"Partenza":"Arrivo");
				opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2));
				opt.position(new LatLng(latitude, longitude));

				if(isStart)
					markerStart = googleMap.addMarker(opt);
				else
					markerEnd = googleMap.addMarker(opt);
				CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));

			}else
				UtilAndroid.makeToast(getApplicationContext(), "Address is not valid. Stupid!!!", Toast.LENGTH_LONG);
		}

	}




	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}
	}


}





