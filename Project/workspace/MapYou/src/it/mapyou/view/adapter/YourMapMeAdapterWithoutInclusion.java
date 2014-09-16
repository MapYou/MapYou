package it.mapyou.view.adapter;
import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class YourMapMeAdapterWithoutInclusion extends BaseAdapter{


	private List<MapMe> mapme;
	private Activity act;
	private SharedPreferences sp;


	public YourMapMeAdapterWithoutInclusion(Activity act, List<MapMe> allmapme) {
		this.act=act;
		this.mapme=allmapme;

		sp=PreferenceManager.getDefaultSharedPreferences(act);

	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapme.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mapme.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(parent.getContext(), R.layout.mapme_list_row, null);
		}

		TextView admin = (TextView) convertView.findViewById(R.id.admin);
		TextView numusr = (TextView) convertView.findViewById(R.id.num_users);
		TextView name = (TextView) convertView.findViewById(R.id.mapmename);
		TextView sa = (TextView) convertView.findViewById(R.id.textViewSA);
		TextView ea = (TextView) convertView.findViewById(R.id.textViewEA);
		
		MapMe m = mapme.get(position);

		admin.setText("Admin: "+m.getAdministrator().getNickname());
		name.setText(m.getName());
		sa.setText(m.getSegment().getStartPoint().getLocation());
		ea.setText(m.getSegment().getEndPoint().getLocation());
		numusr.setText(String.valueOf(
				String.valueOf(m.getNumUsers())+" / "+String.valueOf(m.getMaxNumUsers())));

		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final MapMe m = mapme.get(position);
				AlertDialog	alert2= new AlertDialog.Builder(act).create();
				alert2.setTitle("Request to partecipate");
				alert2.setMessage("Do you want send a request to partecipate "
						+ "to '"+m.getAdministrator().getNickname()+"' for mapme \""+m.getName()+"\"");
				alert2.setIcon(R.drawable.ic_launcher);
				alert2.setButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				alert2.setButton2("Send request", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						new RequestPartecipation().execute(m);
					}
				});

				alert2.show();
			}
		});
		return convertView;
	}
	
	class RequestPartecipation extends AsyncTask<MapMe, Void, String>{

		private HashMap<String, String> parameters=new HashMap<String, String>();
		private String response;
		private ProgressDialog p;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!UtilAndroid.findConnection(act))
				UtilAndroid.makeToast(act, "Internet Connection not found", 5000);

			else{
				p = new ProgressDialog(act);
				p.setMessage("Loading...");
				p.setIndeterminate(false);
				p.setCancelable(false);
				p.show();
			}
		}


		@Override
		protected String doInBackground(MapMe... params) {

			try {
				parameters.put("nickinvite", sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("nickinvited", params[0].getAdministrator().getNickname().toString());
				parameters.put("idm",  ""+Integer.parseInt(""+params[0].getModelID()));
				parameters.put("type",  "REQUEST");
				parameters.put("message",  "You have received a request to partecipate by "+
						sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));
				parameters.put("title",  "MapYou: request to partecipate");
				parameters.put("notif",  "You have received a request to partecipate by "+
						sp.getString(UtilAndroid.KEY_NICKNAME_USER_LOGGED, ""));

				response=DeviceController.getInstance().getServer().
						request(SettingsServer.SEND_PARTECIPATION, DeviceController.getInstance().getServer().setParameters(parameters));

				return response;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			p.dismiss();
			if(result.contains("send")){
				UtilAndroid.makeToast(act, "Request has been sended!", 5000);
			}
			else if(result.contains("User not found")){
				UtilAndroid.makeToast(act, "This user is not registered on MapYou.", 5000);
			}else if(result.contains("error")){
				UtilAndroid.makeToast(act, "Waiting for response from administrator.", 5000);
			}else
				UtilAndroid.makeToast(act, "Error Send!", 5000);
		}
	}
}

