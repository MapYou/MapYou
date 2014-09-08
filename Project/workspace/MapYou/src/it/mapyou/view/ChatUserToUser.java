/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.User;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */



public class ChatUserToUser extends Activity{

	private User user;

	private EditText mess;
	private Activity act;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatusertouser);
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("user")){
			user = (User) b.getSerializable("user");
			if(user!=null){
				TextView n= (TextView) findViewById(R.id.textViewNickname);
				TextView e= (TextView) findViewById(R.id.textViewEmail);
				e.setText(user.getEmail());
				n.setText(user.getEmail());
			}
			act=this;
			mess=(EditText)findViewById(R.id.editTextChatUser);

		}
	}

	public void sendm (View v){

		String message=mess.getText().toString();

		if(message.length() >0)
			new SendMessage().execute("");
		else
			UtilAndroid.makeToast(act, "Please insert text message", 5000);

	}

	class SendMessage extends AsyncTask<String, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String response;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(getApplicationContext()))
				UtilAndroid.makeToast(getApplicationContext(), "Internet Connection not found", 5000);

		}


		@Override
		protected String doInBackground(String... params) {

			try {
				parameters.put("admin", Util.CURRENT_MAPME.getAdministrator().getNickname().toString());
				parameters.put("user", "");
				parameters.put("idm",  ""+Integer.parseInt(""+Util.CURRENT_MAPME.getModelID()));
				parameters.put("type",  "CHAT");
				parameters.put("message",  params[0]);
				parameters.put("title",  Util.CURRENT_MAPME.getAdministrator().getNickname().toString());
				parameters.put("notif",  "Messaggio da "+Util.CURRENT_MAPME.getAdministrator().getNickname().toString());

				response=DeviceController.getInstance().getServer().
						request(SettingsServer.CHAT, DeviceController.getInstance().getServer().setParameters(parameters));

				return response;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result.contains("send")){
				UtilAndroid.makeToast(getApplicationContext(), "The message have been send", 5000);
			}
			else if(result.contains("User not found")){
				UtilAndroid.makeToast(getApplicationContext(), "Risent message", 5000);
			}else
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
		}
	}

}
