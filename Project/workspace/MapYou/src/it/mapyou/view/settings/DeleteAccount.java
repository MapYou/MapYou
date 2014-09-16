/**
 * 
 */
package it.mapyou.view.settings;

import it.mapyou.controller.DeviceController;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.Login;
import it.mapyou.view.Setting;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeleteAccount extends Setting{

	private SharedPreferences sp;
	/**
	 * @param act
	 * @param idView
	 */
	public DeleteAccount(Activity act, int idView) {
		super(act, idView);
		sp=PreferenceManager.getDefaultSharedPreferences(act);
	}

	@Override
	public void performeSetting() {
		String userLogged=sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "");

		if(userLogged!=""){
			new DeleteAccountTask().execute(userLogged.toString());
		}else
			UtilAndroid.makeToast(act, "Please log-in", 5000);
	}

	class DeleteAccountTask extends AsyncTask<String, Void, String>{

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
				UtilAndroid.makeToast(act, "Account deleted", 5000);
				Intent i = new Intent(act, Login.class);
				sp.edit().clear().commit();
				act.startActivity(i);
			}else{
				UtilAndroid.makeToast(act, "Account not deleted...Please log in", 5000);
				Intent i = new Intent(act, Login.class);
				act.startActivity(i);
			}
		}

	}

}
