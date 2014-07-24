/**
 * 
 */
package it.mapyou.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Route;
import it.mapyou.model.StartPoint;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.view.YourMapMeActivity.DownloadYourMapMe;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.GridView;
import android.widget.ListView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MyMapMe extends Activity{

	private Activity act;
	private DeviceController controller;
	private SharedPreferences sp;
	private String admin;
	private ListView listView;
	private List<Integer> numUsr;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_list);
		setTitle("Your MapMe");
		act = this;
		numUsr = new ArrayList<Integer> ();
		listView = (ListView) findViewById(R.id.list);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		admin=sp.getString("nickname", "");
		
		new DownloadYourMapMe().execute(admin);
	}


	class DownloadYourMapMe extends AsyncTask<String, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();


		@Override
		protected JSONObject doInBackground(String... params) {

			try {
				JSONObject json=null;
				parameters.put("nickname", URLEncoder.encode(admin, "UTF-8"));
				json=controller.getServer().requestJson(SettingsServer.YOUR_MAPME, controller.getServer().setParameters(parameters));

				return json;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			List<MapMe> allMapme= getAllMapMe(result);
			numUsr.clear();
			for(int i=0; i<allMapme.size(); i++){
				numUsr.add(new Integer(5));
			}
			listView.setAdapter(new MapMeAdapter(act,allMapme,numUsr));
			listView.setOnItemClickListener(new OnClickMapMe(act, allMapme));
			
		}
	}

	public List<MapMe> getAllMapMe (JSONObject json){

		List<MapMe> allmapme= new ArrayList<MapMe>();
		try {
			JSONArray jsonArr= json.getJSONArray("MapMe");
			for(int i=0; i<jsonArr.length(); i++){
				json=jsonArr.getJSONObject(i);
				MapMe mapme= new MapMe();
				Route route = new Route();
				StartPoint startPoint= new StartPoint();
				EndPoint endPoint = new EndPoint();
				startPoint.setLatitude(Double.parseDouble(json.getString("startLat")));
				startPoint.setLongitude(Double.parseDouble(json.getString("startLon")));
				endPoint.setLatitude(Double.parseDouble(json.getString("endLat")));
				endPoint.setLongitude(Double.parseDouble(json.getString("endLon")));
				route.setStartPoint(startPoint);
				route.setEndPoint(endPoint);
				mapme.setRoute(route);
				mapme.setIdmapme(Integer.parseInt(json.getString("idmapme")));
				mapme.setAdministrator(new User((json.getString("admin"))));
				mapme.setName((json.getString("name")));
				mapme.setMaxNumUsers((Integer.parseInt(json.getString("max_user"))));
				mapme.setStartAddress((json.getString("startAddress")));
				mapme.setEndAddress((json.getString("endAddress")));
				allmapme.add(mapme);
			}
			
		} catch (Exception e) {
			return null;
		}
		return allmapme;
	}
}
