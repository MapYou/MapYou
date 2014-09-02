/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.model.MappingUser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UserOnMapMe extends FragmentActivity{

	private GoogleMap googleMap;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.user_on_mapme_layout);
		Bundle b = getIntent().getExtras();
		MappingUser m = b.getParcelable("mapping");
//		MappingUser m = Util.CURRENT_MAPPING;
		if(m!=null){
			initMap();
			changeMapping(m);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MapMeLayoutHome.class);
		startActivity(i);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResumeFragments()
	 */
	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
		//		Mapping m = getIntent().getExtras().getParcelable("mapping");
		//		changeMapping(m);
	}

//	private String getDirectionsUrl(LatLng origin,LatLng dest){
//
//		// Origin of route
//		String str_origin = "origin="+origin.latitude+","+origin.longitude;
//
//		// Destination of route
//		String str_dest = "destination="+dest.latitude+","+dest.longitude;
//
//		// Sensor enabled
//		String sensor = "sensor=false";
//
//		String avoid = "avoid=tolls|highways|ferries";
//
//		// Building the parameters to the web service
//		//        String parameters = str_origin+"&"+str_dest+"&"+"key=AIzaSyDoGN0tYkORBgqkoP0fZ7joMcLBl_TnqaU"+"&"+sensor;
//		String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+avoid;
//
//		// Output format
//		String output = "json";
//
//		// Building the url to the web service
//		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
//
//		return url;
//	}

	public void changeMapping(MappingUser map){
		googleMap.clear();
//		LatLng l = null;
//		for(int i=0; i<map.size()-1; i++){
//			MappingUser m0 = map.get(i);
//			MappingUser m1 = map.get(i+1);
			MarkerOptions opt1 = new MarkerOptions();
			opt1.position(new LatLng(map.getPoint().getLatitude(), map.getPoint().getLongitude()));
			opt1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//			MarkerOptions opt2 = new MarkerOptions();
//			opt2.position(new LatLng(map.getPoint().getLatitude(), map.getPoint().getLongitude()));
//			opt2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			googleMap.addMarker(opt1);
//			googleMap.addMarker(opt2);
//			if(i==0)
//				l=new LatLng(m0.getPoint().getLatitude(), m0.getPoint().getLongitude());
//			else;
//			String url = getDirectionsUrl(opt1.getPosition(), opt2.getPosition());
//
//			DownloadTask downloadTask = new DownloadTask();
//
//			// Start downloading json data from Google Directions API
//			downloadTask.execute(url);

//		}
		CameraPosition c = new CameraPosition.Builder().target(
				opt1.getPosition()).zoom(15).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(c));
	}

	/**
	 * 
	 */
	private void initMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapUserOnMapMe)).getMap();
			googleMap.setMyLocationEnabled(true);

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),"Problema nella creazione della mappa!", Toast.LENGTH_SHORT).show();
			}
		}
	}

//	private String downloadUrl(String strUrl) throws IOException{
//		String data = "";
//		InputStream iStream = null;
//		HttpURLConnection urlConnection = null;
//		try{
//			URL url = new URL(strUrl);
//
//			// Creating an http connection to communicate with url
//			urlConnection = (HttpURLConnection) url.openConnection();
//
//			// Connecting to url
//			urlConnection.connect();
//
//			// Reading data from url
//			iStream = urlConnection.getInputStream();
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//			StringBuffer sb = new StringBuffer();
//
//			String line = "";
//			while( ( line = br.readLine()) != null){
//				sb.append(line);
//			}
//
//			data = sb.toString();
//
//			br.close();
//
//		}catch(Exception e){
//			Log.d("Exception while downloading url", e.toString());
//		}finally{
//			iStream.close();
//			urlConnection.disconnect();
//		}
//		return data;
//	}

	// Fetches data from url passed
//	private class DownloadTask extends AsyncTask<String, Void, String>{
//
//		// Downloading data in non-ui thread
//		@Override
//		protected String doInBackground(String... url) {
//
//			// For storing data from web service
//			String data = "";
//
//			try{
//				// Fetching the data from web service
//				data = downloadUrl(url[0]);
//			}catch(Exception e){
//				Log.d("Background Task",e.toString());
//			}
//			return data;
//		}
//
//		// Executes in UI thread, after the execution of
//		// doInBackground()
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//
//			ParserTask parserTask = new ParserTask();
//
//			// Invokes the thread for parsing the JSON data
//			parserTask.execute(result);
//		}
//	}

//	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
//
//		// Parsing the data in non-ui thread
//		@Override
//		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//			JSONObject jObject;
//			List<List<HashMap<String, String>>> routes = null;
//
//			try{
//				jObject = new JSONObject(jsonData[0]);
//				DirectionsJSONParser parser = new DirectionsJSONParser();
//
//				// Starts parsing data
//				routes = parser.parse(jObject);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			return routes;
//		}
//
//		// Executes in UI thread, after the parsing process
//		@Override
//		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//			PolylineOptions lineOptions = new PolylineOptions();
//			for(int i=0;i<result.size();i++){
//				List<HashMap<String, String>> path = result.get(i);
//
//				for(int j=0;j<path.size();j++){
//					HashMap<String,String> point = path.get(j);
//
//					double lat = Double.parseDouble(point.get("lat"));
//					double lng = Double.parseDouble(point.get("lng"));
//					LatLng position = new LatLng(lat, lng);
//
//					lineOptions.add(position).width(5).color(R.color.Red);
//				}
//			}
//			if(lineOptions!=null)
//				googleMap.addPolyline(lineOptions);
//			else UtilAndroid.makeToast(getApplicationContext(), "Google maps error", 5000);
//
//		}
//	}
}
