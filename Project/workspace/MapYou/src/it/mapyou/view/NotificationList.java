/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.AbstractAsyncTask;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.model.Notification;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.adapter.NotificationAdapter;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
		new RetrieveNotification(act, "Attempt while retrieving notifications...").execute();
	}
	
	class RetrieveNotification extends AbstractAsyncTask<Void, Void, JSONObject>{

		public RetrieveNotification(Activity act, String message) {
			super(act, message);
			// TODO Auto-generated constructor stub
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

		/* (non-Javadoc)
		 * @see it.mapyou.network.AbstractAsyncTask#newOnPostExecute(java.lang.Object)
		 */
		@Override
		protected void newOnPostExecute(JSONObject result) {
			if(result!=null) {
				try {
					List<Notification> notifications = DeviceController.getInstance().getParsingController().getNotificationParser().parseListFromJsonObject(result);
					if(notifications!=null)
						listView.setAdapter(new NotificationAdapter(act, notifications));
				} catch (Exception e) {
					UtilAndroid.makeToast(getApplicationContext(), "Error while parsing notifications...", 5000);
				}
			}else
				UtilAndroid.makeToast(getApplicationContext(), "There are no notifications...", 5000);
		}

		/* (non-Javadoc)
		 * @see it.mapyou.network.AbstractAsyncTask#newOnPreExecute()
		 */
		@Override
		protected void newOnPreExecute() {
			// TODO Auto-generated method stub
			
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
