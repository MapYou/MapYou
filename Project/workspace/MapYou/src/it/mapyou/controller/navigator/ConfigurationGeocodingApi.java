package it.mapyou.controller.navigator;


public class ConfigurationGeocodingApi implements ConfigurationExternalAPI{

	private String address;
	
	
	/**
	 * @param address
	 */
	public ConfigurationGeocodingApi(String address) {
		super();
		this.address = address;
	}


	/**
	 * Compone l'url per utilizzare le GeoCoding API
	 * @param address
	 * @return
	 */
	@Override
	public String getUrlFromApi() {
		String url="https://maps.googleapis.com/maps/api/geocode/json?";
		String add = "address="+address;
		String sensor = "sensor=false";
		String link=url.concat(add).concat("&"+sensor);
		return link;
	}

}
