/**
 * 
 */
package it.mapyou.controller.parsing;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ParserInterface<X> {
	
	public X parseFromJsonObject(JSONObject o) throws Exception;
	public X parseFromJsonArray(JSONArray o) throws Exception;

}
