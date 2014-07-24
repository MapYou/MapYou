/**
 * 
 */
package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Partecipation;
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
	private DeviceController controller;
	private SharedPreferences sp;
	private User userInvited;
	private TextView invite;
	private TextView inviteMapme;
	private Partecipation partecipation;
	private String nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.send_partecipation_layout);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		controller= new DeviceController();

		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent i = getIntent();
		if(i.getStringExtra("viewnotification")==null)
			goToLoginPage();
		else{

			userInvited= new User();
			nickname=sp.getString("nickname", "");
			userInvited.setNickname(nickname);
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
				json=controller.getServer().requestJson(SettingsServer.SELECT_PARTECIPATION, controller.getServer().setParameters(parameters));

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
				if(partecipation.getModelID()!=0 && !partecipation.getUserInvite().getNickname().equalsIgnoreCase("null")){

					if(isTypePartecipationSend(partecipation)){
						//gestione Invio notifiche da aadmin

						invite.setText(INVITO.concat(partecipation.getUserInvite().getNickname()));
						inviteMapme.setText(MAPME.concat(partecipation.getMapme().getName()));
					}else{
						//gestione richieste di invito
						invite.setText(RICHIESTA.concat(partecipation.getUserInvite().getNickname()));
						inviteMapme.setText(MAPME.concat(partecipation.getMapme().getName()));
					}
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Error Partecipation retrieve", 5000);

			}else
				UtilAndroid.makeToast(getApplicationContext(), "Refresh", 5000);
		}
	}

	public Partecipation getPartecipation (JSONObject json){

		Partecipation partecipation = new Partecipation();
		MapMe mapme= new MapMe();
		User userInvite= new User();
		try {
			JSONArray jsonArr= json.getJSONArray("Partecipation");
			for(int i=0; i<jsonArr.length(); i++){
				json=jsonArr.getJSONObject(i);
				partecipation.setModelID(Integer.parseInt(""+json.getInt("idpartecipation")));
				mapme.setIdmapme(Integer.parseInt(""+json.getString("idmapme")));
				userInvite.setNickname(json.getString("nickname_send"));
				mapme.setName(json.getString("name_mapme"));
				partecipation.setType(json.getString("type"));
				partecipation.setMapme(mapme);
				partecipation.setUserInvite(userInvite);
				partecipation.setUserInvited(userInvited);
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
				parameters.put("idm", ""+partecipation.getMapme().getIdmapme());
				resp=controller.getServer().request(SettingsServer.INSERT_MAPPING, controller.getServer().setParameters(parameters));

				return resp;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result!=null && result.contains("1")){
				UtilAndroid.makeToast(getApplicationContext(), "You are added in "+partecipation.getMapme().getName(),5000);				

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
				resp=controller.getServer().request(SettingsServer.INSERT_MAPPING, controller.getServer().setParameters(parameters));

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
	public boolean isTypePartecipationSend (Partecipation p){
		boolean isPart=false;
		isPart=p.getType().equalsIgnoreCase("SEND")?true:false;
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
