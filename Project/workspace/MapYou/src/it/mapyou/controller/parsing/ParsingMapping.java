/**
 * 
 */
package it.mapyou.controller.parsing;

import it.mapyou.controller.ParsingController;
import it.mapyou.model.MappingUser;
import it.mapyou.model.Point;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingMapping implements ParserInterface<MappingUser> {
	
	public MappingUser parsingMappingFromMapme (JSONObject json){


		try {
			MappingUser m= new MappingUser();
			User admin = ParsingController.getParser().getUserParser().parseFromJsonArray(json.getJSONArray("user"));
			Point point = ParsingController.getParser().getPointParser().parseFromJsonArray(json.getJSONArray("point"));
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

	 
	@Override
	public MappingUser parseFromJsonObject(JSONObject o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public MappingUser parseFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public List<MappingUser> parseListFromJsonObject(JSONObject o)
			throws Exception {
		List< MappingUser> mappings = new ArrayList<MappingUser>();
		JSONArray jsonArr = o.getJSONArray("AllMappings");
		for(int i=0; i<jsonArr.length(); i++){
			MappingUser mp= parsingMappingFromMapme(jsonArr.getJSONObject(i));
			if(mp!=null)
				mappings.add(mp);
		}
		return mappings;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public List<MappingUser> parseListFromJsonArray(JSONArray o)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
