/**
 * 
 */
package it.mapyou.controller.parsing;

import java.util.ArrayList;
import java.util.List;

import it.mapyou.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingUser implements ParserInterface<User> {


	@Override
	public User parseFromJsonObject(JSONObject json) throws Exception {
		User user= new User();
		JSONArray jsonArr= json.getJSONArray("User");
		for(int i=0; i<jsonArr.length(); i++){
			json=jsonArr.getJSONObject(i);
			user.setNickname(json.getString("nickname"));
			user.setEmail(json.getString("email"));
			user.setModelID(json.getInt("id"));
		}
		return user;
	}


	@Override
	public User parseFromJsonArray(JSONArray jsonArr) throws Exception {
		User user= new User();
		JSONObject json = null;
		for(int i=0; i<jsonArr.length(); i++){

			json = jsonArr.getJSONObject(i);
			user.setNickname(json.getString("nickname"));
			user.setEmail(json.getString("email"));
			user.setModelID(json.getInt("id"));
		}
		return user;
	}


	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public List<User> parseListFromJsonObject(JSONObject o) throws Exception {
		List<User> u= new ArrayList<User>();
		JSONArray jsonArray = o.getJSONArray("Users");
		for(int i=0; i<jsonArray.length(); i++){
			o = jsonArray.getJSONObject(i);
			User user = new User();
			user.setNickname(o.getString("nickname"));
			user.setEmail(o.getString("email"));
			user.setModelID(o.getInt("id"));
			u.add(user);
		}
		return u;
	}


	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public List<User> parseListFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
