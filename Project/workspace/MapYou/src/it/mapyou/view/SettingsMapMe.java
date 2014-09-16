/**
 * 
 */
package it.mapyou.view;


import java.util.HashMap;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SettingsMapMe extends Activity {

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}

	public void delete(View v){
		
		String userLogged=sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "");
		
		if(userLogged!=""){
			new DeleteAccount().execute(userLogged.toString());
		}else
			UtilAndroid.makeToast(getApplicationContext(), "Please log-in", 5000);
		
	}
	
	class DeleteAccount extends AsyncTask<String, Void, String>{
		
		private HashMap<String, String> parameters=new HashMap<String, String>();
		
		@Override
		protected String doInBackground(String... params) {
			
			String s="";
			parameters.put("admin", ""+params[0].toString());
			s=DeviceController.getInstance().getServer().request(SettingsServer.DELETE_ACCOUNT, DeviceController.getInstance().getServer().setParameters(parameters));
			
			return s;
		}
		
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(result.contains("success_without_mapme") || result.contains("total_success")){
				UtilAndroid.makeToast(getApplicationContext(), "Account deleted", 5000);
				Intent i = new Intent(getApplicationContext(), Login.class);
				sp.edit().clear().commit();
				startActivity(i);
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Account not deleted...Please log in", 5000);
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
			}
		}
		
	}

}
