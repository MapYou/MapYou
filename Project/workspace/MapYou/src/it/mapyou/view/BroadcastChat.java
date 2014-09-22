
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.model.ChatMessage;
import it.mapyou.model.User;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.adapter.AdapterBoadcastChat;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class BroadcastChat extends MapyouActivity {

	 
	private EditText textMessage;
	private static User currentUser;
	private static List<ChatMessage>notification;
	private static ListView list;
	private Activity act;
	private TextView numUs;
	private TextView nameM;
	//private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.broadcastchat);
		setTitle("MapYou Broadcast Chat");
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
			nameM.setText("MapMe: "+UtilAndroid.CURRENT_MAPME.getName());

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
		list.setAdapter(new AdapterBoadcastChat(notification, currentUser.getModelID()));
	}

	class SendMessageInBroadcast extends AsyncTask<String, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();


		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			}else;
		}
		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("admin", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("idm",  ""+Integer.parseInt(""+UtilAndroid.CURRENT_MAPME.getModelID()));
				parameters.put("message",  params[0]);
				parameters.put("title",  sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("notif",  "Messaggio da "+sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));

				String response=DeviceController.getInstance().getServer().request(SettingsServer.BROADCAST, DeviceController.getInstance().getServer().setParameters(parameters));

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
					GregorianCalendar g= new GregorianCalendar();
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
		sp.edit().putBoolean("isBroadcastMode", true).commit();
		Intent i = new Intent(act, CompleteMapMeLayoutHome.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("currentTab", 1);
		act.startActivity(i);
	}

	class RetrieveBroadcastConversation extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(!UtilAndroid.findConnection(act.getApplicationContext()))
			{
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);
			}else;
		}
		@Override
		protected JSONObject doInBackground(Void... params) {

			try {
				JSONObject json=null;

				parameters.put("user", ""+sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1));
				parameters.put("idm", ""+UtilAndroid.CURRENT_MAPME.getModelID());
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

			if(result!=null){
				try {
					notification = DeviceController.getInstance().getParsingController().getChatMessageParser().parsingAllChatMessageNotifcation(result);
				} catch (Exception e) {
					notification = new ArrayList<ChatMessage>();
				}
				list.setAdapter(new AdapterBoadcastChat(notification, sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)));
			}
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
