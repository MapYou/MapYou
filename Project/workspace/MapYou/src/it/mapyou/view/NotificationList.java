/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NotificationList extends Activity{


	private ListView listView;
	private SharedPreferences sp;
	private Activity act;
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_list);
		act = this;
		listView = (ListView)findViewById(R.id.list);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		retrieveAllPendingNotification();
	}
	
	private void retrieveAllPendingNotification(){
		new RetrieveNotification().execute();
	}
	
	class RetrieveNotification extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else;

		}
		
		@Override
		protected JSONObject doInBackground(Void... params) {

			JSONObject json=null;

			parameters.put("user", ""+
			sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
			json=DeviceController.getInstance().getServer().
					requestJson(SettingsServer.GET_ALL_NOTIFICATION, DeviceController.getInstance().getServer().setParameters(parameters));

			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if(result!=null) {
				List<Notification> notifications = DeviceController.getInstance().getParsingController().getNotificationParser().parsingAllNotification(result);
				if(notifications!=null)
					listView.setAdapter(new NotificationAdapter(act, notifications));
			}else
				UtilAndroid.makeToast(getApplicationContext(), "There are no notifications...", 5000);
		}
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refreshNotification:
			retrieveAllPendingNotification();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
