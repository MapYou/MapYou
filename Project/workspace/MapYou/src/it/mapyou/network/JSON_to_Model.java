/**
 * 
 */
package it.mapyou.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class JSON_to_Model<T> {

	public final List<T> toModel(InputStream io, String jsonName) throws IOException{
		List<T> lis = new ArrayList<T>();
		JSONObject jsonObject=null;
		JSONArray json=null;
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(io));
		while((line=reader.readLine()) !=null){

			try {
				jsonObject= new JSONObject(line);

				json= jsonObject.getJSONArray(jsonName); 
				Log.v("jsonObj",json.toString());

				lis.addAll(getFromJSON(json));
				
			}catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return lis;
	}
	
	public abstract List<T> getFromJSON(JSONArray array);
}
