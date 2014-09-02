/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		act = this;

		mapme = (MapMe) getIntent().getExtras().get("mapme");
		//mapping = mapme.getDistinctMapping();
		//allMapping = mapme.getMapping();

		gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		Button send = (Button) findViewById(R.id.buttonSendPartecipation);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(SEND_DIALOG);
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
		builder.setPositiveButton("Send invite", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				final String nickname = ed.getText().toString();
				if(nickname!=null && nickname.length()>0){
					if(!nickname.equalsIgnoreCase(mapme.getAdministrator().getNickname())){
						new Thread(new Runnable() {

							@Override
							public void run() {

								new DownloadUser().execute(nickname);

							}
						}).start();

					}else
						UtilAndroid.makeToast(getApplicationContext(), "Non puoi invitare te stesso", 5000);
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Please insert nickname", 5000);
				ed.setText("");

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
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
//				parameters.put("user",String.valueOf(PreferenceManager.getDefaultSharedPreferences(act).getInt(UtilAndroid.KEY_ID_USER_LOGGED, 0)));
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
				List<User> reg= getUsersByJSon(result);
				if(reg!=null){
					adapter = new AdapterUsersMapMe(act, reg, mapme);
					gridview.setAdapter(adapter);
				}
				else
					UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);
			}


		}
	}

//	public List<MappingUser> getAllMappingFromMapme (JSONObject json){
//
//		List<MappingUser> mapping= new ArrayList<MappingUser>();
//
//		try {
//			JSONArray jsonArr= json.getJSONArray("Mapping");
//			for(int i=0; i<jsonArr.length(); i++){
//				json=jsonArr.getJSONObject(i);
//				User admin = getUserByJSon(json.getJSONArray("user"));
//				Point point = getPointByJSon(json.getJSONArray("point"));
//				if(admin!=null && point!=null){
//					MappingUser m= new MappingUser();
//					m.setModelID(Integer.parseInt(json.getString("id")));
//					m.setUser(admin);
//					m.setPoint(point);
//					mapping.add(m);
//				}
//			}
//			return mapping;
//
//		}catch (Exception e) {
//			return null;
//		}
//	}
//	
//	private User getUserByJSon (JSONArray jsonArr){
//
//		try {
//			User user= new User();
//			JSONObject json = null;
//			for(int i=0; i<jsonArr.length(); i++){
//
//				json = jsonArr.getJSONObject(i);
//				user.setNickname(json.getString("nickname"));
//				user.setEmail(json.getString("email"));
//				user.setModelID(json.getInt("id"));
//			}
//			return user;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	
	private List<User> getUsersByJSon (JSONObject json){

		try {
			List<User> u= new ArrayList<User>();
			JSONArray jsonArray = json.getJSONArray("Users");
			for(int i=0; i<jsonArray.length(); i++){
				json = jsonArray.getJSONObject(i);
				User user = new User();
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
				u.add(user);
			}
			return u;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}

	}
	
//	private Point getPointByJSon (JSONArray jsonArr){
//
//		try {
//			Point ptn= new Point();
//			JSONObject json = null;
//			for(int i=0; i<jsonArr.length(); i++){
//
//				json = jsonArr.getJSONObject(i);
//				ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
//				ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
//				ptn.setLocation(json.getString("location"));
//			}
//			return ptn;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	
	class DownloadUser extends AsyncTask<String, Void, String>{

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
				parameters.put("nickinvite", mapme.getAdministrator().getNickname().toString());
				parameters.put("nickinvited", URLEncoder.encode(params[0].toString(), "UTF-8"));
				parameters.put("idm",  ""+Integer.parseInt(""+mapme.getModelID()));
			 
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
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);
		return true;

	}


	public void refresh (View v){
		new DownloadAllUser().execute();
	}

}
