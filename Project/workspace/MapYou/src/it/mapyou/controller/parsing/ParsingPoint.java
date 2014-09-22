/**
 * 
 */
package it.mapyou.controller.parsing;

import it.mapyou.model.Point;

import org.json.JSONArray;
import org.json.JSONObject;

 
public class ParsingPoint {
	
	ParsingPoint(){
		
	}
	// getPointByJson
	public Point parsingSinglePoint (JSONArray jsonArr){

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

}
