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
public class ParsingUser {

	
	// return User
	public User getParsingUserJobj (JSONObject json){

		try {
			User user= new User();
			JSONArray jsonArr= json.getJSONArray("User");
			for(int i=0; i<jsonArr.length(); i++){
				json=jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public User getParsingUserJarr (JSONArray jsonArr){

		try {
			User user= new User();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getParsingUsers (JSONObject json){

		try {
			List<User> u= new ArrayList<User>();
			JSONArray jsonArray = json.getJSONArray("Users");
			for(int i=0; i<jsonArray.length(); i++){
				json = jsonArray.getJSONObject(i);
				User user = new User();
				user.setNickname(json.getString("nickname"));
				user.setEmail(json.getString("email"));
				user.setModelID(json.getInt("id"));
				u.add(user);
			}
			return u;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}

	}
}
