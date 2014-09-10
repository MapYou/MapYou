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

import java.text.ParseException;
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
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class BroadcastChat extends Activity {

	private static SharedPreferences sp;
	private EditText textMessage;
	private static User currentUser;
	private static List<ChatMessage>notification;
	private static ListView list;
	private Activity act;

	private TextView numUs;
	private TextView nameM;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.broadcastchat);
		setTitle("MapYou Broadcast Chat");
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		textMessage=(EditText) findViewById(R.id.editTextB);
		list=(ListView) findViewById(R.id.listView1);
		numUs=(TextView) findViewById(R.id.textNumuserMaBrod);
		nameM=(TextView) findViewById(R.id.textNameMapmeBrod);
		this.act = this;
		currentUser = new User();
		currentUser.setModelID(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
		currentUser.setNickname(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
		currentUser.setEmail(sp.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, ""));
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("num_users")){
			int users = b.getInt("num_users");
			notification= new ArrayList<ChatMessage>();
			sp.edit().putBoolean("isBroadcastMode", false).commit();

			numUs.setText("Users: "+(users+1));
			nameM.setText("MapMe: "+Util.CURRENT_MAPME.getName());

			new RetrieveBroadcastConversation().execute();
		}else
			sp.edit().putBoolean("isBroadcastMode", true).commit();

	}

	public void broadcastmess(View v){

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
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  

		private ProgressDialog p;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(act.getApplicationContext()))
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			else{
				p = new ProgressDialog(act);
				p.setMessage("Loading...");
				p.setIndeterminate(false);
				p.setCancelable(false);
				p.show();
			}

		}



		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("admin", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
//				parameters.put("users", getUsers(users));
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
			p.dismiss();
			if(result!=null){
				if(!result.equals("error")){
					ChatMessage n = new ChatMessage();
					n.setMessage(result); //messaggio
					n.setNotifier(currentUser);
					Date d= new Date(System.currentTimeMillis());
					
					GregorianCalendar g= new GregorianCalendar();
					try {
						g.setTime(sdf.parse(d.toString()));
					} catch (ParseException e) {
						
					}
					n.setDate(g);
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
		private ProgressDialog p;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else{
				p = new ProgressDialog(act);
				p.setMessage("Loading...");
				p.setIndeterminate(false);
				p.setCancelable(false);
				p.show();
			}
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
			p.dismiss();
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		try {
			ChatMessage m= new ChatMessage();
			Date dt;
			try {
				String s=json.getString("date");
				dt = sdf.parse(s);
			} catch (Exception e) {
				dt = null;
			}
			if(dt!=null){
				GregorianCalendar g = new GregorianCalendar();
				g.setTime(dt);
				m.setDate(g);
			}else;
			User notifier = getUserByJSon(json.getJSONArray("notifier"));
			User notified = getUserByJSon(json.getJSONArray("notified"));
			m.setNotified(notified);
			m.setNotifier(notifier);
			m.setMessage(json.getString("state"));
			m.setModelID(Integer.parseInt(json.getString("id")));
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


	@Override

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menuchat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refreshChat:
			new RetrieveBroadcastConversation().execute();
			return true;
		case R.id.clearChat:
			notification.removeAll(notification);
			list.setAdapter(new AdapterBoadcastChat(notification, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
