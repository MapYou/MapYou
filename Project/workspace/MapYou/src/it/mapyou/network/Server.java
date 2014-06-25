/**
 * 
 */
package it.mapyou.network;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Server implements ServerInterface {

	private static Server server;
	private HttpURLConnection urlConnection;
	private boolean isOpened;


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

		isOpened = true;
		return isOpened;

	}

	@Override
	public JSONArray request(String parameters,String urlPath,String jsonOb) {

		URL url;
		try {
			url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setConnectTimeout(10);
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
			//Log.v("jsonObj",json.toString());

			return json;

		} catch (Exception e) {
			Log.v("exception class name", e.getClass().getSimpleName());
			if(e instanceof SocketTimeoutException){
				Log.v("socketTimeout", "connessione scaduta: "+e.getMessage());
			}
			return null;
		}
	}

	@Override

	public String request(String page, String parameters) {

		StringBuilder response = new StringBuilder();

		try {
			urlConnection = (HttpURLConnection) new URL(SettingsServer.SERVER_ADDRESS+page).openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream()); 
			wr.write(parameters); 
			wr.flush(); 

			int responseCode = urlConnection.getResponseCode();

			if(responseCode == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine=null;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return response.toString();
		}

		return response.length()>0?
				response.toString().replaceFirst("\t", "").replaceFirst("\n", "").replaceFirst("\r", "")
				:response.toString();

	}

 
	@Override
	public String setParameters(HashMap<String, String> params) {
		
		Iterator<Entry<String, String>> iterator=params.entrySet().iterator();
		StringBuilder parameters= new StringBuilder();
		
		while(iterator.hasNext()){
			
			Entry<String, String> p= iterator.next();
			parameters.append(p.getKey()).append('=').append(p.getValue());
			
			if(iterator.hasNext())
				parameters.append('&');
		}
		return parameters.toString();
	}
}
