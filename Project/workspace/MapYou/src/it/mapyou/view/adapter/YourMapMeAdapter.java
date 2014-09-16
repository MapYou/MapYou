package it.mapyou.view.adapter;
import it.mapyou.R;
import it.mapyou.controller.DeviceController;
import it.mapyou.model.MapMe;
import it.mapyou.network.SettingsServer;
import it.mapyou.util.UtilAndroid;
import it.mapyou.util.BitmapParser;
import it.mapyou.view.MapMeLayoutHome;
import it.mapyou.view.Util;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class YourMapMeAdapter extends BaseAdapter{


	private List<MapMe> mapme;
	private Activity act;
	private int userLogged;
	private MapMe m;
	private SharedPreferences sp;

	public YourMapMeAdapter(Activity act, List<MapMe> allmapme, int userLogged) {
		this.act=act;
		this.mapme=allmapme;
		this.userLogged = userLogged;
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

		ImageView icon=(ImageView) convertView.findViewById(R.id.admin_image);


		m = mapme.get(position);

		if(sp.getString("facebook", "")!=""){
			Bitmap b= BitmapParser.getThumbnail(act.getApplicationContext());
			icon.setImageBitmap(b);
		}
		else;

		admin.setText("Admin: "+m.getAdministrator().getNickname());
		name.setText(m.getName());
		sa.setText(m.getSegment().getStartPoint().getLocation());
		ea.setText(m.getSegment().getEndPoint().getLocation());
		numusr.setText(String.valueOf(
				String.valueOf(m.getNumUsers())+" / "+String.valueOf(m.getMaxNumUsers())));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final MapMe mp = mapme.get(position);
				if(mp.getAdministrator().getModelID()==userLogged){
					AlertDialog	alert2= new AlertDialog.Builder(act).create();
					alert2.setTitle("Choose option");
					alert2.setMessage("You can delete this mapme or view it.");
					alert2.setIcon(R.drawable.ic_launcher);
					alert2.setButton("Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					alert2.setButton3("Delete", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							deleteMapme(mp);
						}
					});
					alert2.setButton2("Enter", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Util.CURRENT_MAPME = mp;
							Intent i = new Intent(act, MapMeLayoutHome.class);
							i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
							act.startActivity(i);
						}
					});
					
					alert2.show();

				}else{
					Util.CURRENT_MAPME = mp;
					Intent i = new Intent(act, MapMeLayoutHome.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					act.startActivity(i);	
				}	
			}
		});
		return convertView;
	}
	
	private void deleteMapme(final MapMe m){
		AlertDialog	alert2= new AlertDialog.Builder(act).create();
		alert2.setTitle("Delete mapme");
		alert2.setMessage("Are you sure to delete this mapme?");
		alert2.setIcon(R.drawable.ic_launcher);
		alert2.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert2.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DeleteMapme(m.getModelID()).execute();
			}
		});
		
		alert2.show();
	}
	
	class DeleteMapme extends AsyncTask<Void, Void, String>{


		private HashMap<String, String> parameters=new HashMap<String, String>();
		private ProgressDialog p;
		private int mapmeId;
		
		public DeleteMapme(int mapmeId) {
			this.mapmeId = mapmeId;
		}

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
		protected String doInBackground(Void... params) {

			try {
				parameters.put("idu", String.valueOf(userLogged));
				parameters.put("idm", String.valueOf(mapmeId));
				String response=DeviceController.getInstance().getServer().
						request(SettingsServer.DELETE_MAPME, DeviceController.getInstance().getServer().setParameters(parameters));
				return response;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			p.dismiss();
			if(result==null){
				UtilAndroid.makeToast(act, "Please refresh....", 5000);
			}else{
				if(result.contains("1")){
					for(int i=0; i<mapme.size(); i++)
						if(mapme.get(i).getModelID()==mapmeId)
							{
							mapme.remove(i);
							break;
							}
					
					notifyDataSetChanged();
				}else
					UtilAndroid.makeToast(act, "Erro while deleting mapme.", 5000);
			}
		}

	}
}

