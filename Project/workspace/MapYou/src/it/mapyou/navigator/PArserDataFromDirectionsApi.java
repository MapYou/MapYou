package it.mapyou.navigator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class PArserDataFromDirectionsApi {

	public PArserDataFromDirectionsApi() {

	}

	public List<List<HashMap<String,String>>>  parserJson (JSONObject json){

		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();

		JSONArray jroute=null, jsteps=null, jlegs=null;
		JSONObject jdistance=null,jduration=null;

		try {
			jroute= json.getJSONArray("routes");
			 

			for(int i=0; i< jroute.length(); i++){
				jlegs= ((JSONObject)jroute.get(i)).getJSONArray("legs");  

				List<HashMap<String, String>> percorso = new ArrayList<HashMap<String,String>>();
				for(int j=0; j<jlegs.length(); j++){

					jdistance= ((JSONObject) jlegs.get(i)).getJSONObject("distance");
					HashMap<String, String> dis= new HashMap<String, String>();
					dis.put("distance", jdistance.getString("text"));

					jduration= ((JSONObject) jlegs.get(i)).getJSONObject("duration");
					HashMap<String, String> dur= new HashMap<String, String>();
					dur.put("duration", jduration.getString("text"));

					percorso.add(dis);
					percorso.add(dur);

					jsteps= ((JSONObject) jlegs.get(i)).getJSONArray("steps");
					for(int z=0; z<jsteps.length(); z++){
						String polyline = "";
						polyline = (String)((JSONObject)((JSONObject)jsteps.get(z)).get("polyline")).get("points");
						List<LatLng> list = decodePoly(polyline);

						 
						for(int l=0;l<list.size();l++){
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude));
							hm.put("lon", Double.toString(((LatLng)list.get(l)).longitude));
							percorso.add(hm);
						}
					}
				}

				routes.add(percorso);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return routes;


	}


	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}
		return poly;
	}
	
	public static String getData(String myUrl) throws Exception{

		URL url=null;
		HttpURLConnection urlConn=null;
		BufferedReader reader=null;
		String line=null;
		StringBuffer bf=null;
		String data="";
		//JSONObject json=null;

		try {
			//encoder = URLEncoder.encode(myUrl, "UTF-8");
			url= new URL(myUrl);
			urlConn= (HttpURLConnection) url.openConnection();
			reader= new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			bf=new StringBuffer();

			while((line=reader.readLine())!=null){
				bf.append(line);
			}
			data=bf.toString();
			reader.close();
			//json= new JSONObject(data);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			 
			urlConn.disconnect();
		}
		return data;
	}


}


