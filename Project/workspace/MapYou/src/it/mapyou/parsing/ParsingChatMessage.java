/**
 * 
 */
package it.mapyou.parsing;

import it.mapyou.model.ChatMessage;
import it.mapyou.model.MapMe;
import it.mapyou.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingChatMessage {


	public ParsingChatMessage() {

	}

	public List<ChatMessage> parsingAllChatMessageNotifcation(JSONObject result){
		try {
			List<ChatMessage> notifications = new ArrayList<ChatMessage>();
			JSONArray jsonArr = result.getJSONArray("");
			for(int i=0; i<jsonArr.length(); i++){
				ChatMessage mp= parsingNotificationFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					notifications.add(mp);
			}
			return notifications;
		} catch (JSONException e) {

			e.printStackTrace();
			return new ArrayList<ChatMessage>();
		}
	}

	public ChatMessage parsingNotificationFromMapme (JSONObject json){

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			ChatMessage m= new ChatMessage();
			User notifier = ParsingController.getParser().getUserParser().getParsingUserJarr(json.getJSONArray("notifier"));
			User notified = ParsingController.getParser().getUserParser().getParsingUserJarr(json.getJSONArray("notified"));
			MapMe mapme = new MapMe();
			mapme.setName(json.getJSONArray("mapme").getJSONObject(0).getString("name"));
			mapme.setModelID(Integer.parseInt(json.getJSONArray("mapme").getJSONObject(0).getString("id")));
			m.setNotified(notified);
			m.setNotifier(notifier);
			m.setMessage(json.getString("state"));
			m.setModelID(Integer.parseInt(json.getString("id")));
			m.setNotificationObject(mapme);
			Date dt;
			try {
				dt = sdf.parse(json.getString("date"));
			} catch (Exception e) {
				dt = null;
			}
			if(dt!=null){
				GregorianCalendar g = new GregorianCalendar();
				g.setTime(dt);
				m.setDate(g);
			}else;
			return m;
		}catch (Exception e) {
			return null;
		}
	}
	
	public ChatMessage parsingNotification (JSONObject json){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		try {
			ChatMessage m= new ChatMessage();
			Date dt;
			try {
				String s=json.getString("date");
				dt = sdf.parse(s);
			} catch (Exception e) {
				dt = null;
			}
			if(dt!=null){
				GregorianCalendar g = new GregorianCalendar();
				g.setTime(dt);
				m.setDate(g);
			}else;

			User notifier = ParsingController.getParser().getUserParser().getParsingUserJarr(json.getJSONArray("notifier"));
			User notified = ParsingController.getParser().getUserParser().getParsingUserJarr(json.getJSONArray("notified"));
			m.setNotified(notified);
			m.setNotifier(notifier);
			m.setMessage(json.getString("state"));
			m.setModelID(Integer.parseInt(json.getString("id")));

			return m;
		}catch (Exception e) {
			return null;
		}
	}


}
