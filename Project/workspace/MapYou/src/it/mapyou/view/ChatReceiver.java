/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.model.ChatMessage;
import it.mapyou.model.User;
import it.mapyou.util.UtilAndroid;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

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

	private boolean isBroadcast = false;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  


	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("it.mapyou.action.CHAT_MESSAGE")){
			String msg = "", title="", notif="", brod = "",ad="",date="";
			int id, mapme_id;
			try {
				Bundle b = intent.getExtras();
				msg = b.getString("message");
				id = b.getInt("idsender");
				mapme_id = b.getInt("idmapme");
				title = b.getString("title");
				notif = b.getString("notif");
				brod = b.getString("broadcast");
				ad = b.getString("admin");
				date=b.getString("date");
				 
			} catch (Exception e) {
				msg = ""; title=""; notif="";
				id = -1;
				mapme_id=-1;
				brod = "false";
			}
			isBroadcast = brod.equals("true");
			int icon = R.drawable.profile;
			long when = System.currentTimeMillis();
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			android.app.Notification notification = new android.app.Notification(icon, notif, when);

			notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= android.app.Notification.DEFAULT_SOUND;
			notification.defaults |= android.app.Notification.DEFAULT_VIBRATE;
			if(mapme_id==UtilAndroid.CURRENT_MAPME.getModelID()){
				ChatMessage n = new ChatMessage();
				User u = new User();
				u.setNickname(ad);
				u.setModelID(id);
				n.setNotifier(u);
				n.setNotified(null);
				n.setMessage(msg);
				GregorianCalendar g= new GregorianCalendar();
				try {
					g.setTime(sdf.parse(date));
				} catch (Exception e) {
					UtilAndroid.makeToast(context, "Peppe ha rag", 5000);
				}
				n.setDate(g);
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

	class UpdateNotification extends AsyncTask<ChatMessage, Void, ChatMessage>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private Context c;
		 
		public UpdateNotification(Context c) {
			this.c=c;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(!UtilAndroid.findConnection(c))
			{
				UtilAndroid.makeToast(c, "Internet Connection not found", 5000);
			}else;
		}
		
		@Override
		protected ChatMessage doInBackground(ChatMessage... params) {

			try {
				parameters.put("idNot", String.valueOf(params[0].getModelID()));
				parameters.put("state", "READ");

				DeviceController.getInstance().getServer().request(SettingsServer.UPDATE_NOT, DeviceController.getInstance().getServer().setParameters(parameters));

				return params[0];

			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(ChatMessage result) {
		
			if(result != null){
				if(isBroadcast)
					BroadcastChat.updateGui(result);
				else
					ChatUserToUser.updateGui(result);
			}
		}
	}

}
