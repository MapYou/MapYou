/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Notification;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
				List<Notification> notifications = retrieveAllNotification(result);
				if(notifications!=null)
					listView.setAdapter(new NotificationAdapter(act, notifications));
			}else
				UtilAndroid.makeToast(getApplicationContext(), "There are no notifications...", 5000);
		}
	}
	
	public List<Notification> retrieveAllNotification(JSONObject result){
		try {
			List<Notification> notifications = new ArrayList<Notification>();
			JSONArray jsonArr = result.getJSONArray("");
			for(int i=0; i<jsonArr.length(); i++){
				Notification mp= getNotificationFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					notifications.add(mp);
			}
			return notifications;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Notification>();
		}
	}

	private Notification getNotificationFromMapme (JSONObject json){


		try {
			Notification m= new Notification();
			User notifier = getUserByJSon(json.getJSONArray("notifier"));
			User notified = getUserByJSon(json.getJSONArray("notified"));
			MapMe mapme = new MapMe();
			mapme.setName(json.getJSONArray("mapme").getJSONObject(0).getString("name"));
			mapme.setModelID(Integer.parseInt(
					json.getJSONArray("mapme").getJSONObject(0).getString("id")));
			m.setNotified(notified);
			m.setNotifier(notifier);
			m.setNotificationType(json.getString("type"));
			m.setModelID(Integer.parseInt(json.getString("id")));
			m.setNotificationObject(mapme);
			return m;
		}catch (Exception e) {
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
	
}
