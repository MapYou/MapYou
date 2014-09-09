/**
 * 
 */
package it.mapyou.view;

import it.mapyou.model.Notification;
import it.mapyou.model.User;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
				id = b.getInt("user_id");
				mapme_id = b.getInt("mapme_id");
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
				ChatUserToUser.updateGui(n);
			}
		}
	}

}
