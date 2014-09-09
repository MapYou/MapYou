/**
 * 
 */
package it.mapyou.view;

import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class UpdateNotification extends AsyncTask<Notification, Void, String>{
	
	private Context context;
	
	public UpdateNotification(Context c) {
		this.context = c;
	}

	private HashMap<String, String> parameters=new HashMap<String, String>();


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(!UtilAndroid.findConnection(context.getApplicationContext()))
			UtilAndroid.makeToast(context.getApplicationContext(), "Internet Connection not found", 5000);

	}

	// CHAT CHAT_BROADCAST
	@Override
	protected String doInBackground(Notification... params) {

		try {
			parameters.put("idNot", String.valueOf(params[0].getModelID()));
			parameters.put("state", "READ");

			String r = DeviceController.getInstance().getServer().
			request(SettingsServer.UPDATE_NOT, DeviceController.getInstance().getServer().setParameters(parameters));

			return r;

		} catch (Exception e) {
			return null;
		}
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.i("log", result);

	}
}