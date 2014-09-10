/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatUserToUser extends Activity{

	private static User user;
	private static User currentUser;

	private EditText mess;
	private Activity act;
	private static SharedPreferences sp;
	private static ListView listView;
	private static List<Notification> notif;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatusertouser);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("user")){
			user = (User) b.getSerializable("user");
			if(user!=null){
				notif = new ArrayList<Notification>();
				sp.edit().putBoolean("isChatMode", true).commit();
				TextView n= (TextView) findViewById(R.id.textViewNickname);
				TextView e= (TextView) findViewById(R.id.textViewEmail);
				e.setText(user.getEmail());
				n.setText(user.getNickname());
				act=this;
				mess=(EditText)findViewById(R.id.editTextBroadcast);
				listView = (ListView) findViewById(R.id.listViewUsertoUser);
				currentUser = new User();
				currentUser.setModelID(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
				currentUser.setNickname(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				currentUser.setEmail(sp.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, ""));
				new RetrieveConversation().execute();
			}else
				sp.edit().putBoolean("isChatMode", false).commit();

		}else
			sp.edit().putBoolean("isChatMode", false).commit();
	}

	public void sendmessage (View v){

		String message=mess.getText().toString();

		if(message.length() >0)
			new SendMessage().execute(message);
		else
			UtilAndroid.makeToast(act, "Please insert text message", 5000);


	}
	
	public static void updateGui(Notification n){
		if(n.getNotified()==null){
			n.setNotified(currentUser);
			n.setNotifier(user);
		}else;
		notif.add(0, n);
		listView.setAdapter(new ChatMessageAdapter(notif, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
	}

	class SendMessage extends AsyncTask<String, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String response;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);

		}

		// CHAT CHAT_BROADCAST
		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("admin", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("user", user.getNickname());
				parameters.put("idm",  ""+Integer.parseInt(""+Util.CURRENT_MAPME.getModelID()));
				parameters.put("type",  "CHAT");
				parameters.put("message",  params[0]);
				parameters.put("title",  sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("notif",  "Messaggio da "+sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));

				response=DeviceController.getInstance().getServer().
						request(SettingsServer.CHAT, DeviceController.getInstance().getServer().setParameters(parameters));

				if(response.contains("send")){
					return params[0];
				}
				else if(response.contains("User not found")){
					return "error";
				}else
					return null;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result!=null){
				if(!result.equals("error")){
					Notification n = new Notification();
					n.setNotificationState(result);
					n.setNotified(user);
					n.setNotifier(currentUser);
					updateGui(n);
					mess.setText("");
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Risent message", 5000);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
		}
	}

	class RetrieveConversation extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);

		}

		@Override
		protected JSONObject doInBackground(Void... params) {

			try {
				JSONObject json=null;

				parameters.put("user", ""+
						sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
				parameters.put("idm", ""+Util.CURRENT_MAPME.getModelID());
				json=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_CONVERSATION, DeviceController.getInstance().getServer().setParameters(parameters));

				return json;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if(result!=null){
				try {
					notif = retrieveAllNotification(result);
				} catch (Exception e) {
					notif = new ArrayList<Notification>();
				}
				listView.setAdapter(new ChatMessageAdapter(notif, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
			}
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
			//			MapMe mapme = new MapMe();
			//			mapme.setName(json.getJSONArray("mapme").getJSONObject(0).getString("name"));
			//			mapme.setModelID(Integer.parseInt(
			//					json.getJSONArray("mapme").getJSONObject(0).getString("id")));
			m.setNotified(notified);
			m.setNotifier(notifier);
			//			m.setNotificationType(json.getString("type"));
			m.setNotificationState(json.getString("state"));
			m.setModelID(Integer.parseInt(json.getString("id")));
			//			m.setNotificationObject(mapme);
			Date dt;
			try {
				dt = sdf.parse(json.getString("date"));
			} catch (Exception e) {
				dt = null;
			}
			if(dt!=null){
				GregorianCalendar g = new GregorianCalendar();
				g.setTime(dt);
				m.setDate(g);
			}else;
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		sp.edit().putBoolean("isChatMode", false).commit();
	}


}
