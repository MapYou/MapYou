/**
 * 
 */
package it.mapyou.network;

import it.mapyou.util.Settings;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Server implements ServerInterface<JSON_Resource> {

	private static Server server;
	private HttpURLConnection urlConnection;
	private boolean isOpened;
	
 
	private Server(){
	}
 
	
	/**
	 * @return the isOpened
	 */
	public boolean isOpened() {
		return isOpened;
	}
	
	/**
	 * @return the server
	 */
	public static Server getServer() {
		if(server==null)
			server = new Server();
		return server;
	}


	@Override
	public boolean close() {
		try {
			urlConnection.disconnect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public boolean open(String conn, String parameters) {
<<<<<<< HEAD
=======
		// TODO Auto-generated method stub
>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
		isOpened = true;
		return isOpened;
	
	}
<<<<<<< HEAD
	
=======

	/* (non-Javadoc)
	 * @see it.mapyou.network.ServerInterface#request(java.lang.String, java.lang.String)
	 */
>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
	@Override
	public JSONArray request(String parameters,String urlPath,String jsonOb) {

		URL url;
		try {
			url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");

			urlConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject jsonObject= new JSONObject(response.toString());

			JSONArray json= jsonObject.getJSONArray(jsonOb); 
			Log.v("jsonObj",json.toString());

			return json;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
<<<<<<< HEAD
	public String request(String urlPath,String parameters) {
		
		URL url;
=======
	public String request(String page, String parameters) {

>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
		StringBuffer response = new StringBuffer();
		
		try {
			urlConnection = (HttpURLConnection) new URL(Settings.SERVER_ADDRESS+page).openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
<<<<<<< HEAD

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine=null;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
=======
			int responseCode = urlConnection.getResponseCode();

			if(responseCode == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine;
			
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
<<<<<<< HEAD
		return response.toString();
=======
		return response.toString().replaceFirst("\t", "").replaceFirst("\n", "").replaceFirst("\r", "");
>>>>>>> 2da9ce03c71eec28d676bf1c5e4f61a40af35892
	}
}
