/**
 * 
 */
package it.mapyou.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class JSON_to_User extends JSON_to_Model<User> {

	/* (non-Javadoc)
	 * @see it.mapyou.network.JSON_to_Model#getFromJSON(org.json.JSONArray)
	 */
	@Override
	public List<User> getFromJSON(JSONArray array) {
		// TODO Auto-generated method stub
		List<User> li = new ArrayList<User>();
		for(int i=0; i<array.length(); i++){
			JSONObject o;
			try {
				o = array.getJSONObject(i);
				User userr = new User();
				userr.setNickname(o.getString("nickname"));
				li.add(userr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} 
		}
		return li;
	}

}
