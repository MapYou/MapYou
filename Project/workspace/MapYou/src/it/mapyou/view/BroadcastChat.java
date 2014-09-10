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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class BroadcastChat extends Activity {

	private String[] users;
	private static SharedPreferences sp;
	private EditText textMessage;
	private static User currentUser;
	private static List<Notification>notification;
	private static ListView list;
	private TextView numUs;
	private TextView nameM;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.broadcastchat);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		textMessage=(EditText) findViewById(R.id.editTextB);
		list=(ListView) findViewById(R.id.listView1);
		numUs=(TextView) findViewById(R.id.textNumuserMaBrod);
		nameM=(TextView) findViewById(R.id.textNameMapmeBrod);
		currentUser = new User();
		currentUser.setModelID(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
		currentUser.setNickname(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
		currentUser.setEmail(sp.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, ""));
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("users")){
			users = b.getStringArray("users");
			if(users!=null && users.length>0){
				notification= new ArrayList<Notification>();
				sp.edit().putBoolean("isBroadcastMode", false).commit();
				int n=users.length+1;
				numUs.setText("Users: "+n);
				nameM.setText("MapMe: "+Util.CURRENT_MAPME.getName());
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

	public void broadcastmess(View v){

		String message= textMessage.getText().toString();

		if(message.length() >0){
			new SendMessageInBroadcast().execute(message);
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error", 5000);

	}
	
	public static void updateGui(Notification n){
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
					Notification n = new Notification();
					n.setNotificationState(result); //messaggio
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

}
