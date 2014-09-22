/**
 * 
 */
package it.mapyou.controller.parsing;

import java.util.List;

import it.mapyou.model.Point;

import org.json.JSONArray;
import org.json.JSONObject;

 
public class ParsingPoint implements ParserInterface<Point> {
	
	public ParsingPoint(){
		
	}
	 
	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public Point parseFromJsonObject(JSONObject o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public Point parseFromJsonArray(JSONArray jsonArr) throws Exception {
		Point ptn= new Point();
		JSONObject json = null;
		for(int i=0; i<jsonArr.length(); i++){

			json = jsonArr.getJSONObject(i);
			ptn.setLatitude(Double.parseDouble(json.getString("latitude")));
			ptn.setLongitude(Double.parseDouble(json.getString("longitude")));
			ptn.setLocation(json.getString("location"));
		}
		return ptn;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonObject(org.json.JSONObject)
	 */
	@Override
	public List<Point> parseListFromJsonObject(JSONObject o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.parsing.ParserInterface#parseListFromJsonArray(org.json.JSONArray)
	 */
	@Override
	public List<Point> parseListFromJsonArray(JSONArray o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
