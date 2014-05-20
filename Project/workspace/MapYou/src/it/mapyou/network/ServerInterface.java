/**
 * 
 */
package it.mapyou.network;

import org.json.JSONArray;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ServerInterface<T extends Resource> {


	public boolean close();
	
	public boolean open(String conn, String parameter);
	
	public JSONArray request(String parameters,String url, String jsonObject);
	
	public String request(String page,String parameters);
}
