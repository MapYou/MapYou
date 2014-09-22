/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.AbstractAsyncTask;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.util.EmailValidator;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Register extends MapyouActivity{

	private EditText nickname;
	private EditText password;
	private EditText confirmP;
	private EditText email;
	private Activity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		this.act = this;
		nickname= (EditText) findViewById(R.id.user_registration);
		password= (EditText) findViewById(R.id.user_password_registration);
		confirmP= (EditText) findViewById(R.id.userconfirm_password);
		email= (EditText) findViewById(R.id.user_email);

	}

	public void register (View v){

		if(verifyItems())
			new Thread(new Runnable() {
				@Override
				public void run() {
					new Registration(act).execute();

				}
			}).start();
	}



	class Registration extends AbstractAsyncTask<Void, Void, String>{
		/**
		 * @param act
		 */
		public Registration(Activity act) {
			super(act);
			// TODO Auto-generated constructor stub
		}


		private String b;

		@Override
		protected String doInBackground(Void... params) {
			try {
				parameters.put("nickname", URLEncoder.encode(nickname.getText().toString(), "UTF-8"));
				parameters.put("password",  URLEncoder.encode(password.getText().toString(), "UTF-8"));
				parameters.put("email", email.getText().toString());
				b=DeviceController.getInstance().getServer().
						request(SettingsServer.REGISTER_PAGE, DeviceController.getInstance().getServer().setParameters(parameters));

				return b;
			} catch (Exception e) {
				return null;
			}
		}


		@Override
		protected void newOnPostExecute(String result) {
			
			if(!result.toString().contains("-1")){
				UtilAndroid.makeToast(getApplicationContext(), "Registred", 5000);
				Intent intentMessage=new Intent();
				intentMessage.putExtra("user",nickname.getText().toString());
				intentMessage.putExtra("password",password.getText().toString());
				intentMessage.putExtra("email",email.getText().toString());
				setResult(2,intentMessage);
				finish();

			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Username already exists ", 5000);
			}
		}
	}

	public boolean verifyItems(){

		EmailValidator val= new EmailValidator();
		boolean correct=false;
		if(nickname.getText().toString().equalsIgnoreCase("") || 
				password.getText().toString().equalsIgnoreCase("") || 
				confirmP.getText().toString().equalsIgnoreCase("") || 
				email.getText().toString().equalsIgnoreCase("")){
			UtilAndroid.makeToast(getApplicationContext(), "The Fields cannot be empty", 4000);
			correct=false;
		}
		else if(nickname.getText().toString().length() >30 ||
				password.getText().toString().length() >30 ||
				confirmP.getText().toString().length() >30){
			UtilAndroid.makeToast(getApplicationContext(), "The Fields must be length 30 Chacarcters", 4000);
			correct=false;
		}
		else if( 
				password.getText().toString().compareTo(confirmP.getText().toString())!=0){
			UtilAndroid.makeToast(getApplicationContext(), "Password doesn't match", 4000);
			correct=false;
		}else if(!val.validate(email.getText().toString())){
			UtilAndroid.makeToast(getApplicationContext(), "Email is not ammissible", 4000);
			correct=false;
		}else
			correct=true;

		return correct;
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent=new Intent(Register.this,Login.class);  
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	 


}
