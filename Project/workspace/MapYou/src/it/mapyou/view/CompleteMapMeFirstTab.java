/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.ChatMessage;
import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.navigator.PArserDataFromDirectionsApi;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.MappingReader;
import it.mapyou.util.UtilAndroid;
import it.mapyou.util.Utils;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class CompleteMapMeFirstTab extends Activity {

	private GoogleMap googleMap;
	private MapMe mapme;
	private Context cont;
	private SharedPreferences sp;
	private Activity act;
	private Intent intent;
	private MyLocation myloc;
	private MappingReader reader;
	private List<MappingUser> mappings;
	
	// distanza in metri del raggio centrato nel punto di arrivo della mapme
	private static final double PROXIMITY_DISTANCE = 50;


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_first_tab);
		cont = this;
		act=this;
		mapme = Util.CURRENT_MAPME;
		
		if(mapme!=null){
			sp=PreferenceManager.getDefaultSharedPreferences(cont);
			sp.edit().putInt("mapmeid", mapme.getModelID()).commit();

			myloc= new MyLocation(this);
			if(initilizeMap()){
				reader = new MappingReader(act);
				mappings = new ArrayList<MappingUser>();
//				myloc.start();
				//new DownlDataFromWebServer().execute(getUrlFromDirectionApi(mapme.getSegment().getStartPoint(),mapme.getSegment().getEndPoint()));
				Timer t = new Timer();
				TimerTask tt = new TimerTask() {
					
					@Override
					public void run() {
						new RetrieveMapping().execute();
					}
				};
				t.schedule(tt, 0, 2000);

			}

		}else
			UtilAndroid.makeToast(cont, "Error while creating live mode.", 5000);
	}

	public void refresh(View v){
		new RetrieveMapping().execute();
	}

	 
	@Override
	public void onBackPressed() {
//		myloc.stop();
		Intent i= new Intent(act, MapMeLayoutHome.class);
		startActivity(i);
		
	}

	private boolean initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.completeMapMapMeFirstTab)).getMap();
			googleMap.setMyLocationEnabled(false);


			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}else{
				googleMap.clear();
			}
		}else;
		return googleMap!=null;
	}
	
class RetrieveMapping extends AsyncTask<Void, Void, String>{

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
			UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			super.onCancelled();
			}

	}

		protected String doInBackground(Void... params) {
			try {

				return reader.read();
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result==null){
				UtilAndroid.makeToast(cont, "Please refresh da file....", 5000);
			}else{
				try {
					mappings = reader.retrieveAllMappings(new JSONObject(result));
					act.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							showMap();
						}
					});
				} catch (JSONException e) {

					UtilAndroid.makeToast(cont, "Error while read postion!", 5000);
				}

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
			opt.visible(true);
			googleMap.addMarker(opt);
			manageProximity(end, p);
		}
	}
	
//	class NotifyProximity extends AsyncTask<Void, Void, String>{
//
//		private HashMap<String, String> parameters=new HashMap<String, String>();
//		private String response;
//
//		private double distance;
//		
//		public NotifyProximity(double distance) {
//			this.distance = distance;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if(!UtilAndroid.findConnection(act.getApplicationContext()))
//				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
//			else{
//				onCancelled();
//			}
//
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//
//			try {
//				parameters.put("admin", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
//				parameters.put("distance", URLEncoder.encode(String.valueOf(distance), "UTF8"));
//				parameters.put("idm",  ""+Integer.parseInt(""+mapme.getModelID()));
//				parameters.put("message",  
//						URLEncoder.encode(
//								sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "")+
//								" è quasi arrivato.", "UTF8"));
//				parameters.put("title",  sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
//				parameters.put("notif",  "Messaggio da "+sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
//
//				response=DeviceController.getInstance().getServer().
//						request(SettingsServer.ALERT_PROXIMITY, DeviceController.getInstance().getServer().setParameters(parameters));
//
//				return response;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			if(result!=null){
//				
//			}else
//				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
//		}
//	}

	
	private void manageProximity(Point end, Point p) {
		double distance = Utils.getDistance(end.getLatitude(), end.getLongitude(), p.getLatitude(), p.getLongitude());
		if((distance*1000)<=PROXIMITY_DISTANCE){
			
		}
	}

	public class DownlDataFromWebServer extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... url) {

			String data="";
			try {
				data=PArserDataFromDirectionsApi.getData(url[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return data;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask p= new ParserTask();
			p.execute(result); 

		}
	}

	public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

		@Override
		protected List<List<HashMap<String,String>>> doInBackground(String...myJson ) {
			
			PArserDataFromDirectionsApi parser = new PArserDataFromDirectionsApi();
			List<List<HashMap<String,String>>> myRoutes=null;

			JSONObject jjson=null;
			try {
				jjson = new JSONObject(myJson[0]); 
				Log.v("json", jjson.toString());
				myRoutes=parser.parserJson(jjson); 

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return myRoutes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			super.onPostExecute(result);
			
			ArrayList<LatLng> points=null;
			PolylineOptions lineOpt=null;
			List<HashMap<String, String>> path;
			HashMap<String, String> map;
			double lat=0,lon=0;
			Log.v("result", result.toString());
			for(int i=0; i<result.size(); i++){
				path=result.get(i);
				points= new ArrayList<LatLng>();
				lineOpt= new PolylineOptions();
				for(int j=0; j<path.size(); j++){
					map=path.get(j);
					if(j==0){
						continue;
					}
					if(j==1){
						continue;
					}
					lat=Double.parseDouble(map.get("lat"));
					lon=Double.parseDouble(map.get("lon"));
					points.add(new LatLng(lat,lon)); 
				}
				lineOpt.addAll(points);
				lineOpt.width(10);
				lineOpt.geodesic(true);
				lineOpt.color(Color.MAGENTA);
			}
			googleMap.addPolyline(lineOpt); //aggiungo polilinea googleMap

		}

	}

	
	public String getUrlFromDirectionApi(Point orig, Point dest){

		String url="";
		String ori="origin="+orig.getLatitude()+","+orig.getLongitude();
		String des="destination="+dest.getLatitude()+","+dest.getLongitude();
		String parameters=ori+"&"+des;
		String sensor="&sensor=false";
		String output="json?";

		String finalOutputString=output.concat(parameters).concat(sensor);
		url="https://maps.googleapis.com/maps/api/directions/"+finalOutputString+"";

		return url; 

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refreshNotification:
			new RetrieveMapping().execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
