/**
 * 
 */
package it.mapyou.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
<<<<<<< HEAD
	private HttpURLConnection urlConnection;

=======
	private boolean isOpened;
	
>>>>>>> 3f3ebe31f9f9a330b89bf5d10db4106e4f5530f4
	private Server(){
	}
<<<<<<< HEAD

=======
	
	/**
	 * @return the isOpened
	 */
	public boolean isOpened() {
		return isOpened;
	}
	
	/**
	 * @return the server
	 */
>>>>>>> 3f3ebe31f9f9a330b89bf5d10db4106e4f5530f4
	public static Server getServer() {
		if(server==null)
			server = new Server();
		return server;
	}


	@Override
	public boolean close() {
<<<<<<< HEAD

		try {
			urlConnection.disconnect();
			return true;
		} catch (Exception e) {
			return false;
		}
=======
		// TODO Auto-generated method stub
		isOpened = false;
		return true;
>>>>>>> 3f3ebe31f9f9a330b89bf5d10db4106e4f5530f4
	}


	@Override
<<<<<<< HEAD
	public boolean open(String conn, String parameters) {
=======
	public boolean open() {
		// TODO Auto-generated method stub
		isOpened = true;
>>>>>>> 3f3ebe31f9f9a330b89bf5d10db4106e4f5530f4
		return true;
	}


	/* (non-Javadoc)
	 * @see it.mapyou.network.ServerInterface#request(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONArray request(String parameters,String urlPath,String jsonOb) {

		URL url;
		try {
			url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");

			// Send post request
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
	public StringBuffer request(String parameters, String urlPath) {
		URL url;
		StringBuffer response = new StringBuffer();
		try {
			url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");

			// Send post request
			urlConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			//int responseCode = urlConnection.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
		
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (Exception e) {
			return null;
		}
		return response;
	}
}
