/**
 * 
 */
package it.mapyou.network;

import java.util.HashMap;

import org.json.JSONArray;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ServerInterface {


	public boolean close();
	
	public boolean open(String conn, String parameter);
	
	public JSONArray request(String parameters,String url, String jsonObject);
	
	public String request(String page,String parameters);
	
	public String setParameters(HashMap<String, String> parameters);
 
}
