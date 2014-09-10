/**
 * 
 */
package it.mapyou.view;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.GridView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatHome extends Activity {
	
	private Activity act;
	private GridView gridView;
	private List<User> users;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlayouthome);
		this.act = this;
		gridView = (GridView) findViewById(R.id.gridView1);
		users = new ArrayList<User>();
		sp=PreferenceManager.getDefaultSharedPreferences(this);
		new DownloadAllUser().execute();
	}
	
	public void broadcast(View v){
		String[] usr = new String[users.size()];
		for(int i=0; i<users.size(); i++)
			usr[i] = users.get(i).getNickname();
		Intent i = new Intent(getApplicationContext(), BroadcastChat.class);
		Bundle b = new Bundle();
		b.putStringArray("users", usr);
		i.putExtras(b);
		startActivity(i);
		
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
				parameters.put("idm",String.valueOf(Util.CURRENT_MAPME.getModelID()));
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
				users= getUsersByJSon(result);
				if(users!=null){
					gridView.setAdapter(new AdapterChatHome(act, users));
				}
				else
					UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);
			}


		}
	}

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
				if(user.getModelID()!=sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1))
					u.add(user);
			}
			return u;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}

	}

}
