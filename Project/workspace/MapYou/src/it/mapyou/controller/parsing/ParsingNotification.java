/**
 * 
 */
package it.mapyou.controller.parsing;

import it.mapyou.controller.ParsingController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Notification;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingNotification implements ParserInterface<Notification>{
	
	public ParsingNotification() {
	 
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public Notification parseFromJsonObject(JSONObject o) throws Exception {
		Notification m= new Notification();
		User notifier = ParsingController.getParser().getUserParser().parseFromJsonArray((o.getJSONArray("notifier")));
		User notified =  ParsingController.getParser().getUserParser().parseFromJsonArray(o.getJSONArray("notified"));
		MapMe mapme = new MapMe();
		mapme.setName(o.getJSONArray("mapme").getJSONObject(0).getString("name"));
		mapme.setModelID(Integer.parseInt(
				o.getJSONArray("mapme").getJSONObject(0).getString("id")));
		m.setNotified(notified);
		m.setNotifier(notifier);
		m.setNotificationType(o.getString("type"));
		m.setModelID(Integer.parseInt(o.getString("id")));
		m.setNotificationObject(mapme);
		return m;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public Notification parseFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public List<Notification> parseListFromJsonObject(JSONObject o)
			throws Exception {
		List<Notification> notifications = new ArrayList<Notification>();
		JSONArray jsonArr = o.getJSONArray("");
		for(int i=0; i<jsonArr.length(); i++){
			Notification mp= parseFromJsonObject(jsonArr.getJSONObject(i));
			if(mp!=null)
				notifications.add(mp);
		}
		return notifications;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public List<Notification> parseListFromJsonArray(JSONArray o)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
