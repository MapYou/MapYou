package it.mapyou;

import it.mapyou.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MapYouMainActivity extends Activity {


	private String user="Peppe";
	private String password="1234";
	private User userr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		
//		Intent in = new Intent(this, TestOpenDb.class);
//		startActivity(in);

	}

	public void test (View v){
//		SQLiteDAOManager s = SQLiteDAOManager.getInstance(getApplicationContext());
//		s.connect();
//		s.getDb().execSQL("drop database "+DatabaseHelper.NAME_DB+";");
//		Toast.makeText(getApplicationContext(), s.getDb().getPath(), Toast.LENGTH_LONG).show();
		EditText nicknameTextView = (EditText) findViewById(R.id.nickname);
		EditText passwordTextView = (EditText) findViewById(R.id.password);
		user = nicknameTextView.getText().toString();
		password = passwordTextView.getText().toString();
		
		boolean isLogin=false;
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://mapyou.altervista.org/myMapYou/Dao/login.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("nickname", user));
			param.add(new BasicNameValuePair("password", password));
			post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
			HttpResponse response = client.execute(post);
			Log.w("http response", EntityUtils.toString(response.getEntity()));
			Toast.makeText(getApplicationContext(), response.getEntity().toString(), Toast.LENGTH_LONG).show();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.map_you_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class ConnTest extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {

			URL url=null;
			HttpURLConnection urlConn=null;
			BufferedReader reader=null;
			JSONObject jsonObject=null;
			JSONArray json=null;
			String line=null;
			
			EditText nicknameTextView = (EditText) findViewById(R.id.nickname);
			EditText passwordTextView = (EditText) findViewById(R.id.password);
			user = nicknameTextView.getText().toString();
			password = passwordTextView.getText().toString();
			
			boolean isLogin=false;
			
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://mapyou.altervista.org/myMapYou/Dao/login.php");
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("nickname", user));
				param.add(new BasicNameValuePair("password", password));
				post.setEntity(new UrlEncodedFormEntity(param));
				HttpResponse response = client.execute(post);
				Log.w("http response", response.getEntity().toString());
				Toast.makeText(getApplicationContext(), response.getEntity().toString(), Toast.LENGTH_LONG).show();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if(user!=null && password!=null && user.length()>0 && password.length()>0){
//				try {
//					url= new URL("http://mapyou.altervista.org/myMapYou/Dao/login.php?nickname="+user+"&"+"password="+password);
//					urlConn = (HttpURLConnection) url.openConnection();
//					reader= new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//					StringBuffer str = new StringBuffer();
//					while((line=reader.readLine()) !=null){
//						str.append(line);
////						if(!line.equalsIgnoreCase("NotUserRegister")){
////							isLogin=true;
////							try {
////								jsonObject= new JSONObject(line);
////
////								json= jsonObject.getJSONArray("User"); 
////								Log.v("jsonObj",json.toString());
////
////								for(int i=0; i<json.length(); i++){
////									JSONObject o = json.getJSONObject(i); 
////									userr=new User();
////									userr.setNickname(o.getString("nickname"));	
////
////								}
////								
////							}catch (Exception e) {
////								e.printStackTrace();
//////								Toast.makeText(getApplicationContext(), e.getMessage(), 10000).show();
////							}
////						}
//					}
//					
//					Toast.makeText(getApplicationContext(), str.toString(), Toast.LENGTH_LONG).show();
//				}catch (Exception e) {
//					e.printStackTrace();
////					Toast.makeText(getApplicationContext(), e.getMessage(), 10000).show();
////					return false;
//				}
//			}
//			else
//				return isLogin;
			return isLogin;
		}


		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if(result)
				Toast.makeText(getApplicationContext(), "Access -> "+user+"", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "User not Registred!", Toast.LENGTH_LONG).show();

		}

	}

}
