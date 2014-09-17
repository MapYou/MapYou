/**
 * 
 */
package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.AbstractAsyncTask;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.adapter.AdapterChatHome;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatHome extends Activity {

	private Activity act;
	private GridView gridView;
	private List<User> users;
	private SharedPreferences sp;
	private TextView nameM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatlayouthome);
		setTitle("MapYou Chat");
		this.act = this;
		gridView = (GridView) findViewById(R.id.gridView1);
		nameM= (TextView) findViewById(R.id.textView1);
		users = new ArrayList<User>();
		sp=PreferenceManager.getDefaultSharedPreferences(this);
		nameM.setText("Users in: "+UtilAndroid.CURRENT_MAPME.getName());
		new DownloadAllUser(act).execute();
	}

	public void broadcast(View v){

		Intent i = new Intent(getApplicationContext(), BroadcastChat.class);
		Bundle b = new Bundle();
		b.putInt("num_users", users.size());
		i.putExtras(b);
		startActivity(i);

	}

	class DownloadAllUser extends AbstractAsyncTask<Void, Void, JSONObject>{

		/**
		 * @param act
		 */
		public DownloadAllUser(Activity act) {
			super(act);
			// TODO Auto-generated constructor stub
		}

		private JSONObject response;

		@Override
		protected JSONObject doInBackground(Void... params) {

			try {
				parameters.put("idm",String.valueOf(UtilAndroid.CURRENT_MAPME.getModelID()));
				response=DeviceController.getInstance().getServer().
						requestJson(SettingsServer.GET_ALL_USER, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void newOnPostExecute(JSONObject result) {

			p.dismiss();
			if(result==null){
				UtilAndroid.makeToast(getApplicationContext(), "Please refresh....", 5000);
			}else{
				users= DeviceController.getInstance().getParsingController().getUserParser().getParsingUsers(result);
				if(users!=null){
					getListWithoutAdmin(users);
					gridView.setAdapter(new AdapterChatHome(act, users));
				}
				else
					UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);
			}
		}
	}

	public void getListWithoutAdmin (List<User> users){
		for(int i=0; i<users.size(); i++)
			if(users.get(i).getModelID()==sp.getInt(UtilAndroid.KEY_ID_USER_LOGGED, -1))
			{
				users.remove(i);
				break;
			}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refreshNotification:
			new DownloadAllUser(act).execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
