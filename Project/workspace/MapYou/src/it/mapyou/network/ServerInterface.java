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
	
<<<<<<< HEAD
	public String request(String parameters,String url);
=======
	public String request(String page,String parameters);
>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
}
