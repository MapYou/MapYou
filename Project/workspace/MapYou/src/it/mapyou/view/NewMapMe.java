
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.navigator.ConfigurationGeocodingApi;
import it.mapyou.navigator.ParserDataFromGeocoding;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
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
	private EditText start;
	private EditText dest;
	private EditText nameMapMe;

	private Marker startMarker;
	private Marker endMarker;
	private SharedPreferences sp;
	private Activity act;
	private DeviceController controller;




	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.new_map_me);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		act=this;

		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		start= (EditText) findViewById(R.id.EditTextStartMapme);
		dest= (EditText) findViewById(R.id.editTextDestinazione);
		nameMapMe= (EditText) findViewById(R.id.editTextMapMeName);
		
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initilizeMap();
	}
	
	public void save (View v){
		
		String nickname= sp.getString("nickname", "");
		String nameMapMee= nameMapMe.getText().toString();
		if(nickname.length()>0 && nameMapMee!=null && nameMapMee.length()>0 && startMarker!=null && endMarker!=null){
			double slat = startMarker.getPosition().latitude;
			double slong = startMarker.getPosition().longitude;
			String sadd = startMarker.getSnippet();
			
			double elat = endMarker.getPosition().latitude;
			double elong = endMarker.getPosition().longitude;
			String eadd = endMarker.getSnippet();
			
			
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Please insert all required information.", 5000);
			
	}


	public void start (View v){

		String add= start.getText().toString().replace(" ", "%20");
		if(startMarker!=null)
			UtilAndroid.makeToast(getApplicationContext(), "Start location can be setted once", 5000);
		else{
			if(add!=null && add.length()>0){
				String url= ConfigurationGeocodingApi.getUrlFrom(add);
				new SettingsTask(true).execute(url);
			}
			else
				UtilAndroid.makeToast(getApplicationContext(), "Field cannot be empty", 5000);
		}
	}

	public void dest (View v){

		String add= dest.getText().toString().replace(" ", "%20");
		if(endMarker!=null)
			UtilAndroid.makeToast(getApplicationContext(), "Destionation location can be setted once", 5000);
		else{
			if(add!=null && add.length()>0){
				String url= ConfigurationGeocodingApi.getUrlFrom(add);
				new SettingsTask(false).execute(url);
			}
			else
				UtilAndroid.makeToast(getApplicationContext(), "Field cannot be empty", 5000);
		}
	}

	public void cleanMap(View v){
		if(googleMap!=null && (startMarker!=null || endMarker!=null)){
			googleMap.clear();startMarker=null;endMarker=null;
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Insert a location before cleaning map.", 5000);
	}



	class SettingsTask extends AsyncTask<String, Void, List<HashMap<String,String>>>{

		boolean isStart=false;

		SettingsTask(boolean isStartt) {
			isStart=isStartt;

		}

		@Override
		protected List<HashMap<String, String>> doInBackground(String... params) {

			
			try {
				List<HashMap<String, String>> listOfpoints= null;
				ParserDataFromGeocoding parser= new ParserDataFromGeocoding();
				String data= parser.retrieveData(params[0]);
				listOfpoints= parser.parse(new JSONObject(data));

				return listOfpoints;
			} catch (JSONException e) {

				return null;
			}
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			super.onPostExecute(result);

			String lat="";
			String lon="";
			String address="";

			if(result!=null && result.size()>0){

				for(HashMap<String, String> maps: result){
					lat=maps.get("lat");
					lon=maps.get("lng");
					address=maps.get("formatted_address");
				}

				double latitude= Double.parseDouble(""+lat);
				double longitude= Double.parseDouble(""+lon);


				MarkerOptions opt= new MarkerOptions();
				opt.title(isStart?"Partenza":"Arrivo");
				opt.snippet(address);
				opt.position(new LatLng(latitude, longitude));

				if(isStart)
				{
					opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					startMarker = googleMap.addMarker(opt);
				}
				else
				{
					opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					endMarker = googleMap.addMarker(opt);
				}

				CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(8).build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
			}
			else
				UtilAndroid.makeToast(getApplicationContext(), "Address not valid.", 5000);
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




