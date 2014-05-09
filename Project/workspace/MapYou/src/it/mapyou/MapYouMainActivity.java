package it.mapyou;

import it.mapyou.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MapYouMainActivity extends Activity {


	private final String user="Peppe";
	private final String password="1234";
	private User userr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

	}

	public void test (View v){

		new ConnTest().execute();
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


			boolean isLogin=false;
			try {
				url= new URL("http://mapyou.altervista.org/myMapYou/test_user.php?user="+user+"&"+"password="+password);
				urlConn = (HttpURLConnection) url.openConnection();
				reader= new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

				while((line=reader.readLine()) !=null){
					if(!line.equalsIgnoreCase("NotUserRegister")){
						isLogin=true;
						try {
							jsonObject= new JSONObject(line);

							json= jsonObject.getJSONArray("User"); 
							Log.v("jsonObj",json.toString());

							for(int i=0; i<json.length(); i++){
								JSONObject o = json.getJSONObject(i); 
								userr=new User();
								userr.setNickname(o.getString("nickname"));	

							}
							
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
			return isLogin;
		}


		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if(result)
				Toast.makeText(getApplicationContext(), "Access -> "+userr.getNickname()+"", 7000).show();
			else
				Toast.makeText(getApplicationContext(), "User not Registred!", 7000).show();

		}

	}

}
