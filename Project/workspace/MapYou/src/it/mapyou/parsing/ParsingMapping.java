/**
 * 
 */
package it.mapyou.parsing;

import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingMapping {

	
	public List< MappingUser> parsingAllMappings(JSONObject result){
		try {
			List< MappingUser> mappings = new ArrayList<MappingUser>();
			JSONArray jsonArr = result.getJSONArray("AllMappings");
			for(int i=0; i<jsonArr.length(); i++){
				MappingUser mp= parsingMappingFromMapme(jsonArr.getJSONObject(i));
				if(mp!=null)
					mappings.add(mp);
			}
			return mappings;
		} catch (JSONException e) {
			e.printStackTrace();
			return new ArrayList<MappingUser>();
		}
	}

	public MappingUser parsingMappingFromMapme (JSONObject json){


		try {
			MappingUser m= new MappingUser();
			User admin = ParsingController.getParser().getUserParser().getParsingUserJarr(json.getJSONArray("user"));
			Point point = ParsingController.getParser().getPointParser().parsingSinglePoint(json.getJSONArray("point"));
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
}
