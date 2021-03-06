
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.FileControllerCache;
import it.mapyou.controller.parsing.external.ParserDataFromDirectionsApi;
import it.mapyou.model.MapMe;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.util.UtilAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 * 
 */

public class LiveActivity extends MapyouActivity {

	private GoogleMap googleMap;
	private MapMe mapme;
	private Context cont;
	private Activity act;
	private static MyLocation myloc;
	private List<MappingUser> mappings;
	private List<Marker> listOfMarker;
	private Marker marker;


	/**
	 * @return the myloc
	 */
	public static MyLocation getMyloc() {
		return myloc;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_mapme_first_tab);
		cont = this;
		act = this;
		mapme = UtilAndroid.CURRENT_MAPME;

		if (mapme != null) {

			if (initilizeMap()) {
				mappings = new ArrayList<MappingUser>();
				listOfMarker= new ArrayList<Marker>();
				sp.edit().putInt("mapmeid", mapme.getModelID()).commit();
				myloc = new MyLocation(this,sp);
				myloc.start();

				// download route
				new DownlDataFromWebServer().execute(new ParserDataFromDirectionsApi(
						mapme.getSegment().getStartPoint(),mapme.getSegment().getEndPoint()).getUrlFromApi());

				Timer t = new Timer();
				TimerTask tt = new TimerTask() {

					@Override
					public void run() {
						new RetrieveMapping().execute();
					}
				};
				t.schedule(tt, 5000, 15000);
			} else
				UtilAndroid.makeToast(cont, "Error while creating live mode.", 5000);
		}
	}

	public void refresh(View v) {
		new RetrieveMapping().execute();
	}


	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		alert("Alert!", "Are sure you want to come back? ");

	}

	private boolean initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.completeMapMapMeFirstTab)).getMap();
			googleMap.setMyLocationEnabled(false);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!",Toast.LENGTH_SHORT).show();
			}else {
				googleMap.clear();
			}
		} else;
		return googleMap != null;
	}


	class RetrieveMapping extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!UtilAndroid.findConnection(act.getApplicationContext())) {
				UtilAndroid.makeToast(act.getApplicationContext(),"Internet Connection not found", 5000);
			}else;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				return FileControllerCache.getInstance(UtilAndroid.NAME_OF_FILE_CACHE).readS();
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
					mappings = DeviceController.getInstance().getParsingController().getMappingParser().parseListFromJsonObject(new JSONObject(result));
					act.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {
								showMap();
							} catch (Exception e) {
							}
						}
					});
				} catch (Exception e) {
					UtilAndroid.makeToast(cont, "Waiting for detecting postion!",3000);
				}
			}
		}
	}

	public void showMap() {
		try {

			if(listOfMarker.size() >0)
				removeAllMarker();
			else;

			Segment s = mapme.getSegment();
			Point end = s.getEndPoint();
			Point st = s.getStartPoint();
			if (end != null) {
				MarkerOptions opt = new MarkerOptions();
				opt.position(new LatLng(end.getLatitude(), end.getLongitude()));
				opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				opt.title(end.getLocation());
				opt.snippet("Destination");
				googleMap.addMarker(opt).setPosition(new LatLng(end.getLatitude(), end.getLongitude()));
			}
			if (st != null) {
				MarkerOptions opt = new MarkerOptions();
				opt.position(new LatLng(st.getLatitude(), st.getLongitude()));
				opt.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
				opt.title(st.getLocation());
				opt.snippet("Start");

				googleMap.addMarker(opt).setPosition(new LatLng(st.getLatitude(), st.getLongitude()));
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
				opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				opt.title(u.getNickname());
				opt.snippet(p.getLocation());
				opt.visible(true);

				marker=googleMap.addMarker(opt);
				listOfMarker.add(marker);

				if(u.getNickname().equals(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""))){
					CameraPosition c = new CameraPosition.Builder().target(opt.getPosition()).zoom(7).build();
					googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
				}else;
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
				data = ParserDataFromDirectionsApi.getData(url[0]);
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
					//fileCacheRoutes.write(result.toString());

					drawRoutesOnMap(result);
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
		ParserDataFromDirectionsApi parser = new ParserDataFromDirectionsApi();
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

	public void removeAllMarker (){

		for(int i=0; i<listOfMarker.size(); i++){
			marker= listOfMarker.get(i);
			marker.remove();
		}
	}



	public void alert( String title, String message ){

		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle( title )
		.setMessage( message )
		.setCancelable(false)
		.setPositiveButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		})
		.setNegativeButton("Yes", new DialogInterface.OnClickListener() {


			public void onClick(DialogInterface arg0, int arg1) {
				myloc.stop();
				Intent i = new Intent(act, MapMeTab.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				act.startActivity(i);


			}
		}).show();
	}
}
