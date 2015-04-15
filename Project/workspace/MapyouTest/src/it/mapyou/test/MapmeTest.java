package it.mapyou.test;

import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.Server;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.controller.parsing.external.ConfigurationGeocodingApi;
import it.mapyou.controller.parsing.external.ParserDataFromGeocoding;
import it.mapyou.model.MapMe;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;


public class MapmeTest extends TestCase {

	private String res;
	private String page; 
	private HashMap<String, String> parameters;
	private DeviceController dev;
	private Server ser;


	protected void setUp() throws Exception {

		dev = DeviceController.getInstance();
		parameters = new HashMap<String, String>();
		dev.init();
		ser = dev.getServer();
		page = SettingsServer.NEW_MAPME;
	}


	public void test_new_mapme(){

		parameters.clear();
		parameters.put("user", "user");
		parameters.put("name", "peppinoMapme");
		parameters.put("slat", "12.545");
		parameters.put("slon", "12.345");
		parameters.put("elat", "45.120");
		parameters.put("elon", "41.2365");
		parameters.put("mnu", "3");
		parameters.put("sadd", "startLocation");
		parameters.put("eadd", "endLocation");
		res=ser.request(page, ser.setParameters(parameters));

		assertEquals("-1",res);
	}


	public void get_mapme(){
		page = SettingsServer.YOUR_MAPME;
		parameters.clear();
		parameters.put("iduser", "66");
		parameters.put("inclusion", "1");
		try {
			List<MapMe> map = 
					dev.getParsingController().getMapmeParser().parseListFromJsonObject(ser.requestJson(page, ser.setParameters(parameters)));
			boolean f = false;
			for(int i=0; i<map.size(); i++){
				if(map.get(i).getName().equals("peppinoMapmehjl"))
					f=true;
			}
			if(!f)
				fail("non ho trovato la mapme");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test_get_location(){
		String url= new ConfigurationGeocodingApi("napoli").getUrlFromApi();
		List<HashMap<String, String>> listOfpoints= null;
		ParserDataFromGeocoding parser= new ParserDataFromGeocoding();
		String data= parser.retrieveData(url);
		try {
			listOfpoints= parser.parse(new JSONObject(data));
//			List<HashMap<String, String>> allElements= new ArrayList<HashMap<String,String>>();
			int size = listOfpoints.size();
			assertTrue(size>=1);
			for(HashMap<String, String> m: listOfpoints){
//				String lat=m.get("lat");
//				String lon=m.get("lng");
				String address=m.get("formatted_address");
				assertTrue(address.contains("napoli"));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
