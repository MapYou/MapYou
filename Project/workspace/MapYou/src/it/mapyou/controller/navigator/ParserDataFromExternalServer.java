/**
 * 
 */
package it.mapyou.controller.navigator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class ParserDataFromExternalServer{

	public final String retrieveData (String urll){

		URL url;
		HttpURLConnection urlconn = null;
		InputStream stream=null;
		JSONObject obj = null;
		StringBuffer b= new StringBuffer();
		try {
			url = new URL(urll);
			urlconn= (HttpURLConnection) url.openConnection();
			urlconn.connect();

			stream=urlconn.getInputStream();
			BufferedReader buff= new BufferedReader(new InputStreamReader(stream));
			String line=null;
			while((line=buff.readLine()) !=null){
				b.append(line);
			}
			buff.close();
			obj= new JSONObject(b.toString());
		}catch(Exception e){

		}finally{
			try {
				stream.close();
				urlconn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return obj.toString();
	}




}
