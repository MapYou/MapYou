/**
 * 
 */
package it.mapyou;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.network.Server;
import it.mapyou.util.UrlUtility;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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



	class ConnTest extends AsyncTask<Void, Void, String>{

		String parameter;

		@Override
		protected String doInBackground(Void... params) {


			try {
				parameter = "nickname=" + URLEncoder.encode(user.getText().toString(), "UTF-8") +
						"&password=" + URLEncoder.encode(password.getText().toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			String b=isLogin(UrlUtility.LOGIN,parameter);


			
			return b;


		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.v("Result", ""+result.length());
			Log.v("Result", ""+result);
			if(result.toString().equalsIgnoreCase("\ttrue")){
				Toast.makeText(getApplicationContext(), "Accesso consentito", 4000).show();
			}else{
				Toast.makeText(getApplicationContext(), "Accesso non consentito", 4000).show();


			}

		}
	}

	public String isLogin (String urlPath, String parameters){
		URL url;
		StringBuffer response = new StringBuffer();
		HttpURLConnection urlConnection;
		try {
			url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
//			urlConnection.setRequestProperty("Content-Type", 
//					"application/x-www-form-urlencoded");

//			urlConnection.setRequestProperty("Content-Length", "" + 
//					Integer.toString(parameters.getBytes().length));
//			urlConnection.setRequestProperty("Content-Language", "en-US");  

		//	urlConnection.setUseCaches (false);
			//urlConnection.setDoInput(true);
			//urlConnection.setDoOutput(true);

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
		return response.toString();
	}
}
