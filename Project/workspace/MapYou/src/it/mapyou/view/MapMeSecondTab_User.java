package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.adapter.AdapterUsersMapMe;
import it.mapyou.view.adapter.AdapterUsersMapMeOnDeleteUser;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeSecondTab_User extends Activity {

	private MapMe mapme;
	private Dialog sendDialog;
	private static final int SEND_DIALOG = 1;
	private Activity act;
	private View sendView;
	private EditText ed;
	private AdapterUsersMapMe adapter;
	private GridView gridview;
	private List<User> reg;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		act = this;
		mapme = Util.CURRENT_MAPME;
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		reg = new ArrayList<User>();
		gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		Button send = (Button) findViewById(R.id.buttonSendPartecipation);
		if(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)==mapme.getAdministrator().getModelID())
			{
			((Button)findViewById(R.id.ButtonDelete)).setVisibility(Button.VISIBLE);
			send.setVisibility(Button.VISIBLE);
			}
		else {
			((Button)findViewById(R.id.ButtonDelete)).setVisibility(Button.INVISIBLE);
			send.setVisibility(Button.INVISIBLE);
		}
		send.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				if(mapme.getAdministrator().getNickname().equals(
						PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
						.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "")
						)){
					showDialog(SEND_DIALOG);
				}else{
					UtilAndroid.makeToast(getApplicationContext(), "You are not administrator for send partecipation.", 5000);
				}

			}
		});

		new DownloadAllUser().execute();
		sendDialog();


	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DrawerMain.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SEND_DIALOG:
			return sendDialog;

		default:
			return null;
		}
	}

	private void sendDialog(){
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Insert nickname");
		builder.setCancelable(true);
		LayoutInflater inflater = act.getLayoutInflater();
		sendView = inflater.inflate(R.layout.send_partecipation_dialog, null);
		ed = (EditText)sendView.findViewById(R.id.editTextNickname);
		builder.setView(sendView);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("Send invite", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				final String nickname = ed.getText().toString();
				if(nickname!=null && nickname.length()>0){
					if(!nickname.equals(mapme.getAdministrator().getNickname())
							){
						new Thread(new Runnable() {

							@Override
							public void run() {

								new DownloadUserAndSend().execute(nickname);

							}
						}).start();

					}else
						UtilAndroid.makeToast(getApplicationContext(), "You are admin!", 5000);
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Please insert nickname", 5000);
				ed.setText("");

			}
		});
		
		sendDialog = builder.create();
	}


	class DownloadAllUser extends AsyncTask<Void, Void, JSONObject>{


		private HashMap<String, String> parameters=new HashMap<String, String>();
		private JSONObject response;
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
				parameters.put("idm",String.valueOf(mapme.getModelID()));
				response=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_ALL_USER, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			p.dismiss();
			if(result==null){
				UtilAndroid.makeToast(getApplicationContext(), "Please refresh....", 5000);
			}else{
				if(reg!=null)reg.clear();
				else;
				reg= DeviceController.getInstance().getParsingController().getUserParser().getParsingUsers(result);
				if(reg!=null){
					adapter = new AdapterUsersMapMe(act, reg, mapme);
					gridview.setAdapter(adapter);
				}
				else
					UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);
			}
		}
	}

	class DownloadUserAndSend extends AsyncTask<String, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String response;
	 

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);
			else;
		}


		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("nickinvite", mapme.getAdministrator().getNickname().toString());
				parameters.put("nickinvited", URLEncoder.encode(params[0].toString(), "UTF-8"));
				parameters.put("idm",  ""+Integer.parseInt(""+mapme.getModelID()));
				parameters.put("type",  "SEND");
				parameters.put("message",  "You have received an invitation from "+mapme.getAdministrator().getNickname());
				parameters.put("title",  "MapYou: invite for mapme");
				parameters.put("notif",  "You have received an invitation from "+mapme.getAdministrator().getNickname());

				response=DeviceController.getInstance().getServer().
						request(SettingsServer.SEND_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));

				return response;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			 
			if(result.contains("send")){
				UtilAndroid.makeToast(getApplicationContext(), "invite for "+mapme.getName()+" has been send!", 5000);
			}
			else if(result.contains("refused")){
				UtilAndroid.makeToast(getApplicationContext(), "This user has been added in "+mapme.getName(), 5000);
			}
			else if(result.contains("User not found")){
				UtilAndroid.makeToast(getApplicationContext(), "This user is not registered on MapYou.", 5000);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
		}
	}

	public void refresh(View v){
		new DownloadAllUser().execute();
	}
	
	public void deleteUser(View v){
		if(sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1)==mapme.getAdministrator().getModelID())
			changeLayout(true);
		else
			UtilAndroid.makeToast(getApplicationContext(), "You aren't administrator for thi mapme. You can't delete any user.", 5000);
	}
	
	private void changeLayout(boolean onDelete){
		if (onDelete) {
			if(reg!=null){
				for(int i=0; i<reg.size(); i++){
					if(reg.get(i).getModelID()==mapme.getAdministrator().getModelID())
						{
						reg.remove(i);
						break;
						}
				}
			}else;
			setContentView(R.layout.delete_user_in_mapme);
			
			gridview = (GridView) findViewById(R.id.gridViewMapMeDeleteUsers);
			AdapterUsersMapMeOnDeleteUser ada = 
			new AdapterUsersMapMeOnDeleteUser(act, reg, mapme);
			gridview.setAdapter(ada);
		}else{
			setContentView(R.layout.mapme_second_tab);
			gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
			new DownloadAllUser().execute();
		}
	}

	public void cancelDelete(View v){
		changeLayout(false);
	}

	public void confirmDelete(View v){
		if(gridview.getId()==R.id.gridViewMapMeDeleteUsers)
			{
			ListAdapter l = gridview.getAdapter();
			if(l instanceof AdapterUsersMapMeOnDeleteUser){
				((AdapterUsersMapMeOnDeleteUser)l).deleteUser();
				changeLayout(false);	
			}
			}
	}
}
