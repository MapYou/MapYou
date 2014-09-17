
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.cache.FileControllerCache;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.navigator.PArserDataFromDirectionsApi;
import it.mapyou.util.UtilAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	private MyLocation myloc;
	private List<MappingUser> mappings;
	private FileControllerCache fileCache;
	private FileControllerCache fileCacheRoutes;

	private static final double EARTH_RADIUS = 6378100.0;
	private int offset;

	// distanza in metri del raggio centrato nel punto di arrivo della mapme
	private static final double PROXIMITY_DISTANCE = 50;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_first_tab);
		cont = this;
		act = this;
		mapme = UtilAndroid.CURRENT_MAPME;

		if (mapme != null) {
			sp = PreferenceManager.getDefaultSharedPreferences(cont);
			sp.edit().putInt("mapmeid", mapme.getModelID()).commit();
			myloc = new MyLocation(this);
			if (initilizeMap()) {
				fileCache = new FileControllerCache(UtilAndroid.NAME_OF_FILE_CACHE, cont);
				fileCacheRoutes= new FileControllerCache(UtilAndroid.ROUTES, cont);
				mappings = new ArrayList<MappingUser>();
				myloc.start();

				if(myloc.getLatitude()>0 && myloc.getLongitude()>0){

					// download route
					new DownlDataFromWebServer().execute(PArserDataFromDirectionsApi.getUrlFromDirectionApi(mapme.getSegment().getStartPoint(),mapme.getSegment().getEndPoint()));

					Timer t = new Timer();
					TimerTask tt = new TimerTask() {

						@Override
						public void run() {
							new RetrieveMapping().execute();
						}
					};
					t.schedule(tt, 3000, 6000);

				}else
					myloc.start();

			} else
				UtilAndroid.makeToast(cont, "Error while creating live mode.", 5000);
		}
	}

	public void refresh(View v) {
		new RetrieveMapping().execute();
	}




	@Override
	public void onBackPressed() {
		myloc.stop();
		Intent i= new Intent(act, MapMeLayoutHome.class);
		startActivity(i);

	}

	private boolean initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.completeMapMapMeFirstTab)).getMap();
			googleMap.setMyLocationEnabled(false);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Problema nella creazione della mappa!",
						Toast.LENGTH_SHORT).show();
			} else {
				googleMap.clear();
			}
		} else
			;
		return googleMap != null;
	}


	class RetrieveMapping extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!UtilAndroid.findConnection(act.getApplicationContext())) {
				UtilAndroid.makeToast(act.getApplicationContext(),
						"Internet Connection not found", 5000);
			}else;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				return fileCache.readS();
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null) {
				UtilAndroid.makeToast(cont, "Please refresh da file....", 5000);
			} else {
				try {
					mappings = DeviceController.getInstance().getParsingController().getMappingParser().parsingAllMappings(new JSONObject(result));
					act.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							showMap();
						}
					});
				} catch (JSONException e) {

					UtilAndroid.makeToast(cont, "Error while read postion!",5000);
				}
			}
		}
	}

	public void showMap() {
		googleMap.clear();

		try {
			String routes=fileCacheRoutes.readS();
			if(routes!=null)
				drawRoutesOnMap(new JSONObject(routes));
			else;
			Segment s = mapme.getSegment();
			Point end = s.getEndPoint();
			Point st = s.getStartPoint();
			if (end != null) {
				MarkerOptions opt = new MarkerOptions();
				opt.position(new LatLng(end.getLatitude(), end.getLongitude()));
				opt.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				opt.title(end.getLocation());
				opt.snippet("Destination");
				googleMap.addMarker(opt);
				//				CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(4).build();
				//				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
			}
			if (st != null) {
				MarkerOptions opt = new MarkerOptions();
				opt.position(new LatLng(st.getLatitude(), st.getLongitude()));
				opt.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
				opt.title(st.getLocation());
				opt.snippet("Start");
				googleMap.addMarker(opt);
			} else;

			for (int i = 0; i < mappings.size(); i++) {
				MappingUser m = mappings.get(i);
				User u = m.getUser();
				Point p = m.getPoint();
				if (p.equals(st) || p.equals(end)) {
					p.setLatitude(p.getLatitude() + 0.00001);
					p.setLongitude(p.getLongitude() + 0.00001);
				} else;
				MarkerOptions opt = new MarkerOptions();
				opt.position(new LatLng(p.getLatitude(), p.getLongitude()));
				opt.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				opt.title(u.getNickname());
				opt.snippet(p.getLocation());
				opt.visible(true);
				googleMap.addMarker(opt);
				// manageProximity(end, p);
			}


		} catch (Exception e) {
			UtilAndroid.makeToast(getApplicationContext(), "Error drawing routes", 2000);

		}


	}

	public class DownlDataFromWebServer extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {

			String data = "";
			try {
				data = PArserDataFromDirectionsApi.getData(url[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask p = new ParserTask();
			p.execute(result);

		}
	}

	public class ParserTask extends AsyncTask<String, Integer, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... myJson) {
			JSONObject jjson = null;

			try {
				jjson = new JSONObject(myJson[0]);
				return jjson;

			} catch (JSONException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			if(result!=null){
				try {
					fileCacheRoutes.write(result.toString());
					//drawRoutesOnMap(result);
				} catch (Exception e1) {

				}
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Error read", 5000);
			}
		}
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

	public void drawRoutesOnMap(JSONObject result){
		PArserDataFromDirectionsApi parser = new PArserDataFromDirectionsApi();
		List<List<HashMap<String, String>>> myRoutes = null;
		myRoutes = parser.parserJson(result);

		ArrayList<LatLng> points = null;
		PolylineOptions lineOpt = null;
		List<HashMap<String, String>> path;
		HashMap<String, String> map;
		double lat = 0, lon = 0;
		for (int i = 0; i < myRoutes.size(); i++) {
			path = myRoutes.get(i);
			points = new ArrayList<LatLng>();
			lineOpt = new PolylineOptions();
			for (int j = 0; j < path.size(); j++) {
				map = path.get(j);
				if (j == 0) {
					continue;
				}
				if (j == 1) {
					continue;
				}
				lat = Double.parseDouble(map.get("lat"));
				lon = Double.parseDouble(map.get("lon"));
				points.add(new LatLng(lat, lon));
			}
			lineOpt.addAll(points);
			lineOpt.width(10);
			lineOpt.geodesic(true);
			lineOpt.color(Color.MAGENTA);
		}
		googleMap.addPolyline(lineOpt); 

	}
}
