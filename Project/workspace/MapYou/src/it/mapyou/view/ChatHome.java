/**
 * 
 */
package it.mapyou.view;


import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.AbstractAsyncTask;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.model.User;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.adapter.AdapterChatHome;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatHome extends MapyouActivity {

	private Activity act;
	private GridView gridView;
	private List<User> users;
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
		nameM.setText("Users in: "+UtilAndroid.CURRENT_MAPME.getName());
		new DownloadAllUser(act,"Loading users...").execute();
	}

	public void broadcast(View v){

		Intent i = new Intent(getApplicationContext(), BroadcastChat.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Bundle b = new Bundle();
		b.putInt("num_users", users.size());
		i.putExtras(b);
		startActivity(i);

	}

	class DownloadAllUser extends AbstractAsyncTask<Void, Void, JSONObject>{

		
		public DownloadAllUser(Activity act,String s) {
			super(act,s);
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
				try {
					users= DeviceController.getInstance().getParsingController().getUserParser().parseListFromJsonObject(result);
					if(users!=null){
						getListWithoutAdmin(users);
						gridView.setAdapter(new AdapterChatHome(act, users));
					}
					else
						UtilAndroid.makeToast(act, "Error while fetching your mapme.", 5000);
				} catch (Exception e) {
					UtilAndroid.makeToast(act, "Error while parsing your mapme.", 5000);
				}
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
			new DownloadAllUser(act,"Loading users...").execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
 
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		
		alert("Alert!", "Are sure you want to come back? ");
		
	}
	
	public void alert( String title, String message ){

		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle( title )
		.setMessage( message )
		.setCancelable(false)
		.setPositiveButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		})
		.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i = new Intent(act, MapMeTab.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				act.startActivity(i);
			}
		}).show();
	}
}
