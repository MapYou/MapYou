/**
 * 
 */
package it.mapyou.view;

import java.util.HashMap;

import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("it.mapyou.action.CHAT_MESSAGE")){
			String msg = "";
			int id, mapme_id;
			try {
				Bundle b = intent.getExtras();
				msg = b.getString("message");
				id = b.getInt("idsender");
				mapme_id = b.getInt("idmapme");
			} catch (Exception e) {
				msg = "";
				id = -1;
				mapme_id=-1;
			}
			if(mapme_id==Util.CURRENT_MAPME.getModelID()){
				Notification n = new Notification();
				User u = new User();
				u.setModelID(id);
				n.setNotifier(u);
				n.setNotified(null);
				n.setNotificationState(msg);
				new UpdateNotification(context).execute(n);
			}
		}
	}
	
	class UpdateNotification extends AsyncTask<Notification, Void, Notification>{

		private HashMap<String, String> parameters=new HashMap<String, String>();

		private Context act;
		
		public UpdateNotification(Context act) {
			this.act = act;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(act.getApplicationContext()))
				UtilAndroid.makeToast(act.getApplicationContext(), "Internet Connection not found", 5000);

		}

		// CHAT CHAT_BROADCAST
		@Override
		protected Notification doInBackground(Notification... params) {

			try {
				parameters.put("idNot", String.valueOf(params[0].getModelID()));
				parameters.put("state", "READ");

				DeviceController.getInstance().getServer().
				request(SettingsServer.UPDATE_NOT, DeviceController.getInstance().getServer().setParameters(parameters));

				return params[0];

			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(Notification result) {
			super.onPostExecute(result);
			if(result != null){
				ChatUserToUser.updateGui(result);
			}
		}
	}

}
