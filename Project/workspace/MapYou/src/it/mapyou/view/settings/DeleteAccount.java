/**
 * 
 */
package it.mapyou.view.settings;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.controller.network.AbstractAsyncTask;
import it.mapyou.controller.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.Login;
import it.mapyou.view.Setting;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeleteAccount extends Setting{

	private SharedPreferences sp;
	private EditText ed;
	
	/**
	 * @param act
	 * @param idView
	 */
	public DeleteAccount(Activity act, int idView) {
		super(act, idView);
		sp=PreferenceManager.getDefaultSharedPreferences(act);
	}

	@Override
	public void performeSetting() {
		String userLogged=sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, "");

		if(userLogged!=""){
			confirmPassword();
		}else
			UtilAndroid.makeToast(act, "Please log-in", 5000);
	}
	
	private void confirmPassword(){
		Builder builder = new AlertDialog.Builder(act);
		builder.setTitle("Help!");
		builder.setCancelable(true);
		LayoutInflater inflater = act.getLayoutInflater();
		View sendView = inflater.inflate(R.layout.send_partecipation_dialog, null);
		ed = (EditText)sendView.findViewById(R.id.editTextNickname);
		ed.setHint("Please insert your email.");
		builder.setView(sendView);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String nickname = ed.getText().toString();
				if(nickname.equals(sp.getString(UtilAndroid.KEY_EMAIL_USER_LOGGED, "")))
						delete(sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				else{
					dialog.dismiss();
					UtilAndroid.makeToast(act, "Email not correct", 2000);
					
				}
					
			}
		});
		builder.create().show();
	}
	
	private void delete(final String userLogged){
		AlertDialog	alert2= new AlertDialog.Builder(act).create();
		alert2.setTitle("Confirm your choose..");
		alert2.setMessage("Are sure you want to delete this account?");
		alert2.setIcon(R.drawable.ic_launcher);
		alert2.setButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert2.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DeleteAccountTask(act).execute(userLogged.toString());
			}
		});
		alert2.show();
	}

	class DeleteAccountTask extends AbstractAsyncTask<String, Void, String>{

		/**
		 * @param act
		 */
		public DeleteAccountTask(Activity act) {
			super(act);
			// TODO Auto-generated constructor stub
		}


		@Override
		protected String doInBackground(String... params) {

			String s="";
			parameters.put("admin", ""+params[0].toString());
			s=DeviceController.getInstance().getServer().request(SettingsServer.DELETE_ACCOUNT, DeviceController.getInstance().getServer().setParameters(parameters));

			return s;
		}


		@Override
		protected void newOnPostExecute(String result) {

			if(result.contains("success_without_mapme") || result.contains("total_success")){
				UtilAndroid.makeToast(act, "Account deleted", 5000);
				Intent i = new Intent(act, Login.class);
				sp.edit().clear().commit();
				act.startActivity(i);
			}else{
				UtilAndroid.makeToast(act, "Account not deleted...Please log in", 5000);
				Intent i = new Intent(act, Login.class);
				act.startActivity(i);
			}
		}

	}

}
