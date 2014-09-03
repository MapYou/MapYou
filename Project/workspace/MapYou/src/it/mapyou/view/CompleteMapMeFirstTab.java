/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class CompleteMapMeFirstTab extends Activity {

	private GoogleMap googleMap;
	private MapMe mapme;
	private List<MappingUser> mappings;
	private Context cont;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_first_tab);
		cont = this;
		mapme = (MapMe) getIntent().getExtras().getParcelable("mapme");
		mappings = new ArrayList<MappingUser>();

		if(initilizeMap()){
			new RetrieveMapping().execute();
		}
	}

	public void refresh(View v){
		new RetrieveMapping().execute();
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

	private boolean initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.completeMapMapMeFirstTab)).getMap();
			googleMap.setMyLocationEnabled(true);


			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}else{
				googleMap.clear();
			}
		}else;
		return googleMap!=null;
	}

	class RetrieveMapping extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(!UtilAndroid.findConnection(cont))
			{
				UtilAndroid.makeToast(cont, "Internet Connection not found", 5000);
				super.onCancelled();
			}
			
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			try {
				parameters.put("mapme", String.valueOf(mapme.getModelID()));
				JSONObject response=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_ALL_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			mappings.clear();
			if(result==null){
				UtilAndroid.makeToast(cont, "Please refresh....", 5000);
			}else{
				retrieveAllMappings(result);
				showMap();
			}

		}

	}
	
	public void showMap(){
		googleMap.clear();
		
		Segment s = mapme.getSegment();
		Point end = s.getEndPoint();
		Point st = s.getStartPoint();
		if(end!=null){
			MarkerOptions opt = new MarkerOptions();
			opt.position(new LatLng(end.getLatitude(), end.getLongitude()));
			opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			opt.title(end.getLocation());
			opt.snippet("Destination");
			googleMap.addMarker(opt);
		}if(st!=null){
			MarkerOptions opt = new MarkerOptions();
			opt.position(new LatLng(st.getLatitude(), st.getLongitude()));
			opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			opt.title(st.getLocation());
			opt.snippet("Start");
			googleMap.addMarker(opt);
		}
		else;
		
		for(int i=0; i<mappings.size(); i++){
			MappingUser m = mappings.get(i);
			User u = m.getUser();
			Point p = m.getPoint();
			if(p.equals(st) || p.equals(end)){
				p.setLatitude(p.getLatitude()+0.00001);
				p.setLongitude(p.getLongitude()+0.00001);
			}else;
			MarkerOptions opt = new MarkerOptions();
			opt.position(new LatLng(p.getLatitude(), p.getLongitude()));
			opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			opt.title(u.getNickname());
			opt.snippet(p.getLocation());
			googleMap.addMarker(opt);
		}
		
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				UtilAndroid.makeToast(cont, 
						arg0.getTitle()
						, 5000);
				return false;
			}
		});
		googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			
			@Override
			public void onMyLocationChange(Location arg0) {
				UtilAndroid.makeToast(cont, 
						String.valueOf(arg0.getLatitude())+" - "+
				String.valueOf(arg0.getLongitude())
						, 5000);
			}
		});
	}

	public void retrieveAllMappings(JSONObject result){
		try {
			JSONArray jsonArr = result.getJSONArray("AllMappings");
			for(int i=0; i<jsonArr.length(); i++){
				MappingUser mp= getMappingFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					mappings.add(mp);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MappingUser getMappingFromMapme (JSONObject json){


		try {
			MappingUser m= new MappingUser();
			User admin = getUserByJSon(json.getJSONArray("user"));
			Point point = getPointByJSon(json.getJSONArray("point"));
			if(admin!=null && point!=null){
				m.setModelID(Integer.parseInt(json.getString("id")));
				m.setUser(admin);
				m.setPoint(point);
			}else;
			return m;
		}catch (Exception e) {
			return null;
		}
	}

	private User getUserByJSon (JSONArray jsonArr){

		try {
			User user= new User();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private Point getPointByJSon (JSONArray jsonArr){

		try {
			Point ptn= new Point();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
				ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
				ptn.setLocation(json.getString("location"));
			}
			return ptn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}