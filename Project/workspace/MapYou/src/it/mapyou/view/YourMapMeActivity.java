package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Point;
import it.mapyou.model.Route;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

public class YourMapMeActivity extends  Activity {


	private Activity act;
	private ListView listView;
	private DeviceController controller;
	private SharedPreferences sp;
	private String admin;

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_list);
		setTitle("Your MapMe");
		act = this;
		listView = (ListView) findViewById(R.id.list);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		admin=String.valueOf(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0));
		new DownloadYourMapMe().execute(admin);
	}


	class DownloadYourMapMe extends AsyncTask<String, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected JSONObject doInBackground(String... params) {

			try {
				JSONObject json=null;
				parameters.put("iduser", URLEncoder.encode(admin, "UTF-8"));
				json=controller.getServer().requestJson(SettingsServer.YOUR_MAPME, controller.getServer().setParameters(parameters));

				return json;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			//			try {
			//				JSONArray ar = result.getJSONArray("Mapme");
			//				for(int i=0; i<ar.length(); i++){
			//					JSONObject j = ar.getJSONObject(i);
			//					JSONArray seg = j.getJSONArray("segment");
			//					for(int k=0; k<seg.length(); k++){
			//						JSONObject o = seg.getJSONObject(k);
			//						JSONArray jep = o.getJSONArray("endPoint");
			//						JSONArray jsp = o.getJSONArray("startPoint");
			//						for(int t=0; t<jep.length(); t++){
			//							JSONObject oo = jep.getJSONObject(t);
			//						}
			//						for(int t=0; t<jsp.length(); t++){
			//							JSONObject oo = jsp.getJSONObject(t);
			//						}
			//					}
			//					JSONArray adm = j.getJSONArray("administrator");
			//					for(int k=0; k<seg.length(); k++){
			//						JSONObject o = seg.getJSONObject(k);
			//					}
			//					Log.i("jsonjsonjson", j.toString());
			//				}
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//			}
			List<MapMe> allMapme= getAllMapMe(result);
			if(allMapme!=null){
				listView.setAdapter(new YourMapMeAdapter(act,allMapme));
//				listView.setOnItemClickListener(new OnClickMapMe(act, allMapme));
			}else
				UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);

		}
	}

	public List<MapMe> getAllMapMe (JSONObject json){

		try {
			List<MapMe> allmapme= new ArrayList<MapMe>();
			JSONArray jsonArr= json.getJSONArray("Mapme");
			for(int i=0; i<jsonArr.length(); i++){

				JSONObject jjson=jsonArr.getJSONObject(i);
				MapMe mapme= new MapMe();
				Route route = new Route();
				Point startPoint= getPointByJSon(jjson.getJSONArray("segment").getJSONObject(0).getJSONArray("startPoint"));
				Point endPoint = getPointByJSon(jjson.getJSONArray("segment").getJSONObject(0).getJSONArray("endPoint"));
				User admin = getUserByJSon(jjson.getJSONArray("administrator"));

				if(admin!=null && startPoint!=null && endPoint!=null){
					route.setStartPoint(startPoint);
					route.setEndPoint(endPoint);
					mapme.setSegment(route);

					mapme.setModelID(Integer.parseInt(jjson.getString("id")));
					mapme.setAdministrator(admin);
					mapme.setName(jjson.getString("name"));
					mapme.setMaxNumUsers((Integer.parseInt(jjson.getString("maxNumUsers"))));
					mapme.setNumUsers((Integer.parseInt(jjson.getString("numUsers"))));

					allmapme.add(mapme);
				}
			}
			return allmapme;

		} catch (Exception e) {
			return null;
		}
	}

	private User getUserByJSon (JSONArray jsonArr){

		try {
			User user= new User();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private Point getPointByJSon (JSONArray jsonArr){

		try {
			Point ptn= new Point();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
				ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
				ptn.setLocation(json.getString("location"));
			}
			return ptn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

