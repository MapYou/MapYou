 
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
	private User userInvited;
	private TextView invite;
	private TextView inviteMapme;
	private Notification partecipation;
	private String idUserLogged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.send_partecipation_layout);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		Intent i = getIntent();
		if(i.getStringExtra("viewnotification")==null)
			goToLoginPage();
		else{

			userInvited= new User();
			idUserLogged=sp.getString(UtilAndroid.KEY_ID_USER_LOGGED, "");
			userInvited.setModelID(Integer.parseInt(idUserLogged)); // idUserLogged
			invite=(TextView) findViewById(R.id.textViewInvitoDa);
			inviteMapme=(TextView) findViewById(R.id.textViewMapMeinvito);

			new DownloadPartecipation().execute();
		
		}
	}

	// Retrieve partecipation Data
	class DownloadPartecipation extends AsyncTask<Void, Void, JSONObject>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		@Override
		protected JSONObject doInBackground(Void... params) {

			try {
				JSONObject json=null;
				parameters.put("userinvite", URLEncoder.encode(userInvited.getNickname(), "UTF-8"));
				json=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.SELECT_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));

				return json;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			if(result!=null){
				partecipation= getPartecipation(result);
				if(partecipation.getModelID()!=0 && !partecipation.getNotifier().getNickname().equalsIgnoreCase("null")){

					if(isTypePartecipationSend(partecipation)){
						//gestione Invio notifiche da aadmin

						invite.setText(INVITO.concat(partecipation.getNotifier().getNickname()));
						inviteMapme.setText(MAPME.concat(partecipation.getNotificationObject().getName()));
					}else{
						//gestione richieste di invito
						invite.setText(RICHIESTA.concat(partecipation.getNotifier().getNickname()));
						inviteMapme.setText(MAPME.concat(partecipation.getNotificationObject().getName()));
					}
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Error Partecipation retrieve", 5000);

			}else
				UtilAndroid.makeToast(getApplicationContext(), "Refresh", 5000);
		}
	}

	public Notification getPartecipation (JSONObject json){

		Notification partecipation = new Notification();
		MapMe mapme= new MapMe();
		User userInvite= new User();
		try {
			JSONArray jsonArr= json.getJSONArray("Notification");
			for(int i=0; i<jsonArr.length(); i++){
				json=jsonArr.getJSONObject(i);
				partecipation.setModelID(Integer.parseInt(""+json.getInt("idpartecipation")));
				mapme.setModelID(Integer.parseInt(""+json.getString("idmapme")));
				userInvite.setNickname(json.getString("nickname_send"));
				mapme.setName(json.getString("name_mapme"));
				partecipation.setNotificationType(json.getString("type"));
				partecipation.setNotificationObject(mapme);
				partecipation.setNotifier(userInvite);
				partecipation.setNotified(userInvited);
			}
			return partecipation;

		}catch (Exception e) {
			return null;
		}
	}

	class AcceptPartecipation extends AsyncTask<Void, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		@Override
		protected String doInBackground(Void... params) {

			try {
				String resp=null;
				parameters.put("user", URLEncoder.encode(userInvited.getNickname(), "UTF-8"));
				parameters.put("idp",""+partecipation.getModelID());
				parameters.put("idm", ""+partecipation.getNotificationObject().getModelID());
				resp=DeviceController.getInstance().getServer().
						request(SettingsServer.INSERT_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));

				return resp;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result!=null && result.contains("1")){
				UtilAndroid.makeToast(getApplicationContext(), "You are added in "+partecipation.getNotificationObject().getName(),5000);				

				Intent i = new Intent(NotificationActivity.this, Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}

		}
	}

	class RefusedPartecipation extends AsyncTask<Void, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		@Override
		protected String doInBackground(Void... params) {

			try {
				String resp=null;

				parameters.put("idp",""+partecipation.getModelID());
				resp=DeviceController.getInstance().getServer().
						request(SettingsServer.INSERT_MAPPING, DeviceController.getInstance().getServer().setParameters(parameters));

				return resp;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result!=null && result.contains("1")){
				UtilAndroid.makeToast(getApplicationContext(), "Invite refused!",5000);				

				Intent i = new Intent(NotificationActivity.this, Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
	}

	public void accept(View v){
		
		if(partecipation!=null){
			new AcceptPartecipation().execute();
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error accept",5000);	
	}

	public void refused(View v){
		
		if(partecipation!=null){
			new RefusedPartecipation().execute();
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Error refused",5000);	
	}
	public boolean isTypePartecipationSend (Notification p){
		boolean isPart=false;
		isPart=p.getNotificationType().equalsIgnoreCase("SEND")?true:false;
		return isPart;
	}

	public void goToLoginPage (){
		Intent i = new Intent(NotificationActivity.this, Login.class);
		i.putExtra("notification", "notification");
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);	
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		sp.edit().clear().commit();
		Intent i = new Intent(NotificationActivity.this,Login.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}