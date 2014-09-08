/**
 * 
 */
package it.mapyou.util;

import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.User;
import it.mapyou.view.Util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MappingReader {
	
	private Activity act;
	
	public MappingReader(Activity act){
		this.act = act;
	}
	
	public List< MappingUser> retrieveAllMappings(JSONObject result){
		try {
			List< MappingUser> mappings = new ArrayList<MappingUser>();
			JSONArray jsonArr = result.getJSONArray("AllMappings");
			for(int i=0; i<jsonArr.length(); i++){
				MappingUser mp= getMappingFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					mappings.add(mp);
			}
			return mappings;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<MappingUser>();
		}
	}

	private MappingUser getMappingFromMapme (JSONObject json){


		try {
			MappingUser m= new MappingUser();
			User admin = getUserByJSon(json.getJSONArray("user"));
			Point point = getPointByJSon(json.getJSONArray("point"));
			if(admin!=null && point!=null){
				m.setModelID(Integer.parseInt(json.getString("id")));
				m.setUser(admin);
				m.setPoint(point);
			}else;
			return m;
		}catch (Exception e) {
			return null;
		}
	}

	private User getUserByJSon (JSONArray jsonArr){

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

	private Point getPointByJSon (JSONArray jsonArr){

		try {
			Point ptn= new Point();
			JSONObject json = null;
			for(int i=0; i<jsonArr.length(); i++){

				json = jsonArr.getJSONObject(i);
				ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
				ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
				ptn.setLocation(json.getString("location"));
			}
			return ptn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	public String read() throws Exception{

		BufferedReader reader=null;
		try {
			FileInputStream f= act.openFileInput(Util.NAME);
			reader= new BufferedReader(new InputStreamReader(f));
			StringBuffer bf=new StringBuffer();
			String line=null;

			while((line=reader.readLine()) !=null){
				bf.append(line);
			}

			return bf.toString();

		} catch (Exception e) {
			return null;
		}
		finally{

			reader.close();

		}
	}

}
