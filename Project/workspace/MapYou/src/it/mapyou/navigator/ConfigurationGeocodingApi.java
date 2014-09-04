package it.mapyou.navigator;


public abstract class ConfigurationGeocodingApi {
	
	
	
	public static String getUrlFrom(String address){

		String url="https://maps.googleapis.com/maps/api/geocode/json?";
		String add = "address="+address;
		String sensor = "sensor=false";
		String link=url.concat(add).concat("&"+sensor);
		return link; 
//http://maps.googleapis.com/maps/api/geocode/json?latlng=41.44106656,14.77795232&sensor=true
	}

}
