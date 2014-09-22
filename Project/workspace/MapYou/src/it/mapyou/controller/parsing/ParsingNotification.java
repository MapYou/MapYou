/**
 * 
 */
package it.mapyou.controller.parsing;

import java.util.ArrayList;
import java.util.List;

import it.mapyou.controller.ParsingController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Notification;
import it.mapyou.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingNotification implements ParserInterface<Notification>{
	
	public ParsingNotification() {
	 
	}
	
	public Notification parsingNotificationInMapme (JSONObject json){

		try {
			Notification m= new Notification();
			User notifier = ParsingController.getParser().getUserParser().parseUserFromJsonArray((json.getJSONArray("notifier")));
			User notified =  ParsingController.getParser().getUserParser().parseUserFromJsonArray(json.getJSONArray("notified"));
			MapMe mapme = new MapMe();
			mapme.setName(json.getJSONArray("mapme").getJSONObject(0).getString("name"));
			mapme.setModelID(Integer.parseInt(
					json.getJSONArray("mapme").getJSONObject(0).getString("id")));
			m.setNotified(notified);
			m.setNotifier(notifier);
			m.setNotificationType(json.getString("type"));
			m.setModelID(Integer.parseInt(json.getString("id")));
			m.setNotificationObject(mapme);
			return m;
		}catch (Exception e) {
			return null;
		}
	}
	
	public List<Notification> parsingAllNotification(JSONObject result){
		try {
			List<Notification> notifications = new ArrayList<Notification>();
			JSONArray jsonArr = result.getJSONArray("");
			for(int i=0; i<jsonArr.length(); i++){
				Notification mp= parsingNotificationInMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					notifications.add(mp);
			}
			return notifications;
		} catch (JSONException e) {
			e.printStackTrace();
			return new ArrayList<Notification>();
		}
	}

}
