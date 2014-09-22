/**
 * 
 */
package it.mapyou.controller.parsing;

import it.mapyou.controller.ParsingController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Point;
import it.mapyou.model.Route;
import it.mapyou.model.User;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingMapMe implements ParserInterface<MapMe> {
	
	 
	public ParsingMapMe() {
		 
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public MapMe parseFromJsonObject(JSONObject o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public MapMe parseFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public List<MapMe> parseListFromJsonObject(JSONObject o) throws Exception {
		List<MapMe> allmapme= new ArrayList<MapMe>();
		JSONArray jsonArr= o.getJSONArray("Mapme");
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jjson=jsonArr.getJSONObject(i);
			MapMe mapme= new MapMe();
			Route route = new Route();
			Point startPoint= ParsingController.getParser().getPointParser().parseFromJsonArray((jjson.getJSONArray("segment").getJSONObject(0).getJSONArray("startPoint")));
			Point endPoint = ParsingController.getParser().getPointParser().parseFromJsonArray(jjson.getJSONArray("segment").getJSONObject(0).getJSONArray("endPoint"));
			User admin = ParsingController.getParser().getUserParser().parseFromJsonArray(((jjson.getJSONArray("administrator"))));
			if(admin!=null && startPoint!=null && endPoint!=null){
				route.setStartPoint(startPoint);
				route.setEndPoint(endPoint);
				mapme.setSegment(route);
				mapme.setModelID(Integer.parseInt(jjson.getString("id")));
				mapme.setAdministrator(admin);
				mapme.setName(jjson.getString("name"));
				mapme.setMaxNumUsers((Integer.parseInt(jjson.getString("maxNumUsers"))));
				mapme.setNumUsers((Integer.parseInt(jjson.getString("numUsers"))));
				allmapme.add(mapme);
			}
		}
		return allmapme;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public List<MapMe> parseListFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
