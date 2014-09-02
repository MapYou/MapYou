/**
 * 
 */
package it.mapyou.network;


import java.io.BufferedReader;
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

	private Server(){
		
	}

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
	public JSONObject requestJson(String urlPath,String parameters) {

		URL url;
		try {
			url = new URL(SettingsServer.SERVER_ADDRESS+urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream()); 
			wr.write(parameters); 
			wr.flush(); 

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine=null;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String r="";
			if(response.length()>0)
				r=response.toString().replaceFirst("\t", "").replaceFirst("\n", "").replaceFirst("\r", "");
			JSONObject jsonObject= new JSONObject(r);
			 

			return jsonObject;

		} catch (Exception e) {
			Log.v("Errorlogin", ""+e.getMessage());
			Log.v("exception class name", e.getClass().getSimpleName());
			if(e instanceof SocketTimeoutException){
				Log.v("socketTimeout", "connessione scaduta: "+e.getMessage());
			}
			return null;
		}
		finally{
			urlConnection.disconnect();
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
			wr.close();

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
		finally{
			urlConnection.disconnect();
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
