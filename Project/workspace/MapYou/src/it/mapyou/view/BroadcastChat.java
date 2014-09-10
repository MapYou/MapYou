/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.ChatMessage;
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

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class BroadcastChat extends Activity {

	private String[] users;
	private static SharedPreferences sp;
	private EditText textMessage;
	private static User currentUser;
	private static List<ChatMessage>notification;
	private static ListView list;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.broadcastchat);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		textMessage=(EditText) findViewById(R.id.editTextBroadcast);
		list=(ListView) findViewById(R.id.listView1);
		currentUser = new User();
		currentUser.setModelID(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
		currentUser.setNickname(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
		currentUser.setEmail(sp.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, ""));
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("users")){
			users = b.getStringArray("users");
			if(users!=null && users.length>0){
				notification= new ArrayList<ChatMessage>();
				sp.edit().putBoolean("isBroadcastMode", false).commit();
				new RetrieveBroadcastConversation().execute();
			}else{
				sp.edit().putBoolean("isBroadcastMode", true).commit();

			}
		}else
			sp.edit().putBoolean("isBroadcastMode", true).commit();

	}

	public String getUsers (String[] us){

		StringBuffer bf=new StringBuffer();
		for(int i=0; i<us.length; i++){
			bf.append(us[i]+";");
		}
		return bf.toString().substring(0, bf.length()-1);
	}

	public void broadc(View v){

		String message= textMessage.getText().toString();

		if(message.length() >0){
			new SendMessageInBroadcast().execute(message);
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error", 5000);

	}
	
	public static void updateGui(ChatMessage n){
		if(n.getNotified()==null){
			n.setNotified(currentUser);
	 
		}else;
		notification.add(0, n);
		list.setAdapter(new AdapterBoadcastChat(notification, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
	}

	class SendMessageInBroadcast extends AsyncTask<String, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String response;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
		}

 
	 
		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("admin", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("users", getUsers(users));
				parameters.put("idm",  ""+Integer.parseInt(""+Util.CURRENT_MAPME.getModelID()));
				parameters.put("message",  params[0]);
				parameters.put("title",  sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("notif",  "Messaggio da "+sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));

				response=DeviceController.getInstance().getServer().
						request(SettingsServer.BROADCAST, DeviceController.getInstance().getServer().setParameters(parameters));

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
					ChatMessage n = new ChatMessage();
					n.setMessage(result); //messaggio
					n.setNotifier(currentUser);
					updateGui(n);
					textMessage.setText("");
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Risent message broadcast", 5000);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
		}
	}
	 
	@Override
	public void onBackPressed() {
		 
		super.onBackPressed();
		sp.edit().putBoolean("isBroadcastMode", true).commit();
	}
	
	class RetrieveBroadcastConversation extends AsyncTask<Void, Void, JSONObject>{

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
				parameters.put("querytype", "d");
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
					notification = retrieveAllNotification(result);
				} catch (Exception e) {
					notification = new ArrayList<ChatMessage>();
				}
				list.setAdapter(new AdapterBoadcastChat(notification, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
			}
		}
	}

	public List<ChatMessage> retrieveAllNotification(JSONObject result){
		try {
			List<ChatMessage> notifications = new ArrayList<ChatMessage>();
			JSONArray jsonArr = result.getJSONArray("");
			for(int i=0; i<jsonArr.length(); i++){
				ChatMessage mp= getNotificationFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					notifications.add(mp);
			}
			return notifications;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<ChatMessage>();
		}
	}

	private ChatMessage getNotificationFromMapme (JSONObject json){


		try {
			ChatMessage m= new ChatMessage();
			User notifier = getUserByJSon(json.getJSONArray("notifier"));
			User notified = getUserByJSon(json.getJSONArray("notified"));
			//			MapMe mapme = new MapMe();
			//			mapme.setName(json.getJSONArray("mapme").getJSONObject(0).getString("name"));
			//			mapme.setModelID(Integer.parseInt(
			//					json.getJSONArray("mapme").getJSONObject(0).getString("id")));
			m.setNotified(notified);
			m.setNotifier(notifier);
			//			m.setNotificationType(json.getString("type"));
			m.setMessage(json.getString("state"));
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

	
}
