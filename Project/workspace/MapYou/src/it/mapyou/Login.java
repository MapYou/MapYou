/**
 * 
 */
package it.mapyou;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.Server;
import it.mapyou.util.UrlUtility;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Login extends Activity {


	private DeviceController controller;
	private Server server;


	private EditText user;
	private EditText password;
	private String parameter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		user=(EditText) findViewById(R.id.user_login);
		password=(EditText) findViewById(R.id.user_password);
		server=Server.getServer();
	}

	public void login (View v){


		new ConnTest().execute();

	}

	class ConnTest extends AsyncTask<Void, Void, StringBuffer>{

		@Override
		protected StringBuffer doInBackground(Void... params) {

			parameter="?nickname="+user.getText().toString()+"&password="+password.getText().toString();


			StringBuffer b=isLogin(UrlUtility.LOGIN,parameter);


			return b;


		}


		@Override
		protected void onPostExecute(StringBuffer result) {
			super.onPostExecute(result);

			String isLogin= result.toString();
			if(isLogin.equalsIgnoreCase("true")){
				Toast.makeText(getApplicationContext(), "Accesso consentito", 4000).show();
			}
			Toast.makeText(getApplicationContext(), "Accesso non consentito", 4000).show();


		}

	}

	public StringBuffer isLogin (String urlPath, String parameters){
		URL url;
		StringBuffer response = new StringBuffer();
		HttpURLConnection urlConnection;
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
