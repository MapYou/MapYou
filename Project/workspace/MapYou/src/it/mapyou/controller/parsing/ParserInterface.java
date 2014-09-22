/**
 * 
 */
package it.mapyou.controller.parsing;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ParserInterface<X> {
	
	public X parseFromJsonObject(JSONObject o) throws Exception;
	public X parseFromJsonArray(JSONArray o) throws Exception;

	public List<X> parseListFromJsonObject(JSONObject o) throws Exception;
	public List<X> parseListFromJsonArray(JSONArray o) throws Exception;

}
