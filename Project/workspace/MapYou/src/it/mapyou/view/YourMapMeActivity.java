package it.mapyou.view;
import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

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
	private SharedPreferences sp;
	private boolean inclusion;

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
		
		inclusion = getIntent().getBooleanExtra("inclusion", true);
		new DownloadYourMapMe().execute(String.valueOf(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0)));
	}


	class DownloadYourMapMe extends AsyncTask<String, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		 
		@Override
		protected void onPreExecute() {
			
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else;
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {

			try {
				JSONObject json=null;
				parameters.put("iduser", URLEncoder.encode(params[0], "UTF-8"));
				parameters.put("inclusion", URLEncoder.encode(String.valueOf(inclusion?1:0), "UTF-8"));
				json=DeviceController.getInstance().getServer().requestJson(SettingsServer.YOUR_MAPME, DeviceController.getInstance().getServer().setParameters(parameters));

				return json;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
		 
			List<MapMe> allMapme= DeviceController.getInstance().getParsingController().getMapmeParser().parsingAllMapMe(result);
			if(allMapme!=null){
				listView.setAdapter(inclusion?
						new YourMapMeAdapter(act,allMapme,sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0)):
							new YourMapMeAdapterWithoutInclusion(act,allMapme));
			}else
				UtilAndroid.makeToast(act, "Error while fetching your mapme. Please Refresh", 5000);

		}
	}
}

