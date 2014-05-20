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
		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void login (View v){

		new ConnTest().execute();

	}

	class ConnTest extends AsyncTask<Void, Void, String>{

		String parameter;
		String b;

		@Override
		protected String doInBackground(Void... params) {


			try {
				parameter = "nickname=" + URLEncoder.encode(user.getText().toString(), "UTF-8") + "&password=" + URLEncoder.encode(password.getText().toString(), "UTF-8");
				b=controller.getServer().request(UrlUtility.LOGIN, parameter);
				return b;


			} catch (Exception e) {
				return null;
			}


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
}
