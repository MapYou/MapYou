/**
 * 
 */
package it.mapyou.view;

import java.util.HashMap;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.Notification;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
			String msg = "", title="", notif="", nickname="";
			int id, mapme_id;
			try {
				Bundle b = intent.getExtras();
				msg = b.getString("message");
				id = b.getInt("idsender");
				mapme_id = b.getInt("idmapme");
				title = b.getString("title");
				notif = b.getString("notif");
				nickname = b.getString("nickname_sender");
			} catch (Exception e) {
				msg = ""; nickname=""; title=""; notif="";
				id = -1;
				mapme_id=-1;
			}
			int icon = R.drawable.profile;
			long when = System.currentTimeMillis();
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			android.app.Notification notification = new android.app.Notification(icon, notif, when);
	
			notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= android.app.Notification.DEFAULT_SOUND;
			notification.defaults |= android.app.Notification.DEFAULT_VIBRATE;
			if(mapme_id==Util.CURRENT_MAPME.getModelID()){
				Notification n = new Notification();
				User u = new User();
				u.setModelID(id);
				n.setNotifier(u);
				n.setNotified(null);
				n.setNotificationState(msg);
				Intent notificationIntent = new Intent("it.mapyou.action.CHAT_MESSAGE_NO_UPDATE");
				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

				PendingIntent p_intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);

				notification.setLatestEventInfo(context, title, msg, p_intent);
				
				notificationManager.notify(0, notification);
				new UpdateNotification(context).execute(n);
			}else{
				Intent notificationIntent = new Intent(context, DrawerMain.class);
				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

				PendingIntent p_intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);

				notification.setLatestEventInfo(context, title, msg, p_intent);
				
				notificationManager.notify(0, notification);
			}
		}else if(intent.getAction().equals("it.mapyou.action.CHAT_MESSAGE_NO_UPDATE")){
			
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
				ChatUserToUser.updateGui(result, true);
			}
		}
	}

}
