/**
 * 
 */
package it.mapyou.controller.network;

import java.util.HashMap;

import org.json.JSONObject;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ServerInterface {


	public boolean close();
	
	public boolean open(String conn, String parameter);
	
	public JSONObject requestJson(String parameters,String url);
	
	public String request(String page,String parameters);
	
	public String setParameters(HashMap<String, String> parameters);
 
}
