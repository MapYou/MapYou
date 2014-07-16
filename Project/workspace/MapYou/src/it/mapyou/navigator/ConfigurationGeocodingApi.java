package it.mapyou.navigator;


public abstract class ConfigurationGeocodingApi {
	
	
	
	protected static String getUrlFrom(String address){

		String url="https://maps.googleapis.com/maps/api/geocode/json?";
		String add = "address="+address;
		String sensor = "sensor=false";
		String link=url.concat(add).concat("&"+sensor);
		return link; 

	}

}
