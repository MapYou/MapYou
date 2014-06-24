/**
 * 
 */
package it.mapyou.network;

import it.mapyou.util.SettingsNotificationServer;
import it.mapyou.util.SettingsServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Application;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NotificationServer extends Application  implements NotificationServerInterface {

	public static NotificationServer instance;

	public static NotificationServer getNotificationServer() {
		if(instance==null)
			instance = new NotificationServer();
		return instance;
	}

	@Override
	public String request(String page, String parameters) {

		StringBuilder response = new StringBuilder();
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection) new URL(SettingsNotificationServer.SERVER_URL+page).openConnection();
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
