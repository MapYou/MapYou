
package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Notification;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NotificationActivity extends Activity {

	private final String INVITO="Invito da:  ";
	private final String RICHIESTA="Richiesta di partecipazione da:  ";
	private final String MAPME="per la MapMe:   ";
	private SharedPreferences sp;
	//	private User userInvited;
	private TextView invite;
	private TextView inviteMapme;
	private Notification notification;
	private String notificationType;
	private boolean isAccept=false;
	private int notificationID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.send_partecipation_layout);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		notificationID = getIntent().getIntExtra("notification_id", -1);

		//		userInvited= new User();
		//		userInvited.setModelID(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)); // idUserLogged
		//		userInvited.setNickname(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
		invite=(TextView) findViewById(R.id.textViewInvitoDa);
		inviteMapme=(TextView) findViewById(R.id.textViewMapMeinvito);

		new DownloadPartecipation().execute();
	}

	// Retrieve partecipation Data
	class DownloadPartecipation extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		@Override
		protected JSONObject doInBackground(Void... params) {

			JSONObject json=null;

			parameters.put("idnot", ""+notificationID);
			json=DeviceController.getInstance().getServer().
					requestJson(SettingsServer.SELECT_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));


			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			if(result!=null) {
				notification= getPartecipation(result);
				if(notification.getModelID() >0){

					if(isTypePartecipationSend(notification)){
						//gestione Invio notifiche da aadmin

						invite.setText(INVITO.concat(notification.getNotifier().getNickname()));
						inviteMapme.setText(MAPME.concat(notification.getNotificationObject().getName()));
					}else{
						//gestione richieste di invito
						invite.setText(RICHIESTA.concat(notification.getNotifier().getNickname()));
						inviteMapme.setText(MAPME.concat(notification.getNotificationObject().getName()));
					}
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Error Partecipation retrieve", 5000);

			}else
				UtilAndroid.makeToast(getApplicationContext(), "Refresh", 5000);
		}
	}

	public Notification getPartecipation (JSONObject json){

		Notification notification = new Notification();
		MapMe mapme= new MapMe();
		User userInvite= new User();
		User userNotified = new User();
		JSONArray jsonUser= null;
		JSONArray jsonMapme= null;
		try {
			JSONArray jsonArr= json.getJSONArray("");
			for(int i=0; i<jsonArr.length(); i++){
				JSONObject hhh=jsonArr.getJSONObject(i);
				notification.setModelID(Integer.parseInt(""+hhh.getInt("id")));

				jsonUser=hhh.getJSONArray("notifier");
				for(int j=0; j<jsonUser.length(); j++){
					JSONObject jjj=jsonUser.getJSONObject(j);
					userInvite.setNickname(jjj.getString("nickname"));
					userInvite.setModelID(Integer.parseInt(jjj.getString("id")));
				}
				
				jsonUser=hhh.getJSONArray("notified");
				for(int j=0; j<jsonUser.length(); j++){
					JSONObject jjj=jsonUser.getJSONObject(j);
					userNotified.setNickname(jjj.getString("nickname"));
					userNotified.setModelID(Integer.parseInt(jjj.getString("id")));
				}

				jsonMapme=hhh.getJSONArray("mapme");
				for(int z=0; z<jsonMapme.length(); z++){
					JSONObject jjj=jsonMapme.getJSONObject(z);
					mapme.setName(jjj.getString("name"));
					mapme.setModelID(Integer.parseInt(jjj.getString("id")));
				}

				notification.setNotificationType(hhh.getString("type"));
				notificationType = notification.getNotificationType();
				notification.setNotificationObject(mapme);
				notification.setNotifier(userInvite);
				notification.setNotified(userNotified);
			}
			return notification;

		}catch (Exception e) {
			return null;
		}
	}

	class AcceptPartecipation extends AsyncTask<Boolean, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		@Override
		protected String doInBackground(Boolean... params) {

			try {
				String resp=null;

				parameters.put("iduser", URLEncoder.encode(""+
						(notificationType.equals("REQUEST")?
								notification.getNotifier().getModelID():
									notification.getNotified().getModelID()
								), "UTF-8"));
				parameters.put("idnot",""+notification.getModelID());
				parameters.put("idm", ""+notification.getNotificationObject().getModelID());
				parameters.put("isAccept", ""+String.valueOf(params[0]));
				resp=DeviceController.getInstance().getServer().
						request(SettingsServer.MANAGEMENT_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));


				return resp;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result!=null){
				if(notificationType.equals("SEND")){
					if(result.contains("true")){
						UtilAndroid.makeToast(getApplicationContext(), "You are added in "+notification.getNotificationObject().getName(),5000);				

					}else if(result.contains("false")){
						UtilAndroid.makeToast(getApplicationContext(), "Error: you are already added in "+notification.getNotificationObject().getName(),5000);				
					}else if(result.contains("refused")){
						UtilAndroid.makeToast(getApplicationContext(), "You are refused invite in "+notification.getNotificationObject().getName(),5000);				
					}else if(result.contains("error")){
						UtilAndroid.makeToast(getApplicationContext(), "Error",5000);				
					}else{
						UtilAndroid.makeToast(getApplicationContext(), "Server error.",5000);				
					}
				}else {

				}

			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Connection error.",5000);				

			}

			startActivityAfterNotification();

		}
	}


	public void startActivityAfterNotification(){
		int modelID = sp.contains(UtilAndroid.KEY_ID_USER_LOGGED)?
				sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1):-1;
				if(modelID>0){
					Intent i = new Intent(NotificationActivity.this, DrawerMain.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);	
				}else{
					Intent i = new Intent(NotificationActivity.this, Login.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
	}


	public void accept(View v){

		if(notification!=null){
			isAccept=true;
			new AcceptPartecipation().execute(isAccept);
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error accept",5000);	
	}

	public void refused(View v){

		if(notification!=null){
			isAccept=false;
			new AcceptPartecipation().execute(isAccept);
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error accept",5000);	
	}


	public boolean isTypePartecipationSend (Notification p){
		boolean isPart=false;
		isPart=p.getNotificationType().equals("SEND")?true:false;
		return isPart;
	}

//	public void goToLoginPage (){
//		Intent i = new Intent(NotificationActivity.this, Login.class);
//		i.putExtra("notification", "notification");
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(i);	
//	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		sp.edit().clear().commit();
		Intent i = new Intent(NotificationActivity.this,Login.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
