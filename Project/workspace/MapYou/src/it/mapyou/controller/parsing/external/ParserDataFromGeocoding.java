package it.mapyou.controller.parsing.external;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserDataFromGeocoding extends ParserDataFromExternalServer {

	public List<HashMap<String,String>> parse(JSONObject jObject){

		JSONArray jarray=null;
		try {
			jarray=jObject.getJSONArray("results");

			return getPlaces(jarray);

		} catch (JSONException e) {
			return null;
		}
		

	}

	private List<HashMap<String, String>> getPlaces(JSONArray jPlaces){
		int placesCount = jPlaces.length();
		List<HashMap<String, String>> placesList = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> place = null;

		for(int i=0; i<placesCount;i++){
			try {
				place = getPlace((JSONObject)jPlaces.get(i));
				placesList.add(place);

			} catch (JSONException e) {
				return null;
			}
		}

		return placesList;
	}

	private HashMap<String, String> getPlace(JSONObject jPlace){

		

		try {
			HashMap<String, String> place = new HashMap<String, String>();
			String formatted_address = "-NA-";
			String lat="";
			String lng="";
			if(!jPlace.isNull("formatted_address")){
				formatted_address = jPlace.getString("formatted_address");
			}

			lat = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
			lng = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");

			place.put("formatted_address", formatted_address);
			place.put("lat", lat);
			place.put("lng", lng);

			return place;
		}catch (JSONException e) {
			return null;
		}
	}

	  




}
