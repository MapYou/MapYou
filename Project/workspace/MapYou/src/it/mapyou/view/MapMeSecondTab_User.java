/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMeSecondTab_User extends Activity {

	private MapMe mapme;
	private List<Mapping> mapping, allMapping;
	private Dialog sendDialog;
	private static final int SEND_DIALOG = 1;
	private Activity act;
	private View sendView;
	private EditText ed;
	private AdapterUsersMapMe adapter;
	private GridView gridview;
	private DeviceController controller;
 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme_second_tab);
		act = this;
 
		gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		mapme = (MapMe) getIntent().getExtras().get("mapme");

		controller= new DeviceController();
		try {
			controller.init(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		mapping = mapme.getMapping();
		//adapter = new AdapterUsersMapMe(mapping);
		gridview.setAdapter(adapter);
 
		//r = new Random();

		mapme = (MapMe) getIntent().getExtras().get("mapme");
		mapping = mapme.getDistinctMapping();
		allMapping = mapme.getMapping();
		adapter = new AdapterUsersMapMe(this, mapping);
		gridview = (GridView) findViewById(R.id.gridViewMapMeUsers);
		gridview.setOnItemClickListener(new OnClickUsersMapMe(act, mapping, mapme));

		gridview.setAdapter(adapter);
		Button send = (Button) findViewById(R.id.buttonSendPartecipation);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(SEND_DIALOG);
			}
		});

		sendDialog();


	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SEND_DIALOG:
			return sendDialog;

		default:
			return null;
		}
	}

	private void sendDialog(){

 
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Insert nickname");
		builder.setCancelable(true);
		LayoutInflater inflater = act.getLayoutInflater();
		sendView = inflater.inflate(R.layout.send_partecipation_dialog, null);
		ed = (EditText)sendView.findViewById(R.id.editTextNickname);
		builder.setView(sendView);
		builder.setPositiveButton("Send invite", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				final String nickname = ed.getText().toString();
				if(nickname!=null && nickname.length()>0){
					if(!nickname.equalsIgnoreCase(mapme.getAdministrator().getNickname())){
						new Thread(new Runnable() {

							@Override
							public void run() {

								new DownloadUser().execute(nickname);

							}
						}).start();

					}else
						UtilAndroid.makeToast(getApplicationContext(), "Non puoi invitare te stesso", 5000);
				}else
					UtilAndroid.makeToast(getApplicationContext(), "Please insert nickname", 5000);
				ed.setText("");

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		sendDialog = builder.create();
	}

 
//		if(sendDialog==null){
//			Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("Insert nickname");
//			builder.setCancelable(true);
//			LayoutInflater inflater = act.getLayoutInflater();
//			sendView = inflater.inflate(R.layout.send_partecipation_dialog, null);
//			ed = (EditText)sendView.findViewById(R.id.editTextNickname);
//			builder.setView(sendView);
//			builder.setPositiveButton("Send invite", new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int whichButton) {
//					String t = ed.getText().toString();
//					if(t!=null && t.length()>0){
//						Toast.makeText(getApplicationContext(), "Invito corretto", 4000).show();
//						User u = new User(t, "email@email.com");
//						Mapping m = new Mapping();
//						m.setUser(u);
//						m.setLatitude(45.4640704);
//						m.setLongitude(7.6700892);
//						//						mapping.add(m);
//						adapter.addItem(m);
//					}else
//						Toast.makeText(getApplicationContext(), "Please insert correct nickname.", 4000).show();
//					ed.setText("");
//
//				}
//			});
//			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//				}
//			});
//			sendDialog = builder.create();
//		}
//>>>>>>> origin/master

	class DownloadUser extends AsyncTask<String, Void, String>{

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
				parameters.put("nickinvite", mapme.getAdministrator().getNickname().toString());
				parameters.put("nickinvited", URLEncoder.encode(params[0].toString(), "UTF-8"));
				parameters.put("idm",  ""+Integer.parseInt(""+mapme.getIdmapme()));
				response=controller.getServer().request(SettingsServer.SEND_PARTECIPATION, controller.getServer().setParameters(parameters));

				return response;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if(result.contains("oke")){
				UtilAndroid.makeToast(getApplicationContext(), "invite for "+mapme.getName()+" has been send!", 5000);
			}else{
				UtilAndroid.makeToast(getApplicationContext(), "Error Send!", 5000);
			}
		}
	}
 
}
